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
public class Awards {
    @TableId(type= IdType.AUTO)
    private Long AwardsId;
    private String awardsName;
    private Long awardsLevel;
    private Long competitionID;
    private String time;
    private String scan;

    @TableField(exist = false)
    private String year_month;
    @TableField(exist = false)
    private String day;
}
