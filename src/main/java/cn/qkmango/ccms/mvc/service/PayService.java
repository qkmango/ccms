package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.QueryException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.dto.PaymentDto;
import cn.qkmango.ccms.domain.entity.Payment;
import cn.qkmango.ccms.domain.entity.Record;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.PaymentAndRecordParam;
import cn.qkmango.ccms.domain.param.PaymentParam;
import cn.qkmango.ccms.domain.vo.PaymentVO;
import cn.qkmango.ccms.domain.vo.RecordVO;

import java.util.List;
import java.util.Locale;

/**
 * 支付/缴费控服务层
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-14 17:09
 */
public interface PayService {
    /**
     * 添加缴费项目
     * 支付款项
     *
     * @param dto    dto模型，包含了Payment缴费项目对象和clazzIds需缴费的班级ID数组
     * @param locale 语言环境
     * @throws InsertException 插入失败
     */
    void insertPayment(PaymentDto dto, Locale locale) throws InsertException;

    /**
     * 分页查询缴费项目列表
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    R listPayment(Pagination<Payment> pagination);

    /**
     * 用户分页查询缴费项目列表
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    R listUserPayment(Pagination<PaymentParam> pagination);

    /**
     * 获取缴费项目详情
     *
     * @param id 缴费项目ID
     * @return 缴费项目详情
     */
    PaymentVO detailPayment(String id);

    /**
     * 修改缴费项目
     *
     * @param payment 缴费项目对象
     * @param locale  语言环境
     * @throws UpdateException 修改失败
     */
    void updatePayment(Payment payment, Locale locale) throws UpdateException;

    /**
     * 分页查询缴费记录列表
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    R<List<RecordVO>> listRecord(Pagination<PaymentAndRecordParam> pagination);

    /**
     * 缴费记录退款
     *
     * @param record 缴费记录对象，包含缴费项目ID和用户ID
     * @param locale 语言环境
     * @throws UpdateException 退款失败
     */
    void refundRecord(Record record, Locale locale) throws UpdateException;


    /**
     * 用户支付缴费项目
     *
     * @param id     缴费记录id
     * @param user   用户id
     * @param locale 语言环境
     * @throws UpdateException 支付失败
     */
    void topay(String id, String user, Locale locale) throws UpdateException, QueryException, InsertException;

    /**
     * 删除缴费记录
     * 只能删除未支付的缴费记录
     *
     * @param id     缴费记录id
     * @param locale 语言环境
     */
    void deleteRecord(String id, Locale locale) throws DeleteException;

    /**
     * 删除缴费项目
     *
     * @param id     缴费项目id
     * @param locale 语言环境
     * @throws DeleteException 删除失败
     */
    void deletePayment(String id, Locale locale) throws DeleteException;
}
