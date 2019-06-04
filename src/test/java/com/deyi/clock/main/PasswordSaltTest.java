package com.deyi.clock.main;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName PasswordSaltTest
 * @Description TODO
 * @createTime 2019年06月04日 15:18
 */
public class PasswordSaltTest {

    @Test
    public void test() throws Exception {
        System.out.println(new com.deyi.clock.config.shiro.ByteSource("lyz"));
        System.out.println(md5("123456","lyp"));
    }

    public static final String md5(String password, String salt){
        //加密方式
        String hashAlgorithmName = "MD5";
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        System.out.println(byteSalt);
        //密码
        Object source = password;
        //加密次数
        int hashIterations = 2;
        SimpleHash result = new SimpleHash(hashAlgorithmName, source, byteSalt, hashIterations);
        return result.toString();
    }

}
