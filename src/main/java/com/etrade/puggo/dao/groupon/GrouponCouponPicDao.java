package com.etrade.puggo.dao.groupon;

import static com.etrade.puggo.db.Tables.GROUPON_COUPON_PICTURE;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.GrouponCouponPictureRecord;
import com.etrade.puggo.service.groupon.GrouponCouponDetailDTO.Picture;
import com.etrade.puggo.service.groupon.dto.S3Picture;
import java.util.List;
import org.jooq.InsertValuesStep5;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 团购券图片
 * @date 2023/6/8 9:21
 **/
@Repository
public class GrouponCouponPicDao extends BaseDao {

    public static final byte IS_MAIN = 1;
    public static final byte IS_ICON = 1;

    public List<Picture> findPicList(Long grouponId) {
        return db.select(GROUPON_COUPON_PICTURE.IS_MAIN, GROUPON_COUPON_PICTURE.URL)
            .from(GROUPON_COUPON_PICTURE)
            .where(GROUPON_COUPON_PICTURE.GROUPON_ID.eq(grouponId))
            .fetchInto(Picture.class);
    }


    public void saveGrouponPic(List<S3Picture> pictureList, Long grouponId) {
        InsertValuesStep5<GrouponCouponPictureRecord, String, Long, String, String, Byte> sql = db.insertInto(
            GROUPON_COUPON_PICTURE,
            GROUPON_COUPON_PICTURE.URL,
            GROUPON_COUPON_PICTURE.GROUPON_ID,
            GROUPON_COUPON_PICTURE.KEY,
            GROUPON_COUPON_PICTURE.VERSION_ID,
            GROUPON_COUPON_PICTURE.IS_MAIN
        );

        for (int i = 0; i < pictureList.size(); i++) {
            S3Picture picture = pictureList.get(i);
            byte isMail = i == 0 ? (byte) 1 : (byte) 0;
            sql.values(picture.getUrl(), grouponId, picture.getKey(), picture.getVersionId(), isMail);
        }

        sql.execute();
    }


    public List<S3Picture> findS3PicList(Long grouponId) {
        return db.select(GROUPON_COUPON_PICTURE.URL, GROUPON_COUPON_PICTURE.IS_MAIN, GROUPON_COUPON_PICTURE.ID)
            .from(GROUPON_COUPON_PICTURE)
            .where(GROUPON_COUPON_PICTURE.GROUPON_ID.eq(grouponId))
            .orderBy(GROUPON_COUPON_PICTURE.CREATED)
            .fetchInto(S3Picture.class);
    }


    public int clearGrouponPic(Long grouponId) {
        return db.deleteFrom(GROUPON_COUPON_PICTURE)
            .where(GROUPON_COUPON_PICTURE.GROUPON_ID.eq(grouponId))
            .execute();
    }


    public Long saveIcon(S3Picture icon) {
        return db.insertInto(
                GROUPON_COUPON_PICTURE,
                GROUPON_COUPON_PICTURE.URL,
                GROUPON_COUPON_PICTURE.KEY,
                GROUPON_COUPON_PICTURE.VERSION_ID,
                GROUPON_COUPON_PICTURE.IS_ICON
            )
            .values(icon.getUrl(), icon.getKey(), icon.getVersionId(), IS_ICON)
            .returning(GROUPON_COUPON_PICTURE.ID).fetchOne().getId();
    }


    public List<S3Picture> findS3IconList(List<Long> iconIdList) {
        return db.select(GROUPON_COUPON_PICTURE.URL, GROUPON_COUPON_PICTURE.ID)
            .from(GROUPON_COUPON_PICTURE)
            .where(GROUPON_COUPON_PICTURE.ID.in(ascendingOrder(iconIdList))
                .and(GROUPON_COUPON_PICTURE.IS_ICON.eq(IS_ICON)))
            .fetchInto(S3Picture.class);
    }

}
