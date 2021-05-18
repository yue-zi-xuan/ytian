package com.gcsj.pojo;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Constants {
    public String MESSAGE_UsrName_NULL = "账号为空!";
    public String MESSAGE_PassWord_NULL = "密码为空!";
    public String MESSAGE_UerNamePassWord_Error = "账号或密码错误!";
    public String MESSAGE_Success = "登陆成功!";
    public static String ADD = "ADD";
    public static String DELETE = "DELETE";
    public static String GETBYID = "GetById";
    public static String POST = "POST";
    public static String PUT = "PUT";
}
