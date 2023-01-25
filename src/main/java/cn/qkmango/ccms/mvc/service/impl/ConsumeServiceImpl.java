package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.QueryException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.entity.Consume;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.ConsumeParam;
import cn.qkmango.ccms.domain.vo.ConsumeDetailsVO;
import cn.qkmango.ccms.mvc.dao.CardDao;
import cn.qkmango.ccms.mvc.dao.ConsumeDao;
import cn.qkmango.ccms.mvc.dao.UserDao;
import cn.qkmango.ccms.mvc.service.ConsumeService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 消费
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-29 23:03
 */
@Service
public class ConsumeServiceImpl implements ConsumeService {

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private ConsumeDao consumeDao;

    @Resource
    private CardDao cardDao;

    @Resource
    private UserDao userDao;


    /**
     * 添加通过POS机消费记录
     * 余额不足限制在数据库层面控制（无符号）
     *
     * @param consume 消费记录信息
     * @param locale  语言环境
     * @throws InsertException 插入异常
     * @throws QueryException 查询异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(Consume consume, Locale locale) throws InsertException, QueryException {

        Card card = cardDao.getCardByUserId(consume.getUser());

        //判断账户是否存在
        if (card == null) {
            throw new QueryException(messageSource.getMessage("db.account.notExist", null, locale));
        }

        //判断账户是否锁定
        if (card.getLock()) {
            boolean is = userDao.isUnsubscribe(consume.getUser());
            //如果账户锁定了，再判断账户是否注销
            if (is) {
                throw new QueryException(messageSource.getMessage("db.account.unsubscribe", null, locale));
            }
            throw new QueryException(messageSource.getMessage("db.account.lock", null, locale));
        }

        //判断余额是否充足
        if (card.getBalance() < consume.getPrice()) {
            throw new QueryException(messageSource.getMessage("db.balance.insufficient", null, locale));
        }

        //设置时间
        consume.setCreateTime(new Date());
        int affectedRows = 0;

        //先修改余额
        affectedRows = cardDao.updateBalance(consume);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.consume.insert.failure", null, locale));
        }

        //添加消费记录
        affectedRows = consumeDao.insert(consume);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.consume.insert.failure", null, locale));
        }
    }

    /**
     * 分页查询消费记录
     *
     * @param pagination 分页查询条件
     * @return
     */
    @Override
    public R<List<Consume>> list(Pagination<ConsumeParam> pagination) {
        List<Consume> consumeList = consumeDao.queryConsumePagination(pagination);
        int count = consumeDao.getCount();
        return R.success(consumeList).setCount(count);
    }

    /**
     * 查询消费记录详情
     *
     * @param consume 消费id和用户id
     * @return 消费记录详情
     */
    @Override
    public ConsumeDetailsVO detail(Consume consume) {
        ConsumeDetailsVO vo = consumeDao.detail(consume);
        return vo;
    }
}
