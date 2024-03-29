/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables;


import com.etrade.puggo.db.EtradeGoods;
import com.etrade.puggo.db.Indexes;
import com.etrade.puggo.db.Keys;
import com.etrade.puggo.db.tables.records.AiOverallAppraisalRecord;

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
 * AI整包鉴定结果
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AiOverallAppraisal extends TableImpl<AiOverallAppraisalRecord> {

    private static final long serialVersionUID = 1385404798;

    /**
     * The reference instance of <code>etrade_goods.ai_overall_appraisal</code>
     */
    public static final AiOverallAppraisal AI_OVERALL_APPRAISAL = new AiOverallAppraisal();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AiOverallAppraisalRecord> getRecordType() {
        return AiOverallAppraisalRecord.class;
    }

    /**
     * The column <code>etrade_goods.ai_overall_appraisal.id</code>.
     */
    public final TableField<AiOverallAppraisalRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>etrade_goods.ai_overall_appraisal.user_id</code>. 用户id
     */
    public final TableField<AiOverallAppraisalRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "用户id");

    /**
     * The column <code>etrade_goods.ai_overall_appraisal.operation_id</code>. 操作id，整包鉴定唯一
     */
    public final TableField<AiOverallAppraisalRecord, String> OPERATION_ID = createField(DSL.name("operation_id"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "操作id，整包鉴定唯一");

    /**
     * The column <code>etrade_goods.ai_overall_appraisal.kind_id</code>. 品类id
     */
    public final TableField<AiOverallAppraisalRecord, Integer> KIND_ID = createField(DSL.name("kind_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "品类id");

    /**
     * The column <code>etrade_goods.ai_overall_appraisal.brand_id</code>. 品牌id
     */
    public final TableField<AiOverallAppraisalRecord, Integer> BRAND_ID = createField(DSL.name("brand_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "品牌id");

    /**
     * The column <code>etrade_goods.ai_overall_appraisal.series_id</code>. 系列id
     */
    public final TableField<AiOverallAppraisalRecord, Integer> SERIES_ID = createField(DSL.name("series_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "系列id");

    /**
     * The column <code>etrade_goods.ai_overall_appraisal.oaid</code>. 整鉴记录id
     */
    public final TableField<AiOverallAppraisalRecord, String> OAID = createField(DSL.name("oaid"), org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "整鉴记录id");

    /**
     * The column <code>etrade_goods.ai_overall_appraisal.genuine</code>. 整体鉴定真假,1-鉴定为真 2-鉴定为假 3-无法鉴定
     */
    public final TableField<AiOverallAppraisalRecord, Byte> GENUINE = createField(DSL.name("genuine"), org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "整体鉴定真假,1-鉴定为真 2-鉴定为假 3-无法鉴定");

    /**
     * The column <code>etrade_goods.ai_overall_appraisal.grade</code>. 整体鉴定分数
     */
    public final TableField<AiOverallAppraisalRecord, BigDecimal> GRADE = createField(DSL.name("grade"), org.jooq.impl.SQLDataType.DECIMAL(10, 4).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.0000", org.jooq.impl.SQLDataType.DECIMAL)), this, "整体鉴定分数");

    /**
     * The column <code>etrade_goods.ai_overall_appraisal.description</code>. 整体鉴定报告描述
     */
    public final TableField<AiOverallAppraisalRecord, String> DESCRIPTION = createField(DSL.name("description"), org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "整体鉴定报告描述");

    /**
     * The column <code>etrade_goods.ai_overall_appraisal.report_url</code>. 鉴定报告链接
     */
    public final TableField<AiOverallAppraisalRecord, String> REPORT_URL = createField(DSL.name("report_url"), org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "鉴定报告链接");

    /**
     * The column <code>etrade_goods.ai_overall_appraisal.state</code>. 鉴定状态
     */
    public final TableField<AiOverallAppraisalRecord, String> STATE = createField(DSL.name("state"), org.jooq.impl.SQLDataType.VARCHAR(25).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "鉴定状态");

    /**
     * The column <code>etrade_goods.ai_overall_appraisal.created</code>.
     */
    public final TableField<AiOverallAppraisalRecord, LocalDateTime> CREATED = createField(DSL.name("created"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>etrade_goods.ai_overall_appraisal.modified</code>.
     */
    public final TableField<AiOverallAppraisalRecord, LocalDateTime> MODIFIED = createField(DSL.name("modified"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * Create a <code>etrade_goods.ai_overall_appraisal</code> table reference
     */
    public AiOverallAppraisal() {
        this(DSL.name("ai_overall_appraisal"), null);
    }

    /**
     * Create an aliased <code>etrade_goods.ai_overall_appraisal</code> table reference
     */
    public AiOverallAppraisal(String alias) {
        this(DSL.name(alias), AI_OVERALL_APPRAISAL);
    }

    /**
     * Create an aliased <code>etrade_goods.ai_overall_appraisal</code> table reference
     */
    public AiOverallAppraisal(Name alias) {
        this(alias, AI_OVERALL_APPRAISAL);
    }

    private AiOverallAppraisal(Name alias, Table<AiOverallAppraisalRecord> aliased) {
        this(alias, aliased, null);
    }

    private AiOverallAppraisal(Name alias, Table<AiOverallAppraisalRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("AI整包鉴定结果"));
    }

    public <O extends Record> AiOverallAppraisal(Table<O> child, ForeignKey<O, AiOverallAppraisalRecord> key) {
        super(child, key, AI_OVERALL_APPRAISAL);
    }

    @Override
    public Schema getSchema() {
        return EtradeGoods.ETRADE_GOODS;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.AI_OVERALL_APPRAISAL_IDX_USER_OPERATION_ID, Indexes.AI_OVERALL_APPRAISAL_PRIMARY);
    }

    @Override
    public Identity<AiOverallAppraisalRecord, Integer> getIdentity() {
        return Keys.IDENTITY_AI_OVERALL_APPRAISAL;
    }

    @Override
    public UniqueKey<AiOverallAppraisalRecord> getPrimaryKey() {
        return Keys.KEY_AI_OVERALL_APPRAISAL_PRIMARY;
    }

    @Override
    public List<UniqueKey<AiOverallAppraisalRecord>> getKeys() {
        return Arrays.<UniqueKey<AiOverallAppraisalRecord>>asList(Keys.KEY_AI_OVERALL_APPRAISAL_PRIMARY);
    }

    @Override
    public AiOverallAppraisal as(String alias) {
        return new AiOverallAppraisal(DSL.name(alias), this);
    }

    @Override
    public AiOverallAppraisal as(Name alias) {
        return new AiOverallAppraisal(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AiOverallAppraisal rename(String name) {
        return new AiOverallAppraisal(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AiOverallAppraisal rename(Name name) {
        return new AiOverallAppraisal(name, null);
    }

    // -------------------------------------------------------------------------
    // Row14 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row14<Integer, Long, String, Integer, Integer, Integer, String, Byte, BigDecimal, String, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row14) super.fieldsRow();
    }
}
