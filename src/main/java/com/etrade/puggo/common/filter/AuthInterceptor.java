package com.etrade.puggo.common.filter;


import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.exception.CommonError;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.config.IgnoreUrlProperties;
import com.etrade.puggo.config.TouristUrlProperties;
import com.etrade.puggo.common.constants.RequestHeaders;
import com.etrade.puggo.common.constants.SpringProfiles;
import com.etrade.puggo.common.constants.UserRoleConstants;
import com.etrade.puggo.utils.*;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 授权拦截
 *
 * @author wendiyou
 * @createTime 2020/10/26
 */
@Component
@Slf4j
@RefreshScope
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private IgnoreUrlProperties ignoreUrlProperties;

    @Resource
    private TouristUrlProperties touristUrlProperties;

    @Value("${security.multi-login:false}")
    private boolean isMultiLogin;

    @Value("${spring.profiles.active}")
    private String active;


    /**
     * 校验token + 写入ThreadLocal
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/26 22:33
     * @editTime 2023/5/26 22:33
     **/
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {

        // 排除OPTIONS请求
        if (Objects.equals(request.getMethod(), "OPTIONS")) {
            return true;
        }

        if (isIgnoreAPI(request)) {
            log.info("开放接口，跳过token校验");
            return true;
        }

        if (isTourist(request)) {
            log.info("游客身份，跳过token校验");
            return true;
        }

        // 检查token
        String token = request.getHeader(RequestHeaders.AUTHENTICATION);

        if (StrUtils.isEmpty(token)) {
            log.error("[AUTH] token不存在");
            authError(response, CommonError.USER_AUTH_FAILURE.getMsg());
            return false;
        }

        Claims claims;
        try {
            claims = JwtUtils.getClaimsBody(token);

            if (claims == null) {
                log.error("[AUTH] token解析失败");
                authError(response, CommonError.USER_AUTH_FAILURE.getMsg());
                return false;
            }
        } catch (Throwable e) {
            log.error("[AUTH] token解析失败");
            authError(response, CommonError.USER_AUTH_FAILURE.getMsg());
            return false;
        }

        long userId = Convert.toLong(claims.get("userId"), 0L);
        String nickname = Convert.toStr(claims.get("nickname"), "");
        String deviceId = Convert.toStr(claims.get("deviceId"), "");
        String role = Convert.toStr(claims.get("role"), UserRoleConstants.USER);

        if (userId == 0L) {
            log.error("[AUTH] token中userId=0");
            authError(response, CommonError.USER_AUTH_FAILURE.getMsg());
            return false;
        }

        String ip = IpUtils.getIpAddress(request);

        log.info("[AUTH]拦截器日志 ==> userId:{}, nickname:{}, deviceId:{}, role:{}", userId, nickname, deviceId, role);
        log.info("[AUTH]拦截器日志 ==> 本次访问ip地址: {}", ip);

        // 写入ThreadLocal
        AuthContext.setUserId(userId);
        AuthContext.setUserName(nickname);
        AuthContext.setDeviceId(deviceId);
        AuthContext.setUserRole(role);
        AuthContext.setLang(request.getHeader("lang"));

        return true;
    }


    /**
     * 认证错误输出
     */
    private void authError(HttpServletResponse resp, String message) throws IOException {
        // 响应中放入返回的状态码, 没有权限访问
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.setHeader("Content-Type", "application/json;charset=UTF-8");
        String json = JsonUtils.toJson(Result.error(HttpStatus.UNAUTHORIZED.value(), message));
        resp.getWriter().print(json);
    }


    /**
     * 验证开放接口
     *
     * @param servletRequest request
     * @return 如果是白名单返回true
     */
    private boolean isIgnoreAPI(HttpServletRequest servletRequest) {

        String uri = servletRequest.getRequestURI();

        List<IgnoreUrlProperties.Permission> permissionList = ignoreUrlProperties.getList();

        if (permissionList == null) {
            return false;
        }

        List<String> pathList = new ArrayList<>(permissionList.size());

        for (IgnoreUrlProperties.Permission permission : permissionList) {

            pathList.add(permission.getPath());

            if (uri.contains(permission.getPath())) {

                log.info("[PASS] URI: {}, permission:{}", uri, pathList);
                return true;
            }
        }

        log.info("[FILTER] URI: {}, permission:{}", uri, pathList);
        return false;
    }


    /**
     * 验证是否有游客登录
     *
     * @param servletRequest request
     * @return 如果是游客登录返回true
     */
    private boolean isTourist(HttpServletRequest servletRequest) {

        String uri = servletRequest.getRequestURI();

        List<TouristUrlProperties.Permission> permissionList = touristUrlProperties.getList();

        if (permissionList == null) {
            return false;
        }

        String token = servletRequest.getHeader(RequestHeaders.AUTHENTICATION);
        boolean isNotToken = StringUtils.isBlank(token);

        List<String> pathList = new ArrayList<>(permissionList.size());

        for (TouristUrlProperties.Permission permission : permissionList) {

            pathList.add(permission.getPath());

            if (uri.contains(permission.getPath()) && isNotToken) {

                log.info("[游客登录] URI: {}, permission:{}", uri, pathList);
                return true;
            }
        }

        log.info("[非游客登录] URI: {}, permission:{}", uri, pathList);
        return false;
    }


    @SuppressWarnings("NullableProblems")
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, @Nullable Exception ex) {
        AuthContext.remove();
    }


    /**
     * 确保是本地网络调用，仅生产环境生效
     *
     * @author zhengbaole
     * @lastEditor zhengbaole
     * @createTime 2022/2/28 10:03 AM
     * @editTime 2022/2/28 10:03 AM
     */
    public void assertIsLocalNetwork(HttpServletRequest req) {
        try {
            String ip = IpUtils.getIpAddress(req);
            if (isProduce()) {
                boolean isLocal = IpUtils.isInternalIp(ip);
                if (!isLocal) {
                    throw new ServiceException("权限不足[外部访问]");
                }
            }
        } catch (Exception e) {
            log.error("判断内网IP失败", e);
        }
    }


    /**
     * 是否生产环境
     */
    public boolean isProduce() {
        String prod = SpringProfiles.prod.name();
        String pre = SpringProfiles.pre.name();
        log.info("当前的环境是:{}", active);
        return StrUtils.startsWithIgnoreCase(active, prod)
                || StrUtils.startsWithIgnoreCase(active, pre);
    }


    /**
     * 是否生产环境
     */
    public boolean isTest() {
        String test = SpringProfiles.test.name();
        log.info("当前的环境是:{}", active);
        return StrUtils.startsWithIgnoreCase(active, test);
    }


    /**
     * 是否开启多端登录
     */
    public boolean isMultiLogin() {
        return this.isMultiLogin;
    }
}