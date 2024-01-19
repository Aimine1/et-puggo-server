/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables;


import com.etrade.puggo.db.EtradeGoods;
import com.etrade.puggo.db.tables.records.AiKindListRecord;

import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row8;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * AI鉴定品类
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AiKindList extends TableImpl<AiKindListRecord> {

    private static final long serialVersionUID = 594776125;

    /**
     * The reference instance of <code>etrade_goods.ai_kind_list</code>
     */
    public static final AiKindList AI_KIND_LIST = new AiKindList();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AiKindListRecord> getRecordType() {
        return AiKindListRecord.class;
    }

    /**
     * The column <code>etrade_goods.ai_kind_list.id</code>. 品类id，值取自接口返回
     */
    public final TableField<AiKindListRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "品类id，值取自接口返回");

    /**
     * The column <code>etrade_goods.ai_kind_list.name</code>. 品类中文名称
     */
    public final TableField<AiKindListRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "品类中文名称");

    /**
     * The column <code>etrade_goods.ai_kind_list.is_related</code>. 是否关联鉴定点（若为true，则该品类直接跳转至鉴定点信息）
     */
    public final TableField<AiKindListRecord, Byte> IS_RELATED = createField(DSL.name("is_related"), org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否关联鉴定点（若为true，则该品类直接跳转至鉴定点信息）");

    /**
     * The column <code>etrade_goods.ai_kind_list.example_img</code>. 品类示意图
     */
    public final TableField<AiKindListRecord, String> EXAMPLE_IMG = createField(DSL.name("example_img"), org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "品类示意图");

    /**
     * The column <code>etrade_goods.ai_kind_list.is_show</code>. 是否展示
     */
    public final TableField<AiKindListRecord, Byte> IS_SHOW = createField(DSL.name("is_show"), org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否展示");

    /**
     * The column <code>etrade_goods.ai_kind_list.lang</code>. 语言
     */
    public final TableField<AiKindListRecord, String> LANG = createField(DSL.name("lang"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "语言");

    /**
     * The column <code>etrade_goods.ai_kind_list.created</code>.
     */
    public final TableField<AiKindListRecord, LocalDateTime> CREATED = createField(DSL.name("created"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>etrade_goods.ai_kind_list.modified</code>.
     */
    public final TableField<AiKindListRecord, LocalDateTime> MODIFIED = createField(DSL.name("modified"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * Create a <code>etrade_goods.ai_kind_list</code> table reference
     */
    public AiKindList() {
        this(DSL.name("ai_kind_list"), null);
    }

    /**
     * Create an aliased <code>etrade_goods.ai_kind_list</code> table reference
     */
    public AiKindList(String alias) {
        this(DSL.name(alias), AI_KIND_LIST);
    }

    /**
     * Create an aliased <code>etrade_goods.ai_kind_list</code> table reference
     */
    public AiKindList(Name alias) {
        this(alias, AI_KIND_LIST);
    }

    private AiKindList(Name alias, Table<AiKindListRecord> aliased) {
        this(alias, aliased, null);
    }

    private AiKindList(Name alias, Table<AiKindListRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("AI鉴定品类"));
    }

    public <O extends Record> AiKindList(Table<O> child, ForeignKey<O, AiKindListRecord> key) {
        super(child, key, AI_KIND_LIST);
    }

    @Override
    public Schema getSchema() {
        return EtradeGoods.ETRADE_GOODS;
    }

    @Override
    public AiKindList as(String alias) {
        return new AiKindList(DSL.name(alias), this);
    }

    @Override
    public AiKindList as(Name alias) {
        return new AiKindList(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AiKindList rename(String name) {
        return new AiKindList(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AiKindList rename(Name name) {
        return new AiKindList(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<Integer, String, Byte, String, Byte, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}
