package com.etrade.puggo.common.enums;

public enum MobileRegularExpEnum {

    /* 以上是项目可能设计到的市场，一下是其他国家的手机号校验正则，欢迎补充*/
    CN("中国", 86, "^(\\+?86\\-?)?1[345789]\\d{9}$"),
    US("美国", 1, "^(\\+?1)?[2-9]\\d{2}[2-9](?!11)\\d{6}$"),
    DZ("阿尔及利亚", 213, "^(\\+?213|0)(5|6|7)\\d{8}$"),
    TW("台湾", 886, "^(\\+?886\\-?|0)?9\\d{8}$"),
    HK("香港", 852, "^(\\+?852\\-?)?[569]\\d{3}\\-?\\d{4}$"),
    MS("马来西亚", 60, "^(\\+?0?60\\-?){1}(([145]{1}(\\-|\\s)?\\d{7,8})|([236789]{1}(\\s|\\-)?\\d{7}))$"),
    PH("菲律宾", 63, "^(\\+?0?63\\-?)?\\d{10}$"),
    TH("泰国", 66, "^(\\+?0?66\\-?)?\\d{10}$"),
    SG("新加坡", 65, "^(\\+?0?65\\-?)?\\d{10}$"),
    SY("叙利亚", 963, "^(!?(\\+?963)|0)?9\\d{8}$"),
    SA("沙特阿拉伯", 966, "^(!?(\\+?966)|0)?5\\d{8}$"),
    CZ("捷克共和国", 420, "^(\\+?420)? ?[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$"),
    DE("德国", 49,
        "^(\\+?49[ \\.\\-])?([\\(]{1}[0-9]{1,6}[\\)])?([0-9 \\.\\-\\/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$"),
    DK("丹麦", 45, "^(\\+?45)?(\\d{8})$"),
    GR("希腊", 30, "^(\\+?30)?(69\\d{8})$"),
    AU("澳大利亚", 61, "^(\\+?61|0)4\\d{8}$"),
    GB("英国", 44, "^(\\+?44|0)7\\d{9}$"),
    CA("加拿大", 1, "^(\\+?1)?[2-9]\\d{2}[2-9](?!11)\\d{6}$"),
    IN("印度", 91, "^(\\+?91|0)?[789]\\d{9}$"),
    NZ("新西兰", 64, "^(\\+?64|0)2\\d{7,9}$"),
    ZA("南非", 27, "^(\\+?27|0)\\d{9}$"),
    ZM("赞比亚", 26, "^(\\+?26)?09[567]\\d{7}$"),
    ES("西班牙", 34, "^(\\+?34)?(6\\d{1}|7[1234])\\d{7}$"),
    FI("芬兰", 358, "^(\\+?358|0)\\s?(4(0|1|2|4|5)?|50)\\s?(\\d\\s?){4,8}\\d$"),
    FR("法国", 33, "^(\\+?33|0)[67]\\d{8}$"),
    IL("以色列", 972, "^(\\+972|0)([23489]|5[0248]|77)[1-9]\\d{6}"),
    HU("匈牙利", 36, "^(\\+?36)(20|30|70)\\d{7}$"),
    IT("意大利", 39, "^(\\+?39)?\\s?3\\d{2} ?\\d{6,7}$"),
    JP("日本", 81, "^(\\+?81|0)\\d{1,4}[ \\-]?\\d{1,4}[ \\-]?\\d{4}$"),
    NO("挪威", 47, "^(\\+?47)?[49]\\d{7}$"),
    BE("比利时", 32, "^(\\+?32|0)4?\\d{8}$"),
    PL("波兰", 48, "^(\\+?48)? ?[5-8]\\d ?\\d{3} ?\\d{2} ?\\d{2}$"),
    BR("巴西", 55, "^(\\+?55|0)\\-?[1-9]{2}\\-?[2-9]{1}\\d{3,4}\\-?\\d{4}$"),
    PT("葡萄牙", 351, "^(\\+?351)?9[1236]\\d{7}$"),
    RU("俄罗斯", 7, "^(\\+?7|8)?9\\d{9}$"),
    RS("塞尔维亚", 3816, "^(\\+3816|06)[- \\d]{5,9}$"),
    R("土耳其", 90, "^(\\+?90|0)?5\\d{9}$"),
    VN("越南", 84, "^(\\+?84|0)?((1(2([0-9])|6([2-9])|88|99))|(9((?!5)[0-9])))([0-9]{7})$");

    /**
     * 国际名称
     */
    private String national;
    /**
     * 国际电话区号编码
     */
    private Integer nationalCode;
    /**
     * 正则表达式
     */
    private String regularExp;


    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getRegularExp() {
        return regularExp;
    }

    public void setRegularExp(String regularExp) {
        this.regularExp = regularExp;
    }

    public int getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(int code) {
        this.nationalCode = code;
    }

    MobileRegularExpEnum(String national, int code, String regularExp) {
        this.national = national;
        this.regularExp = regularExp;
        this.nationalCode = code;
    }

    public static String getRegularExp(String national) {
        for (MobileRegularExpEnum exp : values()) {
            if (exp.national.equals(national)) {
                return exp.regularExp;
            }
        }
        return CN.regularExp;
    }

    public static String getRegularExp(int code) {
        for (MobileRegularExpEnum exp : values()) {
            if (exp.nationalCode.equals(code)) {
                return exp.regularExp;
            }
        }
        return CN.regularExp;
    }
}
