package com.etrade.puggo.common.exception;

import lombok.Getter;

/**
 * @Description 支付失败异常
 * @Author niuzhenyu
 * @Date 2020/11/20 15:45
 **/
@Getter
public class PaymentException extends RuntimeException {

    private Integer code;
    private String description;


    public PaymentException() {
    }

    public PaymentException(String description) {
        super(description);
        this.description = description;
    }

    public PaymentException(int code, String description) {
        super(description);
        this.description = description;
        this.code = code;
    }

    public PaymentException(ErrorMsg errorMsg) {
        this(errorMsg.getCode(), errorMsg.getMsg());
    }

}
