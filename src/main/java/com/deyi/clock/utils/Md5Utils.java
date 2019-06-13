package com.deyi.clock.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName Md5Utils
 * @Description TODO
 * @createTime 2019年06月12日 09:24
 */
public class Md5Utils {

    /**
     * @title md5
     * @description 密码加密
     * @author lyz
     * @param: password
     * @param: salt
     * @updateTime 2019/6/12 0012 09:29
     * @return: java.lang.String
     * @throws
     */
    public static final String[] md5(String password, String salt){
        String[] result =new String[2];
        //加密方式
        String hashAlgorithmName = "MD5";
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        //密码
        Object source = password;
        //加密次数
        int hashIterations = 2;
        SimpleHash resultTemp = new SimpleHash(hashAlgorithmName, source, byteSalt, hashIterations);
        result[0] = byteSalt.toString();
        result[1] = resultTemp.toString();
        return result;
    }

}
