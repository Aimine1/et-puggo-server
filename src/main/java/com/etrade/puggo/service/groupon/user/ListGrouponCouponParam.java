package com.etrade.puggo.service.groupon.user;

import com.etrade.puggo.common.page.PageParam;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 团购券列表参数
 * @date 2023/6/6 15:39
 **/
@Data
@ApiModel("团购券列表参数")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListGrouponCouponParam extends PageParam {

}
