package com.zlutil.tools.toolpackage.Yield.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "田长")
public class YieldHeadDTO {

    @ApiModelProperty(value = "田长id")
    private Long id;
    @ApiModelProperty(value = "县城")
    private String county;
    @ApiModelProperty(value = "镇")
    private String town;
    @ApiModelProperty(value = "村")
    private String village;
    @ApiModelProperty(value = "田长级别(县级，镇级，村级，村副级)")
    private String title;
    @ApiModelProperty(value = "田长名字")
    private String name;
    @ApiModelProperty(value = "上级领导")
    private String leader;
    @ApiModelProperty(value = "负责的巡查地块id (地图空间参考json存储) 存储时由于可能有多个地块需要list转string")
    private String yieldBlockId;
}
