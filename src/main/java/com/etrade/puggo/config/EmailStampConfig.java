package com.etrade.puggo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author niuzhenyu
 * @description : 邮件配置
 * @date 2023/5/22 18:22
 **/
@Component
@Data
@ConfigurationProperties(prefix = "email.stamp")
@RefreshScope
public class EmailStampConfig {

    private String sender;
    private String password;
    private String host;
    private String port;

}
