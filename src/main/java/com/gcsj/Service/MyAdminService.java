package com.gcsj.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gcsj.pojo.MyAdmin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public interface MyAdminService extends IService<MyAdmin> {

    String login(String username,
                 String session, HttpSession request, HttpServletRequest password,
                 HttpServletResponse response);
}
