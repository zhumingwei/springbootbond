package com.zhumingwei.bond.tool;


import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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

    //#############################################
    static SecretKey secretKey;

    public static SecretKey getSecretKey() {
        if (secretKey == null) {
            KeyGenerator keyGenerator = null;
            try {
                keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);

                //指定长度默认值,密钥长度必须的8的倍数
                keyGenerator.init(56);
                //生成一个密钥
                SecretKey Key = keyGenerator.generateKey();
                //得到加密私钥的byte数组
                byte[] byteKey = Key.getEncoded();

                //key转换
                DESKeySpec desKeySpec = new DESKeySpec(byteKey);
                SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
                secretKey = factory.generateSecret(desKeySpec);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }
        return secretKey;
    }

    static Cipher cipher;

    public static Cipher getCipher() {
        if (cipher == null) {
            try {
                cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
        }
        return cipher;
    }

    public static String getEncryptStr(String str) {
        try {

            //加密

            getCipher().init(Cipher.ENCRYPT_MODE, getSecretKey());
            byte[] result = getCipher().doFinal(str.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(result), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getDecodeStr(String encryptStr) {
        try {

            //解密
            getCipher().init(Cipher.DECRYPT_MODE, getSecretKey());
            byte[] result = getCipher().doFinal(Base64.getDecoder().decode(encryptStr.getBytes(StandardCharsets.UTF_8)));

            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            return "";
        }
    }


    //算法名称
    private static final String KEY_ALGORITHM = "DES";
    //算法名称/加密模式/填充方式
    //DES共有四种工作模式，ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    private static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";



}
