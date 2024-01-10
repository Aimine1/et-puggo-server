package com.etrade.puggo.service.groupon;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 电话国际区号
 * @date 2023/6/7 17:47
 **/
@Data
@AllArgsConstructor
public class NationalCodeVO {

    private Integer code;

    private String national;

}
