package cn.qkmango.ccms.common.map;

/**
 * 响应 Map
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-23 17:04
 */
public class R<T> {
    /**
     * 响应状态
     * <li>默认为 false
     * <li>当响应状态为 true 时，表示响应成功
     * <li>当响应状态为 false 时，表示响应失败
     */
    private boolean success;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 行数
     * <p>例如查询数据时，返回的数据行数
     */
    private Integer count;

    /**
     * 元数据
     * <p>存放一些额外的信息
     */
    private Object meta;

    public R() {
    }

    public R(boolean success, String message, T data, Integer count, Object meta) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.count = count;
        this.meta = meta;
    }

    public boolean isSuccess() {
        return success;
    }

    public R<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public R<T> setSuccess() {
        this.success = true;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public R<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public R<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public R<T> setCount(Integer count) {
        this.count = count;
        return this;
    }

    public Object getMeta() {
        return meta;
    }

    public R<T> setMeta(Object meta) {
        this.meta = meta;
        return this;
    }

    public static <T> R<T> success() {
        return new R<T>(true, null, null, null, null);
    }

    public static <T> R<T> success(T data) {
        return new R<T>(true, null, data, null, null);
    }

    public static <T> R<T> success(String message) {
        return new R<T>(true, message, null, null, null);
    }

    public static <T> R<T> success(T data, String message) {
        return new R<T>(true, message, data, null, null);
    }

    public static <T> R<T> success(T data, int count) {
        return new R<T>(true, null, data, count, null);
    }


    public static <T> R<T> fail(T data) {
        return new R<T>(false, null, data, null, null);
    }

    public static <T> R<T> fail(String message) {
        return new R<T>(false, message, null, null, null);
    }

    public static <T> R<T> fail(T data, String message) {
        return new R<T>(false, message, data, null, null);
    }


    @Override
    public String toString() {
        return "ResponseMap{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", count=" + count +
                ", meta='" + meta + '\'' +
                '}';
    }
}
