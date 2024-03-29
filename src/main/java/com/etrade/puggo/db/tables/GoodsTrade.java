/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables;


import com.etrade.puggo.db.EtradeGoods;
import com.etrade.puggo.db.Indexes;
import com.etrade.puggo.db.Keys;
import com.etrade.puggo.db.tables.records.GoodsTradeRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row18;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class GoodsTrade extends TableImpl<GoodsTradeRecord> {

    private static final long serialVersionUID = 1731350639;

    /**
     * The reference instance of <code>etrade_goods.goods_trade</code>
     */
    public static final GoodsTrade GOODS_TRADE = new GoodsTrade();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GoodsTradeRecord> getRecordType() {
        return GoodsTradeRecord.class;
    }

    /**
     * The column <code>etrade_goods.goods_trade.id</code>.
     */
    public final TableField<GoodsTradeRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>etrade_goods.goods_trade.trade_no</code>. 订单编号
     */
    public final TableField<GoodsTradeRecord, String> TRADE_NO = createField(DSL.name("trade_no"), org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "订单编号");

    /**
     * The column <code>etrade_goods.goods_trade.title</code>. 抬头
     */
    public final TableField<GoodsTradeRecord, String> TITLE = createField(DSL.name("title"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "抬头");

    /**
     * The column <code>etrade_goods.goods_trade.goods_id</code>. 商品id
     */
    public final TableField<GoodsTradeRecord, Long> GOODS_ID = createField(DSL.name("goods_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "商品id");

    /**
     * The column <code>etrade_goods.goods_trade.customer_id</code>. 买家用户id
     */
    public final TableField<GoodsTradeRecord, Long> CUSTOMER_ID = createField(DSL.name("customer_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "买家用户id");

    /**
     * The column <code>etrade_goods.goods_trade.customer</code>. 买家昵称
     */
    public final TableField<GoodsTradeRecord, String> CUSTOMER = createField(DSL.name("customer"), org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "买家昵称");

    /**
     * The column <code>etrade_goods.goods_trade.seller_id</code>. 卖家用户id
     */
    public final TableField<GoodsTradeRecord, Long> SELLER_ID = createField(DSL.name("seller_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "卖家用户id");

    /**
     * The column <code>etrade_goods.goods_trade.trading_price</code>. 商品成交金额，不包括额外的运费
     */
    public final TableField<GoodsTradeRecord, BigDecimal> TRADING_PRICE = createField(DSL.name("trading_price"), org.jooq.impl.SQLDataType.DECIMAL(19, 4).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.0000", org.jooq.impl.SQLDataType.DECIMAL)), this, "商品成交金额，不包括额外的运费");

    /**
     * The column <code>etrade_goods.goods_trade.trading_time</code>. 开单时间
     */
    public final TableField<GoodsTradeRecord, LocalDateTime> TRADING_TIME = createField(DSL.name("trading_time"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "开单时间");

    /**
     * The column <code>etrade_goods.goods_trade.state</code>. 交易状态：Pending payment、Payment failed、Completed 等等
     */
    public final TableField<GoodsTradeRecord, String> STATE = createField(DSL.name("state"), org.jooq.impl.SQLDataType.VARCHAR(25).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "交易状态：Pending payment、Payment failed、Completed 等等");

    /**
     * The column <code>etrade_goods.goods_trade.shipping_method</code>. 交易方式：1面交 2快递 4当日达 等等
     */
    public final TableField<GoodsTradeRecord, Integer> SHIPPING_METHOD = createField(DSL.name("shipping_method"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "交易方式：1面交 2快递 4当日达 等等");

    /**
     * The column <code>etrade_goods.goods_trade.shipping_fees</code>. 邮寄费用、AI检测费用等额外费用
     */
    public final TableField<GoodsTradeRecord, BigDecimal> SHIPPING_FEES = createField(DSL.name("shipping_fees"), org.jooq.impl.SQLDataType.DECIMAL(19, 4).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.0000", org.jooq.impl.SQLDataType.DECIMAL)), this, "邮寄费用、AI检测费用等额外费用");

    /**
     * The column <code>etrade_goods.goods_trade.tax</code>. 商品税，或者叫佣金，系统所得费用
     */
    public final TableField<GoodsTradeRecord, BigDecimal> TAX = createField(DSL.name("tax"), org.jooq.impl.SQLDataType.DECIMAL(19, 4).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.0000", org.jooq.impl.SQLDataType.DECIMAL)), this, "商品税，或者叫佣金，系统所得费用");

    /**
     * The column <code>etrade_goods.goods_trade.subtotal</code>. 商家所得费用，如商品费用
     */
    public final TableField<GoodsTradeRecord, BigDecimal> SUBTOTAL = createField(DSL.name("subtotal"), org.jooq.impl.SQLDataType.DECIMAL(19, 4).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.0000", org.jooq.impl.SQLDataType.DECIMAL)), this, "商家所得费用，如商品费用");

    /**
     * The column <code>etrade_goods.goods_trade.delivery_address_id</code>. 收货地址id
     */
    public final TableField<GoodsTradeRecord, Integer> DELIVERY_ADDRESS_ID = createField(DSL.name("delivery_address_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "收货地址id");

    /**
     * The column <code>etrade_goods.goods_trade.payment_invoice_id</code>. 支付id
     */
    public final TableField<GoodsTradeRecord, Long> PAYMENT_INVOICE_ID = createField(DSL.name("payment_invoice_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "支付id");

    /**
     * The column <code>etrade_goods.goods_trade.created</code>.
     */
    public final TableField<GoodsTradeRecord, LocalDateTime> CREATED = createField(DSL.name("created"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>etrade_goods.goods_trade.modified</code>.
     */
    public final TableField<GoodsTradeRecord, LocalDateTime> MODIFIED = createField(DSL.name("modified"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * Create a <code>etrade_goods.goods_trade</code> table reference
     */
    public GoodsTrade() {
        this(DSL.name("goods_trade"), null);
    }

    /**
     * Create an aliased <code>etrade_goods.goods_trade</code> table reference
     */
    public GoodsTrade(String alias) {
        this(DSL.name(alias), GOODS_TRADE);
    }

    /**
     * Create an aliased <code>etrade_goods.goods_trade</code> table reference
     */
    public GoodsTrade(Name alias) {
        this(alias, GOODS_TRADE);
    }

    private GoodsTrade(Name alias, Table<GoodsTradeRecord> aliased) {
        this(alias, aliased, null);
    }

    private GoodsTrade(Name alias, Table<GoodsTradeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("商品交易表"));
    }

    public <O extends Record> GoodsTrade(Table<O> child, ForeignKey<O, GoodsTradeRecord> key) {
        super(child, key, GOODS_TRADE);
    }

    @Override
    public Schema getSchema() {
        return EtradeGoods.ETRADE_GOODS;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.GOODS_TRADE_IDX_GOODS_TRADE_GOODS_ID, Indexes.GOODS_TRADE_PRIMARY);
    }

    @Override
    public Identity<GoodsTradeRecord, Long> getIdentity() {
        return Keys.IDENTITY_GOODS_TRADE;
    }

    @Override
    public UniqueKey<GoodsTradeRecord> getPrimaryKey() {
        return Keys.KEY_GOODS_TRADE_PRIMARY;
    }

    @Override
    public List<UniqueKey<GoodsTradeRecord>> getKeys() {
        return Arrays.<UniqueKey<GoodsTradeRecord>>asList(Keys.KEY_GOODS_TRADE_PRIMARY);
    }

    @Override
    public GoodsTrade as(String alias) {
        return new GoodsTrade(DSL.name(alias), this);
    }

    @Override
    public GoodsTrade as(Name alias) {
        return new GoodsTrade(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GoodsTrade rename(String name) {
        return new GoodsTrade(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GoodsTrade rename(Name name) {
        return new GoodsTrade(name, null);
    }

    // -------------------------------------------------------------------------
    // Row18 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row18<Long, String, String, Long, Long, String, Long, BigDecimal, LocalDateTime, String, Integer, BigDecimal, BigDecimal, BigDecimal, Integer, Long, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row18) super.fieldsRow();
    }
}
