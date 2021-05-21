package com.gcsj.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CommonResult <T>{
    private Integer value;
    private String  name;
    private T       data;

    public CommonResult(Integer code,String message)
    {
        this(code,message,null);
    }
}
