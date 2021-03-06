package com.gcsj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gcsj.Service.NoticeService;
import com.gcsj.annotation.LoginToken;
import com.gcsj.annotation.OperLog;
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
        List<notice> list = noticeService.list(new QueryWrapper<notice>().orderByDesc("time"));
        for (notice c:list
        ) {
           if (c.getTime()!=null){
               c.setYear_month(c.getTime().substring(0,c.getTime().lastIndexOf("-")));
               c.setDay(c.getTime().substring(c.getTime().lastIndexOf("-")+1,c.getTime().lastIndexOf(" ")));
               c.setTime(c.getTime().substring(0,c.getTime().lastIndexOf(" ")));
           }
        }
        return list;
    }


    @GetMapping("/notice/getById/{id}")
    public notice noticeGetByID(@PathVariable("id")Long id)
    {
        return noticeService.getById(id);
    }


    @PostMapping("/notice/add")
    @LoginToken(value = false)
    @OperLog(operModul = "通知",operType = "ADD",operDesc = "添加通知")
    public String addNotice(@Param("notices") notice notices)
    {
        if (noticeService.save(notices))
            return "success!";
        else
            return "fail";
    }

    @PutMapping("/notice/updateById")
    @LoginToken(value = false)
    @OperLog(operModul = "通知",operType = "POST",operDesc = "修改通知")
    public String updateNotice(@Param("notice") notice notices)
    {
        if (noticeService.updateById(notices))
            return "success!";
        else
            return "fail!";
    }

    @DeleteMapping("/notice/delete/{id}")
    @LoginToken(value = false)
    @OperLog(operModul = "通知",operType = "DELETE",operDesc = "删除通知")
    public String noticeDelete(@PathVariable("id")Long id)
    {
        if (noticeService.removeById(id))
            return "success!";
        else return "fail!";

    }
}
