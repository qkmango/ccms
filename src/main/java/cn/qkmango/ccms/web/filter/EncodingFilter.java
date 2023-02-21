package cn.qkmango.ccms.web.filter;

import jakarta.servlet.*;
import java.io.IOException;

/**
 * 编码过滤器
 * <p>可以将请求和响应的乱码问题解决</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2021-01-28 10:10
 */
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        //过滤post请求中文乱码
        req.setCharacterEncoding("UTF-8");
        //过滤响应流中文乱码
        resp.setContentType("text/html;charset=utf-8");

        //将请求放行
        chain.doFilter(req, resp);
    }


    /**
     * 这里是一个坑，filter必须重写init()方法
     *
     * @param filterConfig a <code>FilterConfig</code> object containing the
     *                     filter's configuration and initialization parameters
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }
}
