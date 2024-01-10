package com.etrade.puggo.service.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niuzhenyu
 * @description : 登录验证邮箱
 * @date 2023/5/19 20:06
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("登录成功后返回token以及用户信息")
public class LoginResponse {

    @ApiModelProperty(name = "用户授权唯一凭证", required = true)
    private String token;
    
    @ApiModelProperty(name = "用户id", required = true)
    private Long userId;

    @ApiModelProperty(name = "用户昵称", required = true)
    private String nickname;

    @ApiModelProperty(name = "网易云信IM接口调用凭证", required = true)
    private String imToken;

    @ApiModelProperty(name = "网易云信IM用户唯一id", required = true)
    private String imAccid;

}
