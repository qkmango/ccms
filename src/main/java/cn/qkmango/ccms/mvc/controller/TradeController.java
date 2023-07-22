package cn.qkmango.ccms.mvc.controller;

import jakarta.annotation.Resource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-22 13:43
 */
@Validated
@RestController
@RequestMapping("/trade")
public class TradeController {

    @Resource
    private ReloadableResourceBundleMessageSource ms;



}
