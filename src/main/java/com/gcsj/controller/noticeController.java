package com.gcsj.controller;

import com.gcsj.Service.NoticeService;
import com.gcsj.Utils.OperLog;
import com.gcsj.Utils.OperType;
import com.gcsj.mapper.NoticeMapper;
import com.gcsj.pojo.notice;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@Api(tags = "通知")
public class noticeController {
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/notice/getAll")
    public List<notice> getAll(){
        List<notice> list = noticeService.list();
        for (notice c:list
        ) {
            c.setYear_month(c.getTime().substring(0,c.getTime().lastIndexOf("-")));
            c.setDay(c.getTime().substring(c.getTime().lastIndexOf("-")+1,c.getTime().lastIndexOf(" ")));
        }
        return list;
    }


    @GetMapping("/notice/getById/{id}")
    public notice noticeGetByID(@PathVariable("id")Long id)
    {
        return noticeService.getById(id);
    }


    @PostMapping("/notice/add")
    @OperLog(operModul = "通知",operType = "ADD",operDesc = "添加通知")
    public String addNotice(@Param("notices") notice notices)
    {
        if (noticeService.save(notices))
            return "success!";
        else
            return "fail";
    }

    @PutMapping("/notice/updateById")
    @OperLog(operModul = "通知",operType = "POST",operDesc = "修改通知")
    public String updateNotice(@Param("notice") notice notices)
    {
        if (noticeService.updateById(notices))
            return "success!";
        else
            return "file!";
    }

    @DeleteMapping("/notice/delete/{id}")
    @OperLog(operModul = "通知",operType = "DELETE",operDesc = "删除通知")
    public String noticeDelete(@PathVariable("id")Long id)
    {
        if (noticeService.removeById(id))
            return "success!";
        else return "fail!";

    }





}
