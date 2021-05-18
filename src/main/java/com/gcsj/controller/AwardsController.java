package com.gcsj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gcsj.Service.AwardsService;
import com.gcsj.Utils.OperLog;
import com.gcsj.mapper.AwardsMapper;
import com.gcsj.pojo.Awards;
import com.gcsj.pojo.Constants;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "竞赛奖项")
public class AwardsController {


    @Autowired
    AwardsMapper awardsMapper;
    @Autowired
    AwardsService awardsService;


    /**
     * @author:岳子譞
     * @description :查询所有
     * @Date:2021/4/21
     * @return:List<Awards>
     */

    @ResponseBody
    @GetMapping("/Awards/getAll")
    public List<Awards> getAll() {
        return awardsMapper.selectList(null);
    }


    /**
     * @author:岳子譞
     * @description :查询所有奖项的Name
     * @Date:2021/4/21
     * @return:List<Awards>
     */
    @ResponseBody
    @GetMapping("/Awards/getAllName")
    @OperLog(operModul = "奖项",operDesc = "获取所有奖项名称",operType = "GET")
    public List<String> getAllName() {
        List<String> list = new ArrayList<>();
        List<Awards>awards =awardsMapper.selectList(null);
        awards.forEach(n->list.add(n.getAwardsName()));
        return list;
    }


    /**
     * @author:岳子譞
     * @description :查询ById
     * @Date:2021/4/21
     * @return:Awards
     */


    @GetMapping("/Awards/get/{id}")
    public Awards getByID(@PathVariable("id") Integer id) {
        return awardsMapper.selectById(id);
    }

    /**
     * @author:岳子譞
     * @description :模糊查询
     * @Date:2021/4/21
     * @return:List<Awards>
     */

    @GetMapping("/Awards/search/{AwardsName}")
    public List<Awards> getAwardsLike(@PathVariable("AwardsName") String awardsName)
    {
        return awardsMapper.getAwardsLike(awardsName);
    }

    /**
     * @author:岳子譞
     * @description :删除ById
     * @Date:2021/4/21
     * @return:void
     */

    @DeleteMapping("/Awards/del/{AwardsId}")
    @OperLog(operModul = "竞赛奖项",operType = "DEL",operDesc = "删除ById")
    public void delete(@PathVariable("AwardsId") Long AwardsId)
    {
     awardsService.removeById(AwardsId);
    }

    /**
     * @author:岳子譞
     * @description :新增操作
     * @Date:2021/4/21
     * @return:String
     */

    @PutMapping("/Awards/add")
    @OperLog(operModul = "奖项",operDesc = "新增操作",operType = "ADD")
    public String add(@Param("awards") Awards awards)
    {
        if(awardsService.save(awards))
            return "success!";
        else
            return "failed!";
    }
    /**
     * @author:岳子譞
     * @description :修改操作
     * @Date:2021/4/21
     * @return:String
     */
    @PostMapping("/Awards/post")
    @OperLog(operModul = "奖项",operDesc = "新增操作",operType = "ADD")
    public String post(@Param("awards") Awards awards)
    {
        if(awardsService.updateById(awards))
            return "success!";
        else
            return "failed!";
    }

    /**
     * @author:岳子譞
     * @description :分页操作
     * @Date:2021/4/21
     * @param:pn页数,limit一页的条数  defaultValue = "1"
     * @return:List<Awards>
     */

    @GetMapping("/Awards/page")
    public List<Awards> page(@RequestParam(value = "{pn}",defaultValue = "1")Integer pn,
                             @RequestParam(value = "{limit}",defaultValue = "10")Integer limit){
        Page<Awards> awardsPage = new Page<Awards>(pn,limit);
        final Page<Awards> page = awardsService.page(awardsPage);
        return page.getRecords();
    }

    /**
     * @author:岳子譞
     * @description :分页查询Name
     * @Date:2021/4/21
     * @param:pn页数,limit一页的条数
     * @return:List<String>
     */

    @GetMapping("/Awards/pageName")
    public List<String> pageName(@RequestParam(value = "{pn}",defaultValue = "1")Integer pn,
                             @RequestParam(value = "{limit}",defaultValue = "10")Integer limit){
        Page<Awards> awardsPage = new Page<Awards>(pn,limit);
        final Page<Awards> page = awardsService.page(awardsPage,new QueryWrapper<Awards>().select("awardsName"));
        List<String> list = new ArrayList<>();
        List<Awards> awards = page.getRecords();
        awards.forEach(n->list.add(n.getAwardsName()));
        return list;
    }
}
