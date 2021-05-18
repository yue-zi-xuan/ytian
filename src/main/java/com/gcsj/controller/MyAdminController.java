package com.gcsj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gcsj.Service.MyAdminService;
import com.gcsj.Utils.logsUtils;
import com.gcsj.mapper.MyAdminMapper;
import com.gcsj.pojo.Constants;
import com.gcsj.pojo.MyAdmin;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Api(tags = "普通管理员")
public class MyAdminController {

    @Autowired
    private MyAdminMapper myAdminMapper;
    @Autowired
    private MyAdminService myAdminService;

    /**
     * @author:岳子譞
     * @description :获取所有管理员信息
     * @Date:2021/4/21
     * @return:List<Competition>
     */
    @GetMapping("Admin/getAll")
    public List<MyAdmin> getAll()
    {
        return myAdminMapper.selectList(null);
    }


    /**
     * @author:岳子譞
     * @description :获取管理员信息ById
     * @Date:2021/4/21
     * @return:List<Competition>
     */
    @GetMapping("/Admin/get/{adminId}")
    public MyAdmin getById(@PathVariable("adminId")Long adminId)
    {
        //System.out.println(myAdminService.getById(adminId).toString());
        return myAdminMapper.selectById(adminId);
    }

    /**
     * @author:岳子譞
     * @description :登录
     * @Date:2021/4/21
     * @return:String
     */
    @GetMapping("/Admin/login/")
    public String login(@RequestParam("username")String username,
                        @RequestParam("password")String password)
    {
        final Constants constants = new Constants();
        if (username.isEmpty())
            return constants.MESSAGE_UsrName_NULL;
        else if(myAdminService.getOne(new QueryWrapper<MyAdmin>().
            eq("username",username).eq("password",password))==null)
            return constants.MESSAGE_UerNamePassWord_Error;
        else {
            logsUtils.SetUerName(username);
            return constants.MESSAGE_Success;
        }


    }
}
