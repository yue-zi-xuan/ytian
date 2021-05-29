package com.gcsj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gcsj.Service.MyAdminService;
import com.gcsj.Service.OperationLogService;
import com.gcsj.Service.superAdminService;
import com.gcsj.Utils.logsUtils;
import com.gcsj.annotation.LoginToken;
import com.gcsj.annotation.OperLog;
import com.gcsj.mapper.MyAdminMapper;
import com.gcsj.pojo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.engine.CommentStructureHandler;

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
    @OperLog(operModul = "超级管理员",operType = "PUT",operDesc = "修改管理员权限")
    public String giveAuthority(@RequestParam("adminId")Long adminId,
                                @RequestParam("label")int label)
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
    @ApiOperation(value = "查看管理员的操作日志")
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

    @PostMapping("/superAdmin/add/admin")
    @LoginToken
    @ApiOperation(value = "添加管理员,label为管理员权限标签,0为只读,1为增删改查")
    @OperLog(operModul = "超级管理员",operType = "ADD",operDesc = "添加管理员")
    public CommonResult addAdmin(@RequestParam("username")String username,
                                 @RequestParam("password")String password,
                                 @RequestParam("label")int label)
    {
        if (myAdminService.list(new QueryWrapper<MyAdmin>().eq("username",username)).size()!=0)
        {
            return new CommonResult(400,"该用户名义已存在!");
        }
        else
        {
            MyAdmin admin = new MyAdmin();
            admin.setUsername(username).setPassword(password).setLabel(label);
            myAdminService.save(admin);
            return new CommonResult(200,"添加成功!-----"+ "管理员" +":"+username +"权限"+":"+label);
        }
    }


    @DeleteMapping("/superAdmin/delete/{id}")
    @ApiOperation(value = "删除管理员")
    @LoginToken
    @OperLog(operModul = "超级管理员",operType = "DEL",operDesc = "删除管理员")
    public CommonResult deleteAdmin(@PathVariable("id")Long id)
    {
        if(myAdminService.removeById(id))
            return new CommonResult(200,"删除成功!");
        else
            return new CommonResult(400,"删除失败");
    }

}
