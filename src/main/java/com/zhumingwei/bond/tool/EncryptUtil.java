package com.zhumingwei.bond.tool;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.KeySpec;

public class EncryptUtil {

    public static final String DEFAULTSALT = "zhudafu2018";

    public static String getHmacMd5Str(String s) {
        return getHmacMd5Str(s, DEFAULTSALT);
    }

    public static String getHmacMd5Str(String s, String keyString) {
        String sEncodedString = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes(StandardCharsets.UTF_8), "HmacMD5");
            Mac mac = Mac.getInstance(key.getAlgorithm());
            mac.init(key);

            byte[] bytes = mac.doFinal(s.getBytes(StandardCharsets.UTF_8));

            StringBuffer hash = new StringBuffer();

            byte2string(bytes, hash);
            sEncodedString = hash.toString();
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sEncodedString;
    }

    public static String getSaltMD5(String str) {
        return getSaltMD5(str, DEFAULTSALT);
    }

    public static String getSaltMD5(String str, String salt) {
        return getMD5(getMD5(str) + salt);
    }

    public static String getMD5(String str) {
        String sEncodedString = null;
        try {

            // 生成一个MD5加密计算摘要

            MessageDigest md = MessageDigest.getInstance("MD5");

            // 计算md5函数
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            md.update(bytes);

            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符

            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值

            StringBuffer hash = new StringBuffer();

            byte2string(md.digest(), hash);
            sEncodedString = hash.toString();
        } catch (Exception e) {

            throw new RuntimeException("MD5加密出现错误");

        }
        return sEncodedString;
    }

    private static void byte2string(byte[] bytes, StringBuffer hash) {
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hash.append('0');
            }
            hash.append(hex);
        }
    }

}
