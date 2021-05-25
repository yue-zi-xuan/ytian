package com.gcsj.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gcsj.pojo.StuCertificate;
import io.swagger.models.auth.In;

import java.util.HashMap;

public interface StuCertificateService extends IService<StuCertificate> {

    HashMap<String, Integer>getStudentMapByNature(String nature);
    HashMap<String, Integer>getAllStudentMap();

}
