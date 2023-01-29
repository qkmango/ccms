package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.QueryException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.util.SnowFlake;
import cn.qkmango.ccms.domain.bind.ConsumeType;
import cn.qkmango.ccms.domain.bind.PayState;
import cn.qkmango.ccms.domain.dto.PaymentDto;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.entity.Consume;
import cn.qkmango.ccms.domain.entity.Payment;
import cn.qkmango.ccms.domain.entity.Record;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.PaymentAndRecordParam;
import cn.qkmango.ccms.domain.param.PaymentParam;
import cn.qkmango.ccms.domain.param.RecordParam;
import cn.qkmango.ccms.domain.vo.PaymentVO;
import cn.qkmango.ccms.domain.vo.RecordVO;
import cn.qkmango.ccms.mvc.dao.*;
import cn.qkmango.ccms.mvc.service.ConsumeService;
import cn.qkmango.ccms.mvc.service.PayService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 支付/缴费控制器服务层实现类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-14 17:09
 */
@Service
public class PayServiceImpl implements PayService {
    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    /**
     * 缴费项目Dao
     */
    @Resource
    private PaymentDao paymentDao;

    /**
     * 已支付Dao
     */
    @Resource
    private RecordDao recordDao;

    /**
     * 用户Dao
     */
    @Resource
    private UserDao userDao;

    /**
     * 消费Dao
     */
    @Resource
    private ConsumeDao consumeDao;

    /**
     * 一卡通DAO
     */
    @Resource
    private CardDao cardDao;

    /**
     * 消费service
     */
    @Resource
    private ConsumeService consumeService;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Resource
    private SnowFlake snowFlake;


    /**
     * 添加缴费项目
     *
     * @param dto    dto模型，包含了Payment缴费项目对象和clazzIds需缴费的班级ID数组
     * @param locale 语言环境
     * @throws InsertException 插入失败
     */
    @Override
    public void insertPayment(PaymentDto dto, Locale locale) throws InsertException {
        // 1. 获取缴费项目对象
        Payment payment = dto.getPayment();
        payment.setCreateTime(new Date());
        payment.setId(Long.toString(snowFlake.nextId()));

        // 2. 获取班级ID数组
        List<String> clazzIds = dto.getClazzIds();

        SqlSession sqlSession = null;
        try {
            //开启批量插入，手动提交
            sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            PaymentDao paymentMapper = sqlSession.getMapper(PaymentDao.class);

            // 1. 添加缴费项目
            paymentMapper.insert(payment);

            // 2. 将指定班级的用户添加到已缴费表中，缴费状态为未缴费
            batchInsertRecord(payment.getId(), clazzIds, sqlSession);

            // 5. 提交事务
            sqlSession.commit();
        } catch (Exception e) {
            // 回滚事务
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
            throw new InsertException(messageSource.getMessage("db.payment.insert.failure", null, locale));
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    /**
     * 批量插入需要缴费的用户记录
     *
     * @param paymentId  缴费项目ID
     * @param clazzIds   指定需要缴费的班级ID数组
     * @param sqlSession SqlSession
     */
    private void batchInsertRecord(String paymentId, List<String> clazzIds, SqlSession sqlSession) {
        RecordDao mapper = sqlSession.getMapper(RecordDao.class);
        // 1. 获取指定班级的 user id
        List<String> ids = userDao.getUserIdByClazzIds(clazzIds);

        // 2. 将指定班级的用户添加到已缴费表中，缴费状态为未缴费
        Record record = new Record();
        for (String id : ids) {
            record.setUser(id);
            record.setPayment(paymentId);
            record.setState(PayState.UNPAID);
            // 3. 添加到已缴费表
            mapper.insert(record);
        }
    }

    /**
     * 分页查询缴费项目列表
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    @Override
    public R<List<Payment>> listPayment(Pagination<Payment> pagination) {
        List<Payment> list = paymentDao.list(pagination);
        int count = paymentDao.count();
        return R.success(list).setCount(count);
    }

    /**
     * 用户分页查询缴费项目列表
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    @Override
    public R<List<Payment>> listUserPayment(Pagination<PaymentParam> pagination) {
        List<Payment> list = paymentDao.listUser(pagination);
        int count = paymentDao.count();

        return new R<List<Payment>>()
                .setSuccess()
                .setData(list)
                .setCount(count);
    }

    /**
     * 修改缴费项目状态
     *
     * @param payment 缴费项目对象
     * @param locale  语言环境
     * @throws UpdateException 修改失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePaymentState(Payment payment, Locale locale) throws UpdateException {
        int affectedRows = paymentDao.updateState(payment);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.payment.update.state.failure", null, locale));
        }
    }

    /**
     * 获取缴费项目详情
     *
     * @param id 缴费项目ID
     * @return 缴费项目详情
     */
    @Override
    public PaymentVO detailPayment(String id) {
        return paymentDao.detail(id);
    }

    /**
     * 修改缴费项目
     *
     * @param payment 缴费项目对象
     * @param locale  语言环境
     * @throws UpdateException 修改失败
     */
    @Override
    public void updatePayment(Payment payment, Locale locale) throws UpdateException {
        // 先判断是否可以修改
        boolean can = this.canUpdatePayment(payment);
        if (!can) {
            throw new UpdateException(messageSource.getMessage("db.payment.update.failure@haveRecordCountMoreThanZero", null, locale));
        }

        //可以修改
        int affectedRows = paymentDao.update(payment);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.payment.update.failure", null, locale));
        }
    }

    /**
     * 判断是否可以修改缴费项目
     * 1. 如果已经有人缴费，不能修改金额
     * 2. 如果已经有人缴费，不能删除缴费项目
     *
     * @param payment 包含ID的，缴费项目修改后的信息
     * @return true 可以修改，false 不可以修改
     */
    private boolean canUpdatePayment(Payment payment) {
        //如果为空，返回false（信息不足，不能查询）
        if (payment == null || payment.getId() == null) {
            return false;
        }

        //如果前端请求修改了金额
        if (payment.getPrice() != null) {
            PaymentVO detail = paymentDao.detail(payment.getId());
            //如果缴费项目不存在，返回 false
            if (detail == null) {
                return false;
            }

            //比较金额是否修改，如果修改了金额
            boolean change = detail.getPayment().getPrice().equals(payment.getPrice());
            if (!change) {
                //获取已缴费的用户条数，如果已缴费的用户条数大于0，说明已经有用户缴费了，不能修改金额
                //应该先将已缴费的退还，再修改金额
                RecordParam record = new RecordParam();
                record.setPayment(payment.getId());
                record.setState(PayState.PAID);
                int countBy = recordDao.countBy(record);
                if (countBy > 0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 分页查询缴费记录列表
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    @Override
    public R<List<RecordVO>> listRecord(Pagination<PaymentAndRecordParam> pagination) {
        List<RecordVO> list = recordDao.list(pagination);
        int count = recordDao.count();
        return R.success(list,count);
    }

    /**
     * 缴费项目退款
     * 1. 修改缴费项目记录状态为退款
     * 2. 往用户账户里面加钱
     * 3. 往用户消费明细里面加记录
     *
     * @param record 缴费记录对象，包含缴费项目ID和用户ID
     * @param locale 语言环境
     * @throws UpdateException 退款失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void refundRecord(Record record, Locale locale) throws UpdateException {
        int affectedRows = 0;
        // 修改缴费项目记录状态为退款
        affectedRows = recordDao.refund(record.getId());
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.record.refund.failure", null, locale));
        }

        // 查询缴费项目详情,获取缴费项目金额
        Payment payment = paymentDao.baseById(record.getPayment());

        // 往用户一卡通账户里面加钱
        Card card = new Card();
        card.setUser(record.getUser());
        card.setBalance(payment.getPrice());
        affectedRows = cardDao.recharge(card);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.record.refund.failure", null, locale));
        }


        // 往用户消费明细里面加记录
        Consume consume = new Consume();
        consume.setUser(card.getUser());
        consume.setPrice(card.getBalance());
        consume.setCreateTime(new Date());
        consume.setInfo(payment.getTitle());
        consume.setType(ConsumeType.REFUND);

        //插入消费记录(图款)
        affectedRows = consumeDao.insert(consume);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.record.refund.failure", null, locale));
        }
    }


    /**
     * 用户支付缴费项目
     *
     * @param id     缴费记录id
     * @param user   用户id
     * @param locale 语言环境
     * @throws UpdateException 支付失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void topay(String id, String user, Locale locale) throws UpdateException, QueryException, InsertException {
        //查询缴费记录详情
        Record record = recordDao.baseById(id);
        if (record == null) {
            throw new QueryException(messageSource.getMessage("db.record.failure@notExist", null, locale));
        }

        //查询缴费项目详情
        Payment payment = paymentDao.baseById(record.getPayment());
        if (payment == null) {
            throw new QueryException(messageSource.getMessage("db.payment.failure@notExist", null, locale));
        }

        //消费，会扣除用户一卡通金额，并且插入一条消费记录
        //如果消费失败会抛出异常 InsertException
        Consume consume = new Consume();
        consume.setUser(user);
        consume.setCreateTime(new Date());
        consume.setPrice(payment.getPrice());
        consume.setType(ConsumeType.PAYMENT);
        consume.setInfo(payment.getTitle());
        consumeService.insert(consume, locale);

        //修改缴费记录状态为已缴费
        Record topayRecord = new Record();
        topayRecord.setId(id);
        topayRecord.setCreateTime(new Date());
        int affectedRows = recordDao.topay(topayRecord);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.record.topay.failure", null, locale));
        }
    }


    /**
     * 删除缴费记录
     * 只能删除未支付的缴费记录
     *
     * @param id     缴费记录id
     * @param locale 语言环境
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteRecord(String id, Locale locale) throws DeleteException {
        int affectedRows = recordDao.delete(id);
        if (affectedRows != 1) {
            throw new DeleteException(messageSource.getMessage("db.record.delete.failure", null, locale));
        }
    }

    /**
     * 删除缴费项目
     *
     * @param id     缴费项目id
     * @param locale 语言环境
     * @throws DeleteException 删除失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deletePayment(String id, Locale locale) throws DeleteException {
        int count, affectedRows;
        //获取该项目已缴费的记录条数,如果已缴费的记录条数大于0，不能删除
        RecordParam param = new RecordParam();
        param.setPayment(id);
        param.setState(PayState.PAID);
        count = recordDao.countBy(param);
        if (count > 0) {
            throw new DeleteException(messageSource.getMessage("db.payment.delete.failure@paid", null, locale));
        }

        //删除缴费记录
        param.setPayment(id);
        param.setState(null);
        count = recordDao.countBy(param);
        affectedRows = recordDao.deleteByPayment(id);
        if (affectedRows != count) {
            throw new DeleteException(messageSource.getMessage("db.payment.delete.failure@paid", null, locale));
        }

        //删除缴费项目
        affectedRows = paymentDao.delete(id);
        if (affectedRows != 1) {
            throw new DeleteException(messageSource.getMessage("db.payment.delete.failure", null, locale));
        }

    }
}
