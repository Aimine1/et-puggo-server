package com.etrade.puggo.common.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @author niuzhenyu
 * @description : 团购券的状态
 * @date 2023/5/24 21:36
 **/
public class GrouponState {

    public static final String DRAFT = "DRAFT";
    public static final String DELETED = "DELETED";
    public static final String PUBLISHED = "PUBLISHED";

    public static boolean isValid(String state) {
        List<String> c = Arrays.asList(DRAFT, DELETED, PUBLISHED);
        if (c.contains(state)) {
            return true;
        }
        return false;
    }

}
