package com.etrade.puggo.service;

import lombok.Data;

/**
 * @Description 权限信息
 * @Author liuyuqing
 * @Date 2020/12/22 16:00
 **/
@Data
public class AuthDTO {

    private Long userId;
    private String userName;
    private String deviceId;
}
