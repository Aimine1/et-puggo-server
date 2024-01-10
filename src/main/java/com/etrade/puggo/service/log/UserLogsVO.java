package com.etrade.puggo.service.log;

import com.etrade.puggo.service.goods.sales.GoodsSimpleVO;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 用户日志
 * @date 2023/6/28 17:55
 **/
@Data
public class UserLogsVO {

    @ApiModelProperty("商品日志id")
    private Long id;

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("商品信息")
    private GoodsSimpleVO goodsInfo;

    @ApiModelProperty("操作")
    private String operate;

    @ApiModelProperty("日志内容")
    private String content;

    @ApiModelProperty("日志时间")
    private LocalDateTime logTime;

    @ApiModelProperty("1=消息未读，0=消息已读")
    private Byte isUnread;

}
