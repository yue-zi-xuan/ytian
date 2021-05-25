package com.gcsj.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcsj.Service.CertificateYueService;
import com.gcsj.Service.StuCertificateService;
import com.gcsj.Service.StudentService;
import com.gcsj.mapper.StuCertificateMapper;
import com.gcsj.pojo.Certificate_YUE;
import com.gcsj.pojo.CommonResult;
import com.gcsj.pojo.StuCertificate;
import com.gcsj.pojo.student_y;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class StuCertificateServiceImpl extends ServiceImpl<StuCertificateMapper, StuCertificate> implements StuCertificateService {
    @Autowired
    StuCertificateService stuCertificateService;
    @Autowired
    CertificateYueService certificateYueService;
    @Autowired
    StudentService studentService;

    @Override
    public HashMap<String, Integer> getStudentMapByNature(String nature) {

        int value = 0;
        //获取学生信息
        final HashMap<String, Integer> maps = new HashMap<String, Integer>();
        final List<CommonResult> commonResults = new ArrayList<>();
        final CommonResult result = new CommonResult();


        List<student_y> studentYList = studentService.list(new QueryWrapper<student_y>().like("nature",nature));
        if (studentYList.size()==0) {
            commonResults.add(result.setName("没有此类哦!"));
        }
        //考研学生ID
        final ArrayList<Long> list0= new ArrayList<>();
        studentYList.forEach(s->list0.add(s.getStuID()));

        //获取证书ID
        List<StuCertificate> stucertificates = stuCertificateService.list();
        final ArrayList<Long> list = new ArrayList<>();
        for (int i = 0; i < stucertificates.size(); i++) {
            if (list0.contains(stucertificates.get(i).getStuID()))
            {
                list.add(stucertificates.get(i).getCertificateID());
            }
        }
        List<Certificate_YUE> certificateYueList = certificateYueService.list();
        for (Long id:list
        ) {
            int i  = id.intValue()-1;

            if(maps.containsKey(certificateYueList.get(i).getCertificateName())){
                value = maps.get(certificateYueList.get((i)).getCertificateName())+ 1;
                maps.put(certificateYueList.get(i).getCertificateName(), value);
            }

            else
            {
                maps.put(certificateYueList.get((i)).getCertificateName(),1);
            }

        }
        return maps;


    }

    @Override
    public HashMap<String, Integer> getAllStudentMap() {
        int value = 0;
        final HashMap<String, Integer> maps = new HashMap<String, Integer>();


        //获取所有学生信息
        List<StuCertificate> stucertificates = stuCertificateService.list();
        final ArrayList<Long> list = new ArrayList<>();
        //获取证书ID
        stucertificates.forEach(s->list.add(s.getCertificateID()));
        List<Certificate_YUE> certificateYueList = certificateYueService.list();
        for (Long id:list
        ) {
            int i  = id.intValue()-1;

            if(maps.containsKey(certificateYueList.get(i).getCertificateName())){
                value = maps.get(certificateYueList.get((i)).getCertificateName())+ 1;
                maps.put(certificateYueList.get(i).getCertificateName(), value);
            }

            else
            {
                maps.put(certificateYueList.get((i)).getCertificateName(),0);
            }
        }
        return maps;
    }
}
