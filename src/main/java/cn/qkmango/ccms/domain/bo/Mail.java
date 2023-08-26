package cn.qkmango.ccms.domain.bo;

/**
 * 邮件
 * <p>用于封装邮件信息</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-26 17:52
 */
public class Mail {
    private String to;
    private String from;
    private String subject;
    private String content;

    public Mail() {
    }

    public Mail(String from, String to, String subject, String content) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public Mail setTo(String to) {
        this.to = to;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public Mail setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Mail setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Mail setContent(String content) {
        this.content = content;
        return this;
    }
}
