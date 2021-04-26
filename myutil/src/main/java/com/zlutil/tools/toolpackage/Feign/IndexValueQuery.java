package com.zlutil.tools.toolpackage.Feign;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-07-31 16:30
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：指标值
 */
@Data
@Api("索引值查询")
public class IndexValueQuery implements Serializable {
    @ApiModelProperty("指标名称")
    private String indicator;
    @ApiModelProperty("行政区编码")
    private List<String> region;
    @ApiModelProperty("体系名称")
    private String system;
    @ApiModelProperty("时间区间 格式：from/to。如2010/2011、2010/、/2011等")
    private String time;
    @ApiModelProperty("指标值类型/体系类型")
    private String type;
    @ApiModelProperty("版本名称")
    private String version;
}
