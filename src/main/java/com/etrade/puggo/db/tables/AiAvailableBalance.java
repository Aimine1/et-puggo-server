/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables;


import com.etrade.puggo.db.EtradeGoods;
import com.etrade.puggo.db.Indexes;
import com.etrade.puggo.db.Keys;
import com.etrade.puggo.db.tables.records.AiAvailableBalanceRecord;

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
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * AI鉴定可用次数
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AiAvailableBalance extends TableImpl<AiAvailableBalanceRecord> {

    private static final long serialVersionUID = 1196992398;

    /**
     * The reference instance of <code>etrade_goods.ai_available_balance</code>
     */
    public static final AiAvailableBalance AI_AVAILABLE_BALANCE = new AiAvailableBalance();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AiAvailableBalanceRecord> getRecordType() {
        return AiAvailableBalanceRecord.class;
    }

    /**
     * The column <code>etrade_goods.ai_available_balance.id</code>.
     */
    public final TableField<AiAvailableBalanceRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>etrade_goods.ai_available_balance.kind_id</code>. 品类id，值取自接口返回
     */
    public final TableField<AiAvailableBalanceRecord, Integer> KIND_ID = createField(DSL.name("kind_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "品类id，值取自接口返回");

    /**
     * The column <code>etrade_goods.ai_available_balance.used_times</code>. 已调用次数
     */
    public final TableField<AiAvailableBalanceRecord, Integer> USED_TIMES = createField(DSL.name("used_times"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "已调用次数");

    /**
     * The column <code>etrade_goods.ai_available_balance.available_times</code>. 可用次数
     */
    public final TableField<AiAvailableBalanceRecord, Integer> AVAILABLE_TIMES = createField(DSL.name("available_times"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "可用次数");

    /**
     * The column <code>etrade_goods.ai_available_balance.created</code>.
     */
    public final TableField<AiAvailableBalanceRecord, LocalDateTime> CREATED = createField(DSL.name("created"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>etrade_goods.ai_available_balance.modified</code>.
     */
    public final TableField<AiAvailableBalanceRecord, LocalDateTime> MODIFIED = createField(DSL.name("modified"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * Create a <code>etrade_goods.ai_available_balance</code> table reference
     */
    public AiAvailableBalance() {
        this(DSL.name("ai_available_balance"), null);
    }

    /**
     * Create an aliased <code>etrade_goods.ai_available_balance</code> table reference
     */
    public AiAvailableBalance(String alias) {
        this(DSL.name(alias), AI_AVAILABLE_BALANCE);
    }

    /**
     * Create an aliased <code>etrade_goods.ai_available_balance</code> table reference
     */
    public AiAvailableBalance(Name alias) {
        this(alias, AI_AVAILABLE_BALANCE);
    }

    private AiAvailableBalance(Name alias, Table<AiAvailableBalanceRecord> aliased) {
        this(alias, aliased, null);
    }

    private AiAvailableBalance(Name alias, Table<AiAvailableBalanceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("AI鉴定可用次数"));
    }

    public <O extends Record> AiAvailableBalance(Table<O> child, ForeignKey<O, AiAvailableBalanceRecord> key) {
        super(child, key, AI_AVAILABLE_BALANCE);
    }

    @Override
    public Schema getSchema() {
        return EtradeGoods.ETRADE_GOODS;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.AI_AVAILABLE_BALANCE_PRIMARY, Indexes.AI_AVAILABLE_BALANCE_UX_KIND_ID);
    }

    @Override
    public Identity<AiAvailableBalanceRecord, Integer> getIdentity() {
        return Keys.IDENTITY_AI_AVAILABLE_BALANCE;
    }

    @Override
    public UniqueKey<AiAvailableBalanceRecord> getPrimaryKey() {
        return Keys.KEY_AI_AVAILABLE_BALANCE_PRIMARY;
    }

    @Override
    public List<UniqueKey<AiAvailableBalanceRecord>> getKeys() {
        return Arrays.<UniqueKey<AiAvailableBalanceRecord>>asList(Keys.KEY_AI_AVAILABLE_BALANCE_PRIMARY, Keys.KEY_AI_AVAILABLE_BALANCE_UX_KIND_ID);
    }

    @Override
    public AiAvailableBalance as(String alias) {
        return new AiAvailableBalance(DSL.name(alias), this);
    }

    @Override
    public AiAvailableBalance as(Name alias) {
        return new AiAvailableBalance(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AiAvailableBalance rename(String name) {
        return new AiAvailableBalance(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AiAvailableBalance rename(Name name) {
        return new AiAvailableBalance(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, Integer, Integer, Integer, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}
