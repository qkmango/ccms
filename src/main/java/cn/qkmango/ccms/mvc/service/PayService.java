package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.domain.bo.AccountPayQrcode;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-07 19:45
 */
public interface PayService {
    AccountPayQrcode createQrCode(Integer account);
}
