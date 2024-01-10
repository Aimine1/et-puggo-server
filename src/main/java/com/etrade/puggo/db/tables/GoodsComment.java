/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables;


import com.etrade.puggo.db.EtradeGoods;
import com.etrade.puggo.db.Indexes;
import com.etrade.puggo.db.Keys;
import com.etrade.puggo.db.tables.records.GoodsCommentRecord;
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
import org.jooq.Row12;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * 商品评论
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GoodsComment extends TableImpl<GoodsCommentRecord> {

    private static final long serialVersionUID = 1658495301;

    /**
     * The reference instance of <code>etrade_goods.goods_comment</code>
     */
    public static final GoodsComment GOODS_COMMENT = new GoodsComment();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GoodsCommentRecord> getRecordType() {
        return GoodsCommentRecord.class;
    }

    /**
     * The column <code>etrade_goods.goods_comment.id</code>.
     */
    public final TableField<GoodsCommentRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>etrade_goods.goods_comment.goods_id</code>. 商品id
     */
    public final TableField<GoodsCommentRecord, Long> GOODS_ID = createField(DSL.name("goods_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "商品id");

    /**
     * The column <code>etrade_goods.goods_comment.rate</code>. 评分
     */
    public final TableField<GoodsCommentRecord, BigDecimal> RATE = createField(DSL.name("rate"), org.jooq.impl.SQLDataType.DECIMAL(10, 1).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.0", org.jooq.impl.SQLDataType.DECIMAL)), this, "评分");

    /**
     * The column <code>etrade_goods.goods_comment.identity</code>. 评论者身份, 1买家身份 2卖家身份
     */
    public final TableField<GoodsCommentRecord, Byte> IDENTITY = createField(DSL.name("identity"), org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "评论者身份, 1买家身份 2卖家身份");

    /**
     * The column <code>etrade_goods.goods_comment.comment</code>. 评论内容
     */
    public final TableField<GoodsCommentRecord, String> COMMENT = createField(DSL.name("comment"), org.jooq.impl.SQLDataType.CLOB, this, "评论内容");

    /**
     * The column <code>etrade_goods.goods_comment.type</code>. 评论类型：1评论买家 2评论卖家 3评论私讯
     */
    public final TableField<GoodsCommentRecord, Byte> TYPE = createField(DSL.name("type"), org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "评论类型：1评论买家 2评论卖家 3评论私讯");

    /**
     * The column <code>etrade_goods.goods_comment.from_user_id</code>. 评论用户id
     */
    public final TableField<GoodsCommentRecord, Long> FROM_USER_ID = createField(DSL.name("from_user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "评论用户id");

    /**
     * The column <code>etrade_goods.goods_comment.to_user_id</code>. 目标用户id
     */
    public final TableField<GoodsCommentRecord, Long> TO_USER_ID = createField(DSL.name("to_user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "目标用户id");

    /**
     * The column <code>etrade_goods.goods_comment.last_comment_id</code>. 回复的上一条评论id
     */
    public final TableField<GoodsCommentRecord, Long> LAST_COMMENT_ID = createField(DSL.name("last_comment_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "回复的上一条评论id");

    /**
     * The column <code>etrade_goods.goods_comment.is_reply</code>. 是否为回复，0不是 1是
     */
    public final TableField<GoodsCommentRecord, Byte> IS_REPLY = createField(DSL.name("is_reply"), org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否为回复，0不是 1是");

    /**
     * The column <code>etrade_goods.goods_comment.created</code>.
     */
    public final TableField<GoodsCommentRecord, LocalDateTime> CREATED = createField(DSL.name("created"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>etrade_goods.goods_comment.modified</code>.
     */
    public final TableField<GoodsCommentRecord, LocalDateTime> MODIFIED = createField(DSL.name("modified"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * Create a <code>etrade_goods.goods_comment</code> table reference
     */
    public GoodsComment() {
        this(DSL.name("goods_comment"), null);
    }

    /**
     * Create an aliased <code>etrade_goods.goods_comment</code> table reference
     */
    public GoodsComment(String alias) {
        this(DSL.name(alias), GOODS_COMMENT);
    }

    /**
     * Create an aliased <code>etrade_goods.goods_comment</code> table reference
     */
    public GoodsComment(Name alias) {
        this(alias, GOODS_COMMENT);
    }

    private GoodsComment(Name alias, Table<GoodsCommentRecord> aliased) {
        this(alias, aliased, null);
    }

    private GoodsComment(Name alias, Table<GoodsCommentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("商品评论"));
    }

    public <O extends Record> GoodsComment(Table<O> child, ForeignKey<O, GoodsCommentRecord> key) {
        super(child, key, GOODS_COMMENT);
    }

    @Override
    public Schema getSchema() {
        return EtradeGoods.ETRADE_GOODS;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.GOODS_COMMENT_IDX_GOODS_COMMENT_LAST_COMMENT_ID, Indexes.GOODS_COMMENT_IDX_GOODS_COMMENT_TO_USER_ID, Indexes.GOODS_COMMENT_PRIMARY);
    }

    @Override
    public Identity<GoodsCommentRecord, Long> getIdentity() {
        return Keys.IDENTITY_GOODS_COMMENT;
    }

    @Override
    public UniqueKey<GoodsCommentRecord> getPrimaryKey() {
        return Keys.KEY_GOODS_COMMENT_PRIMARY;
    }

    @Override
    public List<UniqueKey<GoodsCommentRecord>> getKeys() {
        return Arrays.<UniqueKey<GoodsCommentRecord>>asList(Keys.KEY_GOODS_COMMENT_PRIMARY);
    }

    @Override
    public GoodsComment as(String alias) {
        return new GoodsComment(DSL.name(alias), this);
    }

    @Override
    public GoodsComment as(Name alias) {
        return new GoodsComment(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GoodsComment rename(String name) {
        return new GoodsComment(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GoodsComment rename(Name name) {
        return new GoodsComment(name, null);
    }

    // -------------------------------------------------------------------------
    // Row12 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row12<Long, Long, BigDecimal, Byte, String, Byte, Long, Long, Long, Byte, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row12) super.fieldsRow();
    }
}
