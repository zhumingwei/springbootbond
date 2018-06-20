package com.zhumingwei.bond.tool;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

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


    //kotlin巨坑byte，short不支持位运算
    private static void byte2string(byte[] bytes, StringBuffer hash) {
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hash.append('0');
            }
            hash.append(hex);
        }
    }


    public static String getEncryptStr(String str) {
        //TODO 对称加密的方法做
        byte[] bytes = Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String getDecodeStr(String encryptStr){
        //TODO 对称加密的方法做
        byte[] bytes = encryptStr.getBytes(StandardCharsets.UTF_8);
        return new String(Base64.getDecoder().decode(bytes), StandardCharsets.UTF_8);
    }


}
