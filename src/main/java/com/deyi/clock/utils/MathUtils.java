package com.deyi.clock.utils;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName MathUtils
 * @Description TODO
 * @createTime 2019年06月01日 14:20
 */
public class MathUtils {

    public static boolean isIntegerValue(double d) {
        return ((long) d) + 0.0 == d;
    }

}
