package com.etrade.puggo.service.setting;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : key=>val
 * @date 2023/10/25 14:02
 **/
@Data
public class KeyValParam {

    @NotNull(message = "key is null")
    private String key;

    @NotNull(message = "val is null")
    private String val;
}
