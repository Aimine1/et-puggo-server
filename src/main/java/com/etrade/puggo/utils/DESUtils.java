package com.etrade.puggo.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * @author niunafei
 * @description DES 对称性算法加密 解密工具类 可逆性算法
 * @date 2018/12/12  下午2:05
 */
public class DESUtils {

    private static final String DES = "DES";

    /**
     * 公钥  8位以上
     */
    private static final String SECRET_KEY = "9SUxbh8MqcVLkukgjW";

    /**
     * 获取秘钥对象
     *
     * @return
     * @throws Exception
     */
    private static SecretKey getSecretKeyFactory() throws Exception {
        SecretKeyFactory des = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = des.generateSecret(new DESKeySpec(SECRET_KEY.getBytes()));
        return secretKey;
    }

    /**
     * 加密
     *
     * @param param
     * @return
     * @throws Exception
     */
    public static String encrypt(String param) throws Exception {
        Cipher cipher = Cipher.getInstance(DES);
        SecretKey secretKey = getSecretKeyFactory();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return new String(Base64.encodeBase64(cipher.doFinal(param.getBytes())));
    }

    /**
     * 解密
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static String decrypt(String value) throws Exception {
        Cipher cipher = Cipher.getInstance(DES);
        SecretKey secretKey = getSecretKeyFactory();
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.decodeBase64(value.getBytes())));
    }

}