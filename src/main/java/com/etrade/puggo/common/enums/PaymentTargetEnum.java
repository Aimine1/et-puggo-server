package com.etrade.puggo.common.enums;

import lombok.Getter;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付目的
 * @date 2024/1/22 14:42
 */
@Getter
public enum PaymentTargetEnum {

    ai,
    product;

    public static boolean isValid(String paymentType) {
        for (PaymentTargetEnum e : values()) {
            if (e.name().equals(paymentType)) {
                return true;
            }
        }
        return false;
    }

}
