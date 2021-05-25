package com.gcsj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
    @TableId(type= IdType.AUTO)
    private long id;
    private String content;
    private String title;
    private String time;    //操作时间

    @TableField(exist = false)
    private String year_month;
    @TableField(exist = false)
    private String day;

    private String author;
    private int picId;
    private String source;
    private int visits;
}
