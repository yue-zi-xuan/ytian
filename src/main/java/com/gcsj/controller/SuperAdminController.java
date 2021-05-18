package com.gcsj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gcsj.Service.MyAdminService;
import com.gcsj.Service.OperationLogService;
import com.gcsj.Service.superAdminService;
import com.gcsj.Utils.logsUtils;
import com.gcsj.mapper.MyAdminMapper;
import com.gcsj.pojo.Constants;
import com.gcsj.pojo.MyAdmin;
import com.gcsj.pojo.OperationLog;
import com.gcsj.pojo.superAdmin;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "超级管理员")
public class SuperAdminController {

    @Autowired
    private superAdminService MySuperAdminService;
    @Autowired
    private MyAdminService myAdminService;
    @Autowired
    private MyAdminMapper myAdminMapper;
    @Autowired
    private OperationLogService operationLogService;


    /**
     * @author:岳子譞
     * @description :所有查询超级管理员
     * @Date:2021/4/21
     * @return:List<superAdmin>
     */
    @GetMapping("/superAdmin/getAll")
    public List<superAdmin> getAllAdmin()
    {
        return MySuperAdminService.list();
    }

    /**
     * @author:岳子譞
     * @description : 修改管理员权限
     * @Date:2021/4/21
     * @return:List<Competition>
     */

    @PutMapping("/superAdmin/give")
    public String giveAuthority(@RequestParam("adminId")Long adminId,
                                @RequestParam("label")Long label)
    {
        MyAdmin admin = myAdminMapper.selectById(adminId);
        admin.setLabel(label);
        if (myAdminService.update(admin,new QueryWrapper<MyAdmin>().eq("adminId",adminId)))
            return "success!";
        else
            return "failed!";
    }

    /**
     * @author:岳子譞
     * @description : 查看管理员操作日志
     * @Date:2021/4/21
     * @return:List<Competition>
     */

    @GetMapping("/superAdmin/oper")
    public List<OperationLog> getAllOperation()
    {
        return operationLogService.list();
    }

    /**
     * @author:岳子譞
     * @description :登录
     * @Date:2021/4/21
     * @return:String
     */
    @GetMapping("/superAdmin/login/")
    public String login(@RequestParam("username")String username,
                        @RequestParam("password")String password) {
        final Constants constants = new Constants();
        if (username.isEmpty())
            return constants.MESSAGE_UsrName_NULL;
        else if (MySuperAdminService.getOne(new QueryWrapper<superAdmin>().
                eq("username", username).eq("password", password)) == null)
            return constants.MESSAGE_UerNamePassWord_Error;
        else {
            logsUtils.SetUerName(username);
            return constants.MESSAGE_Success;
        }
    }

}
