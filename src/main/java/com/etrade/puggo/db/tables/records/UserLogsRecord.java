/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables.records;


import com.etrade.puggo.db.tables.UserLogs;

import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 用户操作日志
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserLogsRecord extends UpdatableRecordImpl<UserLogsRecord> implements Record7<Long, String, Long, Long, Long, Long, LocalDateTime> {

    private static final long serialVersionUID = 1182542269;

    /**
     * Setter for <code>etrade_goods.user_logs.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>etrade_goods.user_logs.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>etrade_goods.user_logs.type</code>. 操作类型：1收藏商品 2删除商品 3发布商品 4下架商品 5售出商品 6关注用户 7取关用户
     */
    public void setType(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>etrade_goods.user_logs.type</code>. 操作类型：1收藏商品 2删除商品 3发布商品 4下架商品 5售出商品 6关注用户 7取关用户
     */
    public String getType() {
        return (String) get(1);
    }

    /**
     * Setter for <code>etrade_goods.user_logs.user_id</code>. 操作用户id
     */
    public void setUserId(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>etrade_goods.user_logs.user_id</code>. 操作用户id
     */
    public Long getUserId() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>etrade_goods.user_logs.target_goods_id</code>. 对象商品id
     */
    public void setTargetGoodsId(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>etrade_goods.user_logs.target_goods_id</code>. 对象商品id
     */
    public Long getTargetGoodsId() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>etrade_goods.user_logs.target_user_id</code>. 对象用户id
     */
    public void setTargetUserId(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>etrade_goods.user_logs.target_user_id</code>. 对象用户id
     */
    public Long getTargetUserId() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>etrade_goods.user_logs.target_groupon_id</code>. 对象团购券id
     */
    public void setTargetGrouponId(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>etrade_goods.user_logs.target_groupon_id</code>. 对象团购券id
     */
    public Long getTargetGrouponId() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>etrade_goods.user_logs.created</code>.
     */
    public void setCreated(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>etrade_goods.user_logs.created</code>.
     */
    public LocalDateTime getCreated() {
        return (LocalDateTime) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, String, Long, Long, Long, Long, LocalDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Long, String, Long, Long, Long, Long, LocalDateTime> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return UserLogs.USER_LOGS.ID;
    }

    @Override
    public Field<String> field2() {
        return UserLogs.USER_LOGS.TYPE;
    }

    @Override
    public Field<Long> field3() {
        return UserLogs.USER_LOGS.USER_ID;
    }

    @Override
    public Field<Long> field4() {
        return UserLogs.USER_LOGS.TARGET_GOODS_ID;
    }

    @Override
    public Field<Long> field5() {
        return UserLogs.USER_LOGS.TARGET_USER_ID;
    }

    @Override
    public Field<Long> field6() {
        return UserLogs.USER_LOGS.TARGET_GROUPON_ID;
    }

    @Override
    public Field<LocalDateTime> field7() {
        return UserLogs.USER_LOGS.CREATED;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getType();
    }

    @Override
    public Long component3() {
        return getUserId();
    }

    @Override
    public Long component4() {
        return getTargetGoodsId();
    }

    @Override
    public Long component5() {
        return getTargetUserId();
    }

    @Override
    public Long component6() {
        return getTargetGrouponId();
    }

    @Override
    public LocalDateTime component7() {
        return getCreated();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getType();
    }

    @Override
    public Long value3() {
        return getUserId();
    }

    @Override
    public Long value4() {
        return getTargetGoodsId();
    }

    @Override
    public Long value5() {
        return getTargetUserId();
    }

    @Override
    public Long value6() {
        return getTargetGrouponId();
    }

    @Override
    public LocalDateTime value7() {
        return getCreated();
    }

    @Override
    public UserLogsRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public UserLogsRecord value2(String value) {
        setType(value);
        return this;
    }

    @Override
    public UserLogsRecord value3(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public UserLogsRecord value4(Long value) {
        setTargetGoodsId(value);
        return this;
    }

    @Override
    public UserLogsRecord value5(Long value) {
        setTargetUserId(value);
        return this;
    }

    @Override
    public UserLogsRecord value6(Long value) {
        setTargetGrouponId(value);
        return this;
    }

    @Override
    public UserLogsRecord value7(LocalDateTime value) {
        setCreated(value);
        return this;
    }

    @Override
    public UserLogsRecord values(Long value1, String value2, Long value3, Long value4, Long value5, Long value6, LocalDateTime value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserLogsRecord
     */
    public UserLogsRecord() {
        super(UserLogs.USER_LOGS);
    }

    /**
     * Create a detached, initialised UserLogsRecord
     */
    public UserLogsRecord(Long id, String type, Long userId, Long targetGoodsId, Long targetUserId, Long targetGrouponId, LocalDateTime created) {
        super(UserLogs.USER_LOGS);

        set(0, id);
        set(1, type);
        set(2, userId);
        set(3, targetGoodsId);
        set(4, targetUserId);
        set(5, targetGrouponId);
        set(6, created);
    }
}
