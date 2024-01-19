package com.etrade.puggo.common.enums;

import lombok.Getter;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付方式枚举类型
 * @date 2024/1/16 11:04
 */
@Getter
public enum PaymentTypeEnum {

    card,

    apple_pay,

    google_pay,

    wechat,

    alipay;

    public static boolean isValid(String paymentType) {
        for (PaymentTypeEnum e : values()) {
            if (e.name().equals(paymentType)) {
                return true;
            }
        }
        return false;
    }

}
