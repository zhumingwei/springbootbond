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

    public static boolean checkPhoneNum(String phone){
        if(isEmpty(phone)){
            return false;
        }
        String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        return phone.matches(PHONE_NUMBER_REG);
    }

}


