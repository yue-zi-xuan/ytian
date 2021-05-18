package com.gcsj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class OperationLog {
    @TableId(type= IdType.AUTO)
    private String operId; // 主键ID
    private String OperModul; // 操作模块
    private String OperType; // 操作类型{ADD,DEL,GET.....}
    private String OperDesc; // 操作描述
    private String OperMethod; // 请求方法
    private String OperRequParam; // 请求参数
    private String OperAdminName; // 请求管理员Name
    private String OperTime; // 请求时间
}
