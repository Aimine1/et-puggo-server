package com.etrade.puggo.service.ai.pojo;

import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 更新品类Ai鉴定可用次数参数
 * @date 2023/9/16 17:47
 **/
@Data
public class UpdateAvailableParam {

    private Integer kindId;

    private Integer availableBalance;

}
