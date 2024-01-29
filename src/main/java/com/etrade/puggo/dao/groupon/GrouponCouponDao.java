package com.etrade.puggo.dao.groupon;

import static com.etrade.puggo.db.Tables.GROUPON_CLASS;
import static com.etrade.puggo.db.Tables.GROUPON_COUPON;
import static com.etrade.puggo.db.Tables.GROUPON_COUPON_DATA;
import static com.etrade.puggo.db.Tables.GROUPON_COUPON_PICTURE;
import static com.etrade.puggo.db.Tables.GROUPON_COUPON_RULE;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.constants.GrouponState;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.service.groupon.GrouponCouponDTO;
import com.etrade.puggo.service.groupon.GrouponCouponDetailDTO;
import com.etrade.puggo.service.groupon.ListGrouponCouponParam;
import com.etrade.puggo.service.groupon.admin.GenGrouponCouponDTO;
import com.etrade.puggo.service.groupon.admin.GenGrouponCouponDTO.GrouponDetail;
import com.etrade.puggo.service.groupon.admin.GrouponListParam;
import com.etrade.puggo.service.groupon.admin.GrouponListVO;
import com.etrade.puggo.utils.DateTimeUtils;
import com.etrade.puggo.utils.SQLUtils;
import com.etrade.puggo.utils.StrUtils;
import org.jooq.SelectConditionStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 团购券Dao
 * @date 2023/6/6 18:11
 **/
@Repository
public class GrouponCouponDao extends BaseDao {


    public PageContentContainer<GrouponCouponDTO> listGrouponCouponPage(ListGrouponCouponParam param) {

        SelectConditionStep<?> sql = db.select(
                GROUPON_COUPON.ID.as("grouponId"),
                GROUPON_COUPON.TITLE,
                GROUPON_COUPON.ORIGINAL_PRICE,
                GROUPON_COUPON.REAL_PRICE_INTERVAL,
                GROUPON_COUPON.REAL_PRICE,
                GROUPON_COUPON.MONEY_KIND,
                GROUPON_COUPON.CREATED,
                GROUPON_COUPON.STATE,
                GROUPON_COUPON_DATA.BROWSE_NUMBER,
                GROUPON_COUPON_DATA.LIKE_NUMBER,
                DSL.ifnull(GROUPON_COUPON_PICTURE.URL, "").as("mainPic")
            )
            .from(GROUPON_COUPON)
            .leftJoin(GROUPON_COUPON_DATA)
            .on(GROUPON_COUPON.ID.eq(GROUPON_COUPON_DATA.GROUPON_ID))
            .leftJoin(GROUPON_COUPON_PICTURE)
            .on(GROUPON_COUPON.ID.eq(GROUPON_COUPON_PICTURE.GROUPON_ID))
            .where(GROUPON_COUPON_PICTURE.IS_MAIN.eq((GrouponCouponPicDao.IS_MAIN)))
            .and(GROUPON_COUPON.STATE.ne(GrouponState.DELETED));

        if (!StrUtils.isEmpty(param.getClassPath())) {
            sql.and(GROUPON_COUPON.CLASS_PATH.like(SQLUtils.suffixLike(param.getClassPath())));
        }

        if (!StrUtils.isEmpty(param.getTitle())) {
            sql.and(GROUPON_COUPON.TITLE.like(SQLUtils.suffixLike(param.getTitle())));
        }

        if (!CollectionUtils.isEmpty(param.getGrouponList())) {
            sql.and(GROUPON_COUPON.ID.in(param.getGrouponList()));
        }

        if (!StrUtils.isEmpty(param.getSearchLabel())) {
            String searchLabel = param.getSearchLabel();
            if (searchLabel.equals("热门精选")) {
                sql.orderBy(GROUPON_COUPON_DATA.TRADE_NUMBER.desc());
            }
        }

        return getPageResult(sql, param, GrouponCouponDTO.class);
    }


    public GrouponCouponDetailDTO findGrouponCoupon(Long grouponId) {
        return db.select(
                GROUPON_COUPON.ID.as("grouponId"),
                GROUPON_COUPON.TITLE,
                GROUPON_COUPON.ORIGINAL_PRICE,
                GROUPON_COUPON.REAL_PRICE_INTERVAL,
                GROUPON_COUPON.REAL_PRICE,
                GROUPON_COUPON.MONEY_KIND,
                GROUPON_COUPON.CREATED,
                GROUPON_COUPON.STATE,
                GROUPON_COUPON_DATA.BROWSE_NUMBER,
                GROUPON_COUPON_DATA.LIKE_NUMBER
            )
            .from(GROUPON_COUPON)
            .join(GROUPON_COUPON_DATA)
            .on(GROUPON_COUPON.ID.eq(GROUPON_COUPON_DATA.GROUPON_ID))
            .where(GROUPON_COUPON.ID.eq(grouponId))
            .fetchAnyInto(GrouponCouponDetailDTO.class);
    }

    public Long saveGroupon(GrouponDetail param, String state) {
        return db.insertInto(
                GROUPON_COUPON,
                GROUPON_COUPON.TITLE,
                GROUPON_COUPON.ORIGINAL_PRICE,
                GROUPON_COUPON.REAL_PRICE,
                GROUPON_COUPON.REAL_PRICE_INTERVAL,
                GROUPON_COUPON.MONEY_KIND,
                GROUPON_COUPON.CLASS_PATH,
                GROUPON_COUPON.STATE,
                GROUPON_COUPON.LAUNCH_USER_ID,
                GROUPON_COUPON.LAUNCH_USER_NICKNAME,
                GROUPON_COUPON.LAUNCH_LAST_TIME,
                GROUPON_COUPON.BRAND
            )
            .values(
                param.getTitle(),
                param.getOriginalPrice(),
                param.getRealPrice(),
                param.getRealPriceInterval(),
                param.getMoneyKind(),
                param.getClassPath(),
                state,
                userId(),
                userName(),
                DateTimeUtils.now(),
                param.getBrand()
            )
            .returning(GROUPON_COUPON.ID).fetchOne().getId();
    }


    public PageContentContainer<GrouponListVO> listGrouponCouponPageWeb(GrouponListParam param) {

        SelectOnConditionStep<?> sql = db.select(
                GROUPON_COUPON.ID.as("grouponId"),
                GROUPON_COUPON.TITLE,
                GROUPON_COUPON.ORIGINAL_PRICE,
                GROUPON_COUPON.REAL_PRICE_INTERVAL,
                GROUPON_COUPON.REAL_PRICE,
                GROUPON_COUPON.MONEY_KIND,
                GROUPON_COUPON.CREATED,
                GROUPON_COUPON.STATE,
                GROUPON_COUPON.BRAND,
                GROUPON_COUPON.LAUNCH_USER_NICKNAME,
                GROUPON_COUPON.LAUNCH_LAST_TIME,
                GROUPON_CLASS.CLASS_NAME,

                // 团购券规则相关
                GROUPON_COUPON_RULE.SERVICE_TIME_UNIT,
                GROUPON_COUPON_RULE.SERVICE_TIME_INTERVAL,
                GROUPON_COUPON_RULE.SERVICE_TIME_START,
                GROUPON_COUPON_RULE.SERVICE_TIME_END,
                GROUPON_COUPON_RULE.EFFECTIVE_DAYS,
                GROUPON_COUPON_RULE.MIN_REPLY_HOURS,
                GROUPON_COUPON_RULE.IS_ALLOW_CANCEL,
                GROUPON_COUPON_RULE.APPLICABLE_SHOP,
                GROUPON_COUPON_RULE.SHOP_ADDRESS,
                GROUPON_COUPON_RULE.SHOP_ARRIVAL_METHOD,

                // 团购券数据
                GROUPON_COUPON_DATA.BROWSE_NUMBER,
                GROUPON_COUPON_DATA.LIKE_NUMBER,
                GROUPON_COUPON_DATA.TRADE_NUMBER
            )
            .from(GROUPON_COUPON)
            .innerJoin(GROUPON_CLASS)
            .on(GROUPON_COUPON.CLASS_PATH.eq(GROUPON_CLASS.CLASS_PATH))
            .innerJoin(GROUPON_COUPON_RULE)
            .on(GROUPON_COUPON.ID.eq(GROUPON_COUPON_RULE.GROUPON_ID))
            .innerJoin(GROUPON_COUPON_DATA)
            .on(GROUPON_COUPON_DATA.GROUPON_ID.eq(GROUPON_COUPON.ID));

        if (param.getClassId() != null) {
            sql.where(GROUPON_CLASS.ID.eq(param.getClassId()));
        }

        if (!StrUtils.isEmpty(param.getTitle())) {
            sql.where(GROUPON_COUPON.TITLE.like(SQLUtils.suffixLike(param.getTitle())));
        }

        sql.orderBy(GROUPON_COUPON.ID.desc());

        return getPageResult(sql, param, GrouponListVO.class);
    }

    public int updateState(Long grouponId, String state) {
        return db.update(GROUPON_COUPON)
            .set(GROUPON_COUPON.STATE, state)
            .where(GROUPON_COUPON.ID.eq(grouponId))
            .execute();
    }


    public GenGrouponCouponDTO.GrouponDetail findGrouponCouponDetail(Long grouponId) {
        return db.select(
                GROUPON_COUPON.ID.as("grouponId"),
                GROUPON_COUPON.TITLE,
                GROUPON_COUPON.ORIGINAL_PRICE,
                GROUPON_COUPON.REAL_PRICE_INTERVAL,
                GROUPON_COUPON.REAL_PRICE,
                GROUPON_COUPON.MONEY_KIND,
                GROUPON_COUPON.STATE,
                GROUPON_COUPON.CLASS_PATH
            )
            .from(GROUPON_COUPON)
            .where(GROUPON_COUPON.ID.eq(grouponId))
            .fetchAnyInto(GenGrouponCouponDTO.GrouponDetail.class);
    }


    public Long checkExistsGrouponCoupon(Long grouponId) {
        return db.select(
                GROUPON_COUPON.ID
            )
            .from(GROUPON_COUPON)
            .where(GROUPON_COUPON.ID.eq(grouponId))
            .fetchAnyInto(Long.class);
    }


    public int updateGroupon(GrouponDetail param, Long grouponId) {
        return db
            .update(GROUPON_COUPON)
            .set(GROUPON_COUPON.TITLE, param.getTitle())
            .set(GROUPON_COUPON.ORIGINAL_PRICE, param.getOriginalPrice())
            .set(GROUPON_COUPON.REAL_PRICE, param.getRealPrice())
            .set(GROUPON_COUPON.REAL_PRICE_INTERVAL, param.getRealPriceInterval())
            .set(GROUPON_COUPON.MONEY_KIND, param.getMoneyKind())
            .set(GROUPON_COUPON.CLASS_PATH, param.getClassPath())
            .set(GROUPON_COUPON.BRAND, param.getBrand())
            .where(GROUPON_COUPON.ID.eq(grouponId))
            .execute();
    }

}
