package com.gcsj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gcsj.Service.CompetitionNewsService;
import com.gcsj.annotation.LoginToken;
import com.gcsj.annotation.OperLog;
import com.gcsj.Utils.logsUtils;
import com.gcsj.mapper.CompetitionNewsMapper;
import com.gcsj.mapper.PictureMapper;
import com.gcsj.pojo.CompetitionNews;
import com.gcsj.pojo.picture;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
        return competitionNewsService.list(new QueryWrapper<CompetitionNews>().like("title",title));
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
        List<CompetitionNews> competitionNews = competitionNewsService.list();
        competitionNews.forEach(n->list.add(n.getTitle()));
        return list;
    }

    /**
     * @author:岳子譞
     * @description :修改竞赛新闻
     * @Date:2021/4/21
     * @return:String
     */

    @PutMapping("/interceptor/competitionNews/put")
    @LoginToken(value = false)
    @OperLog(operModul = "竞赛新闻",operDesc = "修改操作",operType = "PUT")
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

    @PostMapping("/interceptor/competitionNews/add")
    @LoginToken(value = false)
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

    @DeleteMapping("/interceptor/competitionNews/del/{id}")
    @LoginToken(value = false)
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

        List<CompetitionNews> list = competitionNewsMapper.selectList(new QueryWrapper<CompetitionNews>().
                orderByDesc("time"));
        for (CompetitionNews c:list
             ) {
           if (c.getTime()!=null){
               c.setYear_month(c.getTime().substring(0,c.getTime().lastIndexOf("-")));
               c.setDay(c.getTime().substring(c.getTime().lastIndexOf("-")+1,c.getTime().lastIndexOf(" ")));
               c.setTime(c.getTime().substring(0,c.getTime().lastIndexOf(" ")));
           }
        }
        return list;
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
        queryWrapper.select("title").orderByDesc("time");
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



//    public static final String NEWS_PATH_PREFIX = "static/competition/news/";
//    @RequestMapping(value = "/CompetitionNews/upload", method = RequestMethod.POST)
//    public String upload(@RequestParam("file") MultipartFile file,
//                         @RequestParam("id")Long id) throws ParseException {
//
//        if (file.isEmpty())
//            return "请传入文件";
//        String realPath = "src/main/resources/"+ NEWS_PATH_PREFIX;
//        String format = logsUtils.TransformTime_hm();
//        String oldName = file.getOriginalFilename();
//        File dest = new File(realPath);
//        if(!dest.isDirectory()){
//            //递归生成文件夹
//            dest.mkdirs();
//        }
//        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
//        try {
//            //构建真实的文件路径
//            File newFile = new File(dest.getAbsolutePath()+File.separator+newName);
//            System.out.println(dest.getAbsolutePath());
//            System.out.println(newFile.getAbsolutePath());
//            file.transferTo(newFile);
//            String ImageUrl = realPath + file.getOriginalFilename();
//
//            final CompetitionNews competitionNews = competitionNewsService.getById(id);
//            competitionNews.setImageUrl(ImageUrl);
//            competitionNewsService.updateById(competitionNews);
//            return ImageUrl;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "上传失败!";
//
//    }

    @Autowired
    PictureMapper pictureMapper;

    @PostMapping("/competitionNews/upload/{id}")
    @ApiOperation(value = "上传图片")
    public String savePic(@RequestParam("file") MultipartFile file, @PathVariable("id")int id) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        try {
            InputStream is = file.getInputStream();
            byte[] pic = new byte[(int) file.getSize()];
            is.read(pic);
            final picture picture = new picture();
            picture.setPic(pic);
            pictureMapper.insert(picture);
            CompetitionNews competitionNews = competitionNewsService.getById(id);
            final List<picture> pictures = pictureMapper.selectList(null);

            int pId;
            if (pictures.size()==0)
            {
                pId = 1;
            }
            else
            {
                pId = pictures.get(pictures.size()-1).getId();
            }
            competitionNews.setPicId(pId);
            competitionNewsService.updateById(competitionNews);


            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }


    @GetMapping(value="/competitionNews/getPhoto/{id}")
    @Async
    @ApiOperation(value = "获取图片,通过表格表记录的ID")
    public void getPhotoById(@PathVariable("id")int id, final HttpServletResponse response) throws IOException {
        picture p = pictureMapper.selectById(id);
        byte[] data = p.getPic();
        response.setContentType("image/png");
        response.setCharacterEncoding("UTF-8");
        OutputStream outputSream = response.getOutputStream();
        InputStream in = new ByteArrayInputStream(data);
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = in.read(buf, 0, 1024)) != -1) {
            outputSream.write(buf, 0, len);
        }
        outputSream.close();
    }


    @GetMapping(value = "/competitionNews/updateId")
    public String UpdateId()
    {
        final List<CompetitionNews> list = competitionNewsService.list();
        Long i = 1L;
        for (CompetitionNews a:list
        ) {
            a.setId(i);
            System.out.println(a.getTitle());
            competitionNewsMapper.update(null,new UpdateWrapper<CompetitionNews>().eq("title",a.getTitle()).set("id",i));
            i++;
        }
        return "success";
    }
}

