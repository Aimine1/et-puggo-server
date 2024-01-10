package com.etrade.puggo.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户数据
 *
 * @author niuzhenyu
 * @date 2023-05-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfoDO {

    /**
     * 用户系统唯一id
     */
    private Long userId;

    /**
     * 用户系统唯一昵称
     */
    private String nickname;

    /**
     * 设备唯一标识
     */
    private String deviceId;

    /**
     * 用户角色
     */
    private String role;

}
