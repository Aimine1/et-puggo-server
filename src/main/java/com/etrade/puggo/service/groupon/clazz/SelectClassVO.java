package com.etrade.puggo.service.groupon.clazz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 分类筛选器
 * @date 2023/6/9 15:30
 **/
@Data
public class SelectClassVO {

    private String label;

    private Integer value;

    private List<SelectClassVO> children;

    @JsonIgnore
    private String classPath;

    @JsonIgnore
    private Byte classLevel;

}
