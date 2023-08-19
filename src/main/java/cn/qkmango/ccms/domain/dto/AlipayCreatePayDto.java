package cn.qkmango.ccms.domain.dto;

import cn.qkmango.ccms.domain.bind.trade.TradeLevel2;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * @author qkmango
 * @version 1.0
 * @date 2023-08-19 16:29
 */
public class AlipayCreatePayDto {
    @NotNull
    private String subject;

    // 金额，单位 分
    @NotNull
    @Min(1)
    private Integer amount;

    //
    private TradeLevel2 level2;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public TradeLevel2 getLevel2() {
        return level2;
    }

    public void setLevel2(TradeLevel2 level2) {
        this.level2 = level2;
    }
}
