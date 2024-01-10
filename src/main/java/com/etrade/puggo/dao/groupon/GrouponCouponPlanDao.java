package com.etrade.puggo.dao.groupon;

import static com.etrade.puggo.db.Tables.GROUPON_COUPON_PLAN;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.GrouponCouponPlanRecord;
import com.etrade.puggo.service.groupon.dto.CouponPlan;
import java.math.BigDecimal;
import java.util.List;
import org.jooq.InsertValuesStep5;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 团购券方案
 * @date 2023/6/6 18:11
 **/
@Repository
public class GrouponCouponPlanDao extends BaseDao {


    public List<CouponPlan> findGrouponCouponPlanList(long grouponId) {
        return db
            .select(
                GROUPON_COUPON_PLAN.GROUPON_ID,
                GROUPON_COUPON_PLAN.PLAN_ID,
                GROUPON_COUPON_PLAN.PRICE,
                GROUPON_COUPON_PLAN.PRICE_INTERVAL,
                GROUPON_COUPON_PLAN.TEXT
            )
            .from(GROUPON_COUPON_PLAN)
            .where(GROUPON_COUPON_PLAN.GROUPON_ID.eq(grouponId))
            .orderBy(GROUPON_COUPON_PLAN.PLAN_ID)
            .fetchInto(CouponPlan.class);
    }

    public void saveGrouponPlan(List<CouponPlan> plans, long grouponId) {
        InsertValuesStep5<GrouponCouponPlanRecord, Integer, Long, BigDecimal, String, String> sql = db.insertInto(
            GROUPON_COUPON_PLAN,
            GROUPON_COUPON_PLAN.PLAN_ID,
            GROUPON_COUPON_PLAN.GROUPON_ID,
            GROUPON_COUPON_PLAN.PRICE,
            GROUPON_COUPON_PLAN.PRICE_INTERVAL,
            GROUPON_COUPON_PLAN.TEXT
        );

        for (int i = 0; i < plans.size(); i++) {
            int planId = i + 1;
            CouponPlan plan = plans.get(i);
            sql.values(planId, grouponId, plan.getPrice(), plan.getPriceInterval(), plan.getText());
        }

        sql.execute();
    }

    public List<CouponPlan> findGrouponCouponPlanList(Long grouponId, List<Long> planIdList) {
        return db
            .select(
                GROUPON_COUPON_PLAN.PLAN_ID,
                GROUPON_COUPON_PLAN.PRICE,
                GROUPON_COUPON_PLAN.PRICE_INTERVAL,
                GROUPON_COUPON_PLAN.TEXT
            )
            .from(GROUPON_COUPON_PLAN)
            .where(GROUPON_COUPON_PLAN.PLAN_ID.in(ascendingOrder(planIdList))
                .and(GROUPON_COUPON_PLAN.GROUPON_ID.eq(grouponId))
            )
            .orderBy(GROUPON_COUPON_PLAN.PLAN_ID)
            .fetchInto(CouponPlan.class);
    }


    public List<CouponPlan> findGrouponCouponPlanListByGrouponId(List<Long> grouponIdList) {
        return db
            .select(
                GROUPON_COUPON_PLAN.GROUPON_ID,
                GROUPON_COUPON_PLAN.PLAN_ID,
                GROUPON_COUPON_PLAN.PRICE,
                GROUPON_COUPON_PLAN.PRICE_INTERVAL,
                GROUPON_COUPON_PLAN.TEXT
            )
            .from(GROUPON_COUPON_PLAN)
            .where(GROUPON_COUPON_PLAN.GROUPON_ID.in(ascendingOrder(grouponIdList)))
            .orderBy(GROUPON_COUPON_PLAN.PLAN_ID)
            .fetchInto(CouponPlan.class);
    }


    public int clearGrouponCouponPlans(Long grouponId) {
        return db.deleteFrom(GROUPON_COUPON_PLAN)
            .where(GROUPON_COUPON_PLAN.GROUPON_ID.eq(grouponId))
            .execute();
    }

}
