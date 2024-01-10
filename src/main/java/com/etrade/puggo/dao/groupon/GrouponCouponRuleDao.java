package com.etrade.puggo.dao.groupon;

import static com.etrade.puggo.db.Tables.GROUPON_COUPON_RULE;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.Tables;
import com.etrade.puggo.db.tables.records.GrouponCouponRuleRecord;
import com.etrade.puggo.service.groupon.dto.ApplicableShop;
import com.etrade.puggo.service.groupon.dto.GrouponRule;
import com.etrade.puggo.utils.OptionalUtils;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 团购券规则
 * @date 2023/6/6 18:11
 **/
@Repository
public class GrouponCouponRuleDao extends BaseDao {


    public GrouponCouponRuleRecord findGrouponCouponRule(long grouponId) {
        return db
            .select(
                GROUPON_COUPON_RULE.SERVICE_TIME_START,
                GROUPON_COUPON_RULE.SERVICE_TIME_END,
                GROUPON_COUPON_RULE.SERVICE_TIME_INTERVAL,
                GROUPON_COUPON_RULE.SERVICE_TIME_UNIT,
                GROUPON_COUPON_RULE.MIN_REPLY_HOURS,
                GROUPON_COUPON_RULE.IS_ALLOW_CANCEL,
                GROUPON_COUPON_RULE.EFFECTIVE_DAYS,
                GROUPON_COUPON_RULE.APPLICABLE_BRAND,
                GROUPON_COUPON_RULE.APPLICABLE_SHOP,
                GROUPON_COUPON_RULE.SHOP_ADDRESS,
                GROUPON_COUPON_RULE.SHOP_ARRIVAL_METHOD,
                GROUPON_COUPON_RULE.LIMIT_NUMBER
            )
            .from(Tables.GROUPON_COUPON_RULE)
            .where(GROUPON_COUPON_RULE.GROUPON_ID.eq(grouponId))
            .fetchAnyInto(GrouponCouponRuleRecord.class);
    }


    public void saveGrouponRule(GrouponRule rule, ApplicableShop shop, long grouponId) {
        db.insertInto(
                GROUPON_COUPON_RULE,
                GROUPON_COUPON_RULE.SERVICE_TIME_START,
                GROUPON_COUPON_RULE.SERVICE_TIME_END,
                GROUPON_COUPON_RULE.SERVICE_TIME_INTERVAL,
                GROUPON_COUPON_RULE.SERVICE_TIME_UNIT,
                GROUPON_COUPON_RULE.MIN_REPLY_HOURS,
                GROUPON_COUPON_RULE.IS_ALLOW_CANCEL,
                GROUPON_COUPON_RULE.LIMIT_NUMBER,
                GROUPON_COUPON_RULE.EFFECTIVE_DAYS,
                GROUPON_COUPON_RULE.APPLICABLE_BRAND,
                GROUPON_COUPON_RULE.APPLICABLE_SHOP,
                GROUPON_COUPON_RULE.SHOP_ADDRESS,
                GROUPON_COUPON_RULE.SHOP_ARRIVAL_METHOD,
                GROUPON_COUPON_RULE.GROUPON_ID
            )
            .values(
                rule.getServiceTimeStart(),
                rule.getServiceTimeEnd(),
                rule.getServiceTimeInterval(),
                rule.getServiceTimeUnit(),
                rule.getMinReplyHours(),
                rule.getIsAllowCancel() ? (byte) 1 : (byte) 0,
                rule.getEffectiveDays(),
                OptionalUtils.valueOrDefault(rule.getLimitNumber()),
                shop.getApplicableBrand(),
                shop.getApplicableShop(),
                shop.getShopAddress(),
                shop.getShopArrivalMethod(),
                grouponId
            )
            .execute();
    }


    public int updateGrouponRule(GrouponRule rule, ApplicableShop shop, long grouponId) {
        return db
            .update(GROUPON_COUPON_RULE)
            .set(GROUPON_COUPON_RULE.SERVICE_TIME_START, rule.getServiceTimeStart())
            .set(GROUPON_COUPON_RULE.SERVICE_TIME_END, rule.getServiceTimeEnd())
            .set(GROUPON_COUPON_RULE.SERVICE_TIME_INTERVAL, rule.getServiceTimeInterval())
            .set(GROUPON_COUPON_RULE.SERVICE_TIME_UNIT, rule.getServiceTimeUnit())
            .set(GROUPON_COUPON_RULE.MIN_REPLY_HOURS, rule.getMinReplyHours())
            .set(GROUPON_COUPON_RULE.IS_ALLOW_CANCEL, rule.getIsAllowCancel() ? (byte) 1 : (byte) 0)
            .set(GROUPON_COUPON_RULE.LIMIT_NUMBER, OptionalUtils.valueOrDefault(rule.getLimitNumber()))
            .set(GROUPON_COUPON_RULE.EFFECTIVE_DAYS, rule.getEffectiveDays())
            .set(GROUPON_COUPON_RULE.APPLICABLE_BRAND, shop.getApplicableBrand())
            .set(GROUPON_COUPON_RULE.APPLICABLE_SHOP, shop.getApplicableShop())
            .set(GROUPON_COUPON_RULE.SHOP_ADDRESS, shop.getShopAddress())
            .set(GROUPON_COUPON_RULE.SHOP_ARRIVAL_METHOD, shop.getShopArrivalMethod())
            .where(GROUPON_COUPON_RULE.GROUPON_ID.eq(grouponId))
            .execute();
    }

}
