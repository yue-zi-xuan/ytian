package com.gcsj.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "stu_certificate")
public class StuCertificate {
    private Long StuID;
    private Long certificateID;
    private String time;
    private  String scan;
}
