package com.etrade.puggo.dao.user;

import static com.etrade.puggo.db.Tables.USER_LIKES;

import com.etrade.puggo.dao.BaseDao;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 用户收藏列表
 * @date 2023/6/7 18:15
 **/
@Repository
public class UserLikesDao extends BaseDao {

    public Long likeGroupon(Long grouponId) {
        long userId = userId();

        Long id = isGrouponLike(grouponId, userId);

        if (id == null) {
            return db.insertInto(USER_LIKES, USER_LIKES.USER_ID, USER_LIKES.GROUPON_ID)
                .values(userId, grouponId)
                .returning(USER_LIKES.ID).fetchOne().getId();
        }

        return null;
    }


    public Integer unlikeGroupon(Long grouponId) {
        long userId = userId();

        Long id = isGrouponLike(grouponId, userId);

        if (id != null) {
            return db.delete(USER_LIKES).where(USER_LIKES.ID.eq(id)).execute();
        }

        return 0;
    }


    public Long isGrouponLike(Long grouponId, long userId) {
        return db.select(USER_LIKES.ID)
            .from(USER_LIKES)
            .where(USER_LIKES.USER_ID.eq(userId))
            .and(USER_LIKES.GROUPON_ID.eq(grouponId))
            .fetchAnyInto(Long.class);
    }


    public Integer unlikeGoods(Long goodsId) {
        long userId = userId();

        Long id = isGoodsLike(goodsId, userId);

        if (id != null) {
            return db.delete(USER_LIKES).where(USER_LIKES.ID.eq(id)).execute();
        }

        return 0;
    }

    public Long likeGoods(Long goodsId) {
        long userId = userId();

        Long id = isGoodsLike(goodsId, userId);

        if (id == null) {
            return db.insertInto(USER_LIKES, USER_LIKES.USER_ID, USER_LIKES.GOODS_ID)
                .values(userId, goodsId)
                .returning(USER_LIKES.ID).fetchOne().getId();
        }

        return null;
    }


    public List<Long> isGoodsLike(List<Long> goodsIds, long userId) {
        return db.select(USER_LIKES.GOODS_ID)
            .from(USER_LIKES)
            .where(USER_LIKES.USER_ID.eq(userId))
            .and(USER_LIKES.GOODS_ID.in(goodsIds))
            .fetchInto(Long.class);
    }


    public Long isGoodsLike(Long goodsId, long userId) {
        return db.select(USER_LIKES.ID)
            .from(USER_LIKES)
            .where(USER_LIKES.USER_ID.eq(userId))
            .and(USER_LIKES.GOODS_ID.eq(goodsId))
            .fetchAnyInto(Long.class);
    }


    public List<Long> findUserLikeGoodsList() {
        return db.select(USER_LIKES.GOODS_ID)
            .from(USER_LIKES)
            .where(USER_LIKES.USER_ID.eq(userId()).and(USER_LIKES.GOODS_ID.gt(0L)))
            .fetchInto(Long.class);
    }


    public List<Long> findUserLikeGrouponList() {
        return db.select(USER_LIKES.GROUPON_ID)
            .from(USER_LIKES)
            .where(USER_LIKES.USER_ID.eq(userId()).and(USER_LIKES.GROUPON_ID.gt(0L)))
            .fetchInto(Long.class);
    }

}
