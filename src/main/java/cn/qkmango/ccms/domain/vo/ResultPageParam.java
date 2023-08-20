package cn.qkmango.ccms.domain.vo;

/**
 * 跳转到结果页面需要的数据
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-20 19:13
 */
public class ResultPageParam<T> {
    // 标签标题
    private String title;

    private boolean success;

    private String message;

    // 跳转URL
    private String redirect;

    // 信道名称
    private String channel;

    private T data;

    // 执行类型，redirect:跳转到指定页面; channel:通过信道发送数据后关闭页面
    private String type;

    public String getTitle() {
        return title;
    }

    public ResultPageParam<T> setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public ResultPageParam<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultPageParam<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getRedirect() {
        return redirect;
    }

    public ResultPageParam<T> setRedirect(String redirect) {
        this.redirect = redirect;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public ResultPageParam<T> setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultPageParam<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getType() {
        return type;
    }

    public ResultPageParam<T> setType(String type) {
        this.type = type;
        return this;
    }
}
