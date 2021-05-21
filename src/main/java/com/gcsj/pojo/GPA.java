package com.gcsj.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "schoolsituation")
public class GPA {
    private Long id;
    private Long stuID;
    private float GPA1;
    private float GPA2;
    private float GPA3;
    private float GPA4;
    private float GPA5;
    private float GPA6;
    private float GPA7;
    private float GPA8;
    @TableField(exist = false)
    private float AVG_GPA;
}
