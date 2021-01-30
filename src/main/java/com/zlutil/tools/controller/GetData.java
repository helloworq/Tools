package com.zlutil.tools.controller;

import com.alibaba.fastjson.JSON;
import com.zlutil.tools.toolpackage.Feign.ImsRestConfig;
import com.zlutil.tools.toolpackage.ResponseUtil.ResponseData;
import com.zlutil.tools.toolpackage.ResponseUtil.ResponseUtil;
import com.zlutil.tools.toolpackage.aop.Cut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.time.LocalDateTime;

@RestController
public class GetData {
    @Autowired
    ImsRestConfig imsRestConfig;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.datasource.url}")
    public String data;

    @Value("${self.shareFolder}")
    private String shareFolder;

    @GetMapping("/getFileListUnderShareFolder")
    public ResponseData getFileListUnderShareFolder() {
        File file = new File(shareFolder);
        System.out.println(JSON.toJSONString(file.list()));
        return ResponseUtil.success(file.list());
    }

    @GetMapping("/getIndexValueQuery")
    public ResponseData getIndexValueQuery() {
        System.out.println(imsRestConfig.getIndexValueQuery());
        return ResponseUtil.success(imsRestConfig.getIndexValueQuery());
    }

    @Cut
    @GetMapping("/getData")
    public void getData(String a) {
        System.out.println(data + a);
    }
}
