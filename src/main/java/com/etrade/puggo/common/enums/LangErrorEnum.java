package com.etrade.puggo.common.enums;

import com.etrade.puggo.common.constants.LangConstant;
import com.etrade.puggo.common.filter.AuthContext;

public enum LangErrorEnum {

    GLOBAL_ERROR("对不起，系统繁忙，请您稍后再试", "Sorry, the system is busying..."),
    OPERATE_SUCCESSFULLY("操作成功", "Operate Successfully"),

    /**
     * 商品业务类错误
     */
    GOODS_CLASS("请选择商品分类", "Please select a category"),
    GOODS_QUALITY("请选择商品成色", "Please select a item condition"),
    EMPTY_DESC("商品描述信息不允许为空", "Description cannot be empty"),
    GOODS_PRICE("价格不允许为0", "The price cannot be Zero"),
    GOODS_IS_RESERVED("商品已经预售，不可以购买", "Sorry, The product is already reserved"),

    /**
     * 用户消息类
     */
    MSG_PUBLISH("%s，您的商品发布成功", "%s, Your product launch successfully"),
    MSG_NEW("您有一条消息待查看", "You have a new message"),
    MSG_LIKE("%s 钟意您的商品", "%s favorite your product"),

    /**
     * AI鉴定
     */
    RESULT_IS_TRUE("商品鉴定结果为真", "The authentication result is true"),
    UNKNOWN_POINT("未知的鉴定点", "Unknown authentication point"),
    UNKNOWN_KIND("未知的AI鉴定品类", "Unknown AI Category"),
    RE_UPLOAD("未检测到鉴别点，请您选择合适的角度重新上传图片", "Please choose an appropriate angle to upload again"),
    MUST_POINT("请您上传必须的鉴定点", "Please upload the crucial authentication points"),
    UNKNOWN_AUTHENTICATION_CODE("AI鉴定编号不存在或者已绑定其他商品", "Unknown Authentication Code"),
    AI_INSUFFICIENT_AVAILABLE_TIMES("AI鉴定可用次数不足，请及时充值", "Insufficient available times, please recharge in a timely manner"),
    AI_INSUFFICIENT_AVAILABLE_TIMES2("不可超过系统所有的可用次数", "Cannot exceed the remaining available times of the system"),

    /**
     * 支付
     */
    INVALID_AMOUNT("无效金额", "Invalid amount"),
    INVALID_GOODS("无效商品", "Invalid product"),
    INVALID_CARD_NUMBER("无效的卡号", "Invalid card number"),
    INVALID_EXPIRE_DATE("无效的失效日期", "Invalid expire date"),
    INVALID_PHONE_NUMBER("无效的电话号码", "Invalid phone number"),
    INVALID_DELIVERY_ADDRESS("无效的地址", "Invalid delivery address"),
    INVALID_BILLING_ADDRESS("无效的地址", "Invalid billing address"),
    INVALID_CARD_TYPE("未知的银行卡类型", "Invalid card type"),
    INVALID_ADDRESS_TYPE("未知的地址类型", "Invalid address type"),
    INVALID_PAYMENT_TYPE("未知的支付类型", "Invalid payment type"),
    INVALID_SHIPPING_METHOD("未知的交易方式", "Invalid shipping method"),
    INVALID_PRODUCT_PRICE("商品成交价格异常", "Invalid product price"),
    INVALID_TARGET("未知的支付目的", "Invalid pay target"),
    PAYMENT_FAILED("支付失败，请您联系客服解决", "Payment failed，please contact the administrator"),
    INVALID_COMMENT_TYPE("未知的评论类型", "Invalid review type"),
    INVALID_TRADE("未知的待支付订单", "Invalid payment pending trade"),
    INVALID_PAYMENT_CUSTOMER("支付账号异常，请您退出重新登录", "The payment account is abnormal. Please log out and log in again."),
    INVALID_PAYMENT_SELLER("未知的卖家支付账号", "Invalid seller payment account"),
    INVALID_CARD("无效卡号", "Invalid card info"),

    ;

    final String zh_cn;

    final String en_us;

    LangErrorEnum(String zhCnString, String enUsString) {
        this.zh_cn = zhCnString;
        this.en_us = enUsString;
    }


    public String lang(String lang) {
        switch (lang) {
            case LangConstant.ZH_CH:
                return this.zh_cn;
            case LangConstant.EN_US:
            default:
                return this.en_us;
        }
    }

    public String lang() {
        String lang = AuthContext.getLang();
        return this.lang(lang);
    }

}
