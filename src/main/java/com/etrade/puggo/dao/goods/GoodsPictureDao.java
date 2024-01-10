package com.etrade.puggo.dao.goods;

import static com.etrade.puggo.db.Tables.GOODS_PICTURE;

import com.etrade.puggo.constants.GoodsImgType;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.GoodsPictureRecord;
import com.etrade.puggo.service.goods.sales.GoodsMainPicUrlDTO;
import com.etrade.puggo.service.groupon.dto.S3Picture;
import com.etrade.puggo.service.setting.AdvertisementVO;
import com.etrade.puggo.third.aws.S3PutObjectResult;
import java.util.List;
import org.jooq.InsertValuesStep6;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 商品图片
 * @date 2023/5/24 18:20
 **/
@Repository
public class GoodsPictureDao extends BaseDao {

    /**
     * 是否是主图
     */
    private static final Byte IS_MAIN_PIC = 1;
    private static final Byte NOT_MAIN_PIC = 0;

    /**
     * 是否删除
     */
    public static final byte IS_ACTIVE = 0;
    public static final byte IS_DELETED = 1;


    public void savePic(Long targetId, String imgType, List<S3PutObjectResult> list) {

        InsertValuesStep6<GoodsPictureRecord, Long, String, String, String, String, Byte> s =
            db.insertInto(GOODS_PICTURE)
                .columns(
                    GOODS_PICTURE.TARGET_ID,
                    GOODS_PICTURE.IMG_TYPE,
                    GOODS_PICTURE.KEY,
                    GOODS_PICTURE.URL,
                    GOODS_PICTURE.VERSION_ID,
                    GOODS_PICTURE.IS_MAIN
                );

        for (int i = 0; i < list.size(); i++) {

            S3PutObjectResult pic = list.get(i);

            s.values(
                targetId,
                imgType,
                pic.getKey(),
                pic.getUrl(),
                pic.getVersionId(),
                // 第一张图默认主图
                i == 0 ? IS_MAIN_PIC : NOT_MAIN_PIC
            );
        }

        s.execute();
    }


    public Long saveSingle(S3Picture S3, String imgType) {
        return db.insertInto(
                GOODS_PICTURE,
                GOODS_PICTURE.URL,
                GOODS_PICTURE.KEY,
                GOODS_PICTURE.VERSION_ID,
                GOODS_PICTURE.IMG_TYPE
            )
            .values(S3.getUrl(), S3.getKey(), S3.getVersionId(), imgType)
            .returning(GOODS_PICTURE.ID).fetchOne().getId();
    }


    public List<GoodsMainPicUrlDTO> findGoodsMainPicList(List<Long> goodsIds) {
        return db
            .select(GOODS_PICTURE.URL, GOODS_PICTURE.TARGET_ID.as("goods_id"))
            .from(GOODS_PICTURE)
            .where(GOODS_PICTURE.TARGET_ID.in(ascendingOrder(goodsIds))
                .and(GOODS_PICTURE.IMG_TYPE.eq(GoodsImgType.GOODS))
                .and(GOODS_PICTURE.IS_MAIN.eq(IS_MAIN_PIC))
                .and(GOODS_PICTURE.DELETED.eq(IS_ACTIVE))
            )
            .fetchInto(GoodsMainPicUrlDTO.class);
    }


    public List<S3Picture> findTargetPictures(Long targetId, String imgType) {
        return db
            .select(GOODS_PICTURE.URL, GOODS_PICTURE.IS_MAIN, GOODS_PICTURE.KEY, GOODS_PICTURE.VERSION_ID)
            .from(GOODS_PICTURE)
            .where(GOODS_PICTURE.TARGET_ID.eq(targetId)
                .and(GOODS_PICTURE.IMG_TYPE.eq(imgType))
                .and(GOODS_PICTURE.DELETED.eq(IS_ACTIVE))
            )
            .fetchInto(S3Picture.class);
    }


    public List<S3Picture> findTargetPictures(List<Long> targetIds, String imgType) {
        return db
            .select(GOODS_PICTURE.TARGET_ID, GOODS_PICTURE.URL, GOODS_PICTURE.IS_MAIN)
            .from(GOODS_PICTURE)
            .where(GOODS_PICTURE.TARGET_ID.in(ascendingOrder(targetIds))
                .and(GOODS_PICTURE.IMG_TYPE.eq(imgType))
                .and(GOODS_PICTURE.DELETED.eq(IS_ACTIVE))
            )
            .fetchInto(S3Picture.class);
    }


    public List<S3Picture> findS3IconList(List<Long> iconIdList) {
        return db.select(GOODS_PICTURE.URL, GOODS_PICTURE.ID, GOODS_PICTURE.KEY, GOODS_PICTURE.VERSION_ID)
            .from(GOODS_PICTURE)
            .where(GOODS_PICTURE.ID.in(ascendingOrder(iconIdList))
                .and(GOODS_PICTURE.IMG_TYPE.eq(GoodsImgType.ICON))
                .and(GOODS_PICTURE.DELETED.eq(IS_ACTIVE))
            )
            .fetchInto(S3Picture.class);
    }

    public List<AdvertisementVO> findS3AdList() {
        return db.select(GOODS_PICTURE.URL, GOODS_PICTURE.ID, GOODS_PICTURE.KEY, GOODS_PICTURE.VERSION_ID,
                GOODS_PICTURE.JUMP_URL, GOODS_PICTURE.JUMP_TYPE)
            .from(GOODS_PICTURE)
            .where(GOODS_PICTURE.IMG_TYPE.eq(GoodsImgType.AD)
                .and(GOODS_PICTURE.DELETED.eq(IS_ACTIVE))
            )
            .fetchInto(AdvertisementVO.class);
    }


    public String findGoodsMainPic(Long goodsId) {
        return db
            .select(GOODS_PICTURE.URL)
            .from(GOODS_PICTURE)
            .where(GOODS_PICTURE.TARGET_ID.eq(goodsId)
                .and(GOODS_PICTURE.IMG_TYPE.eq(GoodsImgType.GOODS))
                .and(GOODS_PICTURE.IS_MAIN.eq(IS_MAIN_PIC))
                .and(GOODS_PICTURE.DELETED.eq(IS_ACTIVE))
            )
            .fetchAnyInto(String.class);
    }


    public int deleteS3Object(String key, String imgType) {
        return db.update(GOODS_PICTURE).set(GOODS_PICTURE.DELETED, IS_DELETED)
            .where(GOODS_PICTURE.KEY.eq(key).and(GOODS_PICTURE.IMG_TYPE.eq(imgType))).execute();
    }


    public int deleteS3Object(Long id) {
        return db.update(GOODS_PICTURE).set(GOODS_PICTURE.DELETED, IS_DELETED)
            .where(GOODS_PICTURE.ID.eq(id)).execute();
    }

    public void deleteGoodsPics(Long goodsId) {
        db.deleteFrom(GOODS_PICTURE)
            .where(GOODS_PICTURE.IMG_TYPE.eq(GoodsImgType.GOODS).and(GOODS_PICTURE.TARGET_ID.eq(goodsId)))
            .execute();
    }
}
