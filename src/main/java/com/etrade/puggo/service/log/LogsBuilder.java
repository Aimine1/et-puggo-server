package com.etrade.puggo.service.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niuzhenyu
 * @description : 日志
 * @date 2023/6/28 17:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogsBuilder {

    private Long userId;

    private Long goodsId;

    private String operate;

    private String content;

}
