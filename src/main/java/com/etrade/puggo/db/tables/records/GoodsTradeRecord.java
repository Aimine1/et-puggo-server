/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables.records;


import com.etrade.puggo.db.tables.GoodsTrade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 商品交易
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GoodsTradeRecord extends UpdatableRecordImpl<GoodsTradeRecord> implements Record12<Long, String, Long, Long, String, BigDecimal, LocalDateTime, String, String, BigDecimal, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = -412276151;

    /**
     * Setter for <code>etrade_goods.goods_trade.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.trade_no</code>. 订单编号
     */
    public void setTradeNo(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.trade_no</code>. 订单编号
     */
    public String getTradeNo() {
        return (String) get(1);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.goods_id</code>. 商品id
     */
    public void setGoodsId(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.goods_id</code>. 商品id
     */
    public Long getGoodsId() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.customer_id</code>. 买家id
     */
    public void setCustomerId(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.customer_id</code>. 买家id
     */
    public Long getCustomerId() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.customer</code>. 买家昵称
     */
    public void setCustomer(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.customer</code>. 买家昵称
     */
    public String getCustomer() {
        return (String) get(4);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.trading_price</code>. 成交金额，不包括额外的运费
     */
    public void setTradingPrice(BigDecimal value) {
        set(5, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.trading_price</code>. 成交金额，不包括额外的运费
     */
    public BigDecimal getTradingPrice() {
        return (BigDecimal) get(5);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.trading_time</code>. 成交时间
     */
    public void setTradingTime(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.trading_time</code>. 成交时间
     */
    public LocalDateTime getTradingTime() {
        return (LocalDateTime) get(6);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.state</code>. 交易状态：1待付款 2交易完成 等等
     */
    public void setState(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.state</code>. 交易状态：1待付款 2交易完成 等等
     */
    public String getState() {
        return (String) get(7);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.delivery_type</code>. 发货方式：1邮寄 2面交 等等
     */
    public void setDeliveryType(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.delivery_type</code>. 发货方式：1邮寄 2面交 等等
     */
    public String getDeliveryType() {
        return (String) get(8);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.post_price</code>. 如果是邮寄方式发货，邮费金额
     */
    public void setPostPrice(BigDecimal value) {
        set(9, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.post_price</code>. 如果是邮寄方式发货，邮费金额
     */
    public BigDecimal getPostPrice() {
        return (BigDecimal) get(9);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.created</code>.
     */
    public void setCreated(LocalDateTime value) {
        set(10, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.created</code>.
     */
    public LocalDateTime getCreated() {
        return (LocalDateTime) get(10);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.modified</code>.
     */
    public void setModified(LocalDateTime value) {
        set(11, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.modified</code>.
     */
    public LocalDateTime getModified() {
        return (LocalDateTime) get(11);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record12 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row12<Long, String, Long, Long, String, BigDecimal, LocalDateTime, String, String, BigDecimal, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    @Override
    public Row12<Long, String, Long, Long, String, BigDecimal, LocalDateTime, String, String, BigDecimal, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row12) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return GoodsTrade.GOODS_TRADE.ID;
    }

    @Override
    public Field<String> field2() {
        return GoodsTrade.GOODS_TRADE.TRADE_NO;
    }

    @Override
    public Field<Long> field3() {
        return GoodsTrade.GOODS_TRADE.GOODS_ID;
    }

    @Override
    public Field<Long> field4() {
        return GoodsTrade.GOODS_TRADE.CUSTOMER_ID;
    }

    @Override
    public Field<String> field5() {
        return GoodsTrade.GOODS_TRADE.CUSTOMER;
    }

    @Override
    public Field<BigDecimal> field6() {
        return GoodsTrade.GOODS_TRADE.TRADING_PRICE;
    }

    @Override
    public Field<LocalDateTime> field7() {
        return GoodsTrade.GOODS_TRADE.TRADING_TIME;
    }

    @Override
    public Field<String> field8() {
        return GoodsTrade.GOODS_TRADE.STATE;
    }

    @Override
    public Field<String> field9() {
        return GoodsTrade.GOODS_TRADE.DELIVERY_TYPE;
    }

    @Override
    public Field<BigDecimal> field10() {
        return GoodsTrade.GOODS_TRADE.POST_PRICE;
    }

    @Override
    public Field<LocalDateTime> field11() {
        return GoodsTrade.GOODS_TRADE.CREATED;
    }

    @Override
    public Field<LocalDateTime> field12() {
        return GoodsTrade.GOODS_TRADE.MODIFIED;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getTradeNo();
    }

    @Override
    public Long component3() {
        return getGoodsId();
    }

    @Override
    public Long component4() {
        return getCustomerId();
    }

    @Override
    public String component5() {
        return getCustomer();
    }

    @Override
    public BigDecimal component6() {
        return getTradingPrice();
    }

    @Override
    public LocalDateTime component7() {
        return getTradingTime();
    }

    @Override
    public String component8() {
        return getState();
    }

    @Override
    public String component9() {
        return getDeliveryType();
    }

    @Override
    public BigDecimal component10() {
        return getPostPrice();
    }

    @Override
    public LocalDateTime component11() {
        return getCreated();
    }

    @Override
    public LocalDateTime component12() {
        return getModified();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getTradeNo();
    }

    @Override
    public Long value3() {
        return getGoodsId();
    }

    @Override
    public Long value4() {
        return getCustomerId();
    }

    @Override
    public String value5() {
        return getCustomer();
    }

    @Override
    public BigDecimal value6() {
        return getTradingPrice();
    }

    @Override
    public LocalDateTime value7() {
        return getTradingTime();
    }

    @Override
    public String value8() {
        return getState();
    }

    @Override
    public String value9() {
        return getDeliveryType();
    }

    @Override
    public BigDecimal value10() {
        return getPostPrice();
    }

    @Override
    public LocalDateTime value11() {
        return getCreated();
    }

    @Override
    public LocalDateTime value12() {
        return getModified();
    }

    @Override
    public GoodsTradeRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value2(String value) {
        setTradeNo(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value3(Long value) {
        setGoodsId(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value4(Long value) {
        setCustomerId(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value5(String value) {
        setCustomer(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value6(BigDecimal value) {
        setTradingPrice(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value7(LocalDateTime value) {
        setTradingTime(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value8(String value) {
        setState(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value9(String value) {
        setDeliveryType(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value10(BigDecimal value) {
        setPostPrice(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value11(LocalDateTime value) {
        setCreated(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value12(LocalDateTime value) {
        setModified(value);
        return this;
    }

    @Override
    public GoodsTradeRecord values(Long value1, String value2, Long value3, Long value4, String value5, BigDecimal value6, LocalDateTime value7, String value8, String value9, BigDecimal value10, LocalDateTime value11, LocalDateTime value12) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GoodsTradeRecord
     */
    public GoodsTradeRecord() {
        super(GoodsTrade.GOODS_TRADE);
    }

    /**
     * Create a detached, initialised GoodsTradeRecord
     */
    public GoodsTradeRecord(Long id, String tradeNo, Long goodsId, Long customerId, String customer, BigDecimal tradingPrice, LocalDateTime tradingTime, String state, String deliveryType, BigDecimal postPrice, LocalDateTime created, LocalDateTime modified) {
        super(GoodsTrade.GOODS_TRADE);

        set(0, id);
        set(1, tradeNo);
        set(2, goodsId);
        set(3, customerId);
        set(4, customer);
        set(5, tradingPrice);
        set(6, tradingTime);
        set(7, state);
        set(8, deliveryType);
        set(9, postPrice);
        set(10, created);
        set(11, modified);
    }
}
