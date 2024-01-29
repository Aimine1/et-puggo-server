package com.etrade.puggo.config;

import com.etrade.puggo.common.constants.RequestHeaders;
import com.etrade.puggo.common.filter.AuthContext;
import com.etrade.puggo.utils.StrUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author niuzhenyu
 * @description : Feign拦截器
 * @date 2023/6/7 16:51
 **/
@Configuration
public class FeignInterceptor implements RequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(FeignInterceptor.class);

    public FeignInterceptor() {
    }

    public void apply(RequestTemplate requestTemplate) {
        String token = null;
        try {
            token = AuthContext.getToken();
        } catch (Throwable e) {
            log.error("token 获取失败", e);
        }

        if (!StrUtils.isEmpty(token)) {
            requestTemplate.header(RequestHeaders.AUTHENTICATION, token);
            log.info("Feign 服务间调用自动添加 token 成功");
        }

    }
}