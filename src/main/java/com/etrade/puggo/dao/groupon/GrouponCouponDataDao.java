package com.etrade.puggo.dao.groupon;

import static com.etrade.puggo.db.Tables.GROUPON_COUPON_DATA;

import com.etrade.puggo.dao.BaseDao;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 团购券数据
 * @date 2023/6/7 9:50
 **/
@Repository
public class GrouponCouponDataDao extends BaseDao {

    public void incBrowseNumber(long grouponId) {
        db.update(GROUPON_COUPON_DATA)
            .set(GROUPON_COUPON_DATA.BROWSE_NUMBER, GROUPON_COUPON_DATA.BROWSE_NUMBER.plus(1))
            .where(GROUPON_COUPON_DATA.GROUPON_ID.eq(grouponId))
            .execute();
    }

    public void incLikeNumber(long grouponId) {
        db.update(GROUPON_COUPON_DATA)
            .set(GROUPON_COUPON_DATA.LIKE_NUMBER, GROUPON_COUPON_DATA.LIKE_NUMBER.plus(1))
            .where(GROUPON_COUPON_DATA.GROUPON_ID.eq(grouponId))
            .execute();
    }

    public void incCommentNumber(long grouponId) {
        db.update(GROUPON_COUPON_DATA)
            .set(GROUPON_COUPON_DATA.COMMENT_NUMBER, GROUPON_COUPON_DATA.COMMENT_NUMBER.plus(1))
            .where(GROUPON_COUPON_DATA.GROUPON_ID.eq(grouponId))
            .execute();
    }

    public void incTradeNumber(long grouponId) {
        db.update(GROUPON_COUPON_DATA)
            .set(GROUPON_COUPON_DATA.TRADE_NUMBER, GROUPON_COUPON_DATA.TRADE_NUMBER.plus(1))
            .where(GROUPON_COUPON_DATA.GROUPON_ID.eq(grouponId))
            .execute();
    }

    public void decLikeNumber(long grouponId) {
        db.update(GROUPON_COUPON_DATA)
            .set(GROUPON_COUPON_DATA.LIKE_NUMBER,
                DSL.iif(GROUPON_COUPON_DATA.LIKE_NUMBER.le(1), 0, GROUPON_COUPON_DATA.LIKE_NUMBER.sub(1)))
            .where(GROUPON_COUPON_DATA.GROUPON_ID.eq(grouponId))
            .execute();
    }

    public void newData(long grouponId) {
        Long id = db.select(GROUPON_COUPON_DATA.ID).from(GROUPON_COUPON_DATA)
            .where(GROUPON_COUPON_DATA.GROUPON_ID.eq(grouponId)).fetchAnyInto(Long.class);
        if (id == null) {
            db.insertInto(GROUPON_COUPON_DATA, GROUPON_COUPON_DATA.GROUPON_ID).values(grouponId).execute();
        }
    }
}
