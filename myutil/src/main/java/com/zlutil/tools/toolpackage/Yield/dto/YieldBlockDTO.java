package com.zlutil.tools.toolpackage.Yield.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "田块")
public class YieldBlockDTO {

    @ApiModelProperty(value = "田块id")
    private Long id;
    @ApiModelProperty(value = "归属于哪个组(用户输入后存储)")
    private String yieldGroup;
    @ApiModelProperty(value = "土地利用情况(好，中，差，其它)")
    private Integer utilization;
    @ApiModelProperty(value = "具体利用情况(好->水稻，旱粮，蔬菜||中->养虾，养鱼，果树||差->草皮，苗木，抛荒，建设占用||其它)")
    private String specificUtilization;
    @ApiModelProperty(value = "巡查地块 (地图空间参考json存储) 存储时由于可能有多个地块需要list转string")
    private String geometry;
    @ApiModelProperty(value = "地块面积")
    private String area;
}
