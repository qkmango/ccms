package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.cache.qrcode.QrCodeCache;
import cn.qkmango.ccms.domain.bo.AccountPayQrcode;
import cn.qkmango.ccms.mvc.service.PayService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-07 19:45
 */
@Service
public class PayServiceImpl implements PayService {

    @Resource
    private QrCodeCache qrCodeCache;


    /**
     * 创建支付二维码，并缓存到redis
     * key为 pay:qrcode:accountId
     * value 为 UUID
     *
     * @return 二维码
     */
    @Override
    public AccountPayQrcode createQrCode(Integer account) {
        String code = UUID.randomUUID().toString();
        AccountPayQrcode qrcode = new AccountPayQrcode()
                .setAccount(account)
                .setCode(code);
        qrCodeCache.set(account, code);
        return qrcode;
    }
}
