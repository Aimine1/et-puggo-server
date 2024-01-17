package com.etrade.puggo.common.exception;

import lombok.Getter;

/**
 * @Description 业务层异常
 * @Author liuyuqing
 * @Date 2020/11/20 15:45
 **/
@Getter
public class AwsException extends RuntimeException {

    private Integer code;
    private String description;


    public AwsException() {
    }

    public AwsException(String description) {
        super(description);
        this.description = description;
    }

    public AwsException(int code, String description) {
        super(description);
        this.description = description;
        this.code = code;
    }

    public AwsException(ErrorMsg errorMsg) {
        this(errorMsg.getCode(), errorMsg.getMsg());
    }

}
