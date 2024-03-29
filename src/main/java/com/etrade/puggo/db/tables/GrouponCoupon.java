/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables;


import com.etrade.puggo.db.EtradeGoods;
import com.etrade.puggo.db.Indexes;
import com.etrade.puggo.db.Keys;
import com.etrade.puggo.db.tables.records.GrouponCouponRecord;

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
import org.jooq.Row14;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * 团购优惠券
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GrouponCoupon extends TableImpl<GrouponCouponRecord> {

    private static final long serialVersionUID = 1001171761;

    /**
     * The reference instance of <code>etrade_goods.groupon_coupon</code>
     */
    public static final GrouponCoupon GROUPON_COUPON = new GrouponCoupon();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GrouponCouponRecord> getRecordType() {
        return GrouponCouponRecord.class;
    }

    /**
     * The column <code>etrade_goods.groupon_coupon.id</code>.
     */
    public final TableField<GrouponCouponRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>etrade_goods.groupon_coupon.title</code>. 团购券title
     */
    public final TableField<GrouponCouponRecord, String> TITLE = createField(DSL.name("title"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "团购券title");

    /**
     * The column <code>etrade_goods.groupon_coupon.original_price</code>. 原价
     */
    public final TableField<GrouponCouponRecord, BigDecimal> ORIGINAL_PRICE = createField(DSL.name("original_price"), org.jooq.impl.SQLDataType.DECIMAL(19, 4).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.0000", org.jooq.impl.SQLDataType.DECIMAL)), this, "原价");

    /**
     * The column <code>etrade_goods.groupon_coupon.real_price</code>. 现价
     */
    public final TableField<GrouponCouponRecord, BigDecimal> REAL_PRICE = createField(DSL.name("real_price"), org.jooq.impl.SQLDataType.DECIMAL(19, 4).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.0000", org.jooq.impl.SQLDataType.DECIMAL)), this, "现价");

    /**
     * The column <code>etrade_goods.groupon_coupon.real_price_interval</code>. 现价的区间，EQ=; &gt;=GE; &lt;=LE
     */
    public final TableField<GrouponCouponRecord, String> REAL_PRICE_INTERVAL = createField(DSL.name("real_price_interval"), org.jooq.impl.SQLDataType.VARCHAR(10).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "现价的区间，EQ=; >=GE; <=LE");

    /**
     * The column <code>etrade_goods.groupon_coupon.money_kind</code>. 交易货币：USD美元 CNY人民币 CAD加元等等
     */
    public final TableField<GrouponCouponRecord, String> MONEY_KIND = createField(DSL.name("money_kind"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "交易货币：USD美元 CNY人民币 CAD加元等等");

    /**
     * The column <code>etrade_goods.groupon_coupon.launch_user_id</code>. 发布用户id
     */
    public final TableField<GrouponCouponRecord, Long> LAUNCH_USER_ID = createField(DSL.name("launch_user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "发布用户id");

    /**
     * The column <code>etrade_goods.groupon_coupon.launch_user_nickname</code>. 发布用户昵称
     */
    public final TableField<GrouponCouponRecord, String> LAUNCH_USER_NICKNAME = createField(DSL.name("launch_user_nickname"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "发布用户昵称");

    /**
     * The column <code>etrade_goods.groupon_coupon.class_path</code>. 分类级别
     */
    public final TableField<GrouponCouponRecord, String> CLASS_PATH = createField(DSL.name("class_path"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "分类级别");

    /**
     * The column <code>etrade_goods.groupon_coupon.brand</code>. 品牌
     */
    public final TableField<GrouponCouponRecord, String> BRAND = createField(DSL.name("brand"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "品牌");

    /**
     * The column <code>etrade_goods.groupon_coupon.state</code>. 商品状态：1草稿箱 2已删除 3已下架 4在售 5交易中 6交易完成 等等
     */
    public final TableField<GrouponCouponRecord, String> STATE = createField(DSL.name("state"), org.jooq.impl.SQLDataType.VARCHAR(25).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "商品状态：1草稿箱 2已删除 3已下架 4在售 5交易中 6交易完成 等等");

    /**
     * The column <code>etrade_goods.groupon_coupon.launch_last_time</code>. 商品最新发布时间
     */
    public final TableField<GrouponCouponRecord, LocalDateTime> LAUNCH_LAST_TIME = createField(DSL.name("launch_last_time"), org.jooq.impl.SQLDataType.LOCALDATETIME, this, "商品最新发布时间");

    /**
     * The column <code>etrade_goods.groupon_coupon.created</code>.
     */
    public final TableField<GrouponCouponRecord, LocalDateTime> CREATED = createField(DSL.name("created"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>etrade_goods.groupon_coupon.modified</code>.
     */
    public final TableField<GrouponCouponRecord, LocalDateTime> MODIFIED = createField(DSL.name("modified"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * Create a <code>etrade_goods.groupon_coupon</code> table reference
     */
    public GrouponCoupon() {
        this(DSL.name("groupon_coupon"), null);
    }

    /**
     * Create an aliased <code>etrade_goods.groupon_coupon</code> table reference
     */
    public GrouponCoupon(String alias) {
        this(DSL.name(alias), GROUPON_COUPON);
    }

    /**
     * Create an aliased <code>etrade_goods.groupon_coupon</code> table reference
     */
    public GrouponCoupon(Name alias) {
        this(alias, GROUPON_COUPON);
    }

    private GrouponCoupon(Name alias, Table<GrouponCouponRecord> aliased) {
        this(alias, aliased, null);
    }

    private GrouponCoupon(Name alias, Table<GrouponCouponRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("团购优惠券"));
    }

    public <O extends Record> GrouponCoupon(Table<O> child, ForeignKey<O, GrouponCouponRecord> key) {
        super(child, key, GROUPON_COUPON);
    }

    @Override
    public Schema getSchema() {
        return EtradeGoods.ETRADE_GOODS;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.GROUPON_COUPON_IDX_CLASS_PATH, Indexes.GROUPON_COUPON_IDX_GROUPON_COUPON_TITLE, Indexes.GROUPON_COUPON_PRIMARY);
    }

    @Override
    public Identity<GrouponCouponRecord, Long> getIdentity() {
        return Keys.IDENTITY_GROUPON_COUPON;
    }

    @Override
    public UniqueKey<GrouponCouponRecord> getPrimaryKey() {
        return Keys.KEY_GROUPON_COUPON_PRIMARY;
    }

    @Override
    public List<UniqueKey<GrouponCouponRecord>> getKeys() {
        return Arrays.<UniqueKey<GrouponCouponRecord>>asList(Keys.KEY_GROUPON_COUPON_PRIMARY);
    }

    @Override
    public GrouponCoupon as(String alias) {
        return new GrouponCoupon(DSL.name(alias), this);
    }

    @Override
    public GrouponCoupon as(Name alias) {
        return new GrouponCoupon(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GrouponCoupon rename(String name) {
        return new GrouponCoupon(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GrouponCoupon rename(Name name) {
        return new GrouponCoupon(name, null);
    }

    // -------------------------------------------------------------------------
    // Row14 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row14<Long, String, BigDecimal, BigDecimal, String, String, Long, String, String, String, String, LocalDateTime, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row14) super.fieldsRow();
    }
}
