package com.gcsj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gcsj.Service.CompetitionNewsService;
import com.gcsj.Utils.OperLog;
import com.gcsj.Utils.logsUtils;
import com.gcsj.mapper.CompetitionNewsMapper;
import com.gcsj.pojo.Competition;
import com.gcsj.pojo.CompetitionNews;
import io.swagger.annotations.Api;
import lombok.val;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "竞赛资讯")
public class CompetitionNewsController {

    @Autowired
    private CompetitionNewsMapper competitionNewsMapper;
    @Autowired
    private CompetitionNewsService competitionNewsService;

    /**
     * @author:岳子譞
     * @description :模糊查询
     * @Date:2021/4/21
     * @return:List<Competition>
     */

    @GetMapping("/Competition/News/search/{title}")
    public List<CompetitionNews> getNewsLike(@PathVariable("title") String title) {
        return competitionNewsMapper.getCompetitionNewsLike(title);
    }

    /**
     * @author:岳子譞
     * @description :获取新闻的所有title
     * @Date:2021/4/21
     * @return:List<CompetitionNews>
     */

    @GetMapping("/CompetitionNews/getAllName")
    public List<String> getAllName() {
        List<String> list = new ArrayList<>();
        List<CompetitionNews> competitionNews = competitionNewsMapper.selectList(new QueryWrapper<CompetitionNews>().select("title"));
        competitionNews.forEach(n->list.add(n.getTitle()));
        return list;
    }

    /**
     * @author:岳子譞
     * @description :修改竞赛新闻
     * @Date:2021/4/21
     * @return:String
     */

    @PostMapping("competitionNews/post")
    @OperLog(operModul = "竞赛新闻",operDesc = "修改操作",operType = "POST")
    public String post(@Param("competitionNews") CompetitionNews competitionNews) throws ParseException {
        competitionNews.setTime(logsUtils.TransformTime(competitionNews.getTime()));
        if (competitionNewsService.updateById(competitionNews)) return "success!";
        else return "failed!";
    }

    /**
     * @author:岳子譞
     * @description : 新增竞赛新闻
     * @Date:2021/4/21
     * @return:String
     */

    @PutMapping("competitionNews/add")
    @OperLog(operModul = "竞赛新闻",operDesc = "新增竞赛新闻",operType = "ADD")
    public String add(@Param("competitionNews") CompetitionNews competitionNews) throws ParseException {
        competitionNews.setTime(logsUtils.TransformTime(competitionNews.getTime()));
        if (competitionNewsService.save(competitionNews)) return "success!";
        else return "failed!";
    }

    /**
     * @author:岳子譞
     * @description :删除新闻ById
     * @Date:2021/4/21
     * @return:void
     */

    @DeleteMapping("/competitionNews/del/{id}")
    @OperLog(operModul = "竞赛新闻",operDesc = "删除新闻ById",operType = "DEL")
    public void delete(@PathVariable("id") Long id) {
        competitionNewsService.removeById(id);
    }

    /**
     * @author:岳子譞
     * @description : get新闻ById
     * @Date:2021/4/21
     * @return:ompetition
     */

    @GetMapping("/competitionNews/get/{id}")
    public CompetitionNews getById(@PathVariable("id") Long id) {
        return competitionNewsService.getById(id);
    }

    /**
     * @author:岳子譞
     * @description :获取全部竞赛News
     * @Date:2021/4/21
     * @return:List<Competition>
     */

    @GetMapping("/competitionNews/getAll")
    public List<CompetitionNews> getAll() {
        return competitionNewsService.list();
    }

    /**
     * @author:岳子譞
     * @description : 获取竞赛新闻标题
     * @Date:2021/4/21
     * @return:List<String>
     */

    @GetMapping("competitionNews/title/getAll")
    public List<String> getTitle() {
        QueryWrapper<CompetitionNews> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("title");
        List<CompetitionNews> list = competitionNewsMapper.selectList(queryWrapper);
        final List<String> strings = new ArrayList<String>();
        for (CompetitionNews competitionNews : list
        ) {
            strings.add(competitionNews.getTitle() + "...");
        }
        return strings;
    }

    /**
     * @author:岳子譞
     * @description :分页查询所有
     * @Date:2021/4/21
     * @return:List<Competition>
     * @param: pn 页数,limit 一页条数
     */

    @GetMapping("/CompetitionNews/page")
    public List<CompetitionNews> page(@RequestParam(value = "{pn}",defaultValue = "1")Integer pn,
                                      @RequestParam(value = "{limit}",defaultValue = "5")Integer limit)
    {
        final Page<CompetitionNews> page = new Page<CompetitionNews>(pn, limit);
        final Page<CompetitionNews> page1 = competitionNewsService.page(page, null);
        return page1.getRecords();
    }

    /**
     * @author:岳子譞
     * @description :分页查询title
     * @Date:2021/4/21
     * @return:List<String>
     * @param: pn 页数,limit 一页条数
     */

    @GetMapping("/CompetitionNews/pageName")
    public List<String> pageName(@RequestParam(value = "{pn}",defaultValue = "1")Integer pn,
                                 @RequestParam(value = "{limit}",defaultValue = "5")Integer limit)
    {
        final Page<CompetitionNews> page = new Page<CompetitionNews>(pn, limit);
        final Page<CompetitionNews> page1 = competitionNewsService.page(page, new QueryWrapper<CompetitionNews>().select("title"));
        List<String> list = new ArrayList<>();
        for (CompetitionNews competitionNews:page.getRecords()
        ) {
            list.add(competitionNews.getTitle()+"...");
        }
        return list;
    }
}

