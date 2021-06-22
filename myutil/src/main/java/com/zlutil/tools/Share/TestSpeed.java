package com.zlutil.tools.Share;

import com.zlutil.tools.toolpackage.SycFile.PicUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/test/speed/")
public class TestSpeed {

    @Autowired
    PicUtil picUtil;

    @Autowired
    MyMongoDownload myMongoDownload;

    private static final String PIC_FILE_PATH = "D:\\picBed\\Sync\\Pic\\";

    //private static final String PIC_SYNC_PATH = "D:\\picBed\\Sync\\PicOrigin\\";

    @GetMapping("/first")
    public void first() throws IOException {
        String id = "6083f2d749216a4458286b11";
        long start1 = System.currentTimeMillis();
        myMongoDownload.downloadWithMongo(id);
        System.out.println("Mongo单文件下载及映射时间: " + (System.currentTimeMillis() - start1));

        List<String> list = Files.readAllLines(Paths.get("C:\\Users\\12733\\Desktop\\s.txt"));

        long start2 = System.currentTimeMillis();
        list.forEach(e -> picUtil.getPicRelativePath(e));
        System.out.println("图片工具映射时间: " + (System.currentTimeMillis() - start2));

        long start3 = System.currentTimeMillis();
        list.forEach(e -> picUtil.getPicRelativePathWithoutCache(e));
        System.out.println("无缓存图片工具映射时间: " + (System.currentTimeMillis() - start3));

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @PostMapping("second")
    public void second(@RequestParam("file") MultipartFile file) throws IOException {
        picUtil.save(file);
    }

    @ApiOperation("同步文件")
    @GetMapping("sync")
    public void secondArray(@RequestParam("remoteAddress") String addres) throws IOException, URISyntaxException {
        //"http://127.0.0.1:8081/pic/speed"
        picUtil.syncRemoteFile("http://127.0.0.1:8081/pic/speed");
    }

}
