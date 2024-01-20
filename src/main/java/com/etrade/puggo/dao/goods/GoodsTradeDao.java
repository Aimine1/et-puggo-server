package com.etrade.puggo.dao.goods;

import static com.etrade.puggo.db.Tables.GOODS;
import static com.etrade.puggo.db.Tables.GOODS_CLASS;
import static com.etrade.puggo.db.Tables.GOODS_TRADE;

import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.constants.GoodsTradeState;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.service.goods.sales.pojo.GoodsTradeDTO;
import com.etrade.puggo.service.goods.trade.GoodsTradeParam;
import com.etrade.puggo.service.goods.trade.GoodsTradeVO;
import com.etrade.puggo.service.goods.trade.MyTradeVO;
import com.etrade.puggo.service.goods.trade.UserGoodsTradeParam;
import com.etrade.puggo.utils.SQLUtils;
import com.etrade.puggo.utils.StrUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.jooq.Record10;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 商品订单
 * @date 2023/6/25 12:23
 **/
@Repository
public class GoodsTradeDao extends BaseDao {

    public long save(GoodsTradeDTO trade, String tradeNo) {
        return db.insertInto(GOODS_TRADE,
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


    public List<Long> findTradingGoodsIds(Long customerId) {
        return db
            .select(GOODS_TRADE.GOODS_ID)
            .from(GOODS_TRADE)
            .where(GOODS_TRADE.CUSTOMER_ID.eq(customerId).and(GOODS_TRADE.STATE.eq(GoodsTradeState.TO_USE)))
            .fetchInto(Long.class);
    }


    public PageContentContainer<GoodsTradeVO> findTradePage(GoodsTradeParam param) {
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
            .where(GOODS_TRADE.STATE.in(Arrays.asList(GoodsTradeState.TO_USE, GoodsTradeState.COMPLETE)));

        if (!StrUtils.isBlank(param.getTitle())) {
            sql.and(GOODS.TITLE.like(SQLUtils.suffixLike(param.getTitle())));
        }

        if (!StrUtils.isBlank(param.getClassPath())) {
            sql.and(GOODS.CLASS_PATH.eq(param.getClassPath()));
        }

        return getPageResult(sql, param, GoodsTradeVO.class);

    }


    public PageContentContainer<MyTradeVO> findMyTradePage(UserGoodsTradeParam param) {
        SelectConditionStep<Record10<Long, Long, String, BigDecimal, LocalDateTime, String, String, String, String, Long>> sql =
            db.select(
                    GOODS_TRADE.GOODS_ID,
                    GOODS_TRADE.ID.as("tradeId"),
                    GOODS_TRADE.TRADE_NO,
                    GOODS_TRADE.TRADING_PRICE,
                    GOODS_TRADE.TRADING_TIME,
                    GOODS_TRADE.STATE,
                    GOODS_TRADE.CUSTOMER,

                    GOODS.TITLE.as("goodsTitle"),
                    GOODS.MONEY_KIND,
                    GOODS.LAUNCH_USER_ID
                )
                .from(GOODS_TRADE)
                .join(GOODS)
                .on(GOODS.ID.eq(GOODS_TRADE.GOODS_ID))
                .where(GOODS_TRADE.STATE.in(Arrays.asList(GoodsTradeState.TO_USE, GoodsTradeState.COMPLETE))
                    .and(GOODS_TRADE.CUSTOMER_ID.eq(userId()))
                );

        if (!StrUtils.isBlank(param.getState())) {
            sql.and(GOODS_TRADE.STATE.eq(param.getState()));
        }

        return getPageResult(sql, param, MyTradeVO.class);
    }

}
