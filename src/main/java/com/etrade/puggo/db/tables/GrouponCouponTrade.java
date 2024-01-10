/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables;


import com.etrade.puggo.db.EtradeGoods;
import com.etrade.puggo.db.Indexes;
import com.etrade.puggo.db.Keys;
import com.etrade.puggo.db.tables.records.GrouponCouponTradeRecord;
import java.time.LocalDate;
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
import org.jooq.Row15;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * 团购优惠券交易订单
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GrouponCouponTrade extends TableImpl<GrouponCouponTradeRecord> {

    private static final long serialVersionUID = 13562827;

    /**
     * The reference instance of <code>etrade_goods.groupon_coupon_trade</code>
     */
    public static final GrouponCouponTrade GROUPON_COUPON_TRADE = new GrouponCouponTrade();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GrouponCouponTradeRecord> getRecordType() {
        return GrouponCouponTradeRecord.class;
    }

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.id</code>.
     */
    public final TableField<GrouponCouponTradeRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.trade_no</code>. 订购订单号
     */
    public final TableField<GrouponCouponTradeRecord, String> TRADE_NO = createField(DSL.name("trade_no"), org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "订购订单号");

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.groupon_id</code>. 团购券id
     */
    public final TableField<GrouponCouponTradeRecord, Long> GROUPON_ID = createField(DSL.name("groupon_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "团购券id");

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.plan_ids</code>. 团购方案列表, 格式:1,4,5
     */
    public final TableField<GrouponCouponTradeRecord, String> PLAN_IDS = createField(DSL.name("plan_ids"), org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "团购方案列表, 格式:1,4,5");

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.useage_date</code>. 计划使用日期
     */
    public final TableField<GrouponCouponTradeRecord, LocalDate> USEAGE_DATE = createField(DSL.name("useage_date"), org.jooq.impl.SQLDataType.LOCALDATE, this, "计划使用日期");

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.number</code>. 数量
     */
    public final TableField<GrouponCouponTradeRecord, Integer> NUMBER = createField(DSL.name("number"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "数量");

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.contact_last_name</code>. 联系人姓氏
     */
    public final TableField<GrouponCouponTradeRecord, String> CONTACT_LAST_NAME = createField(DSL.name("contact_last_name"), org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "联系人姓氏");

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.contact_name</code>. 联系人名字
     */
    public final TableField<GrouponCouponTradeRecord, String> CONTACT_NAME = createField(DSL.name("contact_name"), org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "联系人名字");

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.contact_phone</code>. 联系人电话
     */
    public final TableField<GrouponCouponTradeRecord, String> CONTACT_PHONE = createField(DSL.name("contact_phone"), org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "联系人电话");

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.national_code</code>. 电话区号
     */
    public final TableField<GrouponCouponTradeRecord, String> NATIONAL_CODE = createField(DSL.name("national_code"), org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "电话区号");

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.contact_email</code>. 联系人邮箱
     */
    public final TableField<GrouponCouponTradeRecord, String> CONTACT_EMAIL = createField(DSL.name("contact_email"), org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "联系人邮箱");

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.state</code>. 订单状态
     */
    public final TableField<GrouponCouponTradeRecord, String> STATE = createField(DSL.name("state"), org.jooq.impl.SQLDataType.VARCHAR(25).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "订单状态");

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.customer_id</code>. 买家id，etrade_user.user.id
     */
    public final TableField<GrouponCouponTradeRecord, Long> CUSTOMER_ID = createField(DSL.name("customer_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "买家id，etrade_user.user.id");

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.created</code>.
     */
    public final TableField<GrouponCouponTradeRecord, LocalDateTime> CREATED = createField(DSL.name("created"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>etrade_goods.groupon_coupon_trade.modified</code>.
     */
    public final TableField<GrouponCouponTradeRecord, LocalDateTime> MODIFIED = createField(DSL.name("modified"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * Create a <code>etrade_goods.groupon_coupon_trade</code> table reference
     */
    public GrouponCouponTrade() {
        this(DSL.name("groupon_coupon_trade"), null);
    }

    /**
     * Create an aliased <code>etrade_goods.groupon_coupon_trade</code> table reference
     */
    public GrouponCouponTrade(String alias) {
        this(DSL.name(alias), GROUPON_COUPON_TRADE);
    }

    /**
     * Create an aliased <code>etrade_goods.groupon_coupon_trade</code> table reference
     */
    public GrouponCouponTrade(Name alias) {
        this(alias, GROUPON_COUPON_TRADE);
    }

    private GrouponCouponTrade(Name alias, Table<GrouponCouponTradeRecord> aliased) {
        this(alias, aliased, null);
    }

    private GrouponCouponTrade(Name alias, Table<GrouponCouponTradeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("团购优惠券交易订单"));
    }

    public <O extends Record> GrouponCouponTrade(Table<O> child, ForeignKey<O, GrouponCouponTradeRecord> key) {
        super(child, key, GROUPON_COUPON_TRADE);
    }

    @Override
    public Schema getSchema() {
        return EtradeGoods.ETRADE_GOODS;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.GROUPON_COUPON_TRADE_IDX_CUSTOMER_ID, Indexes.GROUPON_COUPON_TRADE_PRIMARY);
    }

    @Override
    public Identity<GrouponCouponTradeRecord, Long> getIdentity() {
        return Keys.IDENTITY_GROUPON_COUPON_TRADE;
    }

    @Override
    public UniqueKey<GrouponCouponTradeRecord> getPrimaryKey() {
        return Keys.KEY_GROUPON_COUPON_TRADE_PRIMARY;
    }

    @Override
    public List<UniqueKey<GrouponCouponTradeRecord>> getKeys() {
        return Arrays.<UniqueKey<GrouponCouponTradeRecord>>asList(Keys.KEY_GROUPON_COUPON_TRADE_PRIMARY);
    }

    @Override
    public GrouponCouponTrade as(String alias) {
        return new GrouponCouponTrade(DSL.name(alias), this);
    }

    @Override
    public GrouponCouponTrade as(Name alias) {
        return new GrouponCouponTrade(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GrouponCouponTrade rename(String name) {
        return new GrouponCouponTrade(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GrouponCouponTrade rename(Name name) {
        return new GrouponCouponTrade(name, null);
    }

    // -------------------------------------------------------------------------
    // Row15 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row15<Long, String, Long, String, LocalDate, Integer, String, String, String, String, String, String, Long, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row15) super.fieldsRow();
    }
}
