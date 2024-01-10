/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables.records;


import com.etrade.puggo.db.tables.GoodsClass;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 商品分类级别
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GoodsClassRecord extends UpdatableRecordImpl<GoodsClassRecord> implements Record8<Integer, String, String, Byte, LocalDateTime, LocalDateTime, Long, String> {

    private static final long serialVersionUID = -1761591628;

    /**
     * Setter for <code>etrade_goods.goods_class.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_class.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>etrade_goods.goods_class.class_name</code>. 级别名称
     */
    public void setClassName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_class.class_name</code>. 级别名称
     */
    public String getClassName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>etrade_goods.goods_class.class_path</code>. 级别path,用于搜索,例如衣服id为1,衣服下的二级T恤id为14,则T恤path为1:14
     */
    public void setClassPath(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_class.class_path</code>. 级别path,用于搜索,例如衣服id为1,衣服下的二级T恤id为14,则T恤path为1:14
     */
    public String getClassPath() {
        return (String) get(2);
    }

    /**
     * Setter for <code>etrade_goods.goods_class.class_level</code>. 级别,从1到n,数字越大分类越精细
     */
    public void setClassLevel(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_class.class_level</code>. 级别,从1到n,数字越大分类越精细
     */
    public Byte getClassLevel() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>etrade_goods.goods_class.created</code>.
     */
    public void setCreated(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_class.created</code>.
     */
    public LocalDateTime getCreated() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>etrade_goods.goods_class.modified</code>.
     */
    public void setModified(LocalDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_class.modified</code>.
     */
    public LocalDateTime getModified() {
        return (LocalDateTime) get(5);
    }

    /**
     * Setter for <code>etrade_goods.goods_class.icon_id</code>. icon
     */
    public void setIconId(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_class.icon_id</code>. icon
     */
    public Long getIconId() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>etrade_goods.goods_class.lang</code>. 语言
     */
    public void setLang(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_class.lang</code>. 语言
     */
    public String getLang() {
        return (String) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row8<Integer, String, String, Byte, LocalDateTime, LocalDateTime, Long, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<Integer, String, String, Byte, LocalDateTime, LocalDateTime, Long, String> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return GoodsClass.GOODS_CLASS.ID;
    }

    @Override
    public Field<String> field2() {
        return GoodsClass.GOODS_CLASS.CLASS_NAME;
    }

    @Override
    public Field<String> field3() {
        return GoodsClass.GOODS_CLASS.CLASS_PATH;
    }

    @Override
    public Field<Byte> field4() {
        return GoodsClass.GOODS_CLASS.CLASS_LEVEL;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return GoodsClass.GOODS_CLASS.CREATED;
    }

    @Override
    public Field<LocalDateTime> field6() {
        return GoodsClass.GOODS_CLASS.MODIFIED;
    }

    @Override
    public Field<Long> field7() {
        return GoodsClass.GOODS_CLASS.ICON_ID;
    }

    @Override
    public Field<String> field8() {
        return GoodsClass.GOODS_CLASS.LANG;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getClassName();
    }

    @Override
    public String component3() {
        return getClassPath();
    }

    @Override
    public Byte component4() {
        return getClassLevel();
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
    public Long component7() {
        return getIconId();
    }

    @Override
    public String component8() {
        return getLang();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getClassName();
    }

    @Override
    public String value3() {
        return getClassPath();
    }

    @Override
    public Byte value4() {
        return getClassLevel();
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
    public Long value7() {
        return getIconId();
    }

    @Override
    public String value8() {
        return getLang();
    }

    @Override
    public GoodsClassRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public GoodsClassRecord value2(String value) {
        setClassName(value);
        return this;
    }

    @Override
    public GoodsClassRecord value3(String value) {
        setClassPath(value);
        return this;
    }

    @Override
    public GoodsClassRecord value4(Byte value) {
        setClassLevel(value);
        return this;
    }

    @Override
    public GoodsClassRecord value5(LocalDateTime value) {
        setCreated(value);
        return this;
    }

    @Override
    public GoodsClassRecord value6(LocalDateTime value) {
        setModified(value);
        return this;
    }

    @Override
    public GoodsClassRecord value7(Long value) {
        setIconId(value);
        return this;
    }

    @Override
    public GoodsClassRecord value8(String value) {
        setLang(value);
        return this;
    }

    @Override
    public GoodsClassRecord values(Integer value1, String value2, String value3, Byte value4, LocalDateTime value5, LocalDateTime value6, Long value7, String value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GoodsClassRecord
     */
    public GoodsClassRecord() {
        super(GoodsClass.GOODS_CLASS);
    }

    /**
     * Create a detached, initialised GoodsClassRecord
     */
    public GoodsClassRecord(Integer id, String className, String classPath, Byte classLevel, LocalDateTime created, LocalDateTime modified, Long iconId, String lang) {
        super(GoodsClass.GOODS_CLASS);

        set(0, id);
        set(1, className);
        set(2, classPath);
        set(3, classLevel);
        set(4, created);
        set(5, modified);
        set(6, iconId);
        set(7, lang);
    }
}
