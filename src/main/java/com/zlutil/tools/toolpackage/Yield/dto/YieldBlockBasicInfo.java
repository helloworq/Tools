package com.zlutil.tools.toolpackage.Yield.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "田块基本信息，供村级副田长填写巡查任务时拉取基本信息的时候使用")
public class YieldBlockBasicInfo {

    @ApiModelProperty(value = "关联图块id")
    private Long id;
    @ApiModelProperty(value = "村")
    private String village;
    @ApiModelProperty(value = "归属于哪个组(用户输入后存储)")
    private String yieldGroup;
    @ApiModelProperty(value = "村级副田长")
    private String viceFieldHead;
    @ApiModelProperty(value = "村级田长")
    private String FieldHead;
    @ApiModelProperty(value = "镇长")
    private String townHead;
    @ApiModelProperty(value = "县长")
    private String countyHead;
    @ApiModelProperty(value = "土地利用情况(好，中，差，其它)")
    private Integer utilization;
    @ApiModelProperty(value = "具体利用情况(好->水稻，旱粮，蔬菜||中->养虾，养鱼，果树||差->草皮，苗木，抛荒，建设占用||其它)")
    private String specificUtilization;
    @ApiModelProperty(value = "巡查地块 (地图空间参考json存储) 存储时由于可能有多个地块需要list转string")
    private String geometry;
    @ApiModelProperty(value = "地块面积")
    private String area;
    @ApiModelProperty(value = "气泡颜色 1红色/2绿色")
    private Integer bubbleColor;
}
