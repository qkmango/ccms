package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.bo.AccountPayQrcode;
import cn.qkmango.ccms.domain.dto.QrCodeConsume;
import cn.qkmango.ccms.mvc.service.PaySystemService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统支付
 * <p>系统支付相关，如一卡通消费等</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-20 16:26
 */
@RestController
@RequestMapping("pay/system")
public class PaySystemController {

    @Resource
    private PaySystemService service;

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
     * 接口只能是收款者（POS）才能请求，消费方式为扫码支付
     *
     * @param consume 二维码消费信息
     */
    @Permission(Role.pos)
    @PostMapping("consume-by-qrcode.do")
    public R<Object> consumeByQrCode(@Validated QrCodeConsume consume) {
        // 创建者为当前登录用户，也即为收款者（POS）
        Integer creator = AccountHolder.getId();
        consume.setCreator(creator);
        return service.consumeByQrCode(consume);
    }
}
