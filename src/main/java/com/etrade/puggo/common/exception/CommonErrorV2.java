package com.etrade.puggo.common.exception;

import com.etrade.puggo.common.enums.LangErrorEnum;

/**
 * @Description 通用异常汇总v2，兼容多语言
 * @Author niuzhenyu
 * @Date 2024/02/05 15:45
 **/
public enum CommonErrorV2 implements ErrorMsg {

    /**
     * 商品异常
     */
    GOODS_IS_RESERVED(1601, LangErrorEnum.GOODS_IS_RESERVED),


    /**
     * 支付异常
     */
    PAYMENT_ERROR(3000, LangErrorEnum.PAYMENT_FAILED),
    PAYMENT_CUSTOMER_ERROR(3001, LangErrorEnum.INVALID_PAYMENT_CUSTOMER),


    /**
     * AI鉴定异常
     */
    AI_INSUFFICIENT_AVAILABLE_TIMES(4001, LangErrorEnum.AI_INSUFFICIENT_AVAILABLE_TIMES),
    AI_INSUFFICIENT_AVAILABLE_TIME2(4002, LangErrorEnum.AI_INSUFFICIENT_AVAILABLE_TIMES2),

    ;


    private Integer code;
    private LangErrorEnum msg;

    CommonErrorV2(int code, LangErrorEnum msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg.lang();
    }

}
