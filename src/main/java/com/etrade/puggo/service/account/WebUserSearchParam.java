package com.etrade.puggo.service.account;

import com.etrade.puggo.common.page.PageParam;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : TODO
 * @date 2023/6/30 12:22
 **/
@Data
public class WebUserSearchParam extends PageParam {

    private String nickname;

}
