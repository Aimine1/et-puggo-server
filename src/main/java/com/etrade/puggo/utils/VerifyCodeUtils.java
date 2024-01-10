package com.etrade.puggo.utils;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;

/**
 * @author niuzhenyu
 * @description : 验证码生成器
 * @date 2023/5/22 17:22
 **/
@Slf4j
public class VerifyCodeUtils {

    /**
     * 生产的验证码位数
     */
    private static final int GenerateVerificationCodeLength = 6;

    private static final String[] MetaCode = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e",
        "f", "g", "h", "i",
        "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E",
        "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};


    public static String generate() {
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();
        while (verificationCode.length() < GenerateVerificationCodeLength) {
            int i = random.nextInt(MetaCode.length);
            verificationCode.append(MetaCode[i]);
        }
        return verificationCode.toString();
    }

    public static String aiIdentifyNo() {
        return "AI" + generate().toUpperCase();
    }


}
