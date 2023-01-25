package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Payment;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.PaymentParam;
import cn.qkmango.ccms.domain.vo.PaymentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 缴费项目Dao层
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-14 21:08
 */
@Mapper
public interface PaymentDao {
    int count();

    String lastInsertId();

    int insert(Payment payment);

    List<Payment> list(Pagination<Payment> pagination);

    List<Payment> listUser(Pagination<PaymentParam> pagination);

    int updateState(Payment payment);

    PaymentVO detail(String id);

    int update(Payment payment);

    /**
     * 根据缴费项目ID查询缴费项目
     * 单表查询
     *
     * @param payment 缴费项目ID
     * @return Payment 缴费项目对象
     */
    Payment baseById(String payment);

    /**
     * 删除缴费项目
     *
     * @param id 缴费项目ID
     * @return int 影响的记录数
     */
    int delete(String id);
}
