package com.gcsj.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class superAdmin {
    @TableId(type= IdType.AUTO)
    private Long superAdminId;
    private String username;
    private String password;

}
