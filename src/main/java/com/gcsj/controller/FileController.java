package com.gcsj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.ParseException;

@RestController
public class FileController {
    @RequestMapping(value = "/awards/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("name")String name,@RequestParam("fileDir")String fileDir) throws ParseException {

        if (file.isEmpty())
            return "文件为空!";
        String realPath = "src/main/resources/static/"+fileDir+"/";
        File dest = new File(realPath);
        if(!dest.isDirectory()){
            //递归生成文件夹
            dest.mkdirs();
        }
        String newName = name + ".jpg";
        try {
            //构建真实的文件路径
            File newFile = new File(dest.getAbsolutePath()+File.separator+newName);
            System.out.println(dest.getAbsolutePath());
            System.out.println(newFile.getAbsolutePath());
            file.transferTo(newFile);
            return "上传成功!";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败!";
    }
}
