package com.etrade.puggo.common;

import com.etrade.puggo.common.exception.ErrorMsg;
import com.etrade.puggo.common.enums.LangErrorEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.amazon.awssdk.services.lambda.endpoints.internal.Value;

/**
 * @Description 接口返回值规范
 * @Author niuzhenyu
 * @Date 2023-05-19
 **/
@Data
@NoArgsConstructor
@ApiModel("接口出参")
@ToString
public class Result<T> {

    public static final int SUCCESS = 200;
    public static final int HALF_SUCCESS = 2000;

    @ApiModelProperty("状态码")
    private int code;

    @ApiModelProperty("提示")
    private String msg;

    @ApiModelProperty("业务数据")
    private T data;

    @ApiModelProperty("失败时的原因")
    private String error;

    private Result(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    private Result(int code, T data, String msg, String error) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.error = error;
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> ok() {
        return new Result(SUCCESS, null, LangErrorEnum.OPERATE_SUCCESSFULLY.lang());
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> ok(String msg) {
        return new Result(SUCCESS, null, msg);
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> ok(T data) {
        return new Result(SUCCESS, data, LangErrorEnum.OPERATE_SUCCESSFULLY.lang());
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> ok(T data, String msg) {
        return new Result(SUCCESS, data, msg);
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> error(int code, String errorMsg) {
        return new Result(code, null, errorMsg);
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> error(int code, String msg, String error) {
        return new Result(code, null, msg, error);
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> error(int code, String errorMsg, T data) {
        return new Result(code, data, errorMsg);
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> error(ErrorMsg errorMsg) {
        return new Result(errorMsg.getCode(), null, errorMsg.getMsg());
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> error(Result<?> result) {
        return new Result(result.getCode(), null, result.getMsg());
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> error(ErrorMsg errorMsg, T data) {
        return new Result(errorMsg.getCode(), data, errorMsg.getMsg());
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return msg;
    }
}
