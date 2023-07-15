package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.LoginException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.util.SnowFlake;
import cn.qkmango.ccms.domain.bind.AccountState;
import cn.qkmango.ccms.domain.bind.CardState;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.entity.Department;
import cn.qkmango.ccms.domain.entity.User;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.AccountInsertParam;
import cn.qkmango.ccms.mvc.dao.CardDao;
import cn.qkmango.ccms.mvc.dao.UserDao;
import cn.qkmango.ccms.mvc.service.DepartmentService;
import cn.qkmango.ccms.security.encoder.PasswordEncoder;
import cn.qkmango.ccms.common.validate.group.Query;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.param.UpdatePasswordParam;
import cn.qkmango.ccms.domain.vo.AccountInfoVO;
import cn.qkmango.ccms.mvc.dao.AccountDao;
import cn.qkmango.ccms.mvc.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private StringRedisTemplate srt;

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 登陆接口
     *
     * @param account 账户
     * @param locale  语言环境
     * @return 登陆成功返回登陆用户信息
     * @throws LoginException 登陆异常
     */
    @Override
    public Account login(@Validated(Query.Login.class) Account account, Locale locale) throws LoginException {

        Account loginAccount = dao.login(account);

        //判断用户是否存在
        if (loginAccount == null) {
            throw new LoginException(messageSource.getMessage("db.account.notExist", null, locale));
        }

        //判断密码是否正确
        String dbPassword = loginAccount.getPassword();
        boolean matches = passwordEncoder.matches(account.getPassword(), dbPassword);
        if (!matches) {
            throw new LoginException(messageSource.getMessage("response.login.failure", null, locale));
        }

        //清除密码
        loginAccount.setPassword(null);

        //将登陆用户信息存入session
        loginAccount.setRole(account.getRole());
        return loginAccount;
    }


    /**
     * 修改密码
     *
     * @param param  新的密码和
     * @param locale 语言环境
     * @throws UpdateException 修改失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePassword(UpdatePasswordParam param, Locale locale) throws UpdateException {

        String id = param.getId();
        String oldRawPassword = param.getOldPassword();
        Role type = param.getRole();
        String newRawPassword = param.getNewPassword();

        //判断输入的旧密码和数据库的旧密码是否一致
        String oldBCryptPassword = dao.getAccountPassword(id, type);
        if (!passwordEncoder.matches(oldRawPassword, oldBCryptPassword)) {
            throw new UpdateException(messageSource.getMessage("db.update.password.failure@different", null, locale));
        }

        //加密新密码
        String newBCryptPassword = passwordEncoder.encode(newRawPassword);

        //更新密码
        int affectedRows = dao.updatePassword(id, newBCryptPassword, type);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.update.password.failure", null, locale));
        }
    }

    /**
     * 获取用户信息
     *
     * @param accountId 用户ID
     * @return 用户信息
     */
    @Override
    public AccountInfoVO accountInfo(String accountId) {
        Account accountRecord = null;
        Card cardRecord = null;
        User userRecord = null;
        LinkedList<Department> departmentChain = null;

        //获取账户信息
        accountRecord = dao.getRecordById(accountId);
        //获取部门链
        departmentChain = departmentService.departmentChain(accountRecord.getDepartment());

        //获取 卡信息/用户信息, 如果是user角色才有卡信息
        if (accountRecord.getRole() == Role.user) {
            cardRecord = cardDao.getRecordByAccount(accountId);
            userRecord = userDao.getRecordByAccount(accountId);
        }

        AccountInfoVO vo = new AccountInfoVO();
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
     * @param locale  语言环境
     * @throws UpdateException 更新异常
     */
    @Override
    public void updateEmail(Account account, String email, String captcha, Locale locale) throws UpdateException {
        // 生成redis key
        String key = String.format("captcha:change:email:%s:%s:%s",
                account.getRole(),
                account.getId(), email);

        // 获取redis中的验证码
        String redisCaptcha = srt.opsForValue().get(key);
        boolean equals = captcha.equals(redisCaptcha);
        if (!equals) {
            throw new UpdateException(messageSource.getMessage("response.captcha.valid.failure", null, locale));
        }

        //更新email
        int affectedRows = dao.updateEmail(account, email);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.update.email.failure", null, locale));
        }
        // 删除redis中的验证码
        srt.delete(key);
    }


    /**
     * 注销账户
     *
     * @param accountId 账户 ID
     * @param locale    语言环境
     * @throws UpdateException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void canceled(String accountId, Locale locale) throws UpdateException {

        Card card = cardDao.getRecordByAccount(accountId);
        // 账户不存在
        if (card != null) {
            throw new UpdateException(messageSource.getMessage("db.account.canceled.failure", null, locale));
        }

        // 判断金额是否为0
        if (card.getBalance() != 0) {
            throw new UpdateException(messageSource.getMessage("db.account.canceled.failure@balance", null, locale));
        }

        // 获取账户信息
        Account account = dao.getRecordById(accountId);

        //判断是否已经注销
        if (AccountState.canceled == account.getState()) {
            throw new UpdateException(messageSource.getMessage("db.account.canceled.failure@canceled", null, locale));
        }


        //注销账户
        int affectedRows = 0;
        // 1. 将账户状态改为注销
        Account updateAccount = new Account();
        updateAccount.setId(accountId);
        updateAccount.setState(AccountState.canceled);
        affectedRows = dao.update(updateAccount);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.account.canceled.failure", null, locale));
        }

        // 2. 将账户下的卡片状态改为注销
        affectedRows = cardDao.updateState(accountId, CardState.canceled);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.account.canceled.failure", null, locale));
        }
    }

    /**
     * 重置密码
     * 默认密码为123456
     *
     * @param account 账户ID
     * @param locale
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void resetPassword(String account, Locale locale) throws UpdateException {
        Account updateAccount = new Account();
        updateAccount.setId(account);
        updateAccount.setPassword(passwordEncoder.encode("123456"));

        int affectedRows = dao.update(updateAccount);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.account.update.failure", null, locale));
        }
    }

    @Override
    public R<List<Account>> list(Pagination<Account> pagination) {
        List<Account> list = dao.list(pagination);
        int count = dao.count();
        return R.success(list).setCount(count);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(AccountInsertParam param, Locale locale) throws InsertException {
        int affectedRows = 0;
        // 1. 添加 account 账户
        Account account = new Account()
                .setId(param.getId().toString())
                .setRole(param.getRole())
                .setState(param.getAccountState())
                .setDepartment(param.getDepartment())
                .setPassword(passwordEncoder.encode("123456"));

        affectedRows = dao.insert(account);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.account.insert.failure", null, locale));
        }

        //如果不是user角色, 则不需要添加 card 和 user
        if (param.getRole() != Role.user) {
            return;
        }

        // 2. 添加 card 卡片
        long cardId = snowFlake.nextId();
        Card card = new Card()
                .setId(Long.toString(cardId))
                .setAccount(param.getId().toString())
                .setBalance(0)
                .setState(CardState.normal);
        affectedRows = cardDao.insert(card);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.account.insert.failure", null, locale));
        }

        // 3. 添加 user 用户
        long userId = snowFlake.nextId();
        User user = new User()
                .setId(Long.toString(userId))
                .setAccount(param.getId())
                .setCard(cardId)
                .setName(param.getName());
        affectedRows = userDao.insert(user);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.account.insert.failure", null, locale));
        }
    }
}
