package com.gcsj.controller.DataAnalysis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gcsj.Service.CertificateYueService;
import com.gcsj.Service.GPAService;
import com.gcsj.Service.StuCertificateService;
import com.gcsj.Service.StudentService;
import com.gcsj.mapper.StuCertificateMapper;
import com.gcsj.pojo.Certificate_YUE;
import com.gcsj.pojo.GPA;
import com.gcsj.pojo.StuCertificate;
import com.gcsj.pojo.student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.*;

@RestController
@Api(tags = "数据分析")
public class natureController {

    public static final String[] FIRST_NATURE = {"工作","考研","公务员"};
    public Integer value = 0;
    public String name;

    @Autowired
    private StudentService studentService;
    @Autowired
    private GPAService gpaService;
    @Autowired
    private StuCertificateService stuCertificateService;
    @Autowired
    private CertificateYueService certificateYueService;



    public List<student> getAllStudent()
    {
        return studentService.list();
    }

    @GetMapping("/data/nature/first")
    @ApiOperation("考研,工作,公务员数量")
    public HashMap<String, Integer> natureShow()
    {
        List<student> list = getAllStudent();
        final HashMap<String, Integer> maps = new HashMap<String, Integer>();
        for (String str:FIRST_NATURE) {
            maps.put(str, 0);
        }
        for (student s:list
             ) {
            if (maps.containsKey(s.getNature()))
            {
                value = maps.get(s.getNature())+1;
                maps.put(s.getNature(),value);
            }
            else if (!s.getNature().isEmpty())
            {
                value = maps.get(FIRST_NATURE[0])+1;
                maps.put(FIRST_NATURE[0],value);
            }
        }
        value = 0;
        return maps;
    }
    

    @GetMapping("/data/certificate")
    @ApiOperation("证书的数量情况")
    public HashMap<String, Integer> certificateDataShow()
    {
        //获取所有学生信息
        final HashMap<String, Integer> maps = new HashMap<String, Integer>();


        //获取证书ID
        List<StuCertificate> stucertificates = stuCertificateService.list();
        final ArrayList<Long> list = new ArrayList<>();
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
        return  maps;
    }


    @GetMapping("/data/certificate/{nature}")
    @ApiOperation("某个nature的证书情况")
    public HashMap<String, Integer> certificateByNatureDataShow(@PathVariable("nature")String nature)
    {
        //获取学生信息
        final HashMap<String, Integer> maps = new HashMap<String, Integer>();
        List<student> studentList = studentService.list(new QueryWrapper<student>().like("nature",nature));
        if (studentList.size()==0) {
            maps.put("没有此类哦>>U=o=U<<喵!",0);
            return maps;
        }
        //考研学生ID
        final ArrayList<Long> list0= new ArrayList<>();
        studentList.forEach(s->list0.add(s.getStuID()));

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
        return  maps;
    }

    @GetMapping(value = "/data/GPA/{nature}")
    @ApiOperation("某个nature的GPA情况")
    public LinkedHashMap<String, Float> GPA(@PathVariable("nature")String nature) {

        float AVG_GAP1 = 0;
        float AVG_GAP2 = 0;
        float AVG_GAP3 = 0;
        float AVG_GAP4 = 0;
        float AVG_GAP5 = 0;
        float AVG_GAP6 = 0;
        float AVG_GAP7 = 0;
        float AVG_GAP8 = 0;
        float AVG_GAP = 0;


        final LinkedHashMap<String, Float> maps = new LinkedHashMap<String, Float>();
        List<student> studentList = studentService.list(new QueryWrapper<student>().like("nature", nature));
        final ArrayList<Long> list = new ArrayList<>();
        studentList.forEach(s -> list.add(s.getStuID()));
        if (studentList.size() == 0) {
            maps.put("没有此类哦>>U=o=U<<喵!", (float) 0);
            return maps;
        }
        System.out.println(list);

        List<GPA> gpaList = gpaService.list();

        for (int i = 0; i < gpaList.size(); i++) {
            GPA gpa = gpaList.get(i);
            if (list.contains(gpa.getStuID())) {
                AVG_GAP1 += gpa.getGPA1();
                AVG_GAP2 += gpa.getGPA2();
                AVG_GAP3 += gpa.getGPA3();
                AVG_GAP4 += gpa.getGPA4();
                AVG_GAP5 += gpa.getGPA5();
                AVG_GAP6 += gpa.getGPA6();
                AVG_GAP7 += gpa.getGPA7();
                AVG_GAP8 += gpa.getGPA8();
            }
        }

        int size = studentList.size();
        maps.put("GAP1", AVG_GAP1 / size);
        maps.put("GAP2", AVG_GAP2 / size);
        maps.put("GAP3", AVG_GAP3 / size);
        maps.put("GAP4", AVG_GAP4 / size);
        maps.put("GAP5", AVG_GAP5 / size);
        maps.put("GAP6", AVG_GAP6 / size);
        maps.put("GAP7", AVG_GAP7 / size);
        maps.put("GAP8", AVG_GAP8 / size);
        maps.put("GAP", (AVG_GAP1 + AVG_GAP2 + AVG_GAP3 + AVG_GAP4 + AVG_GAP5
                + AVG_GAP6 + AVG_GAP7 + AVG_GAP8) / studentList.size() / 8);
        return maps;
    }

}
