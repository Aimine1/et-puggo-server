package com.etrade.puggo.common.enums;

import lombok.Getter;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 买家地址枚举
 * @date 2024/1/19 14:07
 */
@Getter
public enum AddressTypeEnum {

    /**
     * 收货地址
     */
    delivery,

    /**
     * 账单地址
     */
    billing;


    public static boolean isValid(String name) {
        for (AddressTypeEnum e : values()) {
            if (e.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
