package com.etrade.puggo.common.exception;

/**
 * @author niuzhenyu
 */
public enum CommonError implements ErrorMsg {

    /**
     * http请求错误
     */
    SUCCESS(200, "Success"),
    BAD_REQUEST(400, "Bad Request  From Et."),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed From Et."),


    /**
     * 用户授权错误
     */
    USER_NOT_LOGIN(1001, "User not login."),
    USER_MULTI_DEVICE_LOGIN(1002, "Your account has been logging on another device."),
    USER_AUTH_FAILURE(1003, "Authentication failure."),
    USER_VERIFICATION_CODE_ERROR(1004, "Verification code error."),
    USER_LOGIN_FAILURE(1005, "Your Account or Password is error."),
    USER_ACCOUNT_NOT_ALLOWED(1006, "Your Account is not allowed."),
    USER_ALREADY_EXISTS(1008, "Nickname is already exists."),
    ENTERED_PASSWORD_DIFFER(1009, "Entered Passwords differ."),
    NEW_PASSWORD_IS_SAME_OLD(1010, "The new Password is the same as the old one."),
    USER_ACCOUNT_OLD_PASSWORD_ERROR(1011, "Your old password is error."),
    USER_EMAIL_ALREADY_EXISTS(1012, "Email is already exists."),
    USER_NOT_ALLOWED_LOGIN_IN(1013, "You are not authorized to login."),
    USER_IM_ACTION_IS_NOT_LOGIN(1014, "Your IM Action is not login"),
    USER_PERMISSION_DENIED(1015, "Your Account is Permission denied."),


    /**
     * 商品服务错误码 1101 - 1500
     */


    /**
     * 团购券服务错误码 1501 - 2000
     */
    GROUPON_IS_UNKNOWN(1501, "Groupon Coupon is unknown."),
    GROUPON_PLAN_IS_UNKNOWN(1502, "Groupon Coupon Plan is unknown."),
    GROUPON_USEAGE_DATE_ILLEGAL(1503, "Groupon Coupon Useage Date is illegal."),
    TELEPHONE_IS_ILLEGAL(1504, "Telephone is illegal."),

    /**
     * 统一系统运行时错误
     */
    GLOBAL_ERROR(2001, "The system is busy. Please try again later."),

    S3_UPLOAD_ERROR(2101, "文件上传S3失败"),
    S3_REMOVE_ERROR(2102, "文件从S3删除失败"),
    S3_LIST_ERROR(2103, "从S3获取文件失败"),
    S3_OPERATE_ERROR(2104, "文件从S3操作失败"),
    S3_FILE_IS_EXISTS(2105, "文件已存在，请重新上传"),
    ;

    private Integer code;
    private String msg;

    CommonError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
