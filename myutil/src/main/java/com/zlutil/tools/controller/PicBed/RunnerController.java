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
            "6083e6275b5bca563eadef59>>>D:\\picBed\\fast\\6083e6275b5bca563eadef59.jpg\n" +
                    "6083e6275b5bca563eadef61>>>D:\\picBed\\fast\\6083e6275b5bca563eadef61.jpg\n" +
                    "6083e6275b5bca563eadef67>>>D:\\picBed\\fast\\6083e6275b5bca563eadef67.jpg\n" +
                    "6083e6275b5bca563eadef69>>>D:\\picBed\\fast\\6083e6275b5bca563eadef69.jpg\n" +
                    "6083e6275b5bca563eadef75>>>D:\\picBed\\fast\\6083e6275b5bca563eadef75.jpg\n" +
                    "6083e6275b5bca563eadef7b>>>D:\\picBed\\fast\\6083e6275b5bca563eadef7b.jpg\n" +
                    "6083e6275b5bca563eadef7e>>>D:\\picBed\\fast\\6083e6275b5bca563eadef7e.jpg\n" +
                    "6083e6275b5bca563eadef87>>>D:\\picBed\\fast\\6083e6275b5bca563eadef87.jpg\n" +
                    "6083e6275b5bca563eadef8c>>>D:\\picBed\\fast\\6083e6275b5bca563eadef8c.jpg\n" +
                    "6083e6285b5bca563eadef91>>>D:\\picBed\\fast\\6083e6285b5bca563eadef91.jpg\n" +
                    "6083e6285b5bca563eadef97>>>D:\\picBed\\fast\\6083e6285b5bca563eadef97.jpg\n" +
                    "6083e6285b5bca563eadef9a>>>D:\\picBed\\fast\\6083e6285b5bca563eadef9a.jpg\n" +
                    "6083e6285b5bca563eadef9f>>>D:\\picBed\\fast\\6083e6285b5bca563eadef9f.jpg\n" +
                    "6083e6285b5bca563eadefa7>>>D:\\picBed\\fast\\6083e6285b5bca563eadefa7.jpg\n" +
                    "6083e6285b5bca563eadefae>>>D:\\picBed\\fast\\6083e6285b5bca563eadefae.jpg\n" +
                    "6083e6285b5bca563eadefb4>>>D:\\picBed\\fast\\6083e6285b5bca563eadefb4.jpg\n" +
                    "6083e6285b5bca563eadefc3>>>D:\\picBed\\fast\\6083e6285b5bca563eadefc3.jpg\n" +
                    "6083e6285b5bca563eadefcb>>>D:\\picBed\\fast\\6083e6285b5bca563eadefcb.jpg\n" +
                    "6083e6295b5bca563eadefd5>>>D:\\picBed\\fast\\6083e6295b5bca563eadefd5.jpg\n" +
                    "6083e6295b5bca563eadefd8>>>D:\\picBed\\fast\\6083e6295b5bca563eadefd8.jpg\n" +
                    "6083e6295b5bca563eadefe0>>>D:\\picBed\\fast\\6083e6295b5bca563eadefe0.jpg\n" +
                    "6083e6295b5bca563eadefe8>>>D:\\picBed\\fast\\6083e6295b5bca563eadefe8.jpg\n" +
                    "6083e6295b5bca563eadefec>>>D:\\picBed\\fast\\6083e6295b5bca563eadefec.jpg\n" +
                    "6083e6295b5bca563eadeff3>>>D:\\picBed\\fast\\6083e6295b5bca563eadeff3.jpg\n" +
                    "6083e6295b5bca563eadeff8>>>D:\\picBed\\fast\\6083e6295b5bca563eadeff8.jpg";

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
    public List<String> getData(@RequestParam("picIds") String picIds) throws IOException {

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
    public List<String> getDataFast(@RequestParam("picIds") String picIds) throws IOException {

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
