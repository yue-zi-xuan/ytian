package com.gcsj.filter;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gcsj.Service.MyAdminService;
import com.gcsj.annotation.JwtIgnore;
import com.gcsj.Utils.JwtTokenUtil;
import com.gcsj.annotation.LoginToken;
import com.gcsj.pojo.MyAdmin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private MyAdminService adminService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        // 获得cookie
//        Cookie[] cookies = request.getCookies();
//        // 没有cookie信息，则重定向到登录界面
//        if (null == cookies) {
//            response.sendRedirect(request.getContextPath() + "/Admin/login/");
//            return false;
//        }
//        // 定义cookie_username，用户的一些登录信息，例如：用户名，密码等
//        String cookie_username = null;
//        // 获取cookie里面的一些用户信息
//        for (Cookie item : cookies) {
//            if ("cookie_username".equals(item.getName())) {
//                cookie_username = item.getValue();
//                break;
//            }
//        }
//        // 如果cookie里面没有包含用户的一些登录信息，则重定向到登录界面
//        if (StringUtils.isEmpty(cookie_username)) {
//            response.sendRedirect(request.getContextPath() + "/Admin/login/");
//            return false;
//        }
//        // 获取HttpSession对象
//        HttpSession session = request.getSession();
//        // 获取我们登录后存在session中的用户信息，如果为空，表示session已经过期
//        Object obj = session.getAttribute("SYSTEM_USER_SESSION");
//        if (null == obj) {
//            // 根据用户登录账号获取数据库中的用户信息
//            MyAdmin admin = adminService.getOne(new QueryWrapper<MyAdmin>().eq("username",cookie_username));
//            // 将用户保存到session中
//            session.setAttribute("SYSTEM_USER_SESSION", admin);
//        }
        // 已经登录

        // 从http请求头中取出token
        final String token = request.getHeader("token");
        //如果不是映射到方法，直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //如果是方法探测，直接通过
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        //如果方法有JwtIgnore注解，直接通过
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method=handlerMethod.getMethod();
        if (method.isAnnotationPresent(JwtIgnore.class)) {
            JwtIgnore jwtIgnore = method.getAnnotation(JwtIgnore.class);
            if(jwtIgnore.value()){
                return true;
            }
        }


        if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if (loginToken.value())
            {
                if ((int)JwtTokenUtil.getClaim(token).get("label")!=1) {
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("您没有权限!","403");
                    return false;
                }
                JwtTokenUtil.isSigned(token);
                JwtTokenUtil.verify(token);
                response.setHeader("通过","200");
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }


}