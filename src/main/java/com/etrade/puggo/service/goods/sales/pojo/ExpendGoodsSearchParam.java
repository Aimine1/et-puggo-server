package com.etrade.puggo.service.goods.sales.pojo;

import com.etrade.puggo.common.page.PageParam;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jooq.SortOrder;

/**
 * @author niuzhenyu
 * @description : 商品搜索参数
 * @date 2023/6/15 17:20
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpendGoodsSearchParam extends PageParam {

    private String title;

    private String classPath;

    private List<Long> launchUserId;

    private List<Long> goodsIdList;

    private Boolean isFree;

    private Long excludeGoodsId;

    private List<String> state;

    private String excludeState;

    private Boolean isAiIdentify;

    private String sortKey;

    private SortOrder sortOrder;

    public ExpendGoodsSearchParam(PageParam pageParam) {
        this.pageIndex = pageParam.getPageIndex();
        this.pageSize = pageParam.getPageSize();
    }

}
