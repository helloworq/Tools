package com.zlutil.tools.toolpackage.Feign;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-08-03 17:40
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：
 */
@Data
@Component
@ConfigurationProperties(prefix = "ims")
public class ImsRestConfig {
    private IndexValueQuery indexValueQuery = new IndexValueQuery();
}
