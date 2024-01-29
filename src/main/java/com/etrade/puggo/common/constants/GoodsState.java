package com.etrade.puggo.common.constants;

/**
 * @author niuzhenyu
 * @description : 货品状态
 * @date 2023/5/24 21:36
 **/
public class GoodsState {

    // 草稿
    public static final String DRAFT = "DRAFT";
    // 删除
    public static final String DELETED = "DELETED";
    // 下架，只有web端能操作下架
    public static final String OFF_SALE = "OFF_SALE";
    // 已发布
    public static final String PUBLISHED = "PUBLISHED";
    // 预定
    public static final String OCCUPY = "RESERVED";
    // 已售
    public static final String SOLD = "SOLD";
    // 完成
    public static final String COMPLETE = "COMPLETE";

}
