/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables.records;


import com.etrade.puggo.db.tables.GoodsComment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.UpdatableRecordImpl;


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
public class GoodsCommentRecord extends UpdatableRecordImpl<GoodsCommentRecord> implements Record12<Long, Long, BigDecimal, Byte, String, Byte, Long, Long, Long, Byte, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 868705677;

    /**
     * Setter for <code>etrade_goods.goods_comment.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_comment.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>etrade_goods.goods_comment.goods_id</code>. 商品id
     */
    public void setGoodsId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_comment.goods_id</code>. 商品id
     */
    public Long getGoodsId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>etrade_goods.goods_comment.score</code>. 评分
     */
    public void setScore(BigDecimal value) {
        set(2, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_comment.score</code>. 评分
     */
    public BigDecimal getScore() {
        return (BigDecimal) get(2);
    }

    /**
     * Setter for <code>etrade_goods.goods_comment.identity</code>. 评论者身份, 1买家身份 2卖家身份
     */
    public void setIdentity(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_comment.identity</code>. 评论者身份, 1买家身份 2卖家身份
     */
    public Byte getIdentity() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>etrade_goods.goods_comment.comment</code>. 评论内容
     */
    public void setComment(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_comment.comment</code>. 评论内容
     */
    public String getComment() {
        return (String) get(4);
    }

    /**
     * Setter for <code>etrade_goods.goods_comment.type</code>. 评论类型：1评论买家 2评论卖家 3评论私讯
     */
    public void setType(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_comment.type</code>. 评论类型：1评论买家 2评论卖家 3评论私讯
     */
    public Byte getType() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>etrade_goods.goods_comment.from_user_id</code>. 评论用户id
     */
    public void setFromUserId(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_comment.from_user_id</code>. 评论用户id
     */
    public Long getFromUserId() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>etrade_goods.goods_comment.to_user_id</code>. 目标用户id
     */
    public void setToUserId(Long value) {
        set(7, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_comment.to_user_id</code>. 目标用户id
     */
    public Long getToUserId() {
        return (Long) get(7);
    }

    /**
     * Setter for <code>etrade_goods.goods_comment.last_comment_id</code>. 回复的上一条评论id
     */
    public void setLastCommentId(Long value) {
        set(8, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_comment.last_comment_id</code>. 回复的上一条评论id
     */
    public Long getLastCommentId() {
        return (Long) get(8);
    }

    /**
     * Setter for <code>etrade_goods.goods_comment.is_reply</code>. 是否为回复，0不是 1是
     */
    public void setIsReply(Byte value) {
        set(9, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_comment.is_reply</code>. 是否为回复，0不是 1是
     */
    public Byte getIsReply() {
        return (Byte) get(9);
    }

    /**
     * Setter for <code>etrade_goods.goods_comment.created</code>.
     */
    public void setCreated(LocalDateTime value) {
        set(10, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_comment.created</code>.
     */
    public LocalDateTime getCreated() {
        return (LocalDateTime) get(10);
    }

    /**
     * Setter for <code>etrade_goods.goods_comment.modified</code>.
     */
    public void setModified(LocalDateTime value) {
        set(11, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_comment.modified</code>.
     */
    public LocalDateTime getModified() {
        return (LocalDateTime) get(11);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record12 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row12<Long, Long, BigDecimal, Byte, String, Byte, Long, Long, Long, Byte, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    @Override
    public Row12<Long, Long, BigDecimal, Byte, String, Byte, Long, Long, Long, Byte, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row12) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return GoodsComment.GOODS_COMMENT.ID;
    }

    @Override
    public Field<Long> field2() {
        return GoodsComment.GOODS_COMMENT.GOODS_ID;
    }

    @Override
    public Field<BigDecimal> field3() {
        return GoodsComment.GOODS_COMMENT.SCORE;
    }

    @Override
    public Field<Byte> field4() {
        return GoodsComment.GOODS_COMMENT.IDENTITY;
    }

    @Override
    public Field<String> field5() {
        return GoodsComment.GOODS_COMMENT.COMMENT;
    }

    @Override
    public Field<Byte> field6() {
        return GoodsComment.GOODS_COMMENT.TYPE;
    }

    @Override
    public Field<Long> field7() {
        return GoodsComment.GOODS_COMMENT.FROM_USER_ID;
    }

    @Override
    public Field<Long> field8() {
        return GoodsComment.GOODS_COMMENT.TO_USER_ID;
    }

    @Override
    public Field<Long> field9() {
        return GoodsComment.GOODS_COMMENT.LAST_COMMENT_ID;
    }

    @Override
    public Field<Byte> field10() {
        return GoodsComment.GOODS_COMMENT.IS_REPLY;
    }

    @Override
    public Field<LocalDateTime> field11() {
        return GoodsComment.GOODS_COMMENT.CREATED;
    }

    @Override
    public Field<LocalDateTime> field12() {
        return GoodsComment.GOODS_COMMENT.MODIFIED;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getGoodsId();
    }

    @Override
    public BigDecimal component3() {
        return getScore();
    }

    @Override
    public Byte component4() {
        return getIdentity();
    }

    @Override
    public String component5() {
        return getComment();
    }

    @Override
    public Byte component6() {
        return getType();
    }

    @Override
    public Long component7() {
        return getFromUserId();
    }

    @Override
    public Long component8() {
        return getToUserId();
    }

    @Override
    public Long component9() {
        return getLastCommentId();
    }

    @Override
    public Byte component10() {
        return getIsReply();
    }

    @Override
    public LocalDateTime component11() {
        return getCreated();
    }

    @Override
    public LocalDateTime component12() {
        return getModified();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getGoodsId();
    }

    @Override
    public BigDecimal value3() {
        return getScore();
    }

    @Override
    public Byte value4() {
        return getIdentity();
    }

    @Override
    public String value5() {
        return getComment();
    }

    @Override
    public Byte value6() {
        return getType();
    }

    @Override
    public Long value7() {
        return getFromUserId();
    }

    @Override
    public Long value8() {
        return getToUserId();
    }

    @Override
    public Long value9() {
        return getLastCommentId();
    }

    @Override
    public Byte value10() {
        return getIsReply();
    }

    @Override
    public LocalDateTime value11() {
        return getCreated();
    }

    @Override
    public LocalDateTime value12() {
        return getModified();
    }

    @Override
    public GoodsCommentRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public GoodsCommentRecord value2(Long value) {
        setGoodsId(value);
        return this;
    }

    @Override
    public GoodsCommentRecord value3(BigDecimal value) {
        setScore(value);
        return this;
    }

    @Override
    public GoodsCommentRecord value4(Byte value) {
        setIdentity(value);
        return this;
    }

    @Override
    public GoodsCommentRecord value5(String value) {
        setComment(value);
        return this;
    }

    @Override
    public GoodsCommentRecord value6(Byte value) {
        setType(value);
        return this;
    }

    @Override
    public GoodsCommentRecord value7(Long value) {
        setFromUserId(value);
        return this;
    }

    @Override
    public GoodsCommentRecord value8(Long value) {
        setToUserId(value);
        return this;
    }

    @Override
    public GoodsCommentRecord value9(Long value) {
        setLastCommentId(value);
        return this;
    }

    @Override
    public GoodsCommentRecord value10(Byte value) {
        setIsReply(value);
        return this;
    }

    @Override
    public GoodsCommentRecord value11(LocalDateTime value) {
        setCreated(value);
        return this;
    }

    @Override
    public GoodsCommentRecord value12(LocalDateTime value) {
        setModified(value);
        return this;
    }

    @Override
    public GoodsCommentRecord values(Long value1, Long value2, BigDecimal value3, Byte value4, String value5, Byte value6, Long value7, Long value8, Long value9, Byte value10, LocalDateTime value11, LocalDateTime value12) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GoodsCommentRecord
     */
    public GoodsCommentRecord() {
        super(GoodsComment.GOODS_COMMENT);
    }

    /**
     * Create a detached, initialised GoodsCommentRecord
     */
    public GoodsCommentRecord(Long id, Long goodsId, BigDecimal score, Byte identity, String comment, Byte type, Long fromUserId, Long toUserId, Long lastCommentId, Byte isReply, LocalDateTime created, LocalDateTime modified) {
        super(GoodsComment.GOODS_COMMENT);

        set(0, id);
        set(1, goodsId);
        set(2, score);
        set(3, identity);
        set(4, comment);
        set(5, type);
        set(6, fromUserId);
        set(7, toUserId);
        set(8, lastCommentId);
        set(9, isReply);
        set(10, created);
        set(11, modified);
    }
}
