package org.example.fliter;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.utils.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.example.utils.JwtUtils;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        //1.获取请求url
        String url = req.getRequestURI().toString();
        log.info("请求的url:{}",url);
        //2.判断请求url中是否包含login，如果是login，放行
        if(url.contains("login")){
            log.info("登录操作，放行");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //3.获取请求头中的令牌
        String jwt = req.getHeader("token");
        //4.判断令牌是否存在，如果不存在，返回错误结果
        if(StringUtils.isEmpty(jwt)){
            log.info("请求头token为空，返回未登录信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象->json
            String noLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(noLogin);
            return;
        }
        //5.解析token，如果解析失败，返回错误信息
        try{
            JwtUtils.parseJWT(jwt);
        }catch (Exception e){
            e.printStackTrace();
            log.info("解析令牌失败，返回错误信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象->json
            String noLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(noLogin);
            return;
        }
        //6.放行
        log.info("令牌合法");
        filterChain.doFilter(servletRequest, servletResponse);



    }
}
