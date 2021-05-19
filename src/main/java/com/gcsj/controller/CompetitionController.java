package com.gcsj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gcsj.Service.CompetitionService;
import com.gcsj.Utils.OperLog;
import com.gcsj.Utils.logsUtils;
import com.gcsj.mapper.CompetitionMapper;
import com.gcsj.pojo.Awards;
import com.gcsj.pojo.Competition;
import com.gcsj.pojo.CompetitionNews;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
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
    @PutMapping("/competition/put")
    @OperLog(operModul = "竞赛",operDesc = "修改操作",operType = "PUT")
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
    @GetMapping("/competition/search/{Name}")
    public List<Competition> getCompetitionLike(@PathVariable("Name")String Name)
    {
       List<Competition> list = completionService.list(new QueryWrapper<Competition>().like("CompetitionName",Name));
       return  list;
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

        List<Competition> list = competitionMapper.selectList(null);
        for (Competition c:list
        ) {
            c.setYear_month(c.getCompetitionTime().substring(0,c.getCompetitionTime().lastIndexOf("-")));
            c.setDay(c.getCompetitionTime().substring(c.getCompetitionTime().lastIndexOf("-")+1,c.getCompetitionTime().lastIndexOf(" ")));
        }
        return list;
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


    public static final String PATH_PREFIX = "static/competition/";

    @RequestMapping(value = "/awards/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("id")Long id) throws ParseException {

        if (file.isEmpty())
            return "请传入文件";
        String realPath = "src/main/resources/"+ PATH_PREFIX;
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
            final Competition competition = completionService.getById(id);
            competition.setImageUrl(ImageUrl);
            completionService.updateById(competition);
            return ImageUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败!";
    }


}
