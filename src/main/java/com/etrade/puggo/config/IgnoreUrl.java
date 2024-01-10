package com.etrade.puggo.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 白名单
 *
 * @author wendiyou
 * @date 2021-06-03
 */
@Component
@Data
@ConfigurationProperties(prefix = "ignore-url")
@RefreshScope
public class IgnoreUrl {

    private List<Permission> list;

    @Data
    public static class Permission {

        private String path;
    }
}
