package cn.qkmango.ccms.mvc;

import cn.qkmango.ccms.domain.param.DatetimeRange;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-25 14:24
 */
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping("hello")
    public String hello() {
        return "hello 你好";
    }

    @RequestMapping("hello2.do")
    public DatetimeRange hello2(DatetimeRange range) {
        return range;
    }

    @RequestMapping("hello3.do")
    public Date hello2(Date date1) {
        return date1;
    }
}
