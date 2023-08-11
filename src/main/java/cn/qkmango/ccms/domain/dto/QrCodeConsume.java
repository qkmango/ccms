package cn.qkmango.ccms.domain.dto;

import jakarta.validation.constraints.NotNull;

/**
 * 二维码消费
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-09 22:18
 */
public class QrCodeConsume extends BaseConsume {
    @NotNull
    private String code;

    /**
     * 创建者
     */
    private Integer creator;

    public String getCode() {
        return code;
    }

    public QrCodeConsume setCode(String code) {
        this.code = code;
        return this;
    }

    public Integer getCreator() {
        return creator;
    }

    public QrCodeConsume setCreator(Integer creator) {
        this.creator = creator;
        return this;
    }

    @Override
    public String toString() {
        return "QrCodeConsume{" +
                "code='" + code + '\'' +
                ", creator='" + creator + '\'' +
                ", account=" + getAccount() +
                ", amount=" + getAmount() +
                '}';
    }
}
