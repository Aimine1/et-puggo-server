package com.etrade.puggo.service.groupon.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 用户浏览团购券的记录
 * @date 2023/6/7 17:02
 **/
@Data
public class UserBrowseHistoryVO {

    private Long userId;

    private Long goodsId;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime lastBrowseTime;

}
