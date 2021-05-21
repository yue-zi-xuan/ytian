package com.gcsj.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "company")
public class CompanyYtian {
    private Long ID;
    private String companyName;
    private String scale;
    private String nature;
    private int visit;
}
