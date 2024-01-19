package com.etrade.puggo.common.enums;

import lombok.Getter;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 用户银行卡枚举
 * @date 2024/1/19 18:35
 */
@Getter
public enum CardTypeEnum {

    /**
     * 借记卡
     */
    debit,

    /**
     * 信用卡
     */
    credit;


    public static boolean isValid(String name) {
        for (CardTypeEnum e : values()) {
            if (e.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
