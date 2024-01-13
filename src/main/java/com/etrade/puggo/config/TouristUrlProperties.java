package com.etrade.puggo.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 游客url
 *
 * @author niuzhenyu
 * @date 2024-01-13
 */
@Component
@Data
@ConfigurationProperties(prefix = "tourist-url")
@RefreshScope
public class TouristUrlProperties {

    private List<Permission> list;

    @Data
    public static class Permission {

        private String path;
    }
}
