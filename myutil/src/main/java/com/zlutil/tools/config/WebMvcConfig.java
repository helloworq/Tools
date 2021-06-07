package com.zlutil.tools.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置资源映射路径，使得可以生成可供外部访问的链接，这样无须配置各类文件数据的返回值和返回方式
 */
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value("${self.shareFolder}")
    private String shareFolder;

    /**
     * Tomcat配置，修改资源映射路径
     * 当访问ip:port/upload/filename的时候将从指定路径查找文件
     * 例如本项目未部署的时候访问此路径http://localhost:6729/upload/normal.mp4
     * 将在C:\Users\12733\Pictures\Saved Pictures\此文件路径下寻找指定文件normal.mp4
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:D:\\picBed\\Sync\\Pic\\");
    }
}
