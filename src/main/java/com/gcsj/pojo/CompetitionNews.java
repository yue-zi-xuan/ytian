package com.gcsj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CompetitionNews {
    @TableId(type= IdType.AUTO)
    private long id;
    private String content;
    private String title;
    private String time;    //操作时间
    private String author;
    private String imageUrl;
    private String source;
    private int visits;
}

