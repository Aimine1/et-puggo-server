package com.etrade.puggo.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 登录配置
 *
 * @author niuzhenyu
 * @date 2023-05-21
 */
@Component
@Data
@ConfigurationProperties(prefix = "login")
public class LoginConfig {

    /**
     * token生成有效天数-天
     */
    private double tokenEffectiveDay = 15;
    /**
     * token有效刷新时长-小时
     */
    private double tokenRefreshHours = 6;
    /**
     * 密码输错统计窗口市场 默认一个小时
     */
    private Long errorPasswordExpireTime = 3600L;
    /**
     * 一小时内连续允许密码输入错误的最大次数
     */
    private int maxLoginPasswordPerHour = 10;


    /**
     * 是否开启多端登录
     */
    private Boolean multiFlag = false;
    /**
     * token有效小时数
     */
    private double tokenEffectiveHours = 6;
    /**
     * 允许多段登录userId白名单
     */
    private List<String> multiLoginWhiteUserid = new ArrayList<>();

}