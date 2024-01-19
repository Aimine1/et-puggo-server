/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables.records;


import com.etrade.puggo.db.tables.AiPointList;

import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * AI鉴定点查询
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AiPointListRecord extends UpdatableRecordImpl<AiPointListRecord> implements Record14<Integer, Integer, Integer, Integer, Integer, String, String, Byte, Byte, String, String, String, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = -1030534760;

    /**
     * Setter for <code>etrade_goods.ai_point_list.id</code>. 鉴定点id，值取自接口返回
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>etrade_goods.ai_point_list.id</code>. 鉴定点id，值取自接口返回
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>etrade_goods.ai_point_list.kind_id</code>. 品类id
     */
    public void setKindId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>etrade_goods.ai_point_list.kind_id</code>. 品类id
     */
    public Integer getKindId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>etrade_goods.ai_point_list.brand_id</code>. 品牌id
     */
    public void setBrandId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>etrade_goods.ai_point_list.brand_id</code>. 品牌id
     */
    public Integer getBrandId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>etrade_goods.ai_point_list.series_id</code>. 系列id
     */
    public void setSeriesId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>etrade_goods.ai_point_list.series_id</code>. 系列id
     */
    public Integer getSeriesId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>etrade_goods.ai_point_list.category_id</code>. 关联鉴定点的品类/品牌/系列id（鉴定点请求列表中传的那个）
     */
    public void setCategoryId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>etrade_goods.ai_point_list.category_id</code>. 关联鉴定点的品类/品牌/系列id（鉴定点请求列表中传的那个）
     */
    public Integer getCategoryId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>etrade_goods.ai_point_list.description</code>. 鉴定点描述
     */
    public void setDescription(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>etrade_goods.ai_point_list.description</code>. 鉴定点描述
     */
    public String getDescription() {
        return (String) get(5);
    }

    /**
     * Setter for <code>etrade_goods.ai_point_list.example_img</code>. 示例图片
     */
    public void setExampleImg(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>etrade_goods.ai_point_list.example_img</code>. 示例图片
     */
    public String getExampleImg() {
        return (String) get(6);
    }

    /**
     * Setter for <code>etrade_goods.ai_point_list.must</code>. 1-必传 2-选传
     */
    public void setMust(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>etrade_goods.ai_point_list.must</code>. 1-必传 2-选传
     */
    public Byte getMust() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>etrade_goods.ai_point_list.important</code>. 是否重要:0-不重要 1-重要
     */
    public void setImportant(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>etrade_goods.ai_point_list.important</code>. 是否重要:0-不重要 1-重要
     */
    public Byte getImportant() {
        return (Byte) get(8);
    }

    /**
     * Setter for <code>etrade_goods.ai_point_list.point_name</code>. 鉴定点名称
     */
    public void setPointName(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>etrade_goods.ai_point_list.point_name</code>. 鉴定点名称
     */
    public String getPointName() {
        return (String) get(9);
    }

    /**
     * Setter for <code>etrade_goods.ai_point_list.stick_figure_url</code>. 简笔画小图
     */
    public void setStickFigureUrl(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>etrade_goods.ai_point_list.stick_figure_url</code>. 简笔画小图
     */
    public String getStickFigureUrl() {
        return (String) get(10);
    }

    /**
     * Setter for <code>etrade_goods.ai_point_list.big_stick_figure_url</code>. 简笔画大图
     */
    public void setBigStickFigureUrl(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>etrade_goods.ai_point_list.big_stick_figure_url</code>. 简笔画大图
     */
    public String getBigStickFigureUrl() {
        return (String) get(11);
    }

    /**
     * Setter for <code>etrade_goods.ai_point_list.created</code>.
     */
    public void setCreated(LocalDateTime value) {
        set(12, value);
    }

    /**
     * Getter for <code>etrade_goods.ai_point_list.created</code>.
     */
    public LocalDateTime getCreated() {
        return (LocalDateTime) get(12);
    }

    /**
     * Setter for <code>etrade_goods.ai_point_list.modified</code>.
     */
    public void setModified(LocalDateTime value) {
        set(13, value);
    }

    /**
     * Getter for <code>etrade_goods.ai_point_list.modified</code>.
     */
    public LocalDateTime getModified() {
        return (LocalDateTime) get(13);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record14 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row14<Integer, Integer, Integer, Integer, Integer, String, String, Byte, Byte, String, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    @Override
    public Row14<Integer, Integer, Integer, Integer, Integer, String, String, Byte, Byte, String, String, String, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row14) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return AiPointList.AI_POINT_LIST.ID;
    }

    @Override
    public Field<Integer> field2() {
        return AiPointList.AI_POINT_LIST.KIND_ID;
    }

    @Override
    public Field<Integer> field3() {
        return AiPointList.AI_POINT_LIST.BRAND_ID;
    }

    @Override
    public Field<Integer> field4() {
        return AiPointList.AI_POINT_LIST.SERIES_ID;
    }

    @Override
    public Field<Integer> field5() {
        return AiPointList.AI_POINT_LIST.CATEGORY_ID;
    }

    @Override
    public Field<String> field6() {
        return AiPointList.AI_POINT_LIST.DESCRIPTION;
    }

    @Override
    public Field<String> field7() {
        return AiPointList.AI_POINT_LIST.EXAMPLE_IMG;
    }

    @Override
    public Field<Byte> field8() {
        return AiPointList.AI_POINT_LIST.MUST;
    }

    @Override
    public Field<Byte> field9() {
        return AiPointList.AI_POINT_LIST.IMPORTANT;
    }

    @Override
    public Field<String> field10() {
        return AiPointList.AI_POINT_LIST.POINT_NAME;
    }

    @Override
    public Field<String> field11() {
        return AiPointList.AI_POINT_LIST.STICK_FIGURE_URL;
    }

    @Override
    public Field<String> field12() {
        return AiPointList.AI_POINT_LIST.BIG_STICK_FIGURE_URL;
    }

    @Override
    public Field<LocalDateTime> field13() {
        return AiPointList.AI_POINT_LIST.CREATED;
    }

    @Override
    public Field<LocalDateTime> field14() {
        return AiPointList.AI_POINT_LIST.MODIFIED;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getKindId();
    }

    @Override
    public Integer component3() {
        return getBrandId();
    }

    @Override
    public Integer component4() {
        return getSeriesId();
    }

    @Override
    public Integer component5() {
        return getCategoryId();
    }

    @Override
    public String component6() {
        return getDescription();
    }

    @Override
    public String component7() {
        return getExampleImg();
    }

    @Override
    public Byte component8() {
        return getMust();
    }

    @Override
    public Byte component9() {
        return getImportant();
    }

    @Override
    public String component10() {
        return getPointName();
    }

    @Override
    public String component11() {
        return getStickFigureUrl();
    }

    @Override
    public String component12() {
        return getBigStickFigureUrl();
    }

    @Override
    public LocalDateTime component13() {
        return getCreated();
    }

    @Override
    public LocalDateTime component14() {
        return getModified();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getKindId();
    }

    @Override
    public Integer value3() {
        return getBrandId();
    }

    @Override
    public Integer value4() {
        return getSeriesId();
    }

    @Override
    public Integer value5() {
        return getCategoryId();
    }

    @Override
    public String value6() {
        return getDescription();
    }

    @Override
    public String value7() {
        return getExampleImg();
    }

    @Override
    public Byte value8() {
        return getMust();
    }

    @Override
    public Byte value9() {
        return getImportant();
    }

    @Override
    public String value10() {
        return getPointName();
    }

    @Override
    public String value11() {
        return getStickFigureUrl();
    }

    @Override
    public String value12() {
        return getBigStickFigureUrl();
    }

    @Override
    public LocalDateTime value13() {
        return getCreated();
    }

    @Override
    public LocalDateTime value14() {
        return getModified();
    }

    @Override
    public AiPointListRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public AiPointListRecord value2(Integer value) {
        setKindId(value);
        return this;
    }

    @Override
    public AiPointListRecord value3(Integer value) {
        setBrandId(value);
        return this;
    }

    @Override
    public AiPointListRecord value4(Integer value) {
        setSeriesId(value);
        return this;
    }

    @Override
    public AiPointListRecord value5(Integer value) {
        setCategoryId(value);
        return this;
    }

    @Override
    public AiPointListRecord value6(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public AiPointListRecord value7(String value) {
        setExampleImg(value);
        return this;
    }

    @Override
    public AiPointListRecord value8(Byte value) {
        setMust(value);
        return this;
    }

    @Override
    public AiPointListRecord value9(Byte value) {
        setImportant(value);
        return this;
    }

    @Override
    public AiPointListRecord value10(String value) {
        setPointName(value);
        return this;
    }

    @Override
    public AiPointListRecord value11(String value) {
        setStickFigureUrl(value);
        return this;
    }

    @Override
    public AiPointListRecord value12(String value) {
        setBigStickFigureUrl(value);
        return this;
    }

    @Override
    public AiPointListRecord value13(LocalDateTime value) {
        setCreated(value);
        return this;
    }

    @Override
    public AiPointListRecord value14(LocalDateTime value) {
        setModified(value);
        return this;
    }

    @Override
    public AiPointListRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, String value6, String value7, Byte value8, Byte value9, String value10, String value11, String value12, LocalDateTime value13, LocalDateTime value14) {
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
        value13(value13);
        value14(value14);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AiPointListRecord
     */
    public AiPointListRecord() {
        super(AiPointList.AI_POINT_LIST);
    }

    /**
     * Create a detached, initialised AiPointListRecord
     */
    public AiPointListRecord(Integer id, Integer kindId, Integer brandId, Integer seriesId, Integer categoryId, String description, String exampleImg, Byte must, Byte important, String pointName, String stickFigureUrl, String bigStickFigureUrl, LocalDateTime created, LocalDateTime modified) {
        super(AiPointList.AI_POINT_LIST);

        set(0, id);
        set(1, kindId);
        set(2, brandId);
        set(3, seriesId);
        set(4, categoryId);
        set(5, description);
        set(6, exampleImg);
        set(7, must);
        set(8, important);
        set(9, pointName);
        set(10, stickFigureUrl);
        set(11, bigStickFigureUrl);
        set(12, created);
        set(13, modified);
    }
}
