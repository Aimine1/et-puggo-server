package com.etrade.puggo.common.enums;

import lombok.Getter;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 系统配置枚举
 * @date 2024/1/20 19:29
 */
@Getter
public enum SettingsEnum {

    /**
     * 系统IM账号
     */
    systemImAction,

    /**
     * 系统全局货币
     */
    moneyKind,

    /**
     * 商品税百分比
     */
    productTaxPercentage,

    /**
     * same-day delivery 费用
     */
    sameDayDeliveryCharge,

    /**
     * 快递类型
     */
    shippingMethods,


    ;


    public String v() {
        return this.name();
    }

}
