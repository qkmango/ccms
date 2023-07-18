package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.security.encoder.PasswordEncoder;
import cn.qkmango.ccms.mvc.dao.CardDao;
import cn.qkmango.ccms.mvc.dao.UserDao;
import cn.qkmango.ccms.mvc.service.UserService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

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
    @Resource
    private PasswordEncoder passwordEncoder;


    /**
     * 重置密码
     * 重置密码为身份证后6位
     *
     * @param card   校园卡
     * @param locale 语言环境
     * @throws UpdateException 更新异常
     */
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public void resetPassword(Card card, Locale locale) throws UpdateException {
//
//        User user = userDao.getUser(card.getUser());
//        if (user == null) {
//            throw new UpdateException(messageSource.getMessage("db.resetPassword.failure", null, locale));
//        }
//
//        //重置密码为身份证后6位
//        String defaultRawPassword = user.getIdCard().substring(user.getIdCard().length() - 6);
//        String newBCryptPassword = passwordEncoder.encode(defaultRawPassword);
//
//        user.setPassword(newBCryptPassword);
//
//        int affectedRows = userDao.resetPassword(user);
//        if (affectedRows != 1) {
//            throw new UpdateException(messageSource.getMessage("db.resetPassword.failure", null, locale));
//        }
//    }
}
