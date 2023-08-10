package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bo.AccountPayQrcode;
import cn.qkmango.ccms.domain.dto.QrCodeConsume;

/**
 * 支付
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-07 19:45
 */
public interface PayService {
    AccountPayQrcode createQrCode(Integer account);

    R consumeByQrCode(QrCodeConsume consume);
}
