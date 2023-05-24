package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Record;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.PaymentAndRecordParam;
import cn.qkmango.ccms.domain.param.RecordParam;
import cn.qkmango.ccms.domain.vo.RecordVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 已支付Dao层
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-15 12:33
 */
@Mapper
public interface RecordDao {
    int count();

    int insert(Record paid);

    /**
     * 按照条件统计已支付记录
     *
     * @param param 统计条件
     * @return int 符合条件的记录数
     */
    int countBy(RecordParam param);

    /**
     * 分页查询缴费记录列表
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    List<RecordVO> list(Pagination<PaymentAndRecordParam> pagination);

    /**
     * 缴费记录退款
     *
     * @param id 缴费记录对象，包含缴费项目ID和用户ID
     * @return int 影响的记录数
     */
    int refund(String id);

    /**
     * 通过id查询缴费记录信息
     * 单表查询
     *
     * @param id 缴费记录ID
     * @return Record 缴费记录信息
     */
    Record baseById(String id);

    /**
     * 用户支付缴费项目
     *
     * @param record 缴费记录，包含缴费记录项目ID和缴费时间
     * @return int 影响的记录数
     */
    int toPayment(Record record);

    /**
     * 删除缴费记录
     *
     * @param id 缴费记录ID
     * @return int 影响的记录数
     */
    int delete(String id);

    /**
     * 通过缴费项目ID删除缴费记录
     * 不能删除已支付的缴费记录
     *
     * @param id 缴费项目ID
     * @return int 影响的记录数
     */
    int deleteByPayment(String id);
}
