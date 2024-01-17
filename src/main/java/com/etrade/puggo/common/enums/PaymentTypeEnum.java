package com.etrade.puggo.common.enums;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付方式枚举类型
 * @date 2024/1/16 11:04
 */
public enum PaymentTypeEnum {

    CARD("card"),
    APPLE("apple_pay"),
    GOOGLE("google_pay"),
    WECHAT("wechat"),
    ALIPAY("alipay"),
    ;

    private final String type;

    PaymentTypeEnum(String paymentType) {
        this.type = paymentType;
    }


    public String getType() {
        return this.type;
    }

}
