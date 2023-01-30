package cn.qkmango.ccms.config.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串转Date转换器
 * <p>
 * 可转换的格式
 * <li>yyyy-MM-dd HH:mm:ss</li>
 * <li>yyyy-MM-dd</li>
 * <li>时间戳</li>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-29 21:39
 */
public class DateConverter implements Converter<String, Date> {

    private static final String DATETIME_FORMAT_REGEX = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";
    private static final String DATE_FORMAT_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    private static final String TIMESTAMP_FORMAT_REGEX = "^\\d+$";


    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date convert(String source) {

        //如果为空，则返回null
        if (source == null || source.trim().length() == 0) {
            return null;
        }

        if (source.matches(DATETIME_FORMAT_REGEX)) {
            try {
                return datetimeFormat.parse(source);
            } catch (Exception e) {
                throw new RuntimeException(String.format("parser %s to Date fail", source));
            }
        }
        if (source.matches(DATE_FORMAT_REGEX)) {
            try {
                return dateFormat.parse(source);
            } catch (ParseException e) {
                throw new RuntimeException(String.format("parser %s to Date fail", source));
            }
        }
        if (source.matches(TIMESTAMP_FORMAT_REGEX)) {
            return new Date(Long.parseLong(source));
        }

        throw new RuntimeException(String.format("parser %s to Date fail, cannot to match [yyyy-MM-dd HH:mm:ss, yyyy-MM-dd, timestamp]", source));
    }

}
