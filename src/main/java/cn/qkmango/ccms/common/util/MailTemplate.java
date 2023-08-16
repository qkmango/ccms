package cn.qkmango.ccms.common.util;

/**
 * 邮件模板
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-16 16:47
 */
public class MailTemplate {
    private final String[] format;

    /**
     * 构造函数
     * 占位符为 $
     * 如：你好$，你的验证码是$，请在5分钟内完成验证。
     *
     * @param format 格式
     */
    public MailTemplate(String format) {
        this.format = format.split("\\$");
    }

    public String build(String... contents) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < format.length; i++) {
            builder.append(format[i]);
            if (i < contents.length) {
                builder.append(contents[i]);
            }
        }
        return builder.toString();
    }

}
