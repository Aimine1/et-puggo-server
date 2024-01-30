package com.etrade.puggo.dao.goods;

import com.etrade.puggo.common.constants.GoodsTradeState;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.service.goods.sales.pojo.GoodsTradeDTO;
import com.etrade.puggo.service.goods.trade.GoodsTradeParam;
import com.etrade.puggo.service.goods.trade.GoodsTradeVO;
import com.etrade.puggo.service.goods.trade.MyTradeVO;
import com.etrade.puggo.service.goods.trade.UserGoodsTradeParam;
import com.etrade.puggo.utils.DateTimeUtils;
import com.etrade.puggo.utils.SQLUtils;
import com.etrade.puggo.utils.StrUtils;
import org.jooq.Record11;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.etrade.puggo.db.Tables.*;

/**
 * @author niuzhenyu
 * @description : 商品订单
 * @date 2023/6/25 12:23
 **/
@Repository
public class GoodsTradeDao extends BaseDao {

    public long save(GoodsTradeDTO trade, String tradeNo) {
        return db.insertInto(
                        GOODS_TRADE,
                        GOODS_TRADE.TRADE_NO,
                        GOODS_TRADE.GOODS_ID,
                        GOODS_TRADE.CUSTOMER_ID,
                        GOODS_TRADE.CUSTOMER,
                        GOODS_TRADE.SELLER_ID,
                        GOODS_TRADE.TRADING_PRICE,
                        GOODS_TRADE.TRADING_TIME,
                        GOODS_TRADE.STATE
                )
                .values(
                        tradeNo,
                        trade.getGoodsId(),
                        trade.getCustomerId(),
                        trade.getCustomer(),
                        trade.getSellerId(),
                        trade.getTradingPrice(),
                        trade.getTradingTime(),
                        trade.getState()
                )
                .returning(GOODS_TRADE.ID).fetchOne().getId();
    }


    public MyTradeVO getOne(Long goodsId) {
        return db.select(
                        GOODS_TRADE.GOODS_ID,
                        GOODS_TRADE.ID.as("tradeId"),
                        GOODS_TRADE.TRADE_NO,
                        GOODS_TRADE.TRADING_PRICE,
                        GOODS_TRADE.TRADING_TIME,
                        GOODS_TRADE.STATE,
                        GOODS_TRADE.CUSTOMER_ID,
                        GOODS_TRADE.SELLER_ID
                )
                .from(GOODS_TRADE)
                .where(GOODS_TRADE.ID.eq(goodsId))
                .fetchAnyInto(MyTradeVO.class);
    }


    public PageContentContainer<GoodsTradeVO> listTradePage(GoodsTradeParam param) {
        SelectConditionStep<?> sql = db
                .select(
                        GOODS_TRADE.GOODS_ID,
                        GOODS_TRADE.ID.as("tradeId"),
                        GOODS_TRADE.TRADE_NO,
                        GOODS_TRADE.TRADING_PRICE,
                        GOODS_TRADE.TRADING_TIME,
                        GOODS_TRADE.STATE,
                        GOODS_TRADE.CUSTOMER,

                        GOODS.TITLE.as("goodsTitle"),
                        GOODS.MONEY_KIND,
                        GOODS.IS_FREE,

                        GOODS_CLASS.CLASS_NAME
                )
                .from(GOODS_TRADE)
                .join(GOODS)
                .on(GOODS.ID.eq(GOODS_TRADE.GOODS_ID))
                .join(GOODS_CLASS)
                .on(GOODS_CLASS.CLASS_PATH.eq(GOODS.CLASS_PATH))
                .where(DSL.trueCondition());

        if (!StrUtils.isBlank(param.getTitle())) {
            sql.and(GOODS.TITLE.like(SQLUtils.suffixLike(param.getTitle())));
        }

        if (!StrUtils.isBlank(param.getClassPath())) {
            sql.and(GOODS.CLASS_PATH.eq(param.getClassPath()));
        }

        return getPageResult(sql, param, GoodsTradeVO.class);

    }


    public PageContentContainer<MyTradeVO> listMyTradePage(UserGoodsTradeParam param) {
        SelectConditionStep<Record11<Long, Long, String, BigDecimal, LocalDateTime, String, Long, Long, String, String, Long>> sql =
                db.select(
                                GOODS_TRADE.GOODS_ID,
                                GOODS_TRADE.ID.as("tradeId"),
                                GOODS_TRADE.TRADE_NO,
                                GOODS_TRADE.TRADING_PRICE,
                                GOODS_TRADE.TRADING_TIME,
                                GOODS_TRADE.STATE,
                                GOODS_TRADE.CUSTOMER_ID,
                                GOODS_TRADE.SELLER_ID,

                                GOODS.TITLE.as("goodsTitle"),
                                GOODS.MONEY_KIND,
                                GOODS.LAUNCH_USER_ID
                        )
                        .from(GOODS_TRADE)
                        .join(GOODS)
                        .on(GOODS.ID.eq(GOODS_TRADE.GOODS_ID))
                        .where(GOODS_TRADE.CUSTOMER_ID.eq(userId()));

        if (!StrUtils.isBlank(param.getState())) {
            sql.and(GOODS_TRADE.STATE.eq(param.getState()));
        }

        return getPageResult(sql, param, MyTradeVO.class);
    }


    public List<Long> getTradesWithPaymentTimeout() {
        return db.select(GOODS_TRADE.ID)
                .from(GOODS_TRADE)
                .where(GOODS_TRADE.STATE.eq(GoodsTradeState.TO_USE)
                        .and(GOODS_TRADE.CREATED.le(DateTimeUtils.now())))
                .fetchInto(Long.class);
    }


    public void batchUpdateState(List<Long> tradeIds, String state) {
        db.update(GOODS_TRADE).set(GOODS_TRADE.STATE, state).where(GOODS_TRADE.ID.in(ascendingOrder(tradeIds))).execute();
    }

}
