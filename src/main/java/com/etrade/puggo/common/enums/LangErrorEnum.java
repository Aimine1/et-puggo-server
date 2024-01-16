package com.etrade.puggo.common.enums;

import com.etrade.puggo.constants.LangConstant;
import com.etrade.puggo.filter.AuthContext;

public enum LangErrorEnum {

    GLOBAL_ERROR("对不起，系统繁忙，请您稍后再试", "Sorry, the system is busying..."),
    OPERATE_SUCCESSFULLY("操作成功", " Operate Successfully"),

    /**
     * 商品业务类错误
     */
    UNKNOWN_POINT("未知的鉴定点", "Unknown authentication point"),
    RE_UPLOAD("未检测到鉴别点，请您选择合适的角度重新上传图片", "Please choose an appropriate angle to upload again"),
    MUST_POINT("请您上传必须的鉴定点", "Please upload the crucial authentication points"),
    GOODS_CLASS("请选择商品分类", "Please select a category"),
    GOODS_QUALITY("请选择商品成色", "Please select a item condition"),
    EMPTY_DESC("商品描述信息不允许为空", "Description cannot be empty"),
    UNKNOWN_AUTHENTICATION_CODE("AI鉴定编号不存在或者已绑定其他商品", "Unknown Authentication Code"),
    GOODS_PRICE("价格不允许为0", "The price cannot be Zero"),

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
