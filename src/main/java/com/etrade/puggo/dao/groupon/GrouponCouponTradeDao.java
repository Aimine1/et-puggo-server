package com.etrade.puggo.dao.groupon;

import static com.etrade.puggo.db.Tables.GROUPON_CLASS;
import static com.etrade.puggo.db.Tables.GROUPON_COUPON;
import static com.etrade.puggo.db.Tables.GROUPON_COUPON_PICTURE;
import static com.etrade.puggo.db.Tables.GROUPON_COUPON_TRADE;

import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.common.constants.GrouponTradeState;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.service.groupon.trade.TradeGrouponCouponDetailDTO;
import com.etrade.puggo.service.groupon.trade.TradeGrouponCouponListVO;
import com.etrade.puggo.service.groupon.trade.TradeGrouponCouponParam;
import com.etrade.puggo.service.groupon.trade.TradeGrouponCouponSearchParam;
import com.etrade.puggo.service.groupon.trade.TradeGrouponCouponWebVO;
import com.etrade.puggo.utils.SQLUtils;
import com.etrade.puggo.utils.StrUtils;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.jooq.Record6;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectSeekStep1;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 团购券订单
 * @date 2023/6/6 18:11
 **/
@Repository
public class GrouponCouponTradeDao extends BaseDao {


    public Long createTrade(TradeGrouponCouponParam t, String tradeNo) {

        return db.insertInto(
                GROUPON_COUPON_TRADE,
                GROUPON_COUPON_TRADE.TRADE_NO,
                GROUPON_COUPON_TRADE.GROUPON_ID,
                GROUPON_COUPON_TRADE.PLAN_IDS,
                GROUPON_COUPON_TRADE.USEAGE_DATE,
                GROUPON_COUPON_TRADE.NUMBER,
                GROUPON_COUPON_TRADE.CONTACT_NAME,
                GROUPON_COUPON_TRADE.CONTACT_LAST_NAME,
                GROUPON_COUPON_TRADE.CONTACT_PHONE,
                GROUPON_COUPON_TRADE.CONTACT_EMAIL,
                GROUPON_COUPON_TRADE.NATIONAL_CODE,
                GROUPON_COUPON_TRADE.STATE,
                GROUPON_COUPON_TRADE.CUSTOMER_ID
            )
            .values(
                tradeNo,
                t.getGrouponId(),
                t.getPlanList().stream().sorted().map(String::valueOf).collect(Collectors.joining(",")),
                t.getUseageDate(),
                t.getNumber(),
                t.getContactName(),
                t.getContactLastName(),
                t.getContactPhone(),
                t.getContactEmail(),
                t.getNationalCode().toString(),
                GrouponTradeState.TO_USE,
                userId()
            )
            .returning(GROUPON_COUPON_TRADE.ID).fetchOne().getId();

    }


    public Long findTrade(Long tradeId, Long userId) {
        return db.select(GROUPON_COUPON_TRADE.ID)
            .from(GROUPON_COUPON_TRADE)
            .where(GROUPON_COUPON_TRADE.ID.eq(tradeId).and(GROUPON_COUPON_TRADE.CUSTOMER_ID.eq(userId)))
            .fetchAnyInto(Long.class);
    }


    public void updateTradeState(Long tradeId, String state) {
        db.update(GROUPON_COUPON_TRADE)
            .set(GROUPON_COUPON_TRADE.STATE, state)
            .where(GROUPON_COUPON_TRADE.ID.eq(tradeId))
            .execute();
    }

    public PageContentContainer<TradeGrouponCouponListVO> getUserTradeList(PageParam param) {
        SelectSeekStep1<Record6<Long, String, Long, LocalDateTime, String, String>, LocalDateTime> sql = db
            .select(
                GROUPON_COUPON_TRADE.ID.as("tradeId"),
                GROUPON_COUPON_TRADE.TRADE_NO,
                GROUPON_COUPON_TRADE.GROUPON_ID,
                GROUPON_COUPON_TRADE.CREATED.as("tradeTime"),
                GROUPON_COUPON.TITLE.as("grouponTitle"),
                GROUPON_COUPON_PICTURE.URL.as("grouponMainPic")

            )
            .from(GROUPON_COUPON_TRADE)
            .join(GROUPON_COUPON)
            .on(GROUPON_COUPON_TRADE.GROUPON_ID.eq(GROUPON_COUPON.ID))
            .leftJoin(GROUPON_COUPON_PICTURE)
            .on(GROUPON_COUPON.ID.eq(GROUPON_COUPON_PICTURE.GROUPON_ID))
            .where(GROUPON_COUPON_TRADE.CUSTOMER_ID.eq(userId())
                .and(GROUPON_COUPON_TRADE.STATE.ne(GrouponTradeState.CANCELED))
                .and(GROUPON_COUPON_PICTURE.IS_MAIN.eq((byte) 1))
            )
            .orderBy(GROUPON_COUPON_TRADE.CREATED);

        return getPageResult(sql, param, TradeGrouponCouponListVO.class);
    }


    public TradeGrouponCouponDetailDTO getTradeDetail(Long tradeId, Long userId) {
        return db
            .select(
                GROUPON_COUPON_TRADE.ID.as("tradeId"),
                GROUPON_COUPON_TRADE.TRADE_NO,
                GROUPON_COUPON_TRADE.GROUPON_ID,
                GROUPON_COUPON_TRADE.USEAGE_DATE,
                GROUPON_COUPON_TRADE.NUMBER,
                GROUPON_COUPON_TRADE.CONTACT_NAME,
                GROUPON_COUPON_TRADE.CONTACT_LAST_NAME,
                GROUPON_COUPON_TRADE.CONTACT_PHONE,
                GROUPON_COUPON_TRADE.CONTACT_EMAIL,
                GROUPON_COUPON_TRADE.NATIONAL_CODE,
                GROUPON_COUPON_TRADE.STATE
            )
            .from(GROUPON_COUPON_TRADE)
            .where(GROUPON_COUPON_TRADE.ID.eq(tradeId).and(GROUPON_COUPON_TRADE.CUSTOMER_ID.eq(userId)))
            .fetchAnyInto(TradeGrouponCouponDetailDTO.class);
    }


    public String findPlanIdsByTradeId(Long tradeId) {
        return db
            .select(GROUPON_COUPON_TRADE.PLAN_IDS)
            .from(GROUPON_COUPON_TRADE)
            .where(GROUPON_COUPON_TRADE.ID.eq(tradeId))
            .fetchAnyInto(String.class);
    }


    public PageContentContainer<TradeGrouponCouponWebVO> getTradeList(TradeGrouponCouponSearchParam param) {

        SelectOnConditionStep sql = db
            .select(
                GROUPON_COUPON_TRADE.ID.as("tradeId"),
                GROUPON_COUPON_TRADE.TRADE_NO,
                GROUPON_COUPON_TRADE.GROUPON_ID,
                GROUPON_COUPON_TRADE.CREATED.as("tradeTime"),
                GROUPON_COUPON_TRADE.USEAGE_DATE,
                GROUPON_COUPON_TRADE.NUMBER,
                GROUPON_COUPON_TRADE.CUSTOMER_ID,
                GROUPON_COUPON_TRADE.CONTACT_NAME,
                GROUPON_COUPON_TRADE.CONTACT_LAST_NAME,
                GROUPON_COUPON_TRADE.CONTACT_PHONE,
                GROUPON_COUPON_TRADE.CONTACT_EMAIL,
                GROUPON_COUPON_TRADE.PLAN_IDS,
                GROUPON_COUPON_TRADE.STATE,

                GROUPON_COUPON.TITLE.as("grouponTitle")
            )
            .from(GROUPON_COUPON_TRADE)
            .join(GROUPON_COUPON)
            .on(GROUPON_COUPON_TRADE.GROUPON_ID.eq(GROUPON_COUPON.ID))
            .join(GROUPON_CLASS)
            .on(GROUPON_COUPON.CLASS_PATH.eq(GROUPON_CLASS.CLASS_PATH));

        if (!StrUtils.isEmpty(param.getTitle())) {
            sql.where(GROUPON_COUPON.TITLE.like(SQLUtils.suffixLike(param.getTitle())));
        }

        if (param.getClassId() != null) {
            sql.where(GROUPON_CLASS.ID.eq(param.getClassId()));
        }

        if (param.getTradeId() != null) {
            sql.where(GROUPON_COUPON_TRADE.ID.eq(param.getTradeId()));
        }

        sql.orderBy(GROUPON_COUPON_TRADE.ID.desc());

        return getPageResult(sql, param, TradeGrouponCouponWebVO.class);
    }


    public TradeGrouponCouponWebVO getTradeDetail(Long tradeId) {
        return db
            .select(
                GROUPON_COUPON_TRADE.ID.as("tradeId"),
                GROUPON_COUPON_TRADE.TRADE_NO,
                GROUPON_COUPON_TRADE.GROUPON_ID,
                GROUPON_COUPON_TRADE.CREATED.as("tradeTime"),
                GROUPON_COUPON_TRADE.USEAGE_DATE,
                GROUPON_COUPON_TRADE.NUMBER,
                GROUPON_COUPON_TRADE.CUSTOMER_ID,
                GROUPON_COUPON_TRADE.CONTACT_NAME,
                GROUPON_COUPON_TRADE.CONTACT_LAST_NAME,
                GROUPON_COUPON_TRADE.CONTACT_PHONE,
                GROUPON_COUPON_TRADE.CONTACT_EMAIL,
                GROUPON_COUPON_TRADE.PLAN_IDS,
                GROUPON_COUPON_TRADE.STATE,

                GROUPON_COUPON.TITLE.as("grouponTitle")
            )
            .from(GROUPON_COUPON_TRADE)
            .join(GROUPON_COUPON)
            .on(GROUPON_COUPON_TRADE.GROUPON_ID.eq(GROUPON_COUPON.ID))
            .where(GROUPON_COUPON_TRADE.ID.eq(tradeId))
            .fetchAnyInto(TradeGrouponCouponWebVO.class);
    }
}
