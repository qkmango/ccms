package cn.qkmango.ccms.domain.dto;

import jakarta.validation.constraints.NotNull;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-19 10:40
 */
public class TradeRefundDto {
    @NotNull
    private Long id;

    @NotNull
    private Integer version;

    private Integer creator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }
}
