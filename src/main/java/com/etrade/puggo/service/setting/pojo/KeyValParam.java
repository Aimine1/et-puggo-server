package com.etrade.puggo.service.setting.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author niuzhenyu
 * @description : key=>val
 * @date 2023/10/25 14:02
 **/
@Data
public class KeyValParam {

    @NotNull(message = "key is empty")
    @NotBlank(message = "key is empty")
    private String key;

    @NotNull(message = "val is empty")
    @NotBlank(message = "val is empty")
    private String val;

    @ApiModelProperty(value = "备注")
    private String comment;
}
