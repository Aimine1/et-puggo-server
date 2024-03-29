/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables;


import com.etrade.puggo.db.EtradeGoods;
import com.etrade.puggo.db.Indexes;
import com.etrade.puggo.db.Keys;
import com.etrade.puggo.db.tables.records.GrouponCouponDataRecord;

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
import org.jooq.Row8;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * 团购券数据
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GrouponCouponData extends TableImpl<GrouponCouponDataRecord> {

    private static final long serialVersionUID = -1572177261;

    /**
     * The reference instance of <code>etrade_goods.groupon_coupon_data</code>
     */
    public static final GrouponCouponData GROUPON_COUPON_DATA = new GrouponCouponData();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GrouponCouponDataRecord> getRecordType() {
        return GrouponCouponDataRecord.class;
    }

    /**
     * The column <code>etrade_goods.groupon_coupon_data.id</code>.
     */
    public final TableField<GrouponCouponDataRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>etrade_goods.groupon_coupon_data.groupon_id</code>. 团购券id
     */
    public final TableField<GrouponCouponDataRecord, Long> GROUPON_ID = createField(DSL.name("groupon_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "团购券id");

    /**
     * The column <code>etrade_goods.groupon_coupon_data.browse_number</code>. 浏览次数
     */
    public final TableField<GrouponCouponDataRecord, Integer> BROWSE_NUMBER = createField(DSL.name("browse_number"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "浏览次数");

    /**
     * The column <code>etrade_goods.groupon_coupon_data.like_number</code>. 收藏/喜欢次数
     */
    public final TableField<GrouponCouponDataRecord, Integer> LIKE_NUMBER = createField(DSL.name("like_number"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "收藏/喜欢次数");

    /**
     * The column <code>etrade_goods.groupon_coupon_data.comment_number</code>. 评论次数
     */
    public final TableField<GrouponCouponDataRecord, Integer> COMMENT_NUMBER = createField(DSL.name("comment_number"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "评论次数");

    /**
     * The column <code>etrade_goods.groupon_coupon_data.trade_number</code>. 订购次数
     */
    public final TableField<GrouponCouponDataRecord, Integer> TRADE_NUMBER = createField(DSL.name("trade_number"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "订购次数");

    /**
     * The column <code>etrade_goods.groupon_coupon_data.created</code>.
     */
    public final TableField<GrouponCouponDataRecord, LocalDateTime> CREATED = createField(DSL.name("created"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>etrade_goods.groupon_coupon_data.modified</code>.
     */
    public final TableField<GrouponCouponDataRecord, LocalDateTime> MODIFIED = createField(DSL.name("modified"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * Create a <code>etrade_goods.groupon_coupon_data</code> table reference
     */
    public GrouponCouponData() {
        this(DSL.name("groupon_coupon_data"), null);
    }

    /**
     * Create an aliased <code>etrade_goods.groupon_coupon_data</code> table reference
     */
    public GrouponCouponData(String alias) {
        this(DSL.name(alias), GROUPON_COUPON_DATA);
    }

    /**
     * Create an aliased <code>etrade_goods.groupon_coupon_data</code> table reference
     */
    public GrouponCouponData(Name alias) {
        this(alias, GROUPON_COUPON_DATA);
    }

    private GrouponCouponData(Name alias, Table<GrouponCouponDataRecord> aliased) {
        this(alias, aliased, null);
    }

    private GrouponCouponData(Name alias, Table<GrouponCouponDataRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("团购券数据"));
    }

    public <O extends Record> GrouponCouponData(Table<O> child, ForeignKey<O, GrouponCouponDataRecord> key) {
        super(child, key, GROUPON_COUPON_DATA);
    }

    @Override
    public Schema getSchema() {
        return EtradeGoods.ETRADE_GOODS;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.GROUPON_COUPON_DATA_IDX_GROUPON_ID, Indexes.GROUPON_COUPON_DATA_PRIMARY);
    }

    @Override
    public Identity<GrouponCouponDataRecord, Long> getIdentity() {
        return Keys.IDENTITY_GROUPON_COUPON_DATA;
    }

    @Override
    public UniqueKey<GrouponCouponDataRecord> getPrimaryKey() {
        return Keys.KEY_GROUPON_COUPON_DATA_PRIMARY;
    }

    @Override
    public List<UniqueKey<GrouponCouponDataRecord>> getKeys() {
        return Arrays.<UniqueKey<GrouponCouponDataRecord>>asList(Keys.KEY_GROUPON_COUPON_DATA_PRIMARY);
    }

    @Override
    public GrouponCouponData as(String alias) {
        return new GrouponCouponData(DSL.name(alias), this);
    }

    @Override
    public GrouponCouponData as(Name alias) {
        return new GrouponCouponData(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GrouponCouponData rename(String name) {
        return new GrouponCouponData(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GrouponCouponData rename(Name name) {
        return new GrouponCouponData(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<Long, Long, Integer, Integer, Integer, Integer, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}
