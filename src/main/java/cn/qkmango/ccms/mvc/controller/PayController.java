package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.QueryException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.common.validate.group.Query;
import cn.qkmango.ccms.common.validate.group.Update;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.dto.PaymentDto;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Payment;
import cn.qkmango.ccms.domain.entity.Record;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.PaymentAndRecordParam;
import cn.qkmango.ccms.domain.param.RecordParam;
import cn.qkmango.ccms.domain.vo.PaymentVO;
import cn.qkmango.ccms.domain.vo.RecordVO;
import cn.qkmango.ccms.mvc.service.PayService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

/**
 * 支付/缴费控制器
 * <p>负责处理支付/缴费相关的请求</p>
 * <p>包括：添加缴费项目、缴费、查询缴费记录等</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-14 17:08
 */
@RestController
@RequestMapping("pay")
@Permission(Role.admin)
public class PayController {

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private PayService service;

    /**
     * 添加缴费项目
     *
     * @param dto     dto模型，包含了Payment缴费项目对象和clazzIds需缴费的班级ID数组
     * @param session 会话对象
     * @param locale  语言环境
     * @return 插入状态
     * @throws InsertException 插入失败
     */
    @PostMapping("payment/one/insert.do")
    public R insertPayment(@RequestBody @Validated(Insert.class) PaymentDto dto, HttpSession session, Locale locale) throws InsertException {
        Account account = (Account) session.getAttribute("account");
        dto.getPayment().setAuthor(account.getId());
        service.insertPayment(dto, locale);
        return R.success(messageSource.getMessage("db.payment.insert.success", null, locale));
    }


    /**
     * 分页查询缴费项目列表
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/payment/pagination/list.do")
    public R listPayment(@RequestBody Pagination<Payment> pagination) {
        return service.listPayment(pagination);
    }


    /**
     * 获取缴费项目详情
     *
     * @param id 缴费项目ID
     * @return 缴费项目详情
     */
    @GetMapping("/payment/one/detail.do")
    public R<PaymentVO> detailPayment(@RequestParam String id) {
        PaymentVO detail = service.detailPayment(id);
        return R.success(detail);
    }

    /**
     * 修改缴费项目
     *
     * @param payment 缴费项目对象
     * @param locale  语言环境
     * @return 是否修改成功
     * @throws UpdateException 修改失败
     */
    @PostMapping("/payment/one/update.do")
    public R updatePayment(@Validated(Update.class) Payment payment, Locale locale) throws UpdateException {
        service.updatePayment(payment, locale);
        return R.success(messageSource.getMessage("db.payment.update.success", null, locale));
    }

    /**
     * 删除缴费项目
     * @param id 缴费项目ID
     * @param locale 语言环境
     * @return 是否删除成功
     * @throws DeleteException 删除失败
     */
    @PostMapping("/payment/one/delete.do")
    public R deletePayment(@RequestParam String id, Locale locale) throws DeleteException {
        service.deletePayment(id, locale);
        return R.success(messageSource.getMessage("db.payment.delete.success", null, locale));
    }

    /**
     * 分页查询缴费记录列表
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    @Permission({Role.admin, Role.user})
    @PostMapping("/record/pagination/list.do")
    public R<List<RecordVO>> listRecord(@RequestBody @Validated(Query.class)
                                        Pagination<PaymentAndRecordParam> pagination,
                                        HttpSession session) {
        // 如果是普通用户，只能查询自己的缴费记录
        Account account = (Account) session.getAttribute("account");
        Role type = account.getRole();
        if (type == Role.user) {
            PaymentAndRecordParam param = pagination.getParam();
            if (param == null) {
                param = new PaymentAndRecordParam();
                RecordParam record = new RecordParam();
                record.setUser(account.getId());
                param.setRecord(record);
                pagination.setParam(param);
            } else {
                RecordParam record = param.getRecord();
                if (record == null) {
                    record = new RecordParam();
                    record.setUser(account.getId());
                    param.setRecord(record);
                } else {
                    record.setUser(account.getId());
                }
            }
        }

        return service.listRecord(pagination);
    }

    /**
     * 缴费记录退款
     *
     * @param record 缴费记录对象，包含缴费记录ID,缴费项目ID,用户ID
     * @param locale 语言环境
     * @return 是否退款成功
     * @throws UpdateException 退款失败
     */
    @PostMapping("/record/one/refund.do")
    public R refundRecord(@Validated(Update.class) Record record, Locale locale) throws UpdateException {
        service.refundRecord(record, locale);
        return R.success(messageSource.getMessage("db.record.refund.success", null, locale));
    }


    /**
     * 用户支付缴费项目
     *
     * @param id      缴费记录id
     * @param session 用户session
     * @return 是否支付成功
     * @throws UpdateException 支付失败
     * @throws InsertException 支付失败
     * @throws QueryException  支付失败
     */
    @Permission(Role.user)
    @PostMapping("/record/one/toPayment.do")
    public R toPayment(String id, HttpSession session, Locale locale) throws UpdateException, QueryException, InsertException {
        Account account = (Account) session.getAttribute("account");
        String user = account.getId();
        service.toPayment(id, user, locale);
        return R.success(messageSource.getMessage("db.record.topay.success", null, Locale.getDefault()));
    }

    /**
     * 获取缴费记录详情
     *
     * @param id     缴费记录ID
     * @param locale 语言环境
     * @return 删除是否成功
     * @throws DeleteException 删除失败
     */
    @PostMapping("/record/one/delete.do")
    public R deleteRecord(String id, Locale locale) throws DeleteException {
        service.deleteRecord(id, locale);
        return R.success(messageSource.getMessage("db.record.delete.success", null, locale));
    }

}
