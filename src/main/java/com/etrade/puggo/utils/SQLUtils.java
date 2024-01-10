package com.etrade.puggo.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.impl.DSL;

/**
 * @Description SqlUtils
 * @Author zhengbaole
 * @Date 2020/11/12
 **/
public class SQLUtils {


    /**
     * like %ABC%
     */
    public static String surroundingLike(String val) {
        return "%" + clean(val) + "%";
    }

    /**
     * like %ABC
     */
    public static String prefixLike(String val) {
        return "%" + clean(val);
    }

    /**
     * like ABC%
     */
    public static String suffixLike(String val) {
        return clean(val) + "%";
    }

    /**
     * 转义 "%"、"_"
     */
    public static String clean(String val) {
        return
            val.replaceAll("%", "\\\\%")
                .replaceAll("_", "\\\\_");
    }

    /**
     * @author zhengbaole
     * @lastEditor zhengbaole
     * @createTime 2021/12/15 8:51 AM
     * @editTime 2021/12/15 8:51 AM
     */
    public static String idListString(List<Long> idList) {

        String idListString = idList.stream()
            .map(Object::toString).collect(Collectors.joining(", "));

        if (StringUtils.isBlank(idListString)) {
            return "-1";
        }

        return idListString;
    }

    /**
     * @author zhengbaole
     * @lastEditor zhengbaole
     * @createTime 2021/12/23 10:17 AM
     * @editTime 2021/12/23 10:17 AM
     */
    public static Condition or(Condition... conditions) {

        Condition result = DSL.falseCondition();

        for (Condition condition : conditions) {

            if (condition != null) {

                if (result == null) {
                    result = condition;
                } else {
                    result = result.or(condition);
                }
            }
        }

        return result;
    }

    /**
     * @author zhengbaole
     * @lastEditor zhengbaole
     * @createTime 2022/6/2 4:25 PM
     * @editTime 2022/6/2 4:25 PM
     */
    public static String conditionToString(Condition condition) {
        return StringUtils.replace(condition.toString(), "\"", "`");
    }
}
