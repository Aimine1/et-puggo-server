package com.etrade.puggo.common.exception;

import lombok.Getter;

/**
 * @Description 业务层异常
 * @Author liuyuqing
 * @Date 2020/11/20 15:45
 **/
@Getter
public class AwsS3Exception extends RuntimeException {

    private Integer code;
    private String description;


    public AwsS3Exception() {
    }

    public AwsS3Exception(String description) {
        super(description);
        this.description = description;
    }

    public AwsS3Exception(int code, String description) {
        super(description);
        this.description = description;
        this.code = code;
    }

    public AwsS3Exception(ErrorMsg errorMsg) {
        this(errorMsg.getCode(), errorMsg.getMsg());
    }

}
