package com.gcsj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gcsj.Service.MyAdminService;
import com.gcsj.Service.OperationLogService;
import com.gcsj.Utils.JwtTokenUtil;
import com.gcsj.annotation.LoginToken;
import com.gcsj.Utils.logsUtils;
import com.gcsj.annotation.OperLog;
import com.gcsj.mapper.MyAdminMapper;
import com.gcsj.pojo.CommonResult;
import com.gcsj.pojo.Constants;
import com.gcsj.pojo.MyAdmin;
import com.gcsj.pojo.OperationLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "普通管理员")
@Slf4j
public class MyAdminController {



    @Autowired
    private MyAdminMapper myAdminMapper;
    @Autowired
    private MyAdminService myAdminService;
    @Autowired
    OperationLogService operationLogService;

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
//    @GetMapping( "/Admin/login/")
//    public String login(@RequestParam("username")String username,
//                        @RequestParam("password")String password,
//                        HttpServletResponse response)
//    {
//        final Constants constants = new Constants();
//        if (username.isEmpty())
//            return constants.MESSAGE_UsrName_NULL;
//        else if(myAdminService.getOne(new QueryWrapper<MyAdmin>().
//            eq("username",username).eq("password",password))==null)
//            return constants.MESSAGE_UerNamePassWord_Error;
//        else {
//            logsUtils.SetUerName(username);
//             label =  myAdminService.list(new QueryWrapper<MyAdmin>().eq("username",username)).get(0).getLabel();
//             System.out.println(label);
//             Map<String,Object> map = new HashMap<>();
//             map.put(username,password);
//             String token = JwtTokenUtil.generate(map);
////             response.setHeader("token",token);
//             return constants.MESSAGE_Success;
//        }
//    }

    @RequestMapping(value = "/Admin/login/", method = RequestMethod.POST)
    public String login(@RequestParam("username")String username
                        ,@RequestParam("password")String password
                    , HttpSession session
                    , HttpServletRequest request
                    , HttpServletResponse response)
    {
        return myAdminService.login(username.trim(),password.trim(),session,request,response);
    }


    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 删除session里面的用户信息
        session.removeAttribute("SYSTEM_USER_SESSION");

        Cookie cookie_username = new Cookie("cookie_username", "");
        // 设置cookie的持久化时间，0
        cookie_username.setMaxAge(0);
        // 设置为当前项目下都携带这个cookie
        cookie_username.setPath(request.getContextPath());
        // 向客户端发送cookie
        response.addCookie(cookie_username);
        return "logout";
    }


    @PutMapping("/Admin/update/")
    @LoginToken(value = false)
    public CommonResult updateAdmin(@RequestParam("username")String username,
                                    @RequestParam("oldPassword")String oldPassword,
                                    @RequestParam("newPassword")String newPassword){
        // 成功返回200,失败返回400

        if (username.isEmpty())
            return new  CommonResult(400,"用户名为空!");
        else {
            final MyAdmin admin = myAdminService.getOne(new QueryWrapper<MyAdmin>().
                    eq("username", username).eq("password", oldPassword));
            if(admin ==null)
                return new  CommonResult(400,"账号或密码错误!");
            else {
                admin.setPassword(newPassword);
                myAdminService.updateById(admin);
                return new CommonResult(200,"修改成功!");
            }

        }
    }
    @LoginToken(value = false)
    @GetMapping("/getMessage")

    public String getMessage(){
        System.out.println("你已通过验证");
        return "你已通过验证";
    }
    //===================================================================================
    /**
     * @author:岳子譞
     * @description :所有查询超级管理员
     * @Date:2021/4/21
     * @return:List<superAdmin>
     */
    @GetMapping("/superAdmin/getAll")
    public List<MyAdmin> getAllAdmin()
    {
        return myAdminService.list();
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

    @PostMapping("/superAdmin/add/admin")
    @LoginToken(value = true)
    @ApiOperation(value = "添加管理员,label为管理员权限标签,0为普通管理员,1为超级管理员")
    @OperLog(operModul = "超级管理员",operType = "ADD",operDesc = "添加管理员")
    public CommonResult addAdmin(@RequestParam("username")String username,
                                 @RequestParam("password")String password,
                                 @RequestParam("label")int label)
    {
        if (myAdminService.list(new QueryWrapper<MyAdmin>().eq("username",username)).size()!=0)
        {
            System.out.println("400");
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
    @LoginToken(value = true)
    @OperLog(operModul = "超级管理员",operType = "DEL",operDesc = "删除管理员")
    public CommonResult deleteAdmin(@PathVariable("id")Long id)
    {
        if(myAdminService.removeById(id))
            return new CommonResult(200,"删除成功!");
        else
            return new CommonResult(400,"删除失败");
    }

}
