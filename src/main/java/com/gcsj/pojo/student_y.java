package com.gcsj.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value= "student")
public class student_y {
    private Long StuID;
    private String StuName;
    private String whereAbouts;
    private String nature;
    private String gender;
}
