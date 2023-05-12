package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.LoginException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.security.PasswordEncoder;
import cn.qkmango.ccms.common.util.UserSession;
import cn.qkmango.ccms.common.validate.group.Query;
import cn.qkmango.ccms.domain.bind.PermissionType;
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
        Account loginAccount = null;

        //判断用户类型
        switch (account.getPermissionType()) {
            case pos -> loginAccount = dao.loginPos(account);
            case user -> loginAccount = dao.loginUser(account);
            case admin -> loginAccount = dao.loginAdmin(account);
        }

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
        loginAccount.setPermissionType(account.getPermissionType());
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
        PermissionType type = param.getPermissionType();
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
     * @param account 用户ID
     * @return 用户信息
     */
    @Override
    public AccountInfoVO accountInfo(Account account) {
        if (account.getPermissionType() == PermissionType.admin) {
            return dao.adminAccountInfo(account.getId());
        } else if (account.getPermissionType() == PermissionType.user) {
            return dao.userAccountInfo(account.getId());
        }
        return null;
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
                account.getPermissionType(),
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
     * 同组用户列表
     *
     * @return 同组用户列表
     */
    @Override
    public List<Account> groupUser() {
        String id = UserSession.getAccountId();
        return dao.groupUser(id);
    }
}
