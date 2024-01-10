package com.etrade.puggo.service.groupon.clazz;

import com.etrade.puggo.service.groupon.dto.S3Picture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : TODO
 * @date 2023/6/9 13:25
 **/
@Data
public class EditClassParam {

    @NotNull
    private Integer id;

    @NotNull(message = "分类名称不能为空")
    @NotBlank(message = "分类名称不能为空")
    private String className;

    @NotNull
    @Min(value = 1, message = "分类等级 不能小于1")
    @Max(value = 2, message = "分类等级 不能大于2")
    private Byte classLevel;

    @NotNull(message = "父级分类不能为空")
    private Integer parentClass;

    @Valid
    private S3Picture icon;

    @JsonIgnore
    private Long iconId;

    @JsonIgnore
    private String classPath;
}
