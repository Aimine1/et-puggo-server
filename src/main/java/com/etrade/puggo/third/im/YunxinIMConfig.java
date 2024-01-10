package com.etrade.puggo.third.im;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author niuzhenyu
 * @description : 网易云信配置
 * @date 2023/6/21 18:19
 **/
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "im.yunxin")
public class YunxinIMConfig {

    private String appKey;
    private String appSecret;
    private String domain;

}
