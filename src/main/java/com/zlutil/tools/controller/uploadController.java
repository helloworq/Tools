package com.zlutil.tools.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class uploadController {
    @PostMapping("/test/upload")
    public void testUpload(@RequestParam("file") MultipartFile file){
        System.out.println(file.getOriginalFilename());
    }
}
