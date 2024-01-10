package com.etrade.puggo.dao.groupon;

import static com.etrade.puggo.db.Tables.GROUPON_COUPON_DETAIL;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.GrouponCouponDetailRecord;
import com.etrade.puggo.service.groupon.dto.Text;
import java.util.List;
import org.jooq.InsertValuesStep3;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 团购优惠券商品说明
 * @date 2023/6/6 18:11
 **/
@Repository
public class GrouponCouponDetailDao extends BaseDao {


    /**
     * 类别
     */
    public static final String TYPE_BASE_INFO = "BASE_INFO";
    public static final String TYPE_USE_STATEMENT = "USE_STATEMENT";


    public List<Text> findGrouponCouponDetailList(Long grouponId) {
        return db.select(GROUPON_COUPON_DETAIL.TEXT, GROUPON_COUPON_DETAIL.CREATED, GROUPON_COUPON_DETAIL.TYPE)
            .from(GROUPON_COUPON_DETAIL)
            .where(GROUPON_COUPON_DETAIL.GROUPON_ID.eq(grouponId))
            .fetchInto(Text.class);
    }


    public void saveGrouponDetail(List<String> textList, String type, long grouponId) {
        InsertValuesStep3<GrouponCouponDetailRecord, String, Long, String> sql = db.insertInto(
            GROUPON_COUPON_DETAIL,
            GROUPON_COUPON_DETAIL.TEXT,
            GROUPON_COUPON_DETAIL.GROUPON_ID,
            GROUPON_COUPON_DETAIL.TYPE);

        for (String text : textList) {
            sql.values(text, grouponId, type);
        }

        sql.execute();
    }


    public List<Text> findGrouponCouponDetailList(List<Long> grouponIds) {
        return db.select(GROUPON_COUPON_DETAIL.TEXT, GROUPON_COUPON_DETAIL.GROUPON_ID, GROUPON_COUPON_DETAIL.TYPE)
            .from(GROUPON_COUPON_DETAIL)
            .where(GROUPON_COUPON_DETAIL.GROUPON_ID.in(ascendingOrder(grouponIds)))
            .fetchInto(Text.class);
    }


    public int clearGrouponCouponDetail(Long grouponId) {
        return db
            .deleteFrom(GROUPON_COUPON_DETAIL)
            .where(GROUPON_COUPON_DETAIL.GROUPON_ID.eq(grouponId))
            .execute();
    }

}
