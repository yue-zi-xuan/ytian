package com.gcsj.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcsj.Service.MyAdminService;
import com.gcsj.Utils.JwtTokenUtil;
import com.gcsj.mapper.MyAdminMapper;
import com.gcsj.pojo.Constants;
import com.gcsj.pojo.MyAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Service
public class MyAdminServiceImpl extends ServiceImpl<MyAdminMapper,MyAdmin> implements MyAdminService {

    @Autowired
    private MyAdminMapper myAdminMapper;
    @Autowired
    private MyAdminService myAdminService;


    @Override
    public String login(String username
            , String password
            , HttpSession session
            , HttpServletRequest request
            , HttpServletResponse response) {
        final Constants constants = new Constants();
        if (username.isEmpty())
            return constants.MESSAGE_UsrName_NULL;
        else if (myAdminService.getOne(new QueryWrapper<MyAdmin>().
                eq("username", username).eq("password", password)) == null)
            return constants.MESSAGE_UerNamePassWord_Error;
        else {
            MyAdmin admin = myAdminService.getOne(new QueryWrapper<MyAdmin>().
                    eq("username", username).eq("password", password));
            int label = admin.getLabel();
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", username);
            claims.put("password", password);
            claims.put("label", label);
            String token = JwtTokenUtil.generate(claims);
            int s = (int) JwtTokenUtil.getClaim(token).get("label");

            String s1 = (String) JwtTokenUtil.getClaim(token).get("username");
            System.out.println(username + ":" + label);

            response.setHeader("token", token);

            session.setAttribute("SYSTEM_USER_SESSION", admin);
            // 保存cookie，实现自动登录
            Cookie cookie_username = new Cookie("cookie_username", username);
            // 设置cookie的持久化时间，7天
            cookie_username.setMaxAge(7 * 24 * 60 * 60);
            // 设置为当前项目下都携带这个cookie
            cookie_username.setPath(request.getContextPath());
            // 向客户端发送cookie
            response.addCookie(cookie_username);
            return constants.MESSAGE_Success;
        }
    }
}
