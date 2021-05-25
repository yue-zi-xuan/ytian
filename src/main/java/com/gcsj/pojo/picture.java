package com.gcsj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperationSort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class picture {
    @TableId(type = IdType.AUTO)
    public int id;
    @ApiModelProperty(value = "图片")
    public byte[] pic;
}
