/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables.records;


import com.etrade.puggo.db.tables.GoodsPicture;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 商品图片
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GoodsPictureRecord extends UpdatableRecordImpl<GoodsPictureRecord> implements Record11<Long, String, String, String, Byte, String, Long, Byte, LocalDateTime, String, String> {

    private static final long serialVersionUID = -1027967775;

    /**
     * Setter for <code>etrade_goods.goods_picture.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_picture.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>etrade_goods.goods_picture.url</code>. 对象存储地址
     */
    public void setUrl(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_picture.url</code>. 对象存储地址
     */
    public String getUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>etrade_goods.goods_picture.key</code>. 对象存储key
     */
    public void setKey(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_picture.key</code>. 对象存储key
     */
    public String getKey() {
        return (String) get(2);
    }

    /**
     * Setter for <code>etrade_goods.goods_picture.version_id</code>. 对象存储版本号
     */
    public void setVersionId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_picture.version_id</code>. 对象存储版本号
     */
    public String getVersionId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>etrade_goods.goods_picture.is_main</code>. 是否商品主图：1是
     */
    public void setIsMain(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_picture.is_main</code>. 是否商品主图：1是
     */
    public Byte getIsMain() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>etrade_goods.goods_picture.img_type</code>. 图片类型, 可选值 goods, comment, icon等
     */
    public void setImgType(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_picture.img_type</code>. 图片类型, 可选值 goods, comment, icon等
     */
    public String getImgType() {
        return (String) get(5);
    }

    /**
     * Setter for <code>etrade_goods.goods_picture.target_id</code>. 目标id, type=goods为商品id, type=comment为评论id 等等
     */
    public void setTargetId(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_picture.target_id</code>. 目标id, type=goods为商品id, type=comment为评论id 等等
     */
    public Long getTargetId() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>etrade_goods.goods_picture.deleted</code>. 是否弃用, 1 : 废弃，0 : 正常
     */
    public void setDeleted(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_picture.deleted</code>. 是否弃用, 1 : 废弃，0 : 正常
     */
    public Byte getDeleted() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>etrade_goods.goods_picture.created</code>.
     */
    public void setCreated(LocalDateTime value) {
        set(8, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_picture.created</code>.
     */
    public LocalDateTime getCreated() {
        return (LocalDateTime) get(8);
    }

    /**
     * Setter for <code>etrade_goods.goods_picture.jump_type</code>. 跳转类型
     */
    public void setJumpType(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_picture.jump_type</code>. 跳转类型
     */
    public String getJumpType() {
        return (String) get(9);
    }

    /**
     * Setter for <code>etrade_goods.goods_picture.jump_url</code>. 跳转url
     */
    public void setJumpUrl(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>etrade_goods.goods_picture.jump_url</code>. 跳转url
     */
    public String getJumpUrl() {
        return (String) get(10);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row11<Long, String, String, String, Byte, String, Long, Byte, LocalDateTime, String, String> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    @Override
    public Row11<Long, String, String, String, Byte, String, Long, Byte, LocalDateTime, String, String> valuesRow() {
        return (Row11) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return GoodsPicture.GOODS_PICTURE.ID;
    }

    @Override
    public Field<String> field2() {
        return GoodsPicture.GOODS_PICTURE.URL;
    }

    @Override
    public Field<String> field3() {
        return GoodsPicture.GOODS_PICTURE.KEY;
    }

    @Override
    public Field<String> field4() {
        return GoodsPicture.GOODS_PICTURE.VERSION_ID;
    }

    @Override
    public Field<Byte> field5() {
        return GoodsPicture.GOODS_PICTURE.IS_MAIN;
    }

    @Override
    public Field<String> field6() {
        return GoodsPicture.GOODS_PICTURE.IMG_TYPE;
    }

    @Override
    public Field<Long> field7() {
        return GoodsPicture.GOODS_PICTURE.TARGET_ID;
    }

    @Override
    public Field<Byte> field8() {
        return GoodsPicture.GOODS_PICTURE.DELETED;
    }

    @Override
    public Field<LocalDateTime> field9() {
        return GoodsPicture.GOODS_PICTURE.CREATED;
    }

    @Override
    public Field<String> field10() {
        return GoodsPicture.GOODS_PICTURE.JUMP_TYPE;
    }

    @Override
    public Field<String> field11() {
        return GoodsPicture.GOODS_PICTURE.JUMP_URL;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getUrl();
    }

    @Override
    public String component3() {
        return getKey();
    }

    @Override
    public String component4() {
        return getVersionId();
    }

    @Override
    public Byte component5() {
        return getIsMain();
    }

    @Override
    public String component6() {
        return getImgType();
    }

    @Override
    public Long component7() {
        return getTargetId();
    }

    @Override
    public Byte component8() {
        return getDeleted();
    }

    @Override
    public LocalDateTime component9() {
        return getCreated();
    }

    @Override
    public String component10() {
        return getJumpType();
    }

    @Override
    public String component11() {
        return getJumpUrl();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getUrl();
    }

    @Override
    public String value3() {
        return getKey();
    }

    @Override
    public String value4() {
        return getVersionId();
    }

    @Override
    public Byte value5() {
        return getIsMain();
    }

    @Override
    public String value6() {
        return getImgType();
    }

    @Override
    public Long value7() {
        return getTargetId();
    }

    @Override
    public Byte value8() {
        return getDeleted();
    }

    @Override
    public LocalDateTime value9() {
        return getCreated();
    }

    @Override
    public String value10() {
        return getJumpType();
    }

    @Override
    public String value11() {
        return getJumpUrl();
    }

    @Override
    public GoodsPictureRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public GoodsPictureRecord value2(String value) {
        setUrl(value);
        return this;
    }

    @Override
    public GoodsPictureRecord value3(String value) {
        setKey(value);
        return this;
    }

    @Override
    public GoodsPictureRecord value4(String value) {
        setVersionId(value);
        return this;
    }

    @Override
    public GoodsPictureRecord value5(Byte value) {
        setIsMain(value);
        return this;
    }

    @Override
    public GoodsPictureRecord value6(String value) {
        setImgType(value);
        return this;
    }

    @Override
    public GoodsPictureRecord value7(Long value) {
        setTargetId(value);
        return this;
    }

    @Override
    public GoodsPictureRecord value8(Byte value) {
        setDeleted(value);
        return this;
    }

    @Override
    public GoodsPictureRecord value9(LocalDateTime value) {
        setCreated(value);
        return this;
    }

    @Override
    public GoodsPictureRecord value10(String value) {
        setJumpType(value);
        return this;
    }

    @Override
    public GoodsPictureRecord value11(String value) {
        setJumpUrl(value);
        return this;
    }

    @Override
    public GoodsPictureRecord values(Long value1, String value2, String value3, String value4, Byte value5, String value6, Long value7, Byte value8, LocalDateTime value9, String value10, String value11) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GoodsPictureRecord
     */
    public GoodsPictureRecord() {
        super(GoodsPicture.GOODS_PICTURE);
    }

    /**
     * Create a detached, initialised GoodsPictureRecord
     */
    public GoodsPictureRecord(Long id, String url, String key, String versionId, Byte isMain, String imgType, Long targetId, Byte deleted, LocalDateTime created, String jumpType, String jumpUrl) {
        super(GoodsPicture.GOODS_PICTURE);

        set(0, id);
        set(1, url);
        set(2, key);
        set(3, versionId);
        set(4, isMain);
        set(5, imgType);
        set(6, targetId);
        set(7, deleted);
        set(8, created);
        set(9, jumpType);
        set(10, jumpUrl);
    }
}
