package cn.qkmango.ccms.domain.pagination;


import jakarta.validation.Valid;

import java.io.Serializable;

/**
 * 分页实体类
 * 包含当前页码page、每页条数limit、略过的记录条数
 * 每页最大条数 MAX_LIMIT = 100
 *
 * @author qkmango
 */
public class Pagination<T> implements Serializable {

    /**
     * 每页最大条数
     */
    private static final int MAX_LIMIT = 100;
    /**
     * 当前页码
     */
    private Integer page = 1;
    /**
     * 每页条数
     */
    private Integer limit = 20;
    /**
     * 跳过的记录条数
     */
    private Integer skipCount = 0;
    /**
     * 是否预览
     * <li>true|null 开启预览，仅查询内容的前 20 字符
     * <li>false 关闭预览，查询全部内容
     */
    private Boolean preview;

    /**
     * 预览长度
     * <p>默认 20
     */
    private int previewLength = 20;

    /**
     * 是否分页
     * <p>默认就是分页
     */
    private Boolean pagination = true;

    /**
     * 查询条件
     */
    @Valid
    private T param;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.skipCount = (page - 1) * limit;
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        if (limit > MAX_LIMIT) {
            limit = MAX_LIMIT;
        }
        this.skipCount = (page - 1) * limit;
        this.limit = limit;
    }

    public Integer getSkipCount() {
        return skipCount;
    }

    public void setSkipCount() {
        this.skipCount = (page - 1) * limit;
    }

    public Boolean getPagination() {
        return pagination;
    }

    public void setPagination(Boolean pagination) {
        this.pagination = pagination;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    public Boolean getPreview() {
        return preview;
    }

    public void setPreview(Boolean preview) {
        this.preview = preview;
    }

    public int getPreviewLength() {
        return previewLength;
    }

    public void setPreviewLength(int previewLength) {
        this.previewLength = previewLength;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "page=" + page +
                ", limit=" + limit +
                ", skipCount=" + skipCount +
                ", preview=" + preview +
                ", previewLength=" + previewLength +
                ", pagination=" + pagination +
                ", param=" + param +
                '}';
    }
}
