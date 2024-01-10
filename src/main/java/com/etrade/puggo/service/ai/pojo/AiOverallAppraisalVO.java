package com.etrade.puggo.service.ai.pojo;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : AI整包鉴定结果
 * @date 2023/9/10 19:27
 **/
@Data
public class AiOverallAppraisalVO {

    private Integer id;

    private Long userId;

    private String operationId;

    private Integer kindId;

    private Integer brandId;

    private Integer seriesId;

    private String oaid;

    private Byte genuine;

    private BigDecimal grade;

    private String description;

    private String reportUrl;

    private String mappingType;

    private String mappingResult;

}
