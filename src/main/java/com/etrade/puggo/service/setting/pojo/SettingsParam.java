package com.etrade.puggo.service.setting.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 设置
 * @date 2023/6/30 16:12
 **/
@Data
public class SettingsParam {

    @NotNull(message = "货币设置不能为空")
    @NotBlank(message = "货币设置不能为空")
    private String moneyKind;

    @NotNull(message = "系统IM账号不能为空")
    @NotBlank(message = "系统IM账号不能为空")
    private String systemImAction;

}
