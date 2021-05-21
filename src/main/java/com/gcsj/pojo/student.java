package com.gcsj.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class student {
    private Long StuID;
    private String StuName;
    private String whereAbouts;
    private String nature;
    private String gender;
}
