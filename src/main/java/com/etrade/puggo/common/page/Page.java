package com.etrade.puggo.common.page;


import lombok.Data;

/**
 * 用于自定义分页查询
 *
 * @author hguo.yan
 * @lastEditor hguo.yan
 * @createTime 2021/10/21 20:33
 * @editTime 2021/10/21 20:33
 */
@Data
public class Page extends PageParam {


    public Page(int current, int pageSize) {
        this.pageIndex = current;
        this.pageSize = pageSize;
    }
}
