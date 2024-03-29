/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables;


import com.etrade.puggo.db.EtradeGoods;
import com.etrade.puggo.db.Indexes;
import com.etrade.puggo.db.Keys;
import com.etrade.puggo.db.tables.records.UserBrowsingHistoryRecord;

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
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * 用户浏览历史
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserBrowsingHistory extends TableImpl<UserBrowsingHistoryRecord> {

    private static final long serialVersionUID = 1725544844;

    /**
     * The reference instance of <code>etrade_goods.user_browsing_history</code>
     */
    public static final UserBrowsingHistory USER_BROWSING_HISTORY = new UserBrowsingHistory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserBrowsingHistoryRecord> getRecordType() {
        return UserBrowsingHistoryRecord.class;
    }

    /**
     * The column <code>etrade_goods.user_browsing_history.id</code>.
     */
    public final TableField<UserBrowsingHistoryRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>etrade_goods.user_browsing_history.user_id</code>. 用户id
     */
    public final TableField<UserBrowsingHistoryRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "用户id");

    /**
     * The column <code>etrade_goods.user_browsing_history.goods_id</code>. 浏览商品id
     */
    public final TableField<UserBrowsingHistoryRecord, Long> GOODS_ID = createField(DSL.name("goods_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "浏览商品id");

    /**
     * The column <code>etrade_goods.user_browsing_history.groupon_id</code>. 浏览团购券id
     */
    public final TableField<UserBrowsingHistoryRecord, Long> GROUPON_ID = createField(DSL.name("groupon_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "浏览团购券id");

    /**
     * The column <code>etrade_goods.user_browsing_history.last_browse_time</code>. 最近浏览时间
     */
    public final TableField<UserBrowsingHistoryRecord, LocalDateTime> LAST_BROWSE_TIME = createField(DSL.name("last_browse_time"), org.jooq.impl.SQLDataType.LOCALDATETIME, this, "最近浏览时间");

    /**
     * The column <code>etrade_goods.user_browsing_history.created</code>.
     */
    public final TableField<UserBrowsingHistoryRecord, LocalDateTime> CREATED = createField(DSL.name("created"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>etrade_goods.user_browsing_history.modified</code>.
     */
    public final TableField<UserBrowsingHistoryRecord, LocalDateTime> MODIFIED = createField(DSL.name("modified"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * Create a <code>etrade_goods.user_browsing_history</code> table reference
     */
    public UserBrowsingHistory() {
        this(DSL.name("user_browsing_history"), null);
    }

    /**
     * Create an aliased <code>etrade_goods.user_browsing_history</code> table reference
     */
    public UserBrowsingHistory(String alias) {
        this(DSL.name(alias), USER_BROWSING_HISTORY);
    }

    /**
     * Create an aliased <code>etrade_goods.user_browsing_history</code> table reference
     */
    public UserBrowsingHistory(Name alias) {
        this(alias, USER_BROWSING_HISTORY);
    }

    private UserBrowsingHistory(Name alias, Table<UserBrowsingHistoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserBrowsingHistory(Name alias, Table<UserBrowsingHistoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("用户浏览历史"));
    }

    public <O extends Record> UserBrowsingHistory(Table<O> child, ForeignKey<O, UserBrowsingHistoryRecord> key) {
        super(child, key, USER_BROWSING_HISTORY);
    }

    @Override
    public Schema getSchema() {
        return EtradeGoods.ETRADE_GOODS;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.USER_BROWSING_HISTORY_IDX_USER_ID_GROUPON_ID, Indexes.USER_BROWSING_HISTORY_PRIMARY);
    }

    @Override
    public Identity<UserBrowsingHistoryRecord, Long> getIdentity() {
        return Keys.IDENTITY_USER_BROWSING_HISTORY;
    }

    @Override
    public UniqueKey<UserBrowsingHistoryRecord> getPrimaryKey() {
        return Keys.KEY_USER_BROWSING_HISTORY_PRIMARY;
    }

    @Override
    public List<UniqueKey<UserBrowsingHistoryRecord>> getKeys() {
        return Arrays.<UniqueKey<UserBrowsingHistoryRecord>>asList(Keys.KEY_USER_BROWSING_HISTORY_PRIMARY);
    }

    @Override
    public UserBrowsingHistory as(String alias) {
        return new UserBrowsingHistory(DSL.name(alias), this);
    }

    @Override
    public UserBrowsingHistory as(Name alias) {
        return new UserBrowsingHistory(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserBrowsingHistory rename(String name) {
        return new UserBrowsingHistory(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserBrowsingHistory rename(Name name) {
        return new UserBrowsingHistory(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, Long, Long, Long, LocalDateTime, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
