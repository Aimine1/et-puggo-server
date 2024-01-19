package com.etrade.puggo.common.enums;

import lombok.Getter;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 交易方式枚举
 * @date 2024/1/18 17:56
 */
@Getter
public enum ShippingMethodEnum {


    PublicMeetup(1, "Public Meetup"),

    StandardShipping(2, "Standard Shipping"),

    SameDayDelivery(4, "Puggo Same-day Delivery");

    private final int type;
    private final String name;

    ShippingMethodEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static boolean isValid(int type) {
        for (ShippingMethodEnum e : values()) {
            if (e.getType() == type) {
                return true;
            }
        }
        return false;
    }

}
