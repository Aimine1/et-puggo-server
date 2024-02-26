package com.etrade.puggo.dao.goods;

import com.etrade.puggo.common.constants.GoodsTradeState;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.service.goods.sales.pojo.GoodsTradeDTO;
import com.etrade.puggo.service.goods.trade.pojo.*;
import com.etrade.puggo.utils.DateTimeUtils;
import com.etrade.puggo.utils.SQLUtils;
import com.etrade.puggo.utils.StrUtils;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

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


    public long save(GoodsTradeDTO trade, String tradeNo, Long paymentInvoiceId) {
        return db.insertInto(
                        GOODS_TRADE,
                        GOODS_TRADE.TRADE_NO,
                        GOODS_TRADE.GOODS_ID,
                        GOODS_TRADE.CUSTOMER_ID,
                        GOODS_TRADE.CUSTOMER,
                        GOODS_TRADE.SELLER_ID,
                        GOODS_TRADE.TRADING_PRICE,
                        GOODS_TRADE.TRADING_TIME,
                        GOODS_TRADE.STATE,
                        GOODS_TRADE.SHIPPING_METHOD,
                        GOODS_TRADE.DELIVERY_ADDRESS_ID,
                        GOODS_TRADE.TITLE,
                        GOODS_TRADE.PAYMENT_INVOICE_ID
                )
                .values(
                        tradeNo,
                        trade.getGoodsId(),
                        trade.getCustomerId(),
                        trade.getCustomer(),
                        trade.getSellerId(),
                        trade.getTradingPrice(),
                        trade.getTradingTime(),
                        trade.getState(),
                        trade.getShippingMethod(),
                        trade.getDeliveryAddressId(),
                        trade.getTitle(),
                        paymentInvoiceId
                )
                .returning(GOODS_TRADE.ID).fetchOne().getId();
    }


    public MyTradeVO getOne(Long customerId, Long sellerId, Long goodsId) {
        return db.select(
                        GOODS_TRADE.GOODS_ID,
                        GOODS_TRADE.ID.as("tradeId"),
                        GOODS_TRADE.TRADE_NO,
                        GOODS_TRADE.TRADING_PRICE,
                        GOODS_TRADE.TRADING_TIME,
                        GOODS_TRADE.STATE,
                        GOODS_TRADE.CUSTOMER_ID,
                        GOODS_TRADE.SELLER_ID,
                        GOODS_TRADE.DELIVERY_ADDRESS_ID,
                        GOODS_TRADE.TITLE,
                        GOODS_TRADE.SHIPPING_METHOD,

                        PAYMENT_INVOICE.PAYMENT_METHOD_ID,
                        PAYMENT_INVOICE.PAYMENT_TYPE,
                        PAYMENT_INVOICE.BILLING_ADDRESS_ID,
                        PAYMENT_INVOICE.PAYMENT_CARD_ID
                )
                .from(GOODS_TRADE)
                .innerJoin(PAYMENT_INVOICE)
                .on(GOODS_TRADE.PAYMENT_INVOICE_ID.eq(PAYMENT_INVOICE.ID))
                .where(GOODS_TRADE.CUSTOMER_ID.eq(customerId)
                        .and(GOODS_TRADE.SELLER_ID.eq(sellerId))
                        .and(GOODS_TRADE.GOODS_ID.eq(goodsId))
                )
                .fetchAnyInto(MyTradeVO.class);
    }


    public MyTradeVO getOne(Long tradeId) {
        return db.select(
                        GOODS_TRADE.GOODS_ID,
                        GOODS_TRADE.SELLER_ID,
                        GOODS_TRADE.CUSTOMER_ID,
                        GOODS_TRADE.TRADING_PRICE,
                        GOODS_TRADE.PAYMENT_INVOICE_ID
                )
                .from(GOODS_TRADE)
                .where(GOODS_TRADE.ID.eq(tradeId))
                .fetchAnyInto(MyTradeVO.class);
    }


    public MyTradeVO getOneByPaymentInvoiceId(Long paymentInvoiceId) {
        return db.select(
                        GOODS_TRADE.ID.as("tradeId"),
                        GOODS_TRADE.GOODS_ID,
                        GOODS_TRADE.SELLER_ID,
                        GOODS_TRADE.CUSTOMER_ID,
                        GOODS_TRADE.TRADING_PRICE,
                        GOODS_TRADE.PAYMENT_INVOICE_ID
                )
                .from(GOODS_TRADE)
                .where(GOODS_TRADE.PAYMENT_INVOICE_ID.eq(paymentInvoiceId))
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

        SelectConditionStep<?> sql = db.select(
                        GOODS_TRADE.GOODS_ID,
                        GOODS_TRADE.ID.as("tradeId"),
                        GOODS_TRADE.TRADE_NO,
                        GOODS_TRADE.TRADING_PRICE,
                        GOODS_TRADE.TRADING_TIME,
                        GOODS_TRADE.STATE,
                        GOODS_TRADE.CUSTOMER_ID,
                        GOODS_TRADE.SELLER_ID,
                        GOODS_TRADE.SHIPPING_METHOD,
                        GOODS_TRADE.DELIVERY_ADDRESS_ID,
                        GOODS_TRADE.TITLE,

                        PAYMENT_INVOICE.PAYMENT_METHOD_ID,
                        PAYMENT_INVOICE.PAYMENT_TYPE,
                        PAYMENT_INVOICE.BILLING_ADDRESS_ID,
                        PAYMENT_INVOICE.PAYMENT_CARD_ID,

                        GOODS.TITLE.as("goodsTitle"),
                        GOODS.MONEY_KIND,
                        GOODS.LAUNCH_USER_ID
                )
                .from(GOODS_TRADE)
                .join(GOODS)
                .on(GOODS.ID.eq(GOODS_TRADE.GOODS_ID))
                .join(PAYMENT_INVOICE)
                .on(PAYMENT_INVOICE.ID.eq(GOODS_TRADE.PAYMENT_INVOICE_ID))
                .where(GOODS_TRADE.CUSTOMER_ID.eq(userId()));

        if (!StrUtils.isBlank(param.getState())) {
            sql.and(GOODS_TRADE.STATE.eq(param.getState()));
        }

        return getPageResult(sql, param, MyTradeVO.class);
    }


    public List<Long> getTradesWithPaymentTimeout() {
        LocalDateTime deadline = DateTimeUtils.now().minusDays(1);
        return db.select(GOODS_TRADE.ID)
                .from(GOODS_TRADE)
                .where(GOODS_TRADE.STATE.eq(GoodsTradeState.PAY_PENDING)
                        .and(GOODS_TRADE.CREATED.lt(deadline)))
                .fetchInto(Long.class);
    }


    public void updateState(Long tradeId, String state) {
        db.update(GOODS_TRADE).set(GOODS_TRADE.STATE, state).where(GOODS_TRADE.ID.eq(tradeId)).execute();
    }


    public void batchUpdateState(List<Long> tradeIds, String state) {
        db.update(GOODS_TRADE).set(GOODS_TRADE.STATE, state).where(GOODS_TRADE.ID.in(ascendingOrder(tradeIds))).execute();
    }


    public void updateGoodsTrade(UpdateTradeParam param) {
        db.update(GOODS_TRADE)
                .set(GOODS_TRADE.SHIPPING_METHOD, param.getShippingMethod())
                .set(GOODS_TRADE.DELIVERY_ADDRESS_ID, param.getDeliveryAddressId())
                .set(GOODS_TRADE.SUBTOTAL, param.getSubtotal())
                .set(GOODS_TRADE.TAX, param.getTax())
                .where(GOODS_TRADE.ID.eq(param.getTradeId()))
                .execute();
    }

}
