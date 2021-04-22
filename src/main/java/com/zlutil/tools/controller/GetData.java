package com.zlutil.tools.controller;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.zlutil.tools.toolpackage.Feign.ImsRestConfig;
import com.zlutil.tools.toolpackage.JavaBasic.MyIO.MyIOUtil;
import com.zlutil.tools.toolpackage.JavaBasic.NetTools.DownLoad_My_Configs;
import com.zlutil.tools.toolpackage.ResponseUtil.ResponseData;
import com.zlutil.tools.toolpackage.ResponseUtil.ResponseUtil;
import com.zlutil.tools.toolpackage.ZipReader.FileEntity;
import com.zlutil.tools.toolpackage.aop.StrogeService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
public class GetData {
    @Autowired
    ImsRestConfig imsRestConfig;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    StrogeService strogeService;

    public final static String basePath = "C:\\可视化资源文件列表\\压缩包\\";
    public final static String unzipPath = "C:\\可视化资源文件列表\\解压目录\\";

    public long count = 0;

    String uploadPath = "E:\\【搜狐】孤独的美食家1-4季\\cc\\";

    @Value("${spring.datasource.url}")
    public String data;

    @Value("${self.shareFolder}")
    private String shareFolder;

    @Value("${self.enable}")
    private boolean open = false;

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

    @GetMapping(value = "/getData", consumes = MediaType.ALL_VALUE)
    public List<String> getData(@RequestParam("id") String picIds) throws IOException {
        //picId转Url
        return strogeService.createUrlByPicId(new ArrayList<>(Arrays.asList(picIds.split(","))));
    }

    FileEntity readZipInfo(String filePath) {

        String id = UUID.randomUUID().toString().replace("-", "");
        FileEntity fileEntityParent = new FileEntity();
        fileEntityParent.setId(id);
        fileEntityParent.setFilePath(removePrefix(filePath));
        fileEntityParent.setFileName(filePath);
        fileEntityParent.setParentCode(null);
        List<FileEntity> listRes = new ArrayList<>();
        fileEntityParent.setChild(listRes);

        readFile(filePath, fileEntityParent, id);

        return fileEntityParent;
    }

    void readFile(String filePath, FileEntity fileEntityParent, String parentCode) {

        File[] file = new File(filePath).listFiles();

        for (File filePrev : file) {
            if (filePrev.isFile()) {
                FileEntity fileEntityChild = new FileEntity();
                fileEntityChild.setId(UUID.randomUUID().toString().replace("-", ""));
                fileEntityChild.setFilePath(removePrefix(filePrev.getPath()));
                fileEntityChild.setFileName(filePrev.getName());
                fileEntityChild.setParentCode(parentCode);

                fileEntityParent.getChild().add(fileEntityChild);
            } else if (filePrev.isDirectory()) {
                String id = UUID.randomUUID().toString().replace("-", "");
                FileEntity fileEntityChild = new FileEntity();
                fileEntityChild.setId(id);
                fileEntityChild.setFilePath(removePrefix(filePrev.getPath()));
                fileEntityChild.setFileName(filePrev.getName());
                fileEntityChild.setParentCode(parentCode);
                List<FileEntity> listPrev = new ArrayList<>();
                fileEntityChild.setChild(listPrev);

                fileEntityParent.getChild().add(fileEntityChild);
                readFile(filePrev.getPath(), fileEntityChild, id);
            }
        }
    }

    public String getFilename(String filename) {
        return filename.substring(0, filename.indexOf(".") + 1);
    }

    public String removePrefix(String filename) {
        return filename.replace(unzipPath, "");
    }

//    @ApiOperation(value = "更新/上传动态")
//    @PostMapping(value = "/fileUploader")
//    public ResponseData fileUploader(@RequestParam(value = "file") MultipartFile file,
//                                     HttpServletRequest request) throws IOException {
//        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\12733\\Desktop\\a"));
//        MyIOUtil.inputStreamWriteToOutputStream(file.getInputStream(), fileOutputStream);
//        return ResponseUtil.success("success");
//    }

    /**
     * 处理上传文件
     *
     * @param fileMd5
     * @param file
     * @param fileSize
     * @param position
     * @param fileName
     * @throws IOException
     */
    @PostMapping("/upload")
    public void upload(@RequestParam("fileMd5") @ApiParam(value = "文件MD5值", required = true) String fileMd5,
                       @RequestParam("file") @ApiParam(value = "文件", required = true) MultipartFile file,
                       @RequestParam("fileSize") @ApiParam(value = "文件大小", required = true) String fileSize,
                       @RequestParam("position") @ApiParam(value = "文件位置", required = true) String position,
                       @RequestParam("fileName") @ApiParam(value = "文件名", required = true) String fileName) throws IOException {

        FileInfo fileInfo = JSON.parseObject(JSON.toJSONString(redisTemplate.opsForValue().get(fileMd5)), FileInfo.class);

        if (Objects.nonNull(fileInfo)) {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileInfo.getFilePath(), "rw");
            if (randomAccessFile.length() == Long.parseLong(fileSize)) {
                randomAccessFile.close();//文件已完整存储
                //TODO 将文件信息持久化进数据库
                return;
            } else {
                randomAccessFile.seek(Long.parseLong(position));
                randomAccessFile.write(file.getBytes());
                randomAccessFile.close();
                fileInfo.setFileSize(fileInfo.getFileSize() + file.getSize());
                redisTemplate.opsForValue().set(fileMd5, fileInfo);
            }
        } else {
            File filePrev = new File(uploadPath + fileMd5 + "." + MyIOUtil.getFileSuffix(fileName));
            if (!filePrev.exists()) {
                filePrev.createNewFile();
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(filePrev.getPath(), "rw");

            randomAccessFile.seek(Long.parseLong(position));
            randomAccessFile.write(file.getBytes());
            randomAccessFile.close();

            FileInfo fileInfoPrev = new FileInfo();
            fileInfoPrev.setFilePath(filePrev.getPath());//文件存储路径
            fileInfoPrev.setFileName(fileName);//文件原始名
            fileInfoPrev.setFileSize(file.getSize());//文件大小
            redisTemplate.opsForValue().set(fileMd5, fileInfoPrev);
        }
    }

    /**
     * 依据传入的md5获取此文件在服务器上的大小
     *
     * @param fileMd5
     * @return
     */
    @PostMapping("/getSize")
    public String getSize(@RequestParam("fileMd5") @ApiParam(value = "文件MD5值", required = true) String fileMd5) {
        System.out.println("请求文件MD5: " + fileMd5);
        FileInfo fileInfo = JSON.parseObject(JSON.toJSONString(redisTemplate.opsForValue().get(fileMd5)), FileInfo.class);
        return fileInfo == null ? "0" : String.valueOf(fileInfo.getFileSize());
    }

    @PostMapping("/download")
    public byte[] download(@RequestParam("fileMd5") @ApiParam(value = "文件MD5值", required = true) String fileMd5,
                           @RequestParam("fileSize") @ApiParam(value = "已下载文件大小", required = true) String fileSize) throws IOException {
        System.out.println("请求文件MD5: " + fileMd5 + "  " + fileSize);
        int byteLength = 1024 * 1024;//1Mb
        FileInfo fileInfo = JSON.parseObject(JSON.toJSONString(redisTemplate.opsForValue().get(fileMd5)), FileInfo.class);
        String filePath = fileInfo.getFilePath();
        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
        randomAccessFile.seek(Long.parseLong(fileSize));

        byte[] bytes = new byte[(int) (Math.min((randomAccessFile.length() - Long.parseLong(fileSize)), byteLength))];
        randomAccessFile.read(bytes);

        randomAccessFile.close();
        return bytes;
    }


    /**
     * 处理下载(仅供客户端下载使用，网页端禁止调用)
     *
     * @param url
     * @param targetFilepath
     * @param threadNum
     * @return
     * @throws IOException
     */
    @GetMapping("/downloadFast")
    public void download(@RequestParam("url") String url,
                         @RequestParam("targetFilepath") String targetFilepath,
                         @RequestParam("threadNum") Integer threadNum) throws IOException {
        long contentLength = this.getContentLength(url);
        long splitSize = contentLength / threadNum;

        if (threadNum > contentLength) {
            return;//线程数大于文件则中断执行
        }

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < threadNum; i++) {
            long start = i == 0 ? i * splitSize : i * splitSize + 1;
            long end = i == (threadNum - 1) ? contentLength : ((i + 1) * splitSize);
            executorService.execute(() -> muiltDownloadRunner(url, targetFilepath, start, end, threadNum));
        }
    }

    public void muiltDownloadRunner(String url
            , String targetFilepath
            , long start
            , long end
            , long threadNum) {
        try {
            long startTime = System.currentTimeMillis();
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("User-Agent", DownLoad_My_Configs.httpGet_Header);
            httpGet.addHeader("Range", "bytes=" + start + "-" + end);
            HttpResponse response = httpClient.execute(httpGet);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            response.getEntity().writeTo(outputStream);//转换成输出流以便直接获取字节

            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(targetFilepath), "rw");
            randomAccessFile.seek(start);
            randomAccessFile.write(outputStream.toByteArray());

            ++count;
            if (count == threadNum) {
                System.out.println("远程文件异步下载耗时: " + (System.currentTimeMillis() - startTime) + "  当前线程数: " + threadNum);
            }

            outputStream.close();
            randomAccessFile.close();
            response.getEntity().getContent().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long getContentLength(String url) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);

        return Long.valueOf(Arrays.stream(response.getAllHeaders())
                .filter(d -> d.getName().equals("Content-Length"))
                .findFirst()
                .get()
                .getValue());
    }

    public void t() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    }
}
