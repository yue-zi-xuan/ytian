package com.gcsj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gcsj.Service.MyAdminService;
import com.gcsj.Utils.JwtTokenUtil;
import com.gcsj.annotation.LoginToken;
import com.gcsj.Utils.logsUtils;
import com.gcsj.annotation.OperLog;
import com.gcsj.mapper.MyAdminMapper;
import com.gcsj.pojo.CommonResult;
import com.gcsj.pojo.Constants;
import com.gcsj.pojo.MyAdmin;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "普通管理员")
@Slf4j
public class MyAdminController {

    private static int label = 1;

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
    public String login(@RequestParam("username")String username,
                        @RequestParam("password")String password,
                        HttpServletResponse response)
    {
        final Constants constants = new Constants();
        if (username.isEmpty())
            return constants.MESSAGE_UsrName_NULL;
        else if(myAdminService.getOne(new QueryWrapper<MyAdmin>().
                eq("username",username).eq("password",password))==null)
            return constants.MESSAGE_UerNamePassWord_Error;
        else {
            Map<String,Object>  claims = new HashMap<>();
            claims.put("username",username);
            claims.put("password",password);
            claims.put("label",label);
            String token = JwtTokenUtil.generate(claims);
            int s = (int) JwtTokenUtil.getClaim(token).get("label");

            String s1 = (String) JwtTokenUtil.getClaim(token).get("username");
            System.out.println(username+":"+label);

            response.setHeader("token",token);
            return constants.MESSAGE_Success;
        }
    }

    @PutMapping("Admin/update/")
    @LoginToken
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
    @LoginToken(value = true)
    @GetMapping("/getMessage")

    public String getMessage(){
        System.out.println("你已通过验证");
        return "你已通过验证";
    }

}
