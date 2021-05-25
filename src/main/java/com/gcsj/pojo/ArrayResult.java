package com.gcsj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ArrayResult <T,S>{
    public String msg = "success!";
    public T[] data1;
    public S[] data2;
}
