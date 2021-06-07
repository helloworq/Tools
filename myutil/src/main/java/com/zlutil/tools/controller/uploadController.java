package com.zlutil.tools.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class uploadController {

    @Autowired
    MongoTemplate mongoTemplate;

    @Bean
    public HelloService helloService() {
        return new HelloService();
    }

    @Autowired
    HelloService helloService;

    @GetMapping("hello-service")
    public String invoke() {
        return this.helloService.hello();
    }

    @PostMapping("/test/upload")
    public void testUpload(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
    }

}
