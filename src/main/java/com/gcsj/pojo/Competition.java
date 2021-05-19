package com.gcsj.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 这是竞赛实体类类
 * author:岳子譞
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@TableName(value = "competition")
public class Competition {

    @TableId(type= IdType.AUTO)
    private Long id;
    private String CompetitionName;
    private String CompetitionContent;
    private String CompetitionBenefits;
    private String CompetitionTime;
    private Long visits;
    private String officiaWebsite;
    private  String ImageUrl;

    @TableField(exist = false)
    private String year_month;
    @TableField(exist = false)
    private String day;

}
