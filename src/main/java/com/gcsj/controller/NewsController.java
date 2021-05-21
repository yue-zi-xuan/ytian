package com.gcsj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gcsj.Service.NewsService;
import com.gcsj.Utils.OperLog;
import com.gcsj.Utils.logsUtils;
import com.gcsj.mapper.NewsMapper;
import com.gcsj.pojo.Awards;
import com.gcsj.pojo.CompetitionNews;
import com.gcsj.pojo.News;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.swagger.annotations.Api;
import lombok.val;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

        return newsService.list(new QueryWrapper<News>().like("title",title));
    }

    /**
     * @author:岳子譞
     * @description :新增或修改新闻
     * @Date:2021/4/21
     */

    @PostMapping("News/add")
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

    @PutMapping("News/put")
    @OperLog(operModul = "新闻",operDesc = "修改新闻",operType = "PUT")
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

        List<News> newsList = newsService.list();
        List<News> list = new ArrayList<>();

        for (News c:newsList
        ) {
            c.setYear_month(c.getTime().substring(0,c.getTime().lastIndexOf("-")));
            c.setDay(c.getTime().substring(c.getTime().lastIndexOf("-")+1,c.getTime().lastIndexOf(" ")));
        }
        return list;
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


    @GetMapping(value = "/news/add20")
    public void add20()
    {
        List<News> list = new ArrayList<News>();

        for (int i = 0; i < 20; i++) {
            list.add(new News(i+10,"cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了cuit成985了" +
                    "cuit成985了cuit成985了cuit成985了" +
                    "cuit成985了cuit成985了","AAA"+i,logsUtils.TransformTime()
                    ,"jok"+i,"sdsdsd","source",0));

        }
        newsService.saveBatch(list);
    }


    public static final String NEWS_PATH_PREFIX = "static/news/";

    @RequestMapping(value = "/news/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file,
    @RequestParam("id")Long id) throws ParseException {

        if (file.isEmpty())
            return "请传入文件";
        String realPath = "src/main/resources/"+ NEWS_PATH_PREFIX;
        String format = logsUtils.TransformTime_hm();
        String oldName = file.getOriginalFilename();
        File dest = new File(realPath);
        if(!dest.isDirectory()){
            //递归生成文件夹
            dest.mkdirs();
        }
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try {
            //构建真实的文件路径
            File newFile = new File(dest.getAbsolutePath()+File.separator+newName);
            System.out.println(dest.getAbsolutePath());
            System.out.println(newFile.getAbsolutePath());
            file.transferTo(newFile);
            String ImageUrl = realPath + file.getOriginalFilename();

            final News news = newsService.getById(id);
            news.setImageUrl(ImageUrl);
            newsService.updateById(news);
            return ImageUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败!";
    }


    @GetMapping(value = "/news/updateId")
    public String UpdateId()
    {
        final List<News> list = newsService.list();
        Long i = 1L;
        for (News a:list
        ) {
            a.setId(i);
            System.out.println(a.getTitle());
            newsMapper.update(null,new UpdateWrapper<News>().eq("title",a.getTitle()).set("id",i));
            i++;
        }
        return "success";
    }

}
