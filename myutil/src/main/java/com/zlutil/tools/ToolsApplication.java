package com.zlutil.tools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync
@EnableScheduling
@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = {"com.picserver.*","com.zlutil.tools.*"})//扫描图片服务注解
//@NacosPropertySource(dataId = "tool-config", autoRefreshed = true)
public class ToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolsApplication.class, args);
    }

}
