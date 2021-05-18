package com.gcsj.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("admin")
public class MyAdmin {
    @TableId(type= IdType.AUTO)
    private Long adminId;
    private String username;
    private String password;
    private Long label;
}
