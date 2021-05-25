package com.gcsj.controller.DataAnalysis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gcsj.Service.CertificateYueService;
import com.gcsj.Service.GPAService;
import com.gcsj.Service.StuCertificateService;
import com.gcsj.Service.StudentService;
import com.gcsj.pojo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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


    public List<student_y> getAllStudent()
    {
        return studentService.list();
    }

    @GetMapping("/data/nature/first")
    @ApiOperation("考研,工作,公务员数量")
    public List<CommonResult> natureShow()
    {
        List<student_y> list = getAllStudent();
        final HashMap<String, Integer> maps = new HashMap<String, Integer>();
        for (String str:FIRST_NATURE) {
            maps.put(str, 0);
        }
        for (student_y s:list
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
        ArrayList<CommonResult> commonResults = new ArrayList<CommonResult>();
        Set<String> keySet =  maps.keySet();
//        keySet.forEach(r->System.out.println(r));
        for (String set :keySet
        ) {
//            System.out.println(set);
            commonResults.add(new CommonResult().setName(set).setValue(maps.get(set)));
        }

        return commonResults;
    }
    

    @GetMapping("/data/certificate")
    @ApiOperation("证书的数量情况")
    public List<CommonResult> certificateDataShow()
    {
        HashMap<String,Integer> maps = stuCertificateService.getAllStudentMap();
        final List<CommonResult> commonResults = new ArrayList<>();

        final Set<String> keySet = maps.keySet();
        for (String set :keySet
        ) {
            commonResults.add(new CommonResult().setName(set).setValue(maps.get(set)));
        }
        return commonResults;
    }


    @GetMapping("/data/certificate/{nature}")
    @ApiOperation("某个nature的证书情况")
    public List<CommonResult> certificateByNatureDataShow(@PathVariable("nature")String nature)
    {

        HashMap<String,Integer> maps = stuCertificateService.getStudentMapByNature(nature);
        final List<CommonResult> commonResults = new ArrayList<>();

        final Set<String> keySet = maps.keySet();
        for (String set :keySet
        ) {
            commonResults.add(new CommonResult().setName(set).setValue(maps.get(set)));
        }

        return commonResults;

    }



    @GetMapping("/data/certificate/array")
    @ApiOperation("证书数组")
    public ArrayResult<String,Integer> certificateDataArray()
    {


        HashMap<String,Integer> maps = stuCertificateService.getAllStudentMap();
        Integer[] values =  new Integer[maps.size()];
        String names[] = new String[maps.size()];
        final Set<String> keySet =  maps.keySet();
        int i = 0;
        for (String set:keySet
             ) {
            values[i++] = maps.get(set);
        }
        i = 0;
        for (String set:keySet
        ) {
            names[i++] = set;
        }
        final ArrayResult<String, Integer> result = new ArrayResult<>();
        result.setData1(names).setData2(values);
        return result;

    }





    @GetMapping(value = "/data/GPA/{nature}")
    @ApiOperation("某个nature的GPA情况")
    public List<GPAResult> GPA(@PathVariable("nature")String nature) {

        final List<GPAResult> commonResults = new ArrayList<>();
        final GPAResult gpaResult = new GPAResult();

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

        List<student_y> studentYList = studentService.list(new QueryWrapper<student_y>().like("nature", nature));
        final ArrayList<Long> list = new ArrayList<>();
        studentYList.forEach(s -> list.add(s.getStuID()));
        if (studentYList.size() == 0) {
           commonResults.add(gpaResult.setName("没有此类哦!"));
           return commonResults;
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

        int size = studentYList.size();
        maps.put("GAP1", AVG_GAP1 / size);
        maps.put("GAP2", AVG_GAP2 / size);
        maps.put("GAP3", AVG_GAP3 / size);
        maps.put("GAP4", AVG_GAP4 / size);
        maps.put("GAP5", AVG_GAP5 / size);
        maps.put("GAP6", AVG_GAP6 / size);
        maps.put("GAP7", AVG_GAP7 / size);
        maps.put("GAP8", AVG_GAP8 / size);
        maps.put("GAP", (AVG_GAP1 + AVG_GAP2 + AVG_GAP3 + AVG_GAP4 + AVG_GAP5
                + AVG_GAP6 + AVG_GAP7 + AVG_GAP8) / studentYList.size() / 8);



        final Set<String> keySet = maps.keySet();
        for (String set :keySet
        ) {
            commonResults.add(new GPAResult().setName(set).setValue(maps.get(set)));
        }
        return commonResults;

    }


    @GetMapping("data/GPA/array/value/{nature}")
    @ApiOperation(value = "绩点的数组")
    public ArrayResult<String, Float> GPAValue(@PathVariable("nature")String nature)
    {
        final List<GPAResult> commonResults = new ArrayList<>();
        final GPAResult gpaResult = new GPAResult();

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

        List<student_y> studentYList = studentService.list(new QueryWrapper<student_y>().like("nature", nature));
        final ArrayList<Long> list = new ArrayList<>();
        studentYList.forEach(s -> list.add(s.getStuID()));
        if (studentYList.size() == 0) {
            commonResults.add(gpaResult.setName("没有此类哦!"));
            final ArrayResult<String, Float> arrayResult = new ArrayResult<>();
            arrayResult.setMsg("没有此类哦!");
            return arrayResult;
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

        int size = studentYList.size();
        maps.put("GAP1", AVG_GAP1 / size);
        maps.put("GAP2", AVG_GAP2 / size);
        maps.put("GAP3", AVG_GAP3 / size);
        maps.put("GAP4", AVG_GAP4 / size);
        maps.put("GAP5", AVG_GAP5 / size);
        maps.put("GAP6", AVG_GAP6 / size);
        maps.put("GAP7", AVG_GAP7 / size);
        maps.put("GAP8", AVG_GAP8 / size);
        maps.put("GAP", (AVG_GAP1 + AVG_GAP2 + AVG_GAP3 + AVG_GAP4 + AVG_GAP5
                + AVG_GAP6 + AVG_GAP7 + AVG_GAP8) / studentYList.size() / 8);



        final Set<String> keySet = maps.keySet();
        for (String set :keySet
        ) {
            commonResults.add(new GPAResult().setName(set).setValue(maps.get(set)));
        }
        final List<GPAResult> gpa = commonResults;
        Float[] values = new Float[gpa.size()];
        for (int i = 0; i < gpa.size(); i++) {
            values[i] = gpa.get(i).getValue();
        }
        String[] gpaNames = {"AVG_GAP1","AVG_GAP2","AVG_GAP3","AVG_GAP4","AVG_GAP5","AVG_GAP6","AVG_GAP7","AVG_GAP8"};
        final ArrayResult<String, Float> result = new ArrayResult<>();
        result.setData1(gpaNames).setData2(values);
        return result;
    }

}
