package cn.qkmango.ccms.domain.pagination;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.io.Serializable;

/**
 * 流式分页实体类
 * <p>T 为查询条件类型</p>
 * <p>last 为上一页最后一条数据的 id,如果 last 为 null，默认从最前面开始查询
 * 一般情况 记录应该按照 id 降序排列, 记录的 id<last
 * <code>ORDER BY id DESC;id < </code></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-31 18:14
 */
public class Flow<T> implements Serializable {

    @Min(0)
    private Long last;

    /**
     * 每页最大条数
     */
    private static final Byte MAX_LIMIT = 100;

    /**
     * 每页条数
     */
    private Byte limit = 20;

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
    private Byte previewLength = 20;

    /**
     * 查询条件
     */
    @Valid
    private T param;

    /**
     * 如果 last 为 null，则默认返回 Integer 0
     */
    public Long getLast() {
        return last;
    }

    public void setLast(Long last) {
        this.last = last;
    }

    public Byte getLimit() {
        return limit;
    }

    public void setLimit(Byte limit) {
        if (limit > MAX_LIMIT) {
            limit = MAX_LIMIT;
        }
        this.limit = limit;
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

    public void setPreviewLength(Byte previewLength) {
        this.previewLength = previewLength;
    }


    @Override
    public String toString() {
        return "Flow{" +
                "lastId=" + last +
                ", limit=" + limit +
                ", preview=" + preview +
                ", previewLength=" + previewLength +
                ", param=" + param +
                '}';
    }
}
