/*
 * This file is generated by jOOQ.
 */
package com.etrade.puggo.db.tables.records;


import com.etrade.puggo.db.tables.Goods;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 发布的商品
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GoodsRecord extends UpdatableRecordImpl<GoodsRecord> {

    private static final long serialVersionUID = -1554739191;

    /**
     * Setter for <code>etrade_goods.goods.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>etrade_goods.goods.title</code>. 商品简称
     */
    public void setTitle(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.title</code>. 商品简称
     */
    public String getTitle() {
        return (String) get(1);
    }

    /**
     * Setter for <code>etrade_goods.goods.description</code>. 商品介绍
     */
    public void setDescription(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.description</code>. 商品介绍
     */
    public String getDescription() {
        return (String) get(2);
    }

    /**
     * Setter for <code>etrade_goods.goods.original_price</code>. 原价
     */
    public void setOriginalPrice(BigDecimal value) {
        set(3, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.original_price</code>. 原价
     */
    public BigDecimal getOriginalPrice() {
        return (BigDecimal) get(3);
    }

    /**
     * Setter for <code>etrade_goods.goods.real_price</code>. 现价
     */
    public void setRealPrice(BigDecimal value) {
        set(4, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.real_price</code>. 现价
     */
    public BigDecimal getRealPrice() {
        return (BigDecimal) get(4);
    }

    /**
     * Setter for <code>etrade_goods.goods.money_kind</code>. 货币类型：1美元 2人民币 等等
     */
    public void setMoneyKind(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.money_kind</code>. 货币类型：1美元 2人民币 等等
     */
    public String getMoneyKind() {
        return (String) get(5);
    }

    /**
     * Setter for <code>etrade_goods.goods.launch_user_id</code>. 发布用户id
     */
    public void setLaunchUserId(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.launch_user_id</code>. 发布用户id
     */
    public Long getLaunchUserId() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>etrade_goods.goods.launch_user_nickname</code>. 发布用户昵称
     */
    public void setLaunchUserNickname(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.launch_user_nickname</code>. 发布用户昵称
     */
    public String getLaunchUserNickname() {
        return (String) get(7);
    }

    /**
     * Setter for <code>etrade_goods.goods.quality</code>. 成色，1全新 2几乎全新 3半新 等等
     */
    public void setQuality(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.quality</code>. 成色，1全新 2几乎全新 3半新 等等
     */
    public String getQuality() {
        return (String) get(8);
    }

    /**
     * Setter for <code>etrade_goods.goods.class_path</code>. 分类级别
     */
    public void setClassPath(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.class_path</code>. 分类级别
     */
    public String getClassPath() {
        return (String) get(9);
    }

    /**
     * Setter for <code>etrade_goods.goods.brand</code>. 品牌
     */
    public void setBrand(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.brand</code>. 品牌
     */
    public String getBrand() {
        return (String) get(10);
    }

    /**
     * Setter for <code>etrade_goods.goods.size</code>. 尺寸
     */
    public void setSize(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.size</code>. 尺寸
     */
    public String getSize() {
        return (String) get(11);
    }

    /**
     * Setter for <code>etrade_goods.goods.unit</code>. 单位
     */
    public void setUnit(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.unit</code>. 单位
     */
    public String getUnit() {
        return (String) get(12);
    }

    /**
     * Setter for <code>etrade_goods.goods.delivery_type</code>. 发货方式：1邮寄 2面交 等等
     */
    public void setDeliveryType(Byte value) {
        set(13, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.delivery_type</code>. 发货方式：1邮寄 2面交 等等
     */
    public Byte getDeliveryType() {
        return (Byte) get(13);
    }

    /**
     * Setter for <code>etrade_goods.goods.post_type</code>. 邮寄方式：1包邮 2按距离算运费 等等
     */
    public void setPostType(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.post_type</code>. 邮寄方式：1包邮 2按距离算运费 等等
     */
    public String getPostType() {
        return (String) get(14);
    }

    /**
     * Setter for <code>etrade_goods.goods.state</code>. 商品状态：1草稿箱 2已删除 3已下架 4在售 5交易中 6交易完成 等等
     */
    public void setState(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.state</code>. 商品状态：1草稿箱 2已删除 3已下架 4在售 5交易中 6交易完成 等等
     */
    public String getState() {
        return (String) get(15);
    }

    /**
     * Setter for <code>etrade_goods.goods.country</code>. 商品发布国家
     */
    public void setCountry(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.country</code>. 商品发布国家
     */
    public String getCountry() {
        return (String) get(16);
    }

    /**
     * Setter for <code>etrade_goods.goods.province</code>. 商品发布省份/州
     */
    public void setProvince(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.province</code>. 商品发布省份/州
     */
    public String getProvince() {
        return (String) get(17);
    }

    /**
     * Setter for <code>etrade_goods.goods.city</code>. 商品发布城市
     */
    public void setCity(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.city</code>. 商品发布城市
     */
    public String getCity() {
        return (String) get(18);
    }

    /**
     * Setter for <code>etrade_goods.goods.district</code>. 商品发布区域/县
     */
    public void setDistrict(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.district</code>. 商品发布区域/县
     */
    public String getDistrict() {
        return (String) get(19);
    }

    /**
     * Setter for <code>etrade_goods.goods.launch_last_time</code>. 商品最新上架时间
     */
    public void setLaunchLastTime(LocalDateTime value) {
        set(20, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.launch_last_time</code>. 商品最新上架时间
     */
    public LocalDateTime getLaunchLastTime() {
        return (LocalDateTime) get(20);
    }

    /**
     * Setter for <code>etrade_goods.goods.is_free</code>. 是否免费
     */
    public void setIsFree(Byte value) {
        set(21, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.is_free</code>. 是否免费
     */
    public Byte getIsFree() {
        return (Byte) get(21);
    }

    /**
     * Setter for <code>etrade_goods.goods.created</code>.
     */
    public void setCreated(LocalDateTime value) {
        set(22, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.created</code>.
     */
    public LocalDateTime getCreated() {
        return (LocalDateTime) get(22);
    }

    /**
     * Setter for <code>etrade_goods.goods.modified</code>.
     */
    public void setModified(LocalDateTime value) {
        set(23, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.modified</code>.
     */
    public LocalDateTime getModified() {
        return (LocalDateTime) get(23);
    }

    /**
     * Setter for <code>etrade_goods.goods.ai_identify_no</code>. ai鉴定编号
     */
    public void setAiIdentifyNo(String value) {
        set(24, value);
    }

    /**
     * Getter for <code>etrade_goods.goods.ai_identify_no</code>. ai鉴定编号
     */
    public String getAiIdentifyNo() {
        return (String) get(24);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GoodsRecord
     */
    public GoodsRecord() {
        super(Goods.GOODS);
    }

    /**
     * Create a detached, initialised GoodsRecord
     */
    public GoodsRecord(Long id, String title, String description, BigDecimal originalPrice, BigDecimal realPrice, String moneyKind, Long launchUserId, String launchUserNickname, String quality, String classPath, String brand, String size, String unit, Byte deliveryType, String postType, String state, String country, String province, String city, String district, LocalDateTime launchLastTime, Byte isFree, LocalDateTime created, LocalDateTime modified, String aiIdentifyNo) {
        super(Goods.GOODS);

        set(0, id);
        set(1, title);
        set(2, description);
        set(3, originalPrice);
        set(4, realPrice);
        set(5, moneyKind);
        set(6, launchUserId);
        set(7, launchUserNickname);
        set(8, quality);
        set(9, classPath);
        set(10, brand);
        set(11, size);
        set(12, unit);
        set(13, deliveryType);
        set(14, postType);
        set(15, state);
        set(16, country);
        set(17, province);
        set(18, city);
        set(19, district);
        set(20, launchLastTime);
        set(21, isFree);
        set(22, created);
        set(23, modified);
        set(24, aiIdentifyNo);
    }
}
