package com.zlutil.tools.controller.PicBed;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/rest/annotation")
public class RunnerController {

    static String txt =
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" +
            "6083f2d749216a4458286b11>>>D:\\picBed\\fast\\6083f2d749216a4458286b11.jpg\n" ;

    public static void main(String[] args) {
        List<String> list = Arrays.stream(txt.replace("\n", "").split("jpg"))
                .map(e -> e.substring(0, e.indexOf(">>>")))
                .collect(Collectors.toList());
        System.out.println(StringUtils.join(list, ","));
    }

    @Autowired
    Runner runner;

    @PostMapping(value = "/upload/File")
    public String postData(@RequestParam("file") MultipartFile[] file) throws IOException {
        //picId转Url
        Arrays.stream(file).forEach(e -> runner.uploadFile(e));
        return "success";
    }

    @GetMapping(value = "/getData")
    public List<String> getData() throws IOException {

        List<String> listText = Arrays.stream(txt.replace("\n", "").split("jpg"))
                .map(e -> e.substring(0, e.indexOf(">>>")))
                .collect(Collectors.toList());
        StringUtils.join(listText, ",");

        //picId转Url
        long start = System.currentTimeMillis();
        //List<String> list = runner.createFileAndRefict(new ArrayList<>(Arrays.asList(picIds.split(","))));
        List<String> list = runner.createFileAndRefict(listText);
        System.out.println("普通方式处理时间: " + (System.currentTimeMillis() - start));
        return list;
    }

    @GetMapping(value = "/getData/Fast")
    public List<String> getDataFast() throws IOException {

        List<String> listText = Arrays.stream(txt.replace("\n", "").split("jpg"))
                .map(e -> e.substring(0, e.indexOf(">>>")))
                .collect(Collectors.toList());
        StringUtils.join(listText, ",");

        //picId转Url
        long start = System.currentTimeMillis();
        //List<String> list = runner.createFileAndRefictFast(new ArrayList<>(Arrays.asList(picIds.split(","))));
        List<String> list = runner.createFileAndRefictFast(listText);
        System.out.println("特殊方式处理时间: " + (System.currentTimeMillis() - start));
        return list;
    }

    @GetMapping(value = "/pic2Url")
    public String pic2Url(@RequestParam("picId") String picId) {
        //picId转Url
        return runner.picId2Url(picId);
    }

    @GetMapping(value = "/viewPicMap")
    public String viewPicMap(ApplicationContext ctx) {
        //picId转Url
        return ctx.getBeanDefinitionCount() + "";
    }

}
