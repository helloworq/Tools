package com.zlutil.tools.controller;


import com.alibaba.fastjson.JSON;
import com.zlutil.tools.toolpackage.Feign.ImsRestConfig;
import com.zlutil.tools.toolpackage.JavaBasic.MyIO.MyIOUtil;
import com.zlutil.tools.toolpackage.ResponseUtil.ResponseData;
import com.zlutil.tools.toolpackage.ResponseUtil.ResponseUtil;
import com.zlutil.tools.toolpackage.aop.Cut;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Objects;

@Slf4j
@RestController
public class GetData {
    @Autowired
    ImsRestConfig imsRestConfig;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    String uploadPath = "E:\\【搜狐】孤独的美食家1-4季\\cc\\";

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

//    @ApiOperation(value = "更新/上传动态")
//    @PostMapping(value = "/fileUploader")
//    public ResponseData fileUploader(@RequestParam(value = "file") MultipartFile file,
//                                     HttpServletRequest request) throws IOException {
//        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\12733\\Desktop\\a"));
//        MyIOUtil.inputStreamWriteToOutputStream(file.getInputStream(), fileOutputStream);
//        return ResponseUtil.success("success");
//    }

    //处理上传的文件
    @RequestMapping("/upload")
    public void upload(@RequestParam("fileMd5") @ApiParam(value = "文件MD5值", required = true) String fileMd5,
                       @RequestParam("file") @ApiParam(value = "文件", required = true) MultipartFile file,
                       @RequestParam("fileSize") @ApiParam(value = "文件大小", required = true) String fileSize,
                       @RequestParam("position") @ApiParam(value = "文件位置", required = true) String position,
                       @RequestParam("fileName") @ApiParam(value = "文件名", required = true) String fileName) throws IOException {
        System.out.println("文件MD5: " + fileMd5);

        FileInfo fileInfo = JSON.parseObject(JSON.toJSONString(redisTemplate.opsForValue().get(fileMd5)), FileInfo.class);

        if (Objects.nonNull(fileInfo)) {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileInfo.getFilePath(), "rw");
            if (randomAccessFile.length() == Long.parseLong(fileSize)) {
                randomAccessFile.close();//文件已完整存储
                return;
            } else {
                randomAccessFile.seek(Long.parseLong(position));
                randomAccessFile.write(file.getBytes());
                randomAccessFile.close();
                fileInfo.setFileSize(fileInfo.getFileSize() + file.getBytes().length);
                redisTemplate.opsForValue().set(fileMd5, fileInfo);
            }
        } else {
            //File filePrev = File.createTempFile(fileMd5, MyIOUtil.getFileSuffix(fileName), new File(uploadPath));
            File filePrev = new File(uploadPath + fileMd5 + "." + MyIOUtil.getFileSuffix(fileName));
            if (!filePrev.exists()) {
                filePrev.createNewFile();
            }
            System.out.println(filePrev.getPath());
            //File filePrev = FileUtil.createTempFile(fileMd5, MyIOUtil.getFileSuffix(fileName), new File(uploadPath), false);
            RandomAccessFile randomAccessFile = new RandomAccessFile(filePrev.getPath(), "rw");

            FileInfo fileInfoPrev = new FileInfo();
            fileInfoPrev.setFilePath(filePrev.getPath());//文件存储路径
            fileInfoPrev.setFileName(fileName);//文件原始名
            fileInfoPrev.setFileSize(0L);//文件原始名
            redisTemplate.opsForValue().set(fileMd5, fileInfoPrev);

            randomAccessFile.seek(Long.parseLong(position));
            randomAccessFile.write(file.getBytes());
            randomAccessFile.close();
        }
    }

    @RequestMapping("/getSize")
    public String getSize(@RequestParam("fileMd5") String fileMd5) throws IOException {
        System.out.println("请求文件MD5: " + fileMd5);
        FileInfo fileInfo = JSON.parseObject(JSON.toJSONString(redisTemplate.opsForValue().get(fileMd5)), FileInfo.class);
        return fileInfo == null ? "0" : String.valueOf(fileInfo.getFileSize());
    }

}
