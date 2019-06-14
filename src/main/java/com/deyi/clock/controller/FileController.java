package com.deyi.clock.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.deyi.clock.config.core.Result;
import com.deyi.clock.config.core.ResultGenerator;
import com.deyi.clock.utils.FileUtils;
import com.deyi.clock.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName FileController
 * @Description TODO
 * @createTime 2019年06月13日 14:19
 */
@RestController
public class FileController {

    @Value("${web.upload-path}")
    private String path;


    @PostMapping("/imageUpload")
    public Result imageUpload(@RequestParam("file") MultipartFile file) {
        String result_msg = "";//上传结果信息
        Map<String, Object> root = new HashMap<String, Object>();
        if (file.getSize() / 1000 > 500) {
            return ResultGenerator.genFailResult("图片大小不能超过500KB");
        } else {
            //判断上传文件格式
            String fileType = file.getContentType();
            if (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpeg")) {
                //获取文件名
                String fileName = file.getOriginalFilename();
                //获取文件后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                //重新生成文件名
                fileName = UUIDUtils.getUUID() + suffixName;
                if (FileUtils.upload(file, path, fileName)) {
                    System.out.println(fileName);
                    //文件存放的相对路径(一般存放在数据库用于img标签的src)
                    String relativePath = path + fileName;
                    root.put("relativePath", "/image/"+fileName);//前端根据是否存在该字段来判断上传是否成功
                    result_msg = "图片上传成功";
                } else {
                    result_msg = "图片上传失败";
                }
            } else {
                result_msg = "图片格式不正确";
            }
        }
        return ResultGenerator.genSuccessResult(root,result_msg);
    }
}
