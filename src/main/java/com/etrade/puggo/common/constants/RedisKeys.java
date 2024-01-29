package com.etrade.puggo.common.constants;

/**
 * @author niuzhenyu
 * @description : TODO
 * @date 2023/5/22 18:31
 **/
public class RedisKeys {

    // 注册邮箱验证码
    public static final String VERIFY_CODE_KEY = "verify_code:%s";

    // token
    public static final String USER_LOGIN_TOKEN_PREFIX = "user:login:token:";

    // web token
    public static final String USER_WEB_LOGIN_TOKEN_PREFIX = "user:login:token:web:";

    // trade no
    public static final String TRADE_NO_PREFIX = "trade_no_index:%s:%s";

}
