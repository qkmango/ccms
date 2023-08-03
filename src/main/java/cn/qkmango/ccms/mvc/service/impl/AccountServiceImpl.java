package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.cache.captcha.DefaultCaptchaCache;
import cn.qkmango.ccms.common.cache.security.SecurityCache;
import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.exception.permission.LoginException;
import cn.qkmango.ccms.common.util.SnowFlake;
import cn.qkmango.ccms.domain.bind.AccountState;
import cn.qkmango.ccms.domain.bind.CardState;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.bo.OpenPlatformBo;
import cn.qkmango.ccms.domain.dto.AccountInsertDto;
import cn.qkmango.ccms.domain.dto.UpdatePasswordDto;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.entity.Department;
import cn.qkmango.ccms.domain.entity.User;
import cn.qkmango.ccms.domain.pagination.PageData;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.AccountDetailVO;
import cn.qkmango.ccms.mvc.dao.AccountDao;
import cn.qkmango.ccms.mvc.dao.CardDao;
import cn.qkmango.ccms.mvc.dao.UserDao;
import cn.qkmango.ccms.mvc.service.AccountService;
import cn.qkmango.ccms.mvc.service.DepartmentService;
import cn.qkmango.ccms.security.encoder.PasswordEncoder;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * 账户
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 14:59
 */
@Service
public class AccountServiceImpl implements AccountService {


    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private AccountDao dao;

    @Resource
    private UserDao userDao;

    @Resource
    private CardDao cardDao;

    @Resource
    private DepartmentService departmentService;

    @Resource(name = "authAccessCodeCache")
    public SecurityCache authAccessCodeCache;

    @Resource(name = "captchaCache")
    private DefaultCaptchaCache captchaCache;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private StringRedisTemplate srt;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Account getRecordById(Integer id) {
        return dao.getRecordById(id);
    }

    @Override
    public Account getRecordById(Integer id, boolean password) {
        return dao.getRecordById(id, password);
    }

    /**
     * 登陆接口
     *
     * @param account 账户
     * @return 登陆成功返回登陆用户信息
     * @throws LoginException 登陆异常
     */
    @Override
    public Account systemLogin(Account account) throws LoginException {

        Account loginAccount = dao.getRecordById(account.getId(), true);

        //检查账户状态
        checkLoginAccount(loginAccount);

        //判断密码是否正确
        String dbPassword = loginAccount.getPassword();
        boolean matches = passwordEncoder.matches(account.getPassword(), dbPassword);
        if (!matches) {
            throw new LoginException(messageSource.getMessage("response.login.password.error", null, LocaleContextHolder.getLocale()));
        }

        //清除密码
        loginAccount.setPassword(null);

        //返回登陆用户信息，创建 token 由 controller 层完成
        return loginAccount;
    }


    @Override
    public Account accessLogin(String accessCode) throws LoginException {
        //从 redis 中获取 accessCode 对应的账户ID
        String value = authAccessCodeCache.get(accessCode);
        //删除 accessCode
        authAccessCodeCache.delete(accessCode);

        //判断 accessCode 是否存在/是否过期
        if (value == null) {
            throw new LoginException(messageSource.getMessage("response.login.access-code.not.exist", null, LocaleContextHolder.getLocale()));
        }

        //将 value 转换为 OpenPlatformBo 对象
        OpenPlatformBo platform = JSON.parseObject(value, OpenPlatformBo.class);

        //查询数据库
        Account loginAccount = dao.getRecordById(platform.getAccount(), false);
        //检查账户状态
        checkLoginAccount(loginAccount);

        return loginAccount;
    }

    /**
     * 修改密码
     *
     * @param dto 新的密码
     * @throws UpdateException 修改失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePassword(UpdatePasswordDto dto) throws UpdateException {

        Account account = dao.getRecordById(dto.getAccount(), true);

        //判断输入的旧密码和数据库的旧密码是否一致
        String oldBCryptPassword = account.getPassword();
        if (!passwordEncoder.matches(dto.getOldPassword(), oldBCryptPassword)) {
            throw new UpdateException(messageSource.getMessage("db.update.password.failure@different", null, LocaleContextHolder.getLocale()));
        }

        //加密新密码
        String newBCryptPassword = passwordEncoder.encode(dto.getNewPassword());

        //更新密码
        Account updateAccount = new Account()
                .setId(dto.getAccount())
                .setPassword(newBCryptPassword);
        int affectedRows = dao.update(updateAccount);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.update.password.failure", null, LocaleContextHolder.getLocale()));
        }
    }

    /**
     * 重置密码
     * 默认密码为123456
     *
     * @param account 账户ID
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void resetPassword(Integer account) throws UpdateException {
        Account updateAccount = new Account();
        updateAccount.setId(account);
        updateAccount.setPassword(passwordEncoder.encode("123456"));

        int affectedRows = dao.update(updateAccount);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.account.update.failure", null, LocaleContextHolder.getLocale()));
        }
    }

    /**
     * 获取用户信息
     *
     * @param accountId 用户ID
     * @return 用户信息
     */
    @Override
    public AccountDetailVO accountDetail(Integer accountId) {
        Account accountRecord;
        Card cardRecord = null;
        User userRecord = null;
        LinkedList<Department> departmentChain;

        //获取账户信息
        accountRecord = dao.getRecordById(accountId);
        //判断账户是否存在
        if (accountRecord == null) {
            return null;
        }
        //获取部门链
        departmentChain = departmentService.departmentChain(accountRecord.getDepartment());

        //获取 卡信息/用户信息, 如果是user角色才有卡信息
        if (accountRecord.getRole() == Role.user) {
            cardRecord = cardDao.getRecordByAccount(accountId);
            userRecord = userDao.getRecordByAccount(accountId);
        }

        AccountDetailVO vo = new AccountDetailVO();
        vo.setAccount(accountRecord);
        vo.setUser(userRecord);
        vo.setCard(cardRecord);
        vo.setDepartmentChain(departmentChain);

        return vo;
    }

    /**
     * 更新用户email
     *
     * @param account 账户
     * @param captcha 验证码
     * @param email   新的email
     * @throws UpdateException 更新异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateEmail(Integer account, String email, String captcha) throws UpdateException {
        // 验证码校验
        boolean check = captchaCache.check(new String[]{account.toString(), email}, captcha);

        if (!check) {
            throw new UpdateException(messageSource.getMessage("response.captcha.valid.failure", null, LocaleContextHolder.getLocale()));
        }

        //更新email
        int affectedRows = dao.updateEmail(account, email);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.account.update.email.failure", null, LocaleContextHolder.getLocale()));
        }
    }


    /**
     * 注销账户
     *
     * @param accountId 账户 ID
     * @throws UpdateException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void canceled(Integer accountId) throws UpdateException {

        Card card = cardDao.getRecordByAccount(accountId);
        // 卡不存在
        if (card == null) {
            throw new UpdateException(messageSource.getMessage("db.card.failure@notExist", null, LocaleContextHolder.getLocale()));
        }

        // 判断金额是否为0
        if (card.getBalance() != 0) {
            throw new UpdateException(messageSource.getMessage("db.account.canceled.failure@balance", null, LocaleContextHolder.getLocale()));
        }

        // 获取账户信息
        Account account = dao.getRecordById(accountId);

        //判断是否已经注销
        if (AccountState.canceled == account.getState()) {
            throw new UpdateException(messageSource.getMessage("db.account.canceled.failure@canceled", null, LocaleContextHolder.getLocale()));
        }


        //注销账户
        int affectedRows;
        // 1. 将账户状态改为注销
        Account updateAccount = new Account();
        updateAccount.setId(accountId);
        updateAccount.setState(AccountState.canceled);
        affectedRows = dao.update(updateAccount);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.account.canceled.failure", null, LocaleContextHolder.getLocale()));
        }

        // 2. 将账户下的卡片状态改为注销
        affectedRows = cardDao.state(accountId, CardState.canceled);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.account.canceled.failure", null, LocaleContextHolder.getLocale()));
        }
    }


    @Override
    public PageData<Account> list(Pagination<Account> pagination) {
        List<Account> list = dao.list(pagination);
        int count = dao.count();
        PageData<Account> page = PageData.of(list, count);
        return page;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(AccountInsertDto param) throws InsertException {
        int affectedRows;

        // 1. 判断是否已经存在
        Account dbRecord = dao.getRecordById(param.getId());
        if (dbRecord != null) {
            throw new InsertException(messageSource.getMessage("db.account.insert.failure@exist", null, LocaleContextHolder.getLocale()));
        }

        // 2. 添加 account 账户
        Account account = new Account()
                .setId(param.getId())
                .setRole(param.getRole())
                .setState(param.getAccountState())
                .setDepartment(param.getDepartment())
                .setPassword(passwordEncoder.encode("123456"));

        affectedRows = dao.insert(account);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.account.insert.failure", null, LocaleContextHolder.getLocale()));
        }

        //如果不是user角色, 则不需要添加 card 和 user
        if (param.getRole() != Role.user) {
            return;
        }

        // 3. 添加 card 卡片
        long cardId = snowFlake.nextId();
        Card card = new Card()
                .setId(Long.toString(cardId))
                .setAccount(param.getId().toString())
                .setBalance(0)
                .setState(CardState.normal);
        affectedRows = cardDao.insert(card);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.account.insert.failure", null, LocaleContextHolder.getLocale()));
        }

        // 4. 添加 user 用户
        long userId = snowFlake.nextId();
        User user = new User()
                .setId(userId)
                .setAccount(param.getId())
                .setCard(cardId)
                .setName(param.getName());
        affectedRows = userDao.insert(user);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.account.insert.failure", null, LocaleContextHolder.getLocale()));
        }
    }


    /**
     * 检查登录账户
     * 1. 判断账户是否存在
     * 2. 判断账户状态
     *
     * @param account
     */
    private void checkLoginAccount(Account account) {
        //判断账户是否存在
        if (account == null) {
            throw new LoginException(messageSource.getMessage("db.account.failure@notExist", null, LocaleContextHolder.getLocale()));
        }

        //判断账户状态
        switch (account.getState()) {
            case canceled ->
                    throw new LoginException(messageSource.getMessage("db.account.canceled.failure@canceled", null, LocaleContextHolder.getLocale()));
            case frozen ->
                    throw new LoginException(messageSource.getMessage("db.account.canceled.failure@frozen", null, LocaleContextHolder.getLocale()));
        }
    }
}
