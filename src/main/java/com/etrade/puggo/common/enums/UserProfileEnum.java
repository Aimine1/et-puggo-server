package com.etrade.puggo.common.enums;

import lombok.Getter;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 用户偏好设置枚举
 * @date 2024/1/20 19:29
 */
@Getter
public enum UserProfileEnum {

    /**
     * 卖家接受报价时，商品自动标记为已预定
     */
    autoOccupy,


    ;


    public String v() {
        return this.name();
    }

}
