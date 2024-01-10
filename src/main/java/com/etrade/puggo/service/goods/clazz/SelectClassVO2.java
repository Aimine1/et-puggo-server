package com.etrade.puggo.service.goods.clazz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 分类筛选器
 * @date 2023/6/9 15:30
 **/
@Data
public class SelectClassVO2 {

    private String label;

    private String value;

    private List<SelectClassVO2> children;

    @JsonIgnore
    private Byte classLevel;

}
