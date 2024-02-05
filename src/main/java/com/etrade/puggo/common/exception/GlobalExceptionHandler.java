package com.etrade.puggo.common.exception;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.enums.LangErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author wendiyou
 * @date 2021-01-21
 */
@RestControllerAdvice
@Configuration
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 注解验证参数校验异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result validationExceptionExceptionHandler(ValidationException e) {
        // 从异常对象中拿到ObjectError对象
        String message;
        if (e instanceof ConstraintViolationException) {
            message = ((ConstraintViolationException) e).getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining());
        } else {
            message = e.getMessage();
        }

        log.error("validationExceptionExceptionHandler:" + message, e);

        return Result.error(CommonError.BAD_REQUEST.getCode(), message);
    }

    /**
     * 方法参数校验异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        log.error("methodArgumentNotValidExceptionHandler:" + e.getMessage(), e);
        return Result.error(CommonError.BAD_REQUEST.getCode(), objectError.getDefaultMessage());
    }

    /**
     * url后参数读取异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result methodMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        // 从异常对象中拿到ObjectError对象
        log.error("methodMissingServletRequestParameterException:" + e.getMessage(), e);
        return Result.error(CommonError.BAD_REQUEST.getCode(), e.getMessage());
    }

    /**
     * 请求体读取异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("handleHttpMessageNotReadableException:" + e.getMessage(), e);
        return Result.error(CommonError.BAD_REQUEST.getCode(), e.getMessage());
    }

    /**
     * 上传的文件size过大
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("handleMaxUploadSizeExceededException:" + e.getMessage(), e);
        return Result.error(CommonError.BAD_REQUEST.getCode(), "Maximum upload size exceeded");
    }

    /**
     * 捕获应用级异常 异常code统一规范，2000-2999是用户的异常代码段
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleApplicationRuntimeException(ServiceException e) {
        log.error("统一系统运行时异常:", e);
        if (e.getCode() != null) {
            return Result.error(e.getCode(), e.getMessage(), e.getData());
        } else {
            return Result.error(CommonError.GLOBAL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 捕获应用级异常 aws s3操作异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(AwsException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleApplicationAwsS3Exception(AwsException e) {
        log.error("Aws S3运行时异常:", e);
        if (e.getCode() != null) {
            return Result.error(e.getCode(), e.getMessage());
        } else {
            return Result.error(CommonError.GLOBAL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 请求方法有误
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleMethodErrorException(HttpRequestMethodNotSupportedException e) {
        log.error("请求方法有误");
        return Result.error(CommonError.METHOD_NOT_ALLOWED);
    }

    /**
     * 客户端主动放弃
     */
    @ExceptionHandler(ClientAbortException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result<?> handleClientAbortException(ClientAbortException e) {
        log.error("ClientAbortException:", e);
        return Result.error(CommonError.GLOBAL_ERROR.getCode(), "ClientAbort");
    }

    /**
     * 兜底的异常拦截
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result<?> handleException(Throwable t) {
        log.error("统一服务内部异常处理:", t);
        return Result.error(CommonError.GLOBAL_ERROR.getCode(), LangErrorEnum.GLOBAL_ERROR.lang(), t.getMessage());
    }

    /**
     * 支付模块异常
     */
    @ExceptionHandler(PaymentException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result<?> PaymentExceptionHandler(PaymentException e) {
        log.error("PaymentException:", e);
        return Result.error(CommonErrorV2.PAYMENT_ERROR, e.getMessage());
    }

}
