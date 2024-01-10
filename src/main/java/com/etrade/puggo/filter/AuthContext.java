package com.etrade.puggo.filter;

import com.etrade.puggo.constants.LangConstant;
import com.etrade.puggo.constants.RequestHeaders;
import com.etrade.puggo.utils.OptionalUtils;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 登录上下文
 * </p>
 * !!!注意：本上下文中存储的 ThreadLocal 值必须在 remove() 方法中清除 ！！！
 *
 * @author wendiyou
 * @date 2021-06-03
 */
@Slf4j
public class AuthContext {

    public static final ThreadLocal<Long> USER_ID = new InheritableThreadLocal<>();
    public static final ThreadLocal<String> USER_NAME = new InheritableThreadLocal<>();
    public static final ThreadLocal<String> USER_ROLE = new InheritableThreadLocal<>();
    public static final ThreadLocal<String> DEVICE_ID = new InheritableThreadLocal<>();
    public static final ThreadLocal<String> LANG = new InheritableThreadLocal<>();


    public static void setUserId(Long userId) {
        USER_ID.set(userId);
    }

    public static Long getUserId() {
        return OptionalUtils.valueOrDefault(USER_ID.get());
    }

    public static void setDeviceId(String deviceId) {
        DEVICE_ID.set(deviceId);
    }

    public static String getDeviceId() {
        return OptionalUtils.valueOrDefault(DEVICE_ID.get());
    }

    public static void setUserName(String userName) {
        USER_NAME.set(userName);
    }

    public static String getUserName() {
        return OptionalUtils.valueOrDefault(USER_NAME.get());
    }

    public static String getUserRole() {
        return OptionalUtils.valueOrDefault(USER_ROLE.get());
    }

    public static void setUserRole(String userType) {
        USER_ROLE.set(userType);
    }

    public static String getToken() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        return request.getHeader(RequestHeaders.AUTHENTICATION);
    }

    public static void setLang(String lang) {
        LANG.set(lang);
    }

    public static String getLang() {
        if (StringUtils.isBlank(LANG.get())) {
            // 默认英文
            return LangConstant.EN_US;
        } else {
            return LANG.get();
        }
    }

    public static boolean isTourist() {
        return USER_ID.get() == null;
    }

    /**
     * 清除资源
     */
    public static void remove() {
        USER_ID.remove();
        USER_NAME.remove();
        USER_ROLE.remove();
        DEVICE_ID.remove();
        LANG.remove();
    }
}
