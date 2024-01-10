package com.etrade.puggo.dao.goods;

import static com.etrade.puggo.db.Tables.GOODS_DATA;

import com.etrade.puggo.dao.BaseDao;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 商品数据
 * @date 2023/6/15 21:21
 **/
@Repository
public class GoodsDataDao extends BaseDao {

    public void incBrowseNumber(long goodsId) {
        db.update(GOODS_DATA)
            .set(GOODS_DATA.BROWSE_NUMBER, GOODS_DATA.BROWSE_NUMBER.plus(1))
            .where(GOODS_DATA.GOODS_ID.eq(goodsId))
            .execute();
    }

    public void incLikeNumber(long goodsId) {
        db.update(GOODS_DATA)
            .set(GOODS_DATA.LIKE_NUMBER, GOODS_DATA.LIKE_NUMBER.plus(1))
            .where(GOODS_DATA.GOODS_ID.eq(goodsId))
            .execute();
    }

    public void incCommentNumber(long goodsId) {
        db.update(GOODS_DATA)
            .set(GOODS_DATA.COMMENT_NUMBER, GOODS_DATA.COMMENT_NUMBER.plus(1))
            .where(GOODS_DATA.GOODS_ID.eq(goodsId))
            .execute();
    }

    public void decLikeNumber(long goodsId) {
        db.update(GOODS_DATA)
            .set(GOODS_DATA.LIKE_NUMBER,
                DSL.iif(GOODS_DATA.LIKE_NUMBER.le(1), 0, GOODS_DATA.LIKE_NUMBER.sub(1)))
            .where(GOODS_DATA.GOODS_ID.eq(goodsId))
            .execute();
    }

    public void newData(long goodsId) {
        Long id = db.select(GOODS_DATA.ID).from(GOODS_DATA)
            .where(GOODS_DATA.GOODS_ID.eq(goodsId)).fetchAnyInto(Long.class);
        if (id == null) {
            db.insertInto(GOODS_DATA, GOODS_DATA.GOODS_ID).values(goodsId).execute();
        }
    }

}
