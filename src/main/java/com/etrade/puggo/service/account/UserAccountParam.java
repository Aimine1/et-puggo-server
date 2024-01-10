package com.etrade.puggo.service.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niuzhenyu
 * @description : 用户登录账号
 * @date 2023/5/22 15:12
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccountParam {

    private Long userId;

    private String account;

    private String password;

    private String salt;

    private String lastLoginIp;

    private String lastLoginAddress;

    private String lastLoginTime;

}
