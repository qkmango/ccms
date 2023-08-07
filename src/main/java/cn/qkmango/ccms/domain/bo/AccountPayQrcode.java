package cn.qkmango.ccms.domain.bo;

/**
 * 用户支付二维码
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-07 19:47
 */
public class AccountPayQrcode {
    private String code;
    private Integer account;

    public String getCode() {
        return code;
    }

    public AccountPayQrcode setCode(String code) {
        this.code = code;
        return this;
    }

    public Integer getAccount() {
        return account;
    }

    public AccountPayQrcode setAccount(Integer account) {
        this.account = account;
        return this;
    }

    @Override
    public String toString() {
        return "UserPayQrCode{" +
                "code='" + code + '\'' +
                ", account=" + account +
                '}';
    }
}
