package com.etrade.puggo.common.enums;


public enum MoneyKindEnum {

    /**
     * USD 美元 EUR 欧元 JPY 日元 GBP 英镑 AUD 澳元 CHF 瑞士法郎 CAD 加元 NZD 纽元 CNY 人民币
     */
    USD("USD", "$", "美元"),
    EUR("EUR", "€", "欧元"),
    JPY("JPY", "￥", "日元"),
    GBP("GBP", "£", "英镑"),
    AUD("AUD", "A$", "澳元"),
    CHF("CHF", "CHF", "瑞士法郎"),
    CAD("CAD", "Cad$", "加元"),
    NZD("NZD", "NZ$", "纽元"),
    CNY("CNY", "￥", "人民币"),
    ;


    private String code;
    private String symbol;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    MoneyKindEnum(String code, String symbol, String desc) {
        this.code = code;
        this.symbol = symbol;
        this.desc = desc;
    }

    public static String switchSymbol(String code) {
        for (MoneyKindEnum value : values()) {
            if (value.code.equals(code)) {
                return value.symbol;
            }
        }
        return "";
    }

    public static String switchCode(String symbol) {
        for (MoneyKindEnum value : values()) {
            if (value.symbol.equals(symbol)) {
                return value.code;
            }
        }
        return "";
    }

}
