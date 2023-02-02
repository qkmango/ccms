package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.util.JsonUtil;
import cn.qkmango.ccms.domain.bind.ConsumeType;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.entity.Consume;
import cn.qkmango.ccms.domain.entity.User;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.CardInfoParam;
import cn.qkmango.ccms.domain.vo.UserAndCardVO;
import cn.qkmango.ccms.mvc.dao.CardDao;
import cn.qkmango.ccms.mvc.dao.ConsumeDao;
import cn.qkmango.ccms.mvc.dao.UserDao;
import cn.qkmango.ccms.mvc.service.CardService;
import jakarta.annotation.Resource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
    private ConsumeDao consumeDao;
    @Resource
    private ReloadableResourceBundleMessageSource messageSource;
    @Resource
    private JsonUtil jsonUtil;
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> rt;

    /**
     * 更新卡状态
     *
     * @param card   校园卡（user，lock）
     * @param locale 语言环境
     * @throws UpdateException 更新异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void state(Card card, Locale locale) throws UpdateException {
        int affectedRows = 0;

        User user = userDao.getUser(card.getUser());

        // 如果用户存在,并且用户没有注销, 才可以进行 挂失/解挂 操作
        if (user != null && !user.getUnsubscribe()) {
            affectedRows = cardDao.state(card);
            if (affectedRows != 1) {
                throw new UpdateException(messageSource.getMessage("db.updateCardState.failure", null, locale));
            }
        }

        //如果已经注销, 就不允许再次修改状态
        else if (user != null && user.getUnsubscribe()) {
            throw new UpdateException(messageSource.getMessage("db.updateCardState.failure@unsubscribe", null, locale));
        }

        //如果学生不存在, 就不允许修改状态
        else {
            throw new UpdateException(messageSource.getMessage("db.updateCardState.failure@notExist", null, locale));
        }
    }

    /**
     * 分页查询卡信息
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    @Override
    public R<List<UserAndCardVO>> list(Pagination<CardInfoParam> pagination) {

        // 从缓存中获取数据
        String key = jsonUtil.toJson(pagination) + "[@user@card]";
        Object cache = rt.opsForValue().get(key);
        if (cache instanceof R) return (R) cache;

        // 如果缓存中没有数据, 就从数据库中查询
        List<UserAndCardVO> cardList = cardDao.list(pagination);
        int count = cardDao.count();
        R<List<UserAndCardVO>> r = R.success(cardList).setCount(count);

        // 将查询结果存入缓存, 设置过期时间5分钟
        rt.opsForValue().set(key, r, 5, TimeUnit.MINUTES);
        return r;
    }

    /**
     * 添加一卡通
     * 会创建卡信息和用户信息
     *
     * @param user   用户
     * @param locale 语言环境
     * @return 返回添加的卡信息
     * @throws InsertException 插入异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Card insert(User user, Locale locale) throws InsertException {

        //判断学生是否存在
        int count = userDao.userCountOr(user);
        if (count != 0) {
            throw new InsertException(messageSource.getMessage("db.user.failure@exist", null, locale));
        }

        //先插入学生
        insertUser(user, locale);

        //添加卡片
        Card card = new Card(user.getId(), 0);
        int affectedRows = cardDao.insert(card);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.card.insert.failure", null, locale));
        }

        rt.delete("*@user*");

        return card;
    }

    /**
     * 添加用户
     *
     * @param user   用户信息
     * @param locale 语言环境
     * @throws InsertException 插入异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertUser(User user, Locale locale) throws InsertException {
        //设置默认密码
        String idCard = user.getIdCard();
        user.setPassword(idCard.substring(idCard.length() - 6));

        //添加学生
        int affectedRows = userDao.insert(user);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.addUser.failure", null, locale));
        }
    }


    /**
     * 充值
     *
     * @param card 校园卡
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void recharge(Card card, Locale locale) throws UpdateException {

        int affectedRows = 0;

        Consume consume = new Consume();
        consume.setUser(card.getUser());
        consume.setPrice(card.getBalance());
        consume.setCreateTime(new Date());
        consume.setInfo("充值");
        consume.setType(ConsumeType.RECHARGE);

        //插入消费记录(充值)
        affectedRows = consumeDao.insert(consume);
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
     * 根据用户ID查询卡详细信息
     *
     * @param card 校园卡（user ID）
     * @return 卡详细信息
     */
    @Override
    public UserAndCardVO list(Card card) {
        UserAndCardVO vo = cardDao.detail(card);
        return vo;
    }
}
