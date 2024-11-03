package org.example.aop;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.mapper.OperateLogMapper;
import org.example.pojo.OperateLog;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class LogAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private OperateLogMapper operateLogMapper;
    @Around("@annotation(org.example.anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //操作人id--当前登录员工id
        String jwt =  request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer operteUser = (Integer)claims.get("id");
        //操作时间
        LocalDateTime operateTime = LocalDateTime.now();
        //操作类名
        String className = joinPoint.getTarget().getClass().getName();
        //操作方法名
        String methodName = joinPoint.getSignature().getName();
        //操作方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);
        //操作返回值
        long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        String returnValue = JSONObject.toJSONString(result);
        //操作耗时
        Long costTime = end-begin;
        //记录操作日志
        OperateLog operateLog = new OperateLog(null,operteUser,operateTime,
                className,methodName,methodParams,returnValue,costTime);
        operateLogMapper.insert(operateLog);
        return result;
    }
}
