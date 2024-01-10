package com.etrade.puggo.service.setting;

import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 设置
 * @date 2023/6/30 15:36
 **/
@Data
public class SettingsVO {

    private String key;

    private String value;

    private String comment;

}
