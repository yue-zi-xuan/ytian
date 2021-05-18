package com.gcsj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gcsj.Service.NewsService;
import com.gcsj.Utils.OperLog;
import com.gcsj.Utils.logsUtils;
import com.gcsj.mapper.NewsMapper;
import com.gcsj.pojo.CompetitionNews;
import com.gcsj.pojo.News;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "新闻资讯")
public class NewsController {

    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private NewsService newsService;

    /**
     * @author:岳子譞
     * @description :模糊查询
     * @Date:2021/4/21
     */

    @GetMapping("/News/search/{title}")
    public List<News> getNewsLike(@PathVariable("title") String title)
    {
        return newsMapper.getNewsLike(title);
    }

    /**
     * @author:岳子譞
     * @description :新增或修改新闻
     * @Date:2021/4/21
     */

    @PutMapping("News/add")
    @OperLog(operModul = "新闻",operDesc = "新增新闻",operType = "ADD")
    public String add(@Param("news") News news) throws ParseException {
        news.setTime(logsUtils.TransformTime(news.getTime()));
        if (newsService.save(news)) return "success!";
        else return "failed!";
    }

    /**
     * @author:岳子譞
     * @description :修改新闻
     * @Date:2021/4/21
     */

    @PostMapping("News/post")
    @OperLog(operModul = "新闻",operDesc = "修改新闻",operType = "ADD")
    public String post(@Param("news") News news) throws ParseException {
        news.setTime(logsUtils.TransformTime(news.getTime()));
        if (newsService.updateById(news)) return "success!";
        else return "failed!";
    }

    /**
     * @author:岳子譞
     * @description :删除新闻ById
     * @Date:2021/4/21
     */

    @DeleteMapping("/News/del/{id}")
    @OperLog(operModul = "新闻",operDesc = "删除新闻ById",operType = "DEL")
    public void delete(@PathVariable("id")Long id)
    {
        newsService.removeById(id);
    }

    /**
     * @author:岳子譞
     * @description : get新闻ById
     * @Date:2021/4/21
     */

    @GetMapping("/News/get/{id}")
    public News getById(@PathVariable("id")Long id)
    {
        return newsService.getById(id);
    }

    /**
     * @author:岳子譞
     * @description :获取全部News
     * @Date:2021/4/21
     */

    @GetMapping("/News/getAll")
    public List<News> getAll(){
        return newsService.list();
    }

    /**
     * @author:岳子譞
     * @description : 获取新闻标题
     * @Date:2021/4/21
     */

    @GetMapping("News/title/getAll")
     public List<String> getTitle()
     {
         QueryWrapper<News> queryWrapper = new QueryWrapper<>();
         queryWrapper.select("title");
         List<News> list = newsMapper.selectList(queryWrapper);
         final List<String> strings = new ArrayList<String>();
         for (News news:list
              ) {
             strings.add(news.getTitle());
         }
         return strings;
     }

    /**
     * @author:岳子譞
     * @description :分页查询所有
     * @Date:2021/4/21
     * @return:List<News>
     * @param: pn 页数,limit 一页条数
     */

    @GetMapping("/News/page")
    public List<News> page(@RequestParam(value = "{pn}",defaultValue = "1")Integer pn,
                           @RequestParam(value = "{limit}",defaultValue = "5")Integer limit )
    {
        final Page<News> page = new Page<News>(pn, limit);
        final Page<News> page1 = newsService.page(page, null);
        return page1.getRecords();
    }

    /**
     * @author:岳子譞
     * @description :分页查询title
     * @Date:2021/4/21
     * @return:List<String>
     * @param: pn 页数,limit 一页条数
     */

    @GetMapping("/News/pageName")
    public List<String> pageName(@RequestParam(value = "{pn}",defaultValue = "1")Integer pn,
                                 @RequestParam(value = "{limit}",defaultValue = "5")Integer limit )
    {
        final Page<News> page = new Page<News>(pn, limit);
        final Page<News> page1 = newsService.page(page, new QueryWrapper<News>().select("title"));
        List<String> list = new ArrayList<>();
        for (News news:page.getRecords()
        ) {
            list.add(news.getTitle()+"...");
        }
        return list;
    }

}
