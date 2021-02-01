package com.zlutil.tools.toolpackage.Yield.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "巡查任务")
public class YieldPatrolDTO {

    @ApiModelProperty(value = "巡查任务id")
    private Long id;
    @ApiModelProperty(value = "镇")
    private String town;
    @ApiModelProperty(value = "村")
    private String county;
    @ApiModelProperty(value = "地块id")
    private String yieldId;
    @ApiModelProperty(value = "原利用情况")
    private String originalUtilization;
    @ApiModelProperty(value = "现利用情况")
    private String nowlUtilization;
    @ApiModelProperty(value = "巡查时间")
    private Date patrolTime;
    @ApiModelProperty(value = "巡查人")
    private String patrolMan;
    @ApiModelProperty(value = "从创建开始到处理前间隔的天数")
    private String periodTime;
}
