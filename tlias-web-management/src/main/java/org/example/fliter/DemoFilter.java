package org.example.fliter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

//@WebFilter(urlPatterns = "/*")
public class DemoFilter implements Filter {
    @Override//初始化方法 只调用一次
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("init 初始化方法执行");
    }

    @Override//销毁方法 只调用一次
    public void destroy() {
        Filter.super.destroy();
        System.out.println("destroy 销毁方法执行");
    }

    @Override//每次拦截都会调用
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("拦截到请求");
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
