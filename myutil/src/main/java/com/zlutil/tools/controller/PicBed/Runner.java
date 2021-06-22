package com.zlutil.tools.controller.PicBed;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.zlutil.tools.Share.Img2UrlSpeedUp;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 往图库存图片暂定两种侵入最小的方式
 * 1.指定mongo库位置，每隔指定的一断时间扫描库文件
 * 更新维护的图片信息表
 * 2.增加一个注解，存储到Mongo时的注解，捕获返回的图片id，然后通过此id去mongo里下载
 * <p>
 * 性能的关键在于图片信息表的实现
 */
@Slf4j
@Service
public class Runner {

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    public final static String savePath = "D:\\picBed\\fast\\";

    public final static String downloadPath = "D:\\picBed\\normal\\";


    public static ConcurrentHashMap<String, String> picMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        File file = new File("C:\\Users\\12733\\Desktop\\a\\1.jpg");
        System.out.println(file.getPath());
    }

    public String uploadFile(MultipartFile file) {
        String path = null;
        try {
            //向Girdfs存储文件
            ObjectId objectId = gridFsTemplate.store(file.getInputStream(), UUID.randomUUID().toString().replace("-", ""));
            path = objectId.toString();
            log.info(path);
            return path;
        } catch (Exception e) {
            System.out.println("文件读取失败");
            e.printStackTrace();
        }
        log.info(path);
        return path;
    }

    public List<String> createFileAndRefict(List<String> picIds) {

        return picIds.stream().map(picId -> {
            //向Girdfs下载文件
            File fileDownload = FileUtil.createTempFile("hut", ".jpg", new File(downloadPath), true);
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(fileDownload);
                gridFSBucket.downloadToStream(new ObjectId(picId), outputStream);

                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String path = fileDownload.getPath();
            return "http://localhost:8888/upload/" + path.substring(path.lastIndexOf("\\") + 1);
        }).collect(Collectors.toList());

    }

    @Img2UrlSpeedUp
    public List<String> createFileAndRefictFast(List<String> picIds) {

        return picIds.stream().map(picId -> {
            //向Girdfs下载文件
            File fileDownload = FileUtil.createTempFile("hut", ".jpg", new File(downloadPath), true);
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(fileDownload);
                gridFSBucket.downloadToStream(new ObjectId(picId), outputStream);

                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String path = fileDownload.getPath();
            return "http://localhost:8888/upload/" + path.substring(path.lastIndexOf("\\") + 1);
        }).collect(Collectors.toList());
    }

    public String picId2Url(String picId) {
        return picMap.get(picId);
    }

    /**
     * 更新图片信息表
     */
    public String viewPicMap() {
        return JSON.toJSONString(picMap);
    }
}
