package com.etrade.puggo.dao.goods;

import static com.etrade.puggo.db.Tables.GOODS;
import static com.etrade.puggo.db.Tables.GOODS_CLASS;
import static com.etrade.puggo.db.Tables.GOODS_DATA;

import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.constants.GoodsState;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.Goods;
import com.etrade.puggo.db.tables.records.GoodsRecord;
import com.etrade.puggo.common.filter.AuthContext;
import com.etrade.puggo.service.goods.sales.pojo.ExpendGoodsSearchParam;
import com.etrade.puggo.service.goods.sales.pojo.GoodsDetailVO;
import com.etrade.puggo.service.goods.sales.pojo.GoodsSearchParam;
import com.etrade.puggo.service.goods.sales.pojo.GoodsSimpleVO;
import com.etrade.puggo.utils.SQLUtils;
import com.etrade.puggo.utils.StrUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Field;
import org.jooq.SelectConditionStep;
import org.jooq.SortOrder;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * @author niuzhenyu
 * @description : 商品Dao
 * @date 2023/5/24 18:20
 **/
@Repository
public class GoodsDao extends BaseDao {


    private static final Byte IS_FREE = 1;

    public Long saveGoods(GoodsRecord goods) {
        return db.insertInto(
                Goods.GOODS,
                Goods.GOODS.TITLE,
                Goods.GOODS.DESCRIPTION,
                Goods.GOODS.ORIGINAL_PRICE,
                Goods.GOODS.REAL_PRICE,
                Goods.GOODS.MONEY_KIND,
                Goods.GOODS.LAUNCH_USER_ID,
                Goods.GOODS.LAUNCH_USER_NICKNAME,
                Goods.GOODS.LAUNCH_LAST_TIME,
                Goods.GOODS.QUALITY,
                Goods.GOODS.CLASS_PATH,
                Goods.GOODS.STATE,
                Goods.GOODS.COUNTRY,
                Goods.GOODS.PROVINCE,
                Goods.GOODS.CITY,
                Goods.GOODS.DISTRICT,
                Goods.GOODS.BRAND,
                Goods.GOODS.AI_IDENTIFY_NO,
                Goods.GOODS.DELIVERY_TYPE
            )
            .values(
                goods.getTitle(),
                goods.getDescription(),
                goods.getOriginalPrice(),
                goods.getRealPrice(),
                goods.getMoneyKind(),
                goods.getLaunchUserId(),
                goods.getLaunchUserNickname(),
                goods.getLaunchLastTime(),
                goods.getQuality(),
                goods.getClassPath(),
                goods.getState(),
                goods.getCountry(),
                goods.getProvince(),
                goods.getCity(),
                goods.getDistrict(),
                goods.getBrand(),
                goods.getAiIdentifyNo(),
                goods.getDeliveryType()
            )
            .returning(Goods.GOODS.ID).fetchOne().getId();
    }


    public PageContentContainer<GoodsSimpleVO> findGoodsPageList(ExpendGoodsSearchParam param) {
        SelectConditionStep<?> sql = db
            .select(
                GOODS.ID.as("goodsId"),
                GOODS.TITLE,
                GOODS.ORIGINAL_PRICE,
                GOODS.REAL_PRICE,
                GOODS.MONEY_KIND,
                GOODS.QUALITY,
                GOODS.LAUNCH_LAST_TIME,
                GOODS.LAUNCH_USER_ID,
                GOODS.IS_FREE,
                GOODS.AI_IDENTIFY_NO,
                GOODS.STATE,
                GOODS.DESCRIPTION
            )
            .from(GOODS)
            .innerJoin(GOODS_DATA)
            .on(GOODS.ID.eq(GOODS_DATA.GOODS_ID))
            .where(DSL.trueCondition());

        // where
        sql = appendWhere(param, sql);

        // 排序
        String sortKey = param.getSortKey();
        SortOrder sortOrder = param.getSortOrder();
        Field<?> field = GOODS.field(sortKey);
        if (sortKey != null && field != null) {
            sql.orderBy(field.sort(sortOrder));
        }

        return getPageResult(sql, param, GoodsSimpleVO.class);
    }


    private SelectConditionStep<?> appendWhere(ExpendGoodsSearchParam param, SelectConditionStep<?> sql) {
        if (!StrUtils.isBlank(param.getClassPath())) {
            sql.and(GOODS.CLASS_PATH.like(SQLUtils.suffixLike(param.getClassPath())));
        }

        if (!StrUtils.isBlank(param.getTitle())) {
            sql.and(GOODS.TITLE.like(SQLUtils.suffixLike(param.getTitle())));
        }

        if (param.getIsFree() != null && param.getIsFree()) {
            sql.and(GOODS.IS_FREE.eq(IS_FREE));
        }

        if (param.getLaunchUserId() != null) {
            sql.and(GOODS.LAUNCH_USER_ID.in(ascendingOrder(param.getLaunchUserId())));
        }

        if (!CollectionUtils.isEmpty(param.getGoodsIdList())) {
            sql.and(GOODS.ID.in(ascendingOrder(param.getGoodsIdList())));
        }

        if (param.getExcludeGoodsId() != null) {
            sql.and(GOODS.ID.ne(param.getExcludeGoodsId()));
        }

        if (!CollectionUtils.isEmpty(param.getState())) {
            List<String> stateList = param.getState();
            if (!StringUtils.isBlank(param.getExcludeState())) {
                stateList = stateList.stream().filter(o -> !o.equals(GoodsState.OCCUPY)).collect(Collectors.toList());
            }
            sql.and(GOODS.STATE.in(stateList));
        } else {
            sql.and(GOODS.STATE.ne(GoodsState.DELETED));
            if (!StringUtils.isBlank(param.getExcludeState())) {
                sql.and(GOODS.STATE.ne(GoodsState.OCCUPY));
            }
        }

        if (Objects.equals(param.getIsAiIdentify(), true)) {
            sql.and(GOODS.AI_IDENTIFY_NO.ne(""));
        }

        return sql;
    }


    public GoodsDetailVO findGoodsDetail(Long goodsId) {
        return db
            .select(
                GOODS.ID.as("goodsId"),
                GOODS.TITLE,
                GOODS.DESCRIPTION,
                GOODS.ORIGINAL_PRICE,
                GOODS.REAL_PRICE,
                GOODS.MONEY_KIND,
                GOODS.QUALITY,
                GOODS.LAUNCH_LAST_TIME,
                GOODS.LAUNCH_USER_ID,
                GOODS.IS_FREE,
                GOODS.CLASS_PATH,
                GOODS.BRAND,
                GOODS.AI_IDENTIFY_NO,
                GOODS.DELIVERY_TYPE,
                GOODS.STATE,
                // 商品数据
                GOODS_DATA.LIKE_NUMBER,
                GOODS_DATA.BROWSE_NUMBER,
                GOODS_DATA.COMMENT_NUMBER,
                // 商品分类
                GOODS_CLASS.ID.as("classId"),
                GOODS_CLASS.CLASS_NAME
            )
            .from(GOODS)
            .innerJoin(GOODS_CLASS)
            .on(GOODS.CLASS_PATH.eq(GOODS_CLASS.CLASS_PATH))
            .innerJoin(GOODS_DATA)
            .on(GOODS.ID.eq(GOODS_DATA.GOODS_ID))
            .where(GOODS.ID.eq(goodsId))
            .fetchAnyInto(GoodsDetailVO.class);
    }


    public List<GoodsSimpleVO> findGoodsSimpleList(List<Long> goodsIdList) {
        return db.select(
                GOODS.ID.as("goodsId"),
                GOODS.TITLE,
                GOODS.DESCRIPTION,
                GOODS.ORIGINAL_PRICE,
                GOODS.REAL_PRICE,
                GOODS.MONEY_KIND,
                GOODS.QUALITY,
                GOODS.LAUNCH_LAST_TIME,
                GOODS.LAUNCH_USER_ID,
                GOODS.LAUNCH_USER_NICKNAME,
                GOODS.IS_FREE
            ).from(GOODS)
            .where(GOODS.ID.in(ascendingOrder(goodsIdList)))
            .fetchInto(GoodsSimpleVO.class);
    }


    public GoodsSimpleVO findGoodsSimple(Long goodsId) {
        List<GoodsSimpleVO> list = findGoodsSimpleList(Collections.singletonList(goodsId));
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }


    public PageContentContainer<GoodsDetailVO> findWebGoodsPageList(GoodsSearchParam param) {
        SelectConditionStep<?> sql = db
            .select(
                GOODS.ID.as("goodsId"),
                GOODS.TITLE,
                GOODS.DESCRIPTION,
                GOODS.ORIGINAL_PRICE,
                GOODS.REAL_PRICE,
                GOODS.MONEY_KIND,
                GOODS.QUALITY,
                GOODS.LAUNCH_LAST_TIME,
                GOODS.LAUNCH_USER_NICKNAME,
                GOODS.IS_FREE,
                GOODS.STATE,

                GOODS_DATA.LIKE_NUMBER,
                GOODS_DATA.BROWSE_NUMBER,
                GOODS_DATA.COMMENT_NUMBER,

                GOODS_CLASS.CLASS_NAME
            )
            .from(GOODS)
            .innerJoin(GOODS_DATA)
            .on(GOODS.ID.eq(GOODS_DATA.GOODS_ID))
            .innerJoin(GOODS_CLASS)
            .on(GOODS.CLASS_PATH.eq(GOODS_CLASS.CLASS_PATH))
            .where(GOODS.STATE.in(
                Arrays.asList(GoodsState.PUBLISHED, GoodsState.OFF_SALE, GoodsState.OCCUPY, GoodsState.SOLD)))
            .and(GOODS_CLASS.LANG.eq(AuthContext.getLang()));

        if (!StrUtils.isBlank(param.getClassPath())) {
            sql.and(GOODS.CLASS_PATH.like(SQLUtils.suffixLike(param.getClassPath())));
        }

        if (!StrUtils.isBlank(param.getTitle())) {
            sql.and(GOODS.TITLE.like(SQLUtils.suffixLike(param.getTitle())));
        }

        // 排序
        sql.orderBy(GOODS.ID.asc());

        return getPageResult(sql, param, GoodsDetailVO.class);
    }


    public int updateGoodsSale(Long goodsId, String state) {
        return db.update(GOODS).set(GOODS.STATE, state).where(GOODS.ID.eq(goodsId)).execute();
    }


    public GoodsRecord findOne(Long goodsId) {
        return db.select(GOODS.ID, GOODS.STATE, GOODS.TITLE).from(GOODS).where(GOODS.ID.eq(goodsId))
            .fetchAnyInto(GoodsRecord.class);
    }


    public int updateGoods(GoodsRecord goods) {
        return db.update(Goods.GOODS)
            .set(Goods.GOODS.TITLE, goods.getTitle())
            .set(Goods.GOODS.DESCRIPTION, goods.getDescription())
            .set(Goods.GOODS.ORIGINAL_PRICE, goods.getOriginalPrice())
            .set(Goods.GOODS.REAL_PRICE, goods.getRealPrice())
            .set(Goods.GOODS.MONEY_KIND, goods.getMoneyKind())
            .set(Goods.GOODS.LAUNCH_USER_ID, goods.getLaunchUserId())
            .set(Goods.GOODS.LAUNCH_USER_NICKNAME, goods.getLaunchUserNickname())
            .set(Goods.GOODS.LAUNCH_LAST_TIME, goods.getLaunchLastTime())
            .set(Goods.GOODS.QUALITY, goods.getQuality())
            .set(Goods.GOODS.CLASS_PATH, goods.getClassPath())
            .set(Goods.GOODS.STATE, goods.getState())
            .set(Goods.GOODS.COUNTRY, goods.getCountry())
            .set(Goods.GOODS.PROVINCE, goods.getProvince())
            .set(Goods.GOODS.CITY, goods.getCity())
            .set(Goods.GOODS.DISTRICT, goods.getDistrict())
            .set(Goods.GOODS.BRAND, goods.getBrand())
            .set(Goods.GOODS.AI_IDENTIFY_NO, goods.getAiIdentifyNo())
            .set(Goods.GOODS.DELIVERY_TYPE, goods.getDeliveryType())
            .where(Goods.GOODS.ID.eq(goods.getId()))
            .execute();
    }


    public Long findAnyGoodsByAiIdentifyNo(String aiIdentifyNo) {
        return db.select(Goods.GOODS.ID).from(Goods.GOODS).where(Goods.GOODS.AI_IDENTIFY_NO.eq(aiIdentifyNo))
            .fetchAnyInto(Long.class);
    }

}
