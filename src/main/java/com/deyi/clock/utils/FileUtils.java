package com.deyi.clock.utils;

import com.alibaba.fastjson.JSON;
import com.deyi.clock.utils.log.LogUtils;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName FileUtils
 * @Description TODO
 * @createTime 2019年06月13日 14:12
 */
public class FileUtils {

    protected static final Logger PLATFORM_LOGGER = LogUtils.getPlatformLogger();

    /**
     * @param file     文件
     * @param path     文件存放路径
     * @param fileName 保存的文件名
     * @return
     */
    public static boolean upload(MultipartFile file, String path, String fileName) {

        //确定上传的文件名
        String realPath = path + "\\" + fileName;
        File dest = new File(realPath);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();// 新建文件夹
        }
        try {
            //保存文件
            file.transferTo(dest);
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 获取路径下的所有文件/文件夹
     *
     * @param directoryPath  需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @return
     */
    public static List<String> getAllFile(String directoryPath, boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (isAddDirectory) {
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(), isAddDirectory));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }

    /**
     * @title fileNameMaxList
     * @description 得到文件夹内规则最大数
     * @author lyz
     * @param: path
     * @updateTime 2019/6/15 0015 16:12
     * @return: java.lang.Integer
     * @throws
     */
    public static Integer fileNameMaxList(String path) {
        System.out.println(path);
        Set<Integer> integers = new HashSet<>();
        List<String> fileList = getAllFile(path, true);
        Integer num = 0;
        for (String str : fileList) {
            String[] arrTemp = str.split("\\\\");
            //取出文件名
            String fileName = arrTemp[arrTemp.length - 1];
            String suffixName = fileName.split("\\.")[0];
            String[] numArr = suffixName.split("_");
            if (isInteger(numArr[numArr.length - 1])) {
                num = Integer.valueOf(numArr[numArr.length - 1]);
                integers.add(num);
            }
        }
        if (integers.size() > 0) {
            num = Collections.max(integers);
        }

        return num+1;
    }

    /**
     * @title delFile
     * @description 删除文件
     * @author lyz
     * @param: path
     * @param: filename
     * @updateTime 2019/6/15 0015 16:33
     * @throws
     */
    public static boolean deleteFile(String pathname){
        boolean result = false;
        File file = new File(pathname);
        if (file.exists()) {
            file.delete();
            result = true;
        }
        return result;
    }

    /**
     * @title isInteger
     * @description 判断是否为数字
     * @author lyz
     * @param: str
     * @updateTime 2019/6/15 0015 16:11
     * @return: boolean
     * @throws
     */
    public static boolean isInteger(String str) {
        boolean b = true;
        try {
            Integer.valueOf(str);
        } catch (Exception e) {
            b = false;
        }
        return b;
    }

}