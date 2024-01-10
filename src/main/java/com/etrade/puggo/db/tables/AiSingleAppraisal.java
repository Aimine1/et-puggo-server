/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables;


import com.etrade.puggo.db.EtradeGoods;
import com.etrade.puggo.db.Indexes;
import com.etrade.puggo.db.Keys;
import com.etrade.puggo.db.tables.records.AiSingleAppraisalRecord;
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
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * AI单点鉴定结果
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AiSingleAppraisal extends TableImpl<AiSingleAppraisalRecord> {

    private static final long serialVersionUID = 1653338561;

    /**
     * The reference instance of <code>etrade_goods.ai_single_appraisal</code>
     */
    public static final AiSingleAppraisal AI_SINGLE_APPRAISAL = new AiSingleAppraisal();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AiSingleAppraisalRecord> getRecordType() {
        return AiSingleAppraisalRecord.class;
    }

    /**
     * The column <code>etrade_goods.ai_single_appraisal.id</code>.
     */
    public final TableField<AiSingleAppraisalRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.user_id</code>. 用户id
     */
    public final TableField<AiSingleAppraisalRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "用户id");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.operation_id</code>. 操作id，整包鉴定唯一
     */
    public final TableField<AiSingleAppraisalRecord, String> OPERATION_ID = createField(DSL.name("operation_id"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "操作id，整包鉴定唯一");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.kind_id</code>. 品类id
     */
    public final TableField<AiSingleAppraisalRecord, Integer> KIND_ID = createField(DSL.name("kind_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "品类id");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.brand_id</code>. 品牌id
     */
    public final TableField<AiSingleAppraisalRecord, Integer> BRAND_ID = createField(DSL.name("brand_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "品牌id");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.series_id</code>. 系列id
     */
    public final TableField<AiSingleAppraisalRecord, Integer> SERIES_ID = createField(DSL.name("series_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "系列id");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.point_id</code>. 鉴定点id
     */
    public final TableField<AiSingleAppraisalRecord, Integer> POINT_ID = createField(DSL.name("point_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "鉴定点id");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.point_name</code>. 鉴定点名称
     */
    public final TableField<AiSingleAppraisalRecord, String> POINT_NAME = createField(DSL.name("point_name"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "鉴定点名称");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.detection</code>. 是否检测到目标
     */
    public final TableField<AiSingleAppraisalRecord, Byte> DETECTION = createField(DSL.name("detection"), org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否检测到目标");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.said</code>. 鉴定记录id
     */
    public final TableField<AiSingleAppraisalRecord, String> SAID = createField(DSL.name("said"), org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "鉴定记录id");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.genuine</code>. 单鉴定点真假
     */
    public final TableField<AiSingleAppraisalRecord, Byte> GENUINE = createField(DSL.name("genuine"), org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "单鉴定点真假");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.grade</code>. 单鉴定点分数
     */
    public final TableField<AiSingleAppraisalRecord, BigDecimal> GRADE = createField(DSL.name("grade"), org.jooq.impl.SQLDataType.DECIMAL(10, 4).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.0000", org.jooq.impl.SQLDataType.DECIMAL)), this, "单鉴定点分数");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.genuine_standard</code>. 单鉴定点真假标准，标准取值范围(0,1)
     */
    public final TableField<AiSingleAppraisalRecord, BigDecimal> GENUINE_STANDARD = createField(DSL.name("genuine_standard"), org.jooq.impl.SQLDataType.DECIMAL(1, 1).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.0", org.jooq.impl.SQLDataType.DECIMAL)), this, "单鉴定点真假标准，标准取值范围(0,1)");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.refuse</code>. 是否拒鉴
     */
    public final TableField<AiSingleAppraisalRecord, Byte> REFUSE = createField(DSL.name("refuse"), org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否拒鉴");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.original_box</code>. 图片原有尺寸(width*height)
     */
    public final TableField<AiSingleAppraisalRecord, String> ORIGINAL_BOX = createField(DSL.name("original_box"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "图片原有尺寸(width*height)");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.detection_box</code>. 检测出鉴定点的方框左上角(x,y,w,h)
     */
    public final TableField<AiSingleAppraisalRecord, String> DETECTION_BOX = createField(DSL.name("detection_box"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "检测出鉴定点的方框左上角(x,y,w,h)");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.show_fake</code>. 是否需要展示偏假点的红圈圈
     */
    public final TableField<AiSingleAppraisalRecord, Byte> SHOW_FAKE = createField(DSL.name("show_fake"), org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否需要展示偏假点的红圈圈");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.fake_points</code>. 偏假信息点展示,拆分成8*8的矩阵后，返回top3的鉴定点
     */
    public final TableField<AiSingleAppraisalRecord, String> FAKE_POINTS = createField(DSL.name("fake_points"), org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "偏假信息点展示,拆分成8*8的矩阵后，返回top3的鉴定点");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.image_url</code>. 鉴定的图片url
     */
    public final TableField<AiSingleAppraisalRecord, String> IMAGE_URL = createField(DSL.name("image_url"), org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "鉴定的图片url");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.crop_image_url</code>. 鉴定的crop后的图片url
     */
    public final TableField<AiSingleAppraisalRecord, String> CROP_IMAGE_URL = createField(DSL.name("crop_image_url"), org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "鉴定的crop后的图片url");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.description</code>. 鉴定结果描述
     */
    public final TableField<AiSingleAppraisalRecord, String> DESCRIPTION = createField(DSL.name("description"), org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "鉴定结果描述");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.created</code>.
     */
    public final TableField<AiSingleAppraisalRecord, LocalDateTime> CREATED = createField(DSL.name("created"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>etrade_goods.ai_single_appraisal.modified</code>.
     */
    public final TableField<AiSingleAppraisalRecord, LocalDateTime> MODIFIED = createField(DSL.name("modified"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * Create a <code>etrade_goods.ai_single_appraisal</code> table reference
     */
    public AiSingleAppraisal() {
        this(DSL.name("ai_single_appraisal"), null);
    }

    /**
     * Create an aliased <code>etrade_goods.ai_single_appraisal</code> table reference
     */
    public AiSingleAppraisal(String alias) {
        this(DSL.name(alias), AI_SINGLE_APPRAISAL);
    }

    /**
     * Create an aliased <code>etrade_goods.ai_single_appraisal</code> table reference
     */
    public AiSingleAppraisal(Name alias) {
        this(alias, AI_SINGLE_APPRAISAL);
    }

    private AiSingleAppraisal(Name alias, Table<AiSingleAppraisalRecord> aliased) {
        this(alias, aliased, null);
    }

    private AiSingleAppraisal(Name alias, Table<AiSingleAppraisalRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("AI单点鉴定结果"));
    }

    public <O extends Record> AiSingleAppraisal(Table<O> child, ForeignKey<O, AiSingleAppraisalRecord> key) {
        super(child, key, AI_SINGLE_APPRAISAL);
    }

    @Override
    public Schema getSchema() {
        return EtradeGoods.ETRADE_GOODS;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.AI_SINGLE_APPRAISAL_IDX_USER_OPERATION_ID, Indexes.AI_SINGLE_APPRAISAL_PRIMARY);
    }

    @Override
    public Identity<AiSingleAppraisalRecord, Integer> getIdentity() {
        return Keys.IDENTITY_AI_SINGLE_APPRAISAL;
    }

    @Override
    public UniqueKey<AiSingleAppraisalRecord> getPrimaryKey() {
        return Keys.KEY_AI_SINGLE_APPRAISAL_PRIMARY;
    }

    @Override
    public List<UniqueKey<AiSingleAppraisalRecord>> getKeys() {
        return Arrays.<UniqueKey<AiSingleAppraisalRecord>>asList(Keys.KEY_AI_SINGLE_APPRAISAL_PRIMARY);
    }

    @Override
    public AiSingleAppraisal as(String alias) {
        return new AiSingleAppraisal(DSL.name(alias), this);
    }

    @Override
    public AiSingleAppraisal as(Name alias) {
        return new AiSingleAppraisal(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AiSingleAppraisal rename(String name) {
        return new AiSingleAppraisal(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AiSingleAppraisal rename(Name name) {
        return new AiSingleAppraisal(name, null);
    }
}
