package cn.qkmango.ccms.config.converter;

import cn.qkmango.ccms.domain.param.DatetimeRange;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串转 DatetimeRange 转换器
 * <p>日期范围字符串转换器</p>
 *
 * 如果使用 @RequestParam("range") 注解接收参数，那么就会使用指定的参数名来接收参数
 *   <pre>range=2020-01-01 - 2020-02-02</pre>
 * 如果不使用 @RequestParam("参数名") 注解接收参数，那么就会使用结构名为 datetimeRange 的参数
 *   <pre>datetimeRange=2020-01-01 - 2020-02-02</pre>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-08 19:19
 */
public class DatetimeRangeConverter implements Converter<String, DatetimeRange> {

    private static final String DATETIME_RANGE_FORMAT_REGEX = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} - \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";
    private static final String DATE_RANGE_FORMAT_REGEX = "^\\d{4}-\\d{2}-\\d{2} - \\d{4}-\\d{2}-\\d{2}$";
    private static final String TIMESTAMP_RANGE_FORMAT_REGEX = "^\\d+ - \\d+$";


    private static final SimpleDateFormat datetimeRangeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateRangeFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public DatetimeRange convert(String source) {
        if (source.trim().length() == 0) {
            return null;
        }

        //日期时间范围
        if (source.matches(DATETIME_RANGE_FORMAT_REGEX)) {
            try {
                String[] split = source.split(" - ");
                Date start = datetimeRangeFormat.parse(split[0]);
                Date end = datetimeRangeFormat.parse(split[1]);

                DatetimeRange datetimeRange = new DatetimeRange();
                datetimeRange.setStartTime(start);
                datetimeRange.setEndTime(end);
                return datetimeRange;
            } catch (Exception e) {
                throw new RuntimeException(String.format("parser %s to Date fail", source));
            }
        }

        //日期范围
        else if (source.matches(DATE_RANGE_FORMAT_REGEX)) {
            try {
                String[] split = source.split(" - ");
                Date start = dateRangeFormat.parse(split[0]);
                Date end = dateRangeFormat.parse(split[1]);

                DatetimeRange datetimeRange = new DatetimeRange();
                datetimeRange.setStartTime(start);
                datetimeRange.setEndTime(end);
                return datetimeRange;
            } catch (ParseException e) {
                throw new RuntimeException(String.format("parser %s to Date fail", source));
            }
        }

        //时间戳范围
        else if (source.matches(TIMESTAMP_RANGE_FORMAT_REGEX)) {
            String[] split = source.split(" - ");
            Date start = new Date(Long.parseLong(split[0]));
            Date end = new Date(Long.parseLong(split[1]));

            DatetimeRange datetimeRange = new DatetimeRange();
            datetimeRange.setStartTime(start);
            datetimeRange.setEndTime(end);
            return datetimeRange;
        }

        return null;
    }
}
