package cn.qkmango.ccms.mvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return "hello";
        }
}
