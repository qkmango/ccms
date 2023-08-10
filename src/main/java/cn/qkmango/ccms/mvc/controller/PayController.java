package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.bo.AccountPayQrcode;
import cn.qkmango.ccms.domain.dto.QrCodeConsume;
import cn.qkmango.ccms.mvc.service.PayService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.annotation.Resource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-07 19:39
 */
@RestController
@RequestMapping("pay")
public class PayController {

    @Resource
    private ReloadableResourceBundleMessageSource ms;

    @Resource
    private PayService service;

    /**
     * 创建支付二维码，并缓存到redis
     * key为 pay:qrcode:accountId
     * value 为 UUID
     *
     * @return 二维码
     */
    @Permission(Role.user)
    @GetMapping("create-qrcode.do")
    public R<AccountPayQrcode> createQrCode() {
        Integer account = AccountHolder.getId();
        AccountPayQrcode qrcode = service.createQrCode(account);
        return R.success(qrcode);
    }

    /**
     * 二维码支付消费
     *
     * @param consume 二维码
     */
    @Permission(Role.pos)
    @PostMapping("consume-by-qrcode.do")
    public R<Object> consumeByQrCode(@RequestBody @Validated QrCodeConsume consume) {
        return service.consumeByQrCode(consume);
    }

}
