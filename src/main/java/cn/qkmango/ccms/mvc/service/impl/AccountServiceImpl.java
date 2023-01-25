package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.LoginException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.validate.group.Query;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.param.ChangePasswordParam;
import cn.qkmango.ccms.domain.vo.UserInfoVO;
import cn.qkmango.ccms.mvc.dao.AccountDao;
import cn.qkmango.ccms.mvc.service.AccountService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
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

        //判断，如果是刷卡机登陆，还需要获取刷卡机的类型
        if (account.getPermissionType() == PermissionType.pos) {
            loginAccount = dao.loginPos(account);
        } else {
            loginAccount = dao.login(account);
        }

        if (loginAccount == null) {
            throw new LoginException(messageSource.getMessage("response.login.failure", null, locale));
        }

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
    public void changePassword(ChangePasswordParam param, Locale locale) throws UpdateException {
        int affectedRows = dao.changePassword(param);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.changePassword.failure", null, locale));
        }
    }

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public UserInfoVO userInfo(String id) {
        return dao.userInfo(id);
    }
}
