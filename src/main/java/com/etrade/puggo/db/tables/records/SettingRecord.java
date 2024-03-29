/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables.records;


import com.etrade.puggo.db.tables.Setting;

import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 设置
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SettingRecord extends UpdatableRecordImpl<SettingRecord> implements Record6<Long, String, String, String, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = -994970326;

    /**
     * Setter for <code>etrade_goods.setting.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>etrade_goods.setting.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>etrade_goods.setting.key</code>. key
     */
    public void setKey(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>etrade_goods.setting.key</code>. key
     */
    public String getKey() {
        return (String) get(1);
    }

    /**
     * Setter for <code>etrade_goods.setting.value</code>. value
     */
    public void setValue(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>etrade_goods.setting.value</code>. value
     */
    public String getValue() {
        return (String) get(2);
    }

    /**
     * Setter for <code>etrade_goods.setting.comment</code>. comment
     */
    public void setComment(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>etrade_goods.setting.comment</code>. comment
     */
    public String getComment() {
        return (String) get(3);
    }

    /**
     * Setter for <code>etrade_goods.setting.created</code>.
     */
    public void setCreated(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>etrade_goods.setting.created</code>.
     */
    public LocalDateTime getCreated() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>etrade_goods.setting.modified</code>.
     */
    public void setModified(LocalDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>etrade_goods.setting.modified</code>.
     */
    public LocalDateTime getModified() {
        return (LocalDateTime) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, String, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Long, String, String, String, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Setting.SETTING.ID;
    }

    @Override
    public Field<String> field2() {
        return Setting.SETTING.KEY;
    }

    @Override
    public Field<String> field3() {
        return Setting.SETTING.VALUE;
    }

    @Override
    public Field<String> field4() {
        return Setting.SETTING.COMMENT;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return Setting.SETTING.CREATED;
    }

    @Override
    public Field<LocalDateTime> field6() {
        return Setting.SETTING.MODIFIED;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getKey();
    }

    @Override
    public String component3() {
        return getValue();
    }

    @Override
    public String component4() {
        return getComment();
    }

    @Override
    public LocalDateTime component5() {
        return getCreated();
    }

    @Override
    public LocalDateTime component6() {
        return getModified();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getKey();
    }

    @Override
    public String value3() {
        return getValue();
    }

    @Override
    public String value4() {
        return getComment();
    }

    @Override
    public LocalDateTime value5() {
        return getCreated();
    }

    @Override
    public LocalDateTime value6() {
        return getModified();
    }

    @Override
    public SettingRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public SettingRecord value2(String value) {
        setKey(value);
        return this;
    }

    @Override
    public SettingRecord value3(String value) {
        setValue(value);
        return this;
    }

    @Override
    public SettingRecord value4(String value) {
        setComment(value);
        return this;
    }

    @Override
    public SettingRecord value5(LocalDateTime value) {
        setCreated(value);
        return this;
    }

    @Override
    public SettingRecord value6(LocalDateTime value) {
        setModified(value);
        return this;
    }

    @Override
    public SettingRecord values(Long value1, String value2, String value3, String value4, LocalDateTime value5, LocalDateTime value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SettingRecord
     */
    public SettingRecord() {
        super(Setting.SETTING);
    }

    /**
     * Create a detached, initialised SettingRecord
     */
    public SettingRecord(Long id, String key, String value, String comment, LocalDateTime created, LocalDateTime modified) {
        super(Setting.SETTING);

        set(0, id);
        set(1, key);
        set(2, value);
        set(3, comment);
        set(4, created);
        set(5, modified);
    }
}
