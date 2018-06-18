package com.zhumingwei.bond.tool;

/**
 * @author zhumingwei
 * @date 2018/6/17 下午12:19
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        } else {
            return str.isEmpty();
        }
    }
}
