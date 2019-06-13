package com.deyi.clock.utils;

import java.util.UUID;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UUIDUtils
 * @Description TODO
 * @createTime 2019年06月13日 14:11
 */
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
