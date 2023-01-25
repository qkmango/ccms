package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.entity.User;
import cn.qkmango.ccms.domain.vo.UserAndCardVO;
import cn.qkmango.ccms.mvc.dao.CardDao;
import cn.qkmango.ccms.mvc.dao.UserDao;
import cn.qkmango.ccms.mvc.service.UserService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.Locale;

/**
 * 用户业务层实现类
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-28 22:04
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
    @Resource
    private CardDao cardDao;
    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    /**
     * 注销账户
     *
     * @param card   校园卡
     * @param locale 语言环境
     * @throws UpdateException 更新异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void unsubscribe(Card card, Locale locale) throws UpdateException {
        int affectedRows = 0;
        card.setLock(true);

        UserAndCardVO details = cardDao.detail(card);

        //判断余额是否为0
        if (details.getCard().getBalance() != 0) {
            throw new UpdateException(messageSource.getMessage("db.account.unsubscribe.failure@balance", null, locale));
        } else if (details.getUser().getUnsubscribe()) {
            //判断是否已经注销过
            throw new UpdateException(messageSource.getMessage("db.account.unsubscribe.failure@unsubscribed", null, locale));
        }


        //将卡状态设为挂失
        affectedRows = cardDao.state(card);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.account.unsubscribe.failure", null, locale));
        }

        //将学生账户状态设为注销
        affectedRows = userDao.unsubscribe(card);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.account.unsubscribe.failure", null, locale));
        }
    }

    /**
     * 重置密码
     * 重置密码为身份证后6位
     *
     * @param card   校园卡
     * @param locale 语言环境
     * @throws UpdateException 更新异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void resetPassword(Card card, Locale locale) throws UpdateException {

        User user = userDao.getUser(card.getUser());
        if (user == null) {
            throw new UpdateException(messageSource.getMessage("db.resetPassword.failure", null, locale));
        }

        user.setPassword(user.getIdCard().substring(user.getIdCard().length() - 6));

        int affectedRows = userDao.resetPassword(user);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.resetPassword.failure", null, locale));
        }
    }
}
