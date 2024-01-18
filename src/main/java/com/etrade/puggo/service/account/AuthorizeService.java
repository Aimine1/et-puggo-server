package com.etrade.puggo.service.account;

import com.etrade.puggo.common.exception.CommonError;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.constants.ClientConstants;
import com.etrade.puggo.constants.RedisKeys;
import com.etrade.puggo.common.filter.AuthInterceptor;
import com.etrade.puggo.common.filter.UserInfoDO;
import com.etrade.puggo.utils.Convert;
import com.etrade.puggo.utils.JwtUtils;
import com.etrade.puggo.utils.RedisUtils;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author niuzhenyu
 * @description : TODO
 * @date 2023/6/11 18:07
 **/
@Service
public class AuthorizeService {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private AuthInterceptor authInterceptor;

    private static final int APP_TOKEN_SECONDS = 60 * 60 * 24;
    private static final int WEB_TOKEN_SECONDS = 60 * 60 * 8;


    public String auth(String client, UserInfoDO user) {
        if (ClientConstants.PC.equals(client)) {
            return webAuth(user);
        } else {
            return appAuth(user);
        }
    }

    private String appAuth(UserInfoDO user) {
        String redisKey = RedisKeys.USER_LOGIN_TOKEN_PREFIX + user.getUserId();

        // 判断App是否多账号登录
        if (redisUtils.hasKey(redisKey)) {
            String redisToken = (String) redisUtils.get(redisKey);
            checkIsAppAccountMultiLogin(redisToken, user.getDeviceId());
        }

        String token = JwtUtils.genJwt(user);
        redisUtils.set(redisKey, token, APP_TOKEN_SECONDS);

        return token;
    }

    private String webAuth(UserInfoDO user) {
        String redisKey = RedisKeys.USER_WEB_LOGIN_TOKEN_PREFIX + user.getUserId();
        String token = JwtUtils.genJwt(user);
        redisUtils.set(redisKey, token, WEB_TOKEN_SECONDS);
        return token;
    }


    public void checkIsAppAccountMultiLogin(String redisToken, String deviceId) {
        String tokenDeviceId = Convert.toStr(JwtUtils.getVal(redisToken, "deviceId"), "");
        if (!Objects.equals(deviceId, tokenDeviceId) && !authInterceptor.isMultiLogin()) {
            throw new ServiceException(CommonError.USER_MULTI_DEVICE_LOGIN);
        }
    }

}
