package com.etrade.puggo.dao.goods;

import static com.etrade.puggo.db.Tables.GOODS_MESSAGE_LOGS;

import com.etrade.puggo.common.constants.GoodsMessageState;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.GoodsMessageLogsRecord;
import com.etrade.puggo.service.goods.message.BuyerOfferPriceCallbackParam;
import com.etrade.puggo.utils.DateTimeUtils;
import com.etrade.puggo.utils.OptionalUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.jooq.Record9;
import org.jooq.SelectOrderByStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 商品会话日志
 * @date 2023/7/11 18:40
 **/
@Repository
public class GoodsMessageLogDao extends BaseDao {


    public GoodsMessageLogsRecord selectOne(Long buyerId, Long sellerId, Long goodsId) {
        if (goodsId == null) {
            return selectOne(buyerId, sellerId);
        }
        return db.select(
                GOODS_MESSAGE_LOGS.ID,
                GOODS_MESSAGE_LOGS.GOODS_ID,
                GOODS_MESSAGE_LOGS.BUYER_ID,
                GOODS_MESSAGE_LOGS.SELLER_ID,
                GOODS_MESSAGE_LOGS.BUYER_PRICE,
                GOODS_MESSAGE_LOGS.STATE,
                GOODS_MESSAGE_LOGS.IS_GOODS_CONMMENT,
                GOODS_MESSAGE_LOGS.IS_BUYER_CONMMENT,
                GOODS_MESSAGE_LOGS.IS_SELLER_CONMMENT
            )
            .from(GOODS_MESSAGE_LOGS)
            .where(GOODS_MESSAGE_LOGS.BUYER_ID.eq(buyerId)
                .and(GOODS_MESSAGE_LOGS.SELLER_ID.eq(sellerId))
                .and(GOODS_MESSAGE_LOGS.GOODS_ID.eq(goodsId))
            )
            .fetchAnyInto(GoodsMessageLogsRecord.class);
    }


    public GoodsMessageLogsRecord selectOne(Long buyerId, Long sellerId) {

        SelectOrderByStep<Record9<Long, Long, Long, BigDecimal, String, Byte, Byte, Byte, LocalDateTime>> sql = db
            .select(
                GOODS_MESSAGE_LOGS.GOODS_ID,
                GOODS_MESSAGE_LOGS.BUYER_ID,
                GOODS_MESSAGE_LOGS.SELLER_ID,
                GOODS_MESSAGE_LOGS.BUYER_PRICE,
                GOODS_MESSAGE_LOGS.STATE,
                GOODS_MESSAGE_LOGS.IS_GOODS_CONMMENT,
                GOODS_MESSAGE_LOGS.IS_BUYER_CONMMENT,
                GOODS_MESSAGE_LOGS.IS_SELLER_CONMMENT,
                GOODS_MESSAGE_LOGS.MODIFIED
            )
            .from(GOODS_MESSAGE_LOGS)
            .where(GOODS_MESSAGE_LOGS.BUYER_ID.eq(buyerId).and(GOODS_MESSAGE_LOGS.SELLER_ID.eq(sellerId)))
            .union(
                DSL.select(
                        GOODS_MESSAGE_LOGS.GOODS_ID,
                        GOODS_MESSAGE_LOGS.BUYER_ID,
                        GOODS_MESSAGE_LOGS.SELLER_ID,
                        GOODS_MESSAGE_LOGS.BUYER_PRICE,
                        GOODS_MESSAGE_LOGS.STATE,
                        GOODS_MESSAGE_LOGS.IS_GOODS_CONMMENT,
                        GOODS_MESSAGE_LOGS.IS_BUYER_CONMMENT,
                        GOODS_MESSAGE_LOGS.IS_SELLER_CONMMENT,
                        GOODS_MESSAGE_LOGS.MODIFIED
                    )
                    .from(GOODS_MESSAGE_LOGS)
                    .where(GOODS_MESSAGE_LOGS.BUYER_ID.eq(sellerId).and(GOODS_MESSAGE_LOGS.SELLER_ID.eq(buyerId)))
            );

        return sql.orderBy(GOODS_MESSAGE_LOGS.MODIFIED.desc()).fetchAnyInto(GoodsMessageLogsRecord.class);
    }


    public Long saveOne(Long sellerId, Long goodsId, String state, BigDecimal buyerPrice) {
        return db.insertInto(GOODS_MESSAGE_LOGS)
            .columns(
                GOODS_MESSAGE_LOGS.BUYER_ID,
                GOODS_MESSAGE_LOGS.SELLER_ID,
                GOODS_MESSAGE_LOGS.GOODS_ID,
                GOODS_MESSAGE_LOGS.BUYER_PRICE,
                GOODS_MESSAGE_LOGS.STATE
            )
            .values(
                userId(),
                sellerId,
                goodsId,
                OptionalUtils.valueOrDefault(buyerPrice),
                state
            )
            .returning(GOODS_MESSAGE_LOGS.ID).fetchOne().getId();
    }


    public Integer updateBuyerPrice(BuyerOfferPriceCallbackParam param) {
        return db.update(GOODS_MESSAGE_LOGS)
            .set(GOODS_MESSAGE_LOGS.BUYER_PRICE, param.getBuyerPrice())
            .set(GOODS_MESSAGE_LOGS.STATE, GoodsMessageState.OFFER_PRICE)
            .where(GOODS_MESSAGE_LOGS.BUYER_ID.eq(userId())
                .and(GOODS_MESSAGE_LOGS.SELLER_ID.eq(param.getSellerId()))
                .and(GOODS_MESSAGE_LOGS.GOODS_ID.eq(param.getGoodsId()))
            )
            .limit(1)
            .execute();
    }


    public Integer updateState(Long buyerId, Long sellerId, Long goodsId, String state) {
        return db.update(GOODS_MESSAGE_LOGS)
            .set(GOODS_MESSAGE_LOGS.STATE, state)
            .where(GOODS_MESSAGE_LOGS.BUYER_ID.eq(buyerId)
                .and(GOODS_MESSAGE_LOGS.SELLER_ID.eq(sellerId))
                .and(GOODS_MESSAGE_LOGS.GOODS_ID.eq(goodsId))
            )
            .limit(1)
            .execute();
    }


    public Integer updateIsGoodsComment(Long buyerId, Long sellerId, Long goodsId) {
        return db.update(GOODS_MESSAGE_LOGS)
            .set(GOODS_MESSAGE_LOGS.IS_GOODS_CONMMENT, (byte) 1)
            .where(GOODS_MESSAGE_LOGS.BUYER_ID.eq(buyerId)
                .and(GOODS_MESSAGE_LOGS.SELLER_ID.eq(sellerId))
                .and(GOODS_MESSAGE_LOGS.GOODS_ID.eq(goodsId))
            )
            .limit(1)
            .execute();
    }

    public Integer updateIsSellerComment(Long buyerId, Long sellerId, Long goodsId) {
        return db.update(GOODS_MESSAGE_LOGS)
            .set(GOODS_MESSAGE_LOGS.IS_SELLER_CONMMENT, (byte) 1)
            .where(GOODS_MESSAGE_LOGS.BUYER_ID.eq(buyerId)
                .and(GOODS_MESSAGE_LOGS.SELLER_ID.eq(sellerId))
                .and(GOODS_MESSAGE_LOGS.GOODS_ID.eq(goodsId))
            )
            .limit(1)
            .execute();
    }

    public Integer updateIsBuyerComment(Long buyerId, Long sellerId, Long goodsId) {
        return db.update(GOODS_MESSAGE_LOGS)
            .set(GOODS_MESSAGE_LOGS.IS_BUYER_CONMMENT, (byte) 1)
            .where(GOODS_MESSAGE_LOGS.BUYER_ID.eq(buyerId)
                .and(GOODS_MESSAGE_LOGS.SELLER_ID.eq(sellerId))
                .and(GOODS_MESSAGE_LOGS.GOODS_ID.eq(goodsId))
            )
            .limit(1)
            .execute();
    }


    public void updateModified(Long id) {
        db.update(GOODS_MESSAGE_LOGS)
            .set(GOODS_MESSAGE_LOGS.MODIFIED, DateTimeUtils.now())
            .where(GOODS_MESSAGE_LOGS.ID.eq(id)).execute();
    }

}
