/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables;


import com.etrade.puggo.db.EtradeGoods;
import com.etrade.puggo.db.Indexes;
import com.etrade.puggo.db.Keys;
import com.etrade.puggo.db.tables.records.UserImActionRecord;

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
 * 用户IM账号
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserImAction extends TableImpl<UserImActionRecord> {

    private static final long serialVersionUID = -467614731;

    /**
     * The reference instance of <code>etrade_goods.user_im_action</code>
     */
    public static final UserImAction USER_IM_ACTION = new UserImAction();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserImActionRecord> getRecordType() {
        return UserImActionRecord.class;
    }

    /**
     * The column <code>etrade_goods.user_im_action.id</code>.
     */
    public final TableField<UserImActionRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>etrade_goods.user_im_action.user_id</code>. 用户id
     */
    public final TableField<UserImActionRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "用户id");

    /**
     * The column <code>etrade_goods.user_im_action.accid</code>. 云信账号accid
     */
    public final TableField<UserImActionRecord, String> ACCID = createField(DSL.name("accid"), org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "云信账号accid");

    /**
     * The column <code>etrade_goods.user_im_action.name</code>. 云信账号name，即e-trade app的nickname
     */
    public final TableField<UserImActionRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "云信账号name，即e-trade app的nickname");

    /**
     * The column <code>etrade_goods.user_im_action.token</code>. 云信账号授权
     */
    public final TableField<UserImActionRecord, String> TOKEN = createField(DSL.name("token"), org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "云信账号授权");

    /**
     * The column <code>etrade_goods.user_im_action.created</code>.
     */
    public final TableField<UserImActionRecord, LocalDateTime> CREATED = createField(DSL.name("created"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>etrade_goods.user_im_action.modified</code>.
     */
    public final TableField<UserImActionRecord, LocalDateTime> MODIFIED = createField(DSL.name("modified"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * Create a <code>etrade_goods.user_im_action</code> table reference
     */
    public UserImAction() {
        this(DSL.name("user_im_action"), null);
    }

    /**
     * Create an aliased <code>etrade_goods.user_im_action</code> table reference
     */
    public UserImAction(String alias) {
        this(DSL.name(alias), USER_IM_ACTION);
    }

    /**
     * Create an aliased <code>etrade_goods.user_im_action</code> table reference
     */
    public UserImAction(Name alias) {
        this(alias, USER_IM_ACTION);
    }

    private UserImAction(Name alias, Table<UserImActionRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserImAction(Name alias, Table<UserImActionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("用户IM账号"));
    }

    public <O extends Record> UserImAction(Table<O> child, ForeignKey<O, UserImActionRecord> key) {
        super(child, key, USER_IM_ACTION);
    }

    @Override
    public Schema getSchema() {
        return EtradeGoods.ETRADE_GOODS;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.USER_IM_ACTION_PRIMARY);
    }

    @Override
    public Identity<UserImActionRecord, Long> getIdentity() {
        return Keys.IDENTITY_USER_IM_ACTION;
    }

    @Override
    public UniqueKey<UserImActionRecord> getPrimaryKey() {
        return Keys.KEY_USER_IM_ACTION_PRIMARY;
    }

    @Override
    public List<UniqueKey<UserImActionRecord>> getKeys() {
        return Arrays.<UniqueKey<UserImActionRecord>>asList(Keys.KEY_USER_IM_ACTION_PRIMARY);
    }

    @Override
    public UserImAction as(String alias) {
        return new UserImAction(DSL.name(alias), this);
    }

    @Override
    public UserImAction as(Name alias) {
        return new UserImAction(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserImAction rename(String name) {
        return new UserImAction(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserImAction rename(Name name) {
        return new UserImAction(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, Long, String, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
