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
import org.jooq.Record18;
import org.jooq.Row18;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 商品交易表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GoodsTradeRecord extends UpdatableRecordImpl<GoodsTradeRecord> implements Record18<Long, String, String, Long, Long, String, Long, BigDecimal, LocalDateTime, String, Integer, BigDecimal, BigDecimal, BigDecimal, Integer, Long, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1523523396;

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
     * Setter for <code>etrade_goods.goods_trade.title</code>. 抬头
     */
    public void setTitle(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.title</code>. 抬头
     */
    public String getTitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.goods_id</code>. 商品id
     */
    public void setGoodsId(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.goods_id</code>. 商品id
     */
    public Long getGoodsId() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.customer_id</code>. 买家用户id
     */
    public void setCustomerId(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.customer_id</code>. 买家用户id
     */
    public Long getCustomerId() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.customer</code>. 买家昵称
     */
    public void setCustomer(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.customer</code>. 买家昵称
     */
    public String getCustomer() {
        return (String) get(5);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.seller_id</code>. 卖家用户id
     */
    public void setSellerId(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.seller_id</code>. 卖家用户id
     */
    public Long getSellerId() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.trading_price</code>. 商品成交金额，不包括额外的运费
     */
    public void setTradingPrice(BigDecimal value) {
        set(7, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.trading_price</code>. 商品成交金额，不包括额外的运费
     */
    public BigDecimal getTradingPrice() {
        return (BigDecimal) get(7);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.trading_time</code>. 开单时间
     */
    public void setTradingTime(LocalDateTime value) {
        set(8, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.trading_time</code>. 开单时间
     */
    public LocalDateTime getTradingTime() {
        return (LocalDateTime) get(8);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.state</code>. 交易状态：Pending payment、Payment failed、Completed 等等
     */
    public void setState(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.state</code>. 交易状态：Pending payment、Payment failed、Completed 等等
     */
    public String getState() {
        return (String) get(9);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.shipping_method</code>. 交易方式：1面交 2快递 4当日达 等等
     */
    public void setShippingMethod(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.shipping_method</code>. 交易方式：1面交 2快递 4当日达 等等
     */
    public Integer getShippingMethod() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.shipping_fees</code>. 邮寄费用、AI检测费用等额外费用
     */
    public void setShippingFees(BigDecimal value) {
        set(11, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.shipping_fees</code>. 邮寄费用、AI检测费用等额外费用
     */
    public BigDecimal getShippingFees() {
        return (BigDecimal) get(11);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.tax</code>. 商品税，或者叫佣金，系统所得费用
     */
    public void setTax(BigDecimal value) {
        set(12, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.tax</code>. 商品税，或者叫佣金，系统所得费用
     */
    public BigDecimal getTax() {
        return (BigDecimal) get(12);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.subtotal</code>. 商家所得费用，如商品费用
     */
    public void setSubtotal(BigDecimal value) {
        set(13, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.subtotal</code>. 商家所得费用，如商品费用
     */
    public BigDecimal getSubtotal() {
        return (BigDecimal) get(13);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.delivery_address_id</code>. 收货地址id
     */
    public void setDeliveryAddressId(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.delivery_address_id</code>. 收货地址id
     */
    public Integer getDeliveryAddressId() {
        return (Integer) get(14);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.payment_invoice_id</code>. 支付id
     */
    public void setPaymentInvoiceId(Long value) {
        set(15, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.payment_invoice_id</code>. 支付id
     */
    public Long getPaymentInvoiceId() {
        return (Long) get(15);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.created</code>.
     */
    public void setCreated(LocalDateTime value) {
        set(16, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.created</code>.
     */
    public LocalDateTime getCreated() {
        return (LocalDateTime) get(16);
    }

    /**
     * Setter for <code>etrade_goods.goods_trade.modified</code>.
     */
    public void setModified(LocalDateTime value) {
        set(17, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_trade.modified</code>.
     */
    public LocalDateTime getModified() {
        return (LocalDateTime) get(17);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record18 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row18<Long, String, String, Long, Long, String, Long, BigDecimal, LocalDateTime, String, Integer, BigDecimal, BigDecimal, BigDecimal, Integer, Long, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row18) super.fieldsRow();
    }

    @Override
    public Row18<Long, String, String, Long, Long, String, Long, BigDecimal, LocalDateTime, String, Integer, BigDecimal, BigDecimal, BigDecimal, Integer, Long, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row18) super.valuesRow();
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
    public Field<String> field3() {
        return GoodsTrade.GOODS_TRADE.TITLE;
    }

    @Override
    public Field<Long> field4() {
        return GoodsTrade.GOODS_TRADE.GOODS_ID;
    }

    @Override
    public Field<Long> field5() {
        return GoodsTrade.GOODS_TRADE.CUSTOMER_ID;
    }

    @Override
    public Field<String> field6() {
        return GoodsTrade.GOODS_TRADE.CUSTOMER;
    }

    @Override
    public Field<Long> field7() {
        return GoodsTrade.GOODS_TRADE.SELLER_ID;
    }

    @Override
    public Field<BigDecimal> field8() {
        return GoodsTrade.GOODS_TRADE.TRADING_PRICE;
    }

    @Override
    public Field<LocalDateTime> field9() {
        return GoodsTrade.GOODS_TRADE.TRADING_TIME;
    }

    @Override
    public Field<String> field10() {
        return GoodsTrade.GOODS_TRADE.STATE;
    }

    @Override
    public Field<Integer> field11() {
        return GoodsTrade.GOODS_TRADE.SHIPPING_METHOD;
    }

    @Override
    public Field<BigDecimal> field12() {
        return GoodsTrade.GOODS_TRADE.SHIPPING_FEES;
    }

    @Override
    public Field<BigDecimal> field13() {
        return GoodsTrade.GOODS_TRADE.TAX;
    }

    @Override
    public Field<BigDecimal> field14() {
        return GoodsTrade.GOODS_TRADE.SUBTOTAL;
    }

    @Override
    public Field<Integer> field15() {
        return GoodsTrade.GOODS_TRADE.DELIVERY_ADDRESS_ID;
    }

    @Override
    public Field<Long> field16() {
        return GoodsTrade.GOODS_TRADE.PAYMENT_INVOICE_ID;
    }

    @Override
    public Field<LocalDateTime> field17() {
        return GoodsTrade.GOODS_TRADE.CREATED;
    }

    @Override
    public Field<LocalDateTime> field18() {
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
    public String component3() {
        return getTitle();
    }

    @Override
    public Long component4() {
        return getGoodsId();
    }

    @Override
    public Long component5() {
        return getCustomerId();
    }

    @Override
    public String component6() {
        return getCustomer();
    }

    @Override
    public Long component7() {
        return getSellerId();
    }

    @Override
    public BigDecimal component8() {
        return getTradingPrice();
    }

    @Override
    public LocalDateTime component9() {
        return getTradingTime();
    }

    @Override
    public String component10() {
        return getState();
    }

    @Override
    public Integer component11() {
        return getShippingMethod();
    }

    @Override
    public BigDecimal component12() {
        return getShippingFees();
    }

    @Override
    public BigDecimal component13() {
        return getTax();
    }

    @Override
    public BigDecimal component14() {
        return getSubtotal();
    }

    @Override
    public Integer component15() {
        return getDeliveryAddressId();
    }

    @Override
    public Long component16() {
        return getPaymentInvoiceId();
    }

    @Override
    public LocalDateTime component17() {
        return getCreated();
    }

    @Override
    public LocalDateTime component18() {
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
    public String value3() {
        return getTitle();
    }

    @Override
    public Long value4() {
        return getGoodsId();
    }

    @Override
    public Long value5() {
        return getCustomerId();
    }

    @Override
    public String value6() {
        return getCustomer();
    }

    @Override
    public Long value7() {
        return getSellerId();
    }

    @Override
    public BigDecimal value8() {
        return getTradingPrice();
    }

    @Override
    public LocalDateTime value9() {
        return getTradingTime();
    }

    @Override
    public String value10() {
        return getState();
    }

    @Override
    public Integer value11() {
        return getShippingMethod();
    }

    @Override
    public BigDecimal value12() {
        return getShippingFees();
    }

    @Override
    public BigDecimal value13() {
        return getTax();
    }

    @Override
    public BigDecimal value14() {
        return getSubtotal();
    }

    @Override
    public Integer value15() {
        return getDeliveryAddressId();
    }

    @Override
    public Long value16() {
        return getPaymentInvoiceId();
    }

    @Override
    public LocalDateTime value17() {
        return getCreated();
    }

    @Override
    public LocalDateTime value18() {
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
    public GoodsTradeRecord value3(String value) {
        setTitle(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value4(Long value) {
        setGoodsId(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value5(Long value) {
        setCustomerId(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value6(String value) {
        setCustomer(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value7(Long value) {
        setSellerId(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value8(BigDecimal value) {
        setTradingPrice(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value9(LocalDateTime value) {
        setTradingTime(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value10(String value) {
        setState(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value11(Integer value) {
        setShippingMethod(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value12(BigDecimal value) {
        setShippingFees(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value13(BigDecimal value) {
        setTax(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value14(BigDecimal value) {
        setSubtotal(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value15(Integer value) {
        setDeliveryAddressId(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value16(Long value) {
        setPaymentInvoiceId(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value17(LocalDateTime value) {
        setCreated(value);
        return this;
    }

    @Override
    public GoodsTradeRecord value18(LocalDateTime value) {
        setModified(value);
        return this;
    }

    @Override
    public GoodsTradeRecord values(Long value1, String value2, String value3, Long value4, Long value5, String value6, Long value7, BigDecimal value8, LocalDateTime value9, String value10, Integer value11, BigDecimal value12, BigDecimal value13, BigDecimal value14, Integer value15, Long value16, LocalDateTime value17, LocalDateTime value18) {
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
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
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
    public GoodsTradeRecord(Long id, String tradeNo, String title, Long goodsId, Long customerId, String customer, Long sellerId, BigDecimal tradingPrice, LocalDateTime tradingTime, String state, Integer shippingMethod, BigDecimal shippingFees, BigDecimal tax, BigDecimal subtotal, Integer deliveryAddressId, Long paymentInvoiceId, LocalDateTime created, LocalDateTime modified) {
        super(GoodsTrade.GOODS_TRADE);

        set(0, id);
        set(1, tradeNo);
        set(2, title);
        set(3, goodsId);
        set(4, customerId);
        set(5, customer);
        set(6, sellerId);
        set(7, tradingPrice);
        set(8, tradingTime);
        set(9, state);
        set(10, shippingMethod);
        set(11, shippingFees);
        set(12, tax);
        set(13, subtotal);
        set(14, deliveryAddressId);
        set(15, paymentInvoiceId);
        set(16, created);
        set(17, modified);
    }
}
