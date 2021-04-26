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
public class ToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolsApplication.class, args);
//        ZJPFileMonitor m=null;
//        try{
//            m=new ZJPFileMonitor(5000);
//        }catch (Exception e){
//            System.out.println("出错!");
//        }
//        m.monitor("C:\\Users\\12733\\Desktop\\HotBoot",new ZJPFileListener());
//        try{
//            m.start();
//        }catch (Exception e){
//            System.out.println("启动错误!");
//        }
    }

}