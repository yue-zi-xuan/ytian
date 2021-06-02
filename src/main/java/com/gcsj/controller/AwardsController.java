package com.gcsj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gcsj.Service.AwardsService;
import com.gcsj.annotation.LoginToken;
import com.gcsj.annotation.OperLog;
import com.gcsj.mapper.AwardsMapper;
import com.gcsj.pojo.Awards;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
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


        List<Awards> list = awardsMapper.selectList(null);
        for (Awards c:list
        ) {
            if (c.getTime()!=null)
            {
                c.setYear_month(c.getTime().substring(0,c.getTime().lastIndexOf("-")));
                c.setDay(c.getTime().substring(c.getTime().lastIndexOf("-")+1,c.getTime().lastIndexOf(" ")));
            }
        }
        return list;
    }


    /**
     * @author:岳子譞
     * @description :查询所有奖项的Name
     * @Date:2021/4/21
     * @return:List<Awards>
     */
    @ResponseBody
    @GetMapping("/Awards/getAllName")
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
       return awardsService.list(new QueryWrapper<Awards>().like("awardsName",awardsName));
    }

    /**
     * @author:岳子譞
     * @description :删除ById
     * @Date:2021/4/21
     * @return:void
     */

    @DeleteMapping("/interceptor/Awards/del/{AwardsId}")
    @LoginToken
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

    @PostMapping("/Awards/add")
    @LoginToken(value = false)
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
    @PutMapping("/Awards/put")
    @LoginToken(value = false)
    @OperLog(operModul = "奖项",operDesc = "修改操作",operType = "PUT")
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



//    @RequestMapping(value = "/awards/upload", method = RequestMethod.POST)
//    public String upload(@RequestParam("file") MultipartFile file,
//                         @RequestParam("name")String name,@RequestParam("fileDir")String fileDir) throws ParseException {
//
//        if (file.isEmpty())
//            return "文件为空!";
//        String realPath = "src/main/resources/static/"+fileDir+"/";
//        File dest = new File(realPath);
//        if(!dest.isDirectory()){
//            //递归生成文件夹
//            dest.mkdirs();
//        }
//        String newName = name + ".jpg";
//        try {
//            //构建真实的文件路径
//            File newFile = new File(dest.getAbsolutePath()+File.separator+newName);
//            System.out.println(dest.getAbsolutePath());
//            System.out.println(newFile.getAbsolutePath());
//            file.transferTo(newFile);
//            return "上传成功!";
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "上传失败!";
//    }

//    @Autowired
//    private PictureMapper pictureMapper;
//
//    @PostMapping("/awards/upload/{id}")
//    @ApiOperation(value = "上传图片")
//    public String savePic(@RequestParam("file") MultipartFile file, @PathVariable("id")int id) {
//        if (file.isEmpty()) {
//            return "上传失败，请选择文件";
//        }
//        try {
//            InputStream is = file.getInputStream();
//            byte[] pic = new byte[(int) file.getSize()];
//            is.read(pic);
//            final picture picture = new picture();
//            picture.setPic(pic);
//            pictureMapper.insert(picture);
//            final Awards awards = awardsService.getById(id);
//            final List<picture> pictures = pictureMapper.selectList(null);
//
//            int pId;
//            if (pictures.size()==0)
//            {
//                pId = 1;
//            }
//            else
//            {
//                pId = pictures.get(pictures.size()-1).getId();
//            }
//            awards.setPicId(pId);
//            awardsService.updateById(awards);
//
//
//            return "上传成功";
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "success";
//    }
//
//
//    @GetMapping(value="/awards/getPhoto/{id}")
//    @Async
//    @ApiOperation(value = "获取图片,通过表格表记录的ID")
//    public void getPhotoById(@PathVariable("id")int id, final HttpServletResponse response) throws IOException {
//        picture p = pictureMapper.selectById(id);
//        byte[] data = p.getPic();
//        response.setContentType("image/png");
//        response.setCharacterEncoding("UTF-8");
//        OutputStream outputSream = response.getOutputStream();
//        InputStream in = new ByteArrayInputStream(data);
//        int len = 0;
//        byte[] buf = new byte[1024];
//        while ((len = in.read(buf, 0, 1024)) != -1) {
//            outputSream.write(buf, 0, len);
//        }
//        outputSream.close();
//    }

    //已被封印,需要时解封即可
//    public String UpdateId()
//    {
//        final List<Awards> list = awardsService.list();
//        Long i = 1L;
//        for (Awards a:list
//             ) {
//            a.setAwardsId(i);
//            awardsService.update(a,new QueryWrapper<Awards>().eq("awardsName",a.getAwardsName()));
//            i++;
//        }
//       return "success";
//    }
}
