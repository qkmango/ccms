package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.mvc.dao.CardDao;
import cn.qkmango.ccms.mvc.dao.UserDao;
import cn.qkmango.ccms.mvc.service.CardService;
import jakarta.annotation.Resource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 20:14
 */
@Service
public class CardServiceImpl implements CardService {

    @Resource
    private CardDao cardDao;
    @Resource
    private UserDao userDao;

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    /**
     * 更新卡状态
     *
     * @param card   校园卡（user，lock）
     * @param locale 语言环境
     * @throws UpdateException 更新异常
     */
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public void state(Card card, Locale locale) throws UpdateException {
//        int affectedRows = 0;
//
//        User user = userDao.getUser(card.getUser());
//
//        // 如果用户存在,并且用户没有注销, 才可以进行 挂失/解挂 操作
//        if (user != null && !user.getUnsubscribe()) {
//            affectedRows = cardDao.state(card);
//            if (affectedRows != 1) {
//                throw new UpdateException(messageSource.getMessage("db.updateCardState.failure", null, locale));
//            }
//            //删除redis中的缓存
//            redis.deleteWithTable("card");
//        }
//
//        //如果已经注销, 就不允许再次修改状态
//        else if (user != null && user.getUnsubscribe()) {
//            throw new UpdateException(messageSource.getMessage("db.updateCardState.failure@unsubscribe", null, locale));
//        }
//
//        //如果学生不存在, 就不允许修改状态
//        else {
//            throw new UpdateException(messageSource.getMessage("db.updateCardState.failure@notExist", null, locale));
//        }
//    }

    /**
     * 分页查询卡信息
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    @Override
    public R<List<Card>> list(Pagination<Card> pagination) {
        List<Card> cardList = cardDao.list(pagination);
        int count = cardDao.count();
        return R.success(cardList).setCount(count);
    }

//    /**
//     * 添加一卡通
//     * 会创建卡信息和用户信息
//     *
//     * @param user   用户
//     * @param locale 语言环境
//     * @return 返回添加的卡信息
//     * @throws InsertException 插入异常
//     */
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public Card insert(User user, Locale locale) throws InsertException {
//
//        //判断学生是否存在
//        int count = userDao.userCountOr(user);
//        if (count != 0) {
//            throw new InsertException(messageSource.getMessage("db.user.failure@exist", null, locale));
//        }
//
//        //先插入学生
//        cardService.insertUser(user, locale);
//
//        //添加卡片
//        Card card = new Card(user.getId(), 0);
//        int affectedRows = cardDao.insert(card);
//        if (affectedRows != 1) {
//            throw new InsertException(messageSource.getMessage("db.card.insert.failure", null, locale));
//        }
//        redis.deleteWithTable("user", "card");
//
//        return card;
//    }

    /**
     * 添加用户
     *
     * @param user   用户信息
     * @param locale 语言环境
     * @throws InsertException 插入异常
     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public void insertUser(User user, Locale locale) throws InsertException {
//        //设置默认密码
//        String idCard = user.getIdCard();
//        user.setPassword(idCard.substring(idCard.length() - 6));
//
//        //添加学生
//        int affectedRows = userDao.insert(user);
//        if (affectedRows != 1) {
//            throw new InsertException(messageSource.getMessage("db.addUser.failure", null, locale));
//        }
//    }


    /**
     * 充值
     * TODO
     *
     * @param card 校园卡
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void recharge(Card card, Locale locale) throws UpdateException {

        int affectedRows = 0;



        //插入消费记录(充值)
        // TODO
        affectedRows = 0;
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.update.recharge.failure", null, locale));
        }

        //更新卡余额
        affectedRows = cardDao.recharge(card);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.update.recharge.failure", null, locale));
        }
    }

    /**
     * 根据账户ID查询卡信息
     *
     * @param account
     * @return 卡详细信息
     */
    @Override
    public Card detail(String account) {
        return cardDao.getRecordByAccount(account);
    }
}
