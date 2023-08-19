package cn.qkmango.ccms.common.map;

import java.io.Serializable;

/**
 * 响应结果
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-23 17:04
 */
public class R<T> implements Serializable {
    /**
     * 响应状态
     * <li>默认为 false
     * <li>当响应状态为 true 时，表示响应成功
     * <li>当响应状态为 false 时，表示响应失败
     */
    private boolean success;

    /**
     * 响应状态码
     */
    private int code;

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

    public R() {
    }

    public R(boolean success, int code, String message, T data, Integer count) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.count = count;
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

    public int getCode() {
        return code;
    }

    public R<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public static <T> R<T> success() {
        return new R<>(true, 0, null, null, null);
    }

    public static <T> R<T> success(T data) {
        return new R<>(true, 0, null, data, null);
    }

    public static <T> R<T> success(String message) {
        return new R<>(true, 0, message, null, null);
    }

    public static <T> R<T> success(T data, String message) {
        return new R<>(true, 0, message, data, null);
    }

    public static <T> R<T> success(T data, int count) {
        return new R<>(true, 0, null, data, count);
    }

    public static <T> R<T> fail() {
        return new R<>(false, 400, null, null, null);
    }

    public static <T> R<T> fail(T data) {
        return new R<>(false, 400, null, data, null);
    }

    public static <T> R<T> fail(String message) {
        return new R<>(false, 400, message, null, null);
    }

    public static <T> R<T> fail(T data, String message) {
        return new R<>(false, 400, message, data, null);
    }


    @Override
    public String toString() {
        return "R{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", count=" + count +
                ", setSuccess=" + setSuccess() +
                '}';
    }
}
