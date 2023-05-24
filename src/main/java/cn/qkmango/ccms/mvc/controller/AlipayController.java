package cn.qkmango.ccms.mvc.controller;


import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.mvc.service.AlipayService;
import com.alipay.api.AlipayApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Locale;

/**
 * 支付宝
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-24 13:57
 */
@RestController
@RequestMapping("/pay/alipay")
public class AlipayController {

    @Resource
    private AlipayService service;


    @GetMapping("/createPay.do")
    private R createPay(
            @RequestParam String subject,
            @RequestParam String totalAmount,
            Locale locale) throws InsertException {
        String url = service.createPay(subject, totalAmount, locale);
        return R.success().setData(url);
    }

    /**
     * 支付接口
     * subject=xxx&traceNo=xxx&totalAmount=xxx
     *
     * @param subject      支付的名称
     * @param traceNo      我们自己生成的订单编号
     * @param totalAmount  订单的总金额
     * @param httpResponse http响应
     */
    @GetMapping("/pay.do")
    public void pay(
            @RequestParam String subject,
            @RequestParam String traceNo,
            @RequestParam String totalAmount,
            HttpServletResponse httpResponse) throws IOException, AlipayApiException {
        service.pay(subject, traceNo, totalAmount, httpResponse);
    }

    /**
     * 支付结果异步通知
     * 必须是POST
     *
     * @param tradeNo    支付宝交易号
     * @param outTradeNo 商家订单号, 原支付请求的商家订单号
     * @param gmtPayment 交易付款时间, 格式为 yyyy-MM-dd HH:mm:ss
     * @param request    http请求
     */
    @PostMapping("/notify.do")
    public void payNotify(
            @RequestParam("trade_no") String tradeNo,
            @RequestParam("out_trade_no") String outTradeNo,
            @RequestParam("gmt_payment") String gmtPayment,
            @RequestParam("receipt_amount") String receiptAmount,
            @RequestParam("sign") String sign,
            Locale locale,
            HttpServletRequest request) throws AlipayApiException, UpdateException, DeleteException, JsonProcessingException {
        service.payNotify(tradeNo, outTradeNo, gmtPayment, receiptAmount, sign, locale, request);
    }

}
