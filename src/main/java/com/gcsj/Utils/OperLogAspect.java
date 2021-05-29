package com.gcsj.Utils;


import com.gcsj.annotation.OperLog;
import com.gcsj.mapper.OperationLogMapper;
import com.gcsj.pojo.OperationLog;
import jdk.nashorn.internal.parser.Token;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * @author:岳子譞
 * @description :  面向切面类
 * @Date:2021/4/27
 */

@Aspect
@Component
@Slf4j
public class OperLogAspect {

    @Autowired
    private OperationLogMapper operationLogMapper;


    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.gcsj.annotation.OperLog)")
    public void operLogPoinCut() {
    }


    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "keys")
    public void saveOperLog(JoinPoint joinPoint, Object keys) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        String token = request.getHeader("token");
        String username =(String)JwtTokenUtil.getClaim(token).get("username");

        Object args[] = joinPoint.getArgs();

        OperationLog operlog = new OperationLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperLog opLog = method.getAnnotation(OperLog.class);
            if (opLog != null) {
                //注解类中获得
                String operModul = opLog.operModul();
                String operType = opLog.operType();
                String operDesc = opLog.operDesc();
                operlog.setOperModul(operModul); //填入操作模块
                operlog.setOperType(operType); //填入操作类型
                operlog.setOperDesc(operDesc); //填入操作描述
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;

            operlog.setOperMethod(methodName); // 请求方法
            operlog.setOperTime(new logsUtils().TransformTime());//请求时间
            operlog.setOperAdminName(username); //管理员名称

            // 请求的参数
            // Map<String, String> rtnMap = converMap(request.getParameterMap());//暂时无用
            // 将参数所在的数组转换成String
            String params = null;
            for (int i = 0; i < args.length; i++) {
                params = params  + "第" + (i+1) + "个参数为:" + args[i];
            }
            operlog.setOperRequParam(params);//返回请求参数
            log.info("当前的记录是:"+methodName+params);
            operationLogMapper.insert(operlog);//插入到数据库
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

}