package com.gcsj.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class notice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String time;
    private String source;

    @TableField(exist = false)
    private String year_month;
    @TableField(exist = false)
    private String day;
}
