package com.gcsj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gcsj.Service.CompetitionService;
import com.gcsj.Utils.OperLog;
import com.gcsj.mapper.CompetitionMapper;
import com.gcsj.pojo.Competition;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@Api(tags = "竞赛")
public class CompetitionController {


    @Autowired
    CompetitionMapper competitionMapper;
    @Autowired
    CompetitionService completionService;

    /**
     * @author:岳子譞
     * @description :查询ById
     * @Date:2021/4/21
     * @return:Competition
     */
    @GetMapping("/competition/getById")
    public Competition getById(@PathVariable("id")Long id)
    {
            return completionService.getById(id);
    }

    /**
     * @author:岳子譞
     * @description :获取所有name
     * @Date:2021/4/21
     * @return:List<Competition> 只含有name
     */

    @GetMapping("/competition/getAllName")
    public List<String> getAllName()
    {
        List<String> list = new ArrayList<>();
        List<Competition> competitions = completionService.list(new QueryWrapper<Competition>().select("CompetitionName"));
        competitions.forEach(n->list.add(n.getCompetitionName()));
        return list;
    }



    /**
     * @author:岳子譞
     * @description :删除ById
     * @Date:2021/4/21
     * @return:void
     */
    @ResponseBody
    @DeleteMapping("/competition/del/{id}")
    @OperLog(operModul = "竞赛",operDesc = "删除操作",operType = "DEL")
    public void deleteCompetition(@PathVariable("id") Long id){
        competitionMapper.deleteById(id);
    }

    /**
     * @author:岳子譞
     * @description :新增操做
     * @Date:2021/4/21
     * @return:String
     */
    @ResponseBody
    @PutMapping("/competition/add")
    @OperLog(operModul = "竞赛",operDesc = "更新操作",operType = "ADD")
    public String addCompetition(@Param("competition") Competition competition){
        if(completionService.save(competition))
            return "success!";
        else
            return "failed!";
    }

    /**
     * @author:岳子譞
     * @description :新增操做
     * @Date:2021/4/21
     * @return:String
     */
    @ResponseBody
    @PutMapping("/competition/post")
    @OperLog(operModul = "竞赛",operDesc = "修改操作",operType = "POST")
    public String postCompetition(@Param("competition") Competition competition){
        if(completionService.updateById(competition))
            return "success!";
        else
            return "failed!";
    }

    /**
     * @author:岳子譞
     * @description :模糊查询
     * @Date:2021/4/21
     * @return:List<Competition>
     */

    @ResponseBody
    @GetMapping("/competition/search/{CompetitionName}")
    public List<Competition> getCompetitionLike(@PathVariable("CompetitionName")String CompetitionName)
    {
       return competitionMapper.getCompetitionLike(CompetitionName);
    }

    /**
     * @author:岳子譞
     * @description :查询所有
     * @Date:2021/4/21
     * @return:List<Competition>
     */

    @ResponseBody
    @GetMapping("/competition/getAll")
    public List<Competition> getAll()
    {
        return competitionMapper.selectList(null);
    }

    /**
     * @author:岳子譞
     * @description :分页
     * @Date:2021/4/21
     * @param: pn 页数,limit 一页条数
     * @return:List<Competition>
     */

    @ResponseBody
    @GetMapping("/competition/Page")
    public List<Competition> CompetitionPage(@RequestParam(value="{pn}",defaultValue = "1")Integer pn,
                                             @RequestParam(value="{limit}",defaultValue = "10")Integer limit)
    {
        Page<Competition> page = new Page<>(pn, limit);
        Page<Competition> page1 = completionService.page(page, null);
        return page1.getRecords();
    }

    /**
     * @author:岳子譞
     * @description :分页查询Name
     * @Date:2021/4/21
     * @param:pn页数,limit一页的条数
     * @return:List<String>
     */

    @GetMapping("/Competition/pageName")
    public List<String> pageName(@RequestParam(value = "{pn}",defaultValue = "1")Integer pn,
                                 @RequestParam(value = "{limit}",defaultValue = "10")Integer limit){
        Page<Competition> competitionPage = new Page<Competition>(pn,limit);
        final Page<Competition> page = completionService.page(competitionPage,new QueryWrapper<Competition>().select("CompetitionName"));
        List<String> list = new ArrayList<>();
        page.getRecords().forEach(n->list.add(n.getCompetitionName()));
        return list;
    }




}
