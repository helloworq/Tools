package com.zlutil.tools.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zlutil.tools.controller.PicBed.Runner;
import com.zlutil.tools.toolpackage.Feign.ImsRestConfig;
import com.zlutil.tools.toolpackage.JavaBasic.MyIO.MyIOUtil;
import com.zlutil.tools.toolpackage.JavaBasic.NetTools.DownLoad_My_Configs;
import com.zlutil.tools.toolpackage.ResponseUtil.ResponseData;
import com.zlutil.tools.toolpackage.ResponseUtil.ResponseUtil;
import com.zlutil.tools.toolpackage.SycFile.PicUtil;
import com.zlutil.tools.toolpackage.aop.StrogeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
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

    @Autowired
    Runner runner;

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

//    @NacosValue(value = "${es.host}", autoRefreshed = true)
//    private String useLocalCache;

    @Autowired
    MongoTemplate mongoTemplate;

    @ApiOperation(value = "批量保存日志原始")
    @PostMapping(value = "public/v1/log/batch/origin")
    public ResponseData SaveLog(@ApiParam(value = "日志参数说明请查看接口public/v1/log/parameter") @RequestBody Object logParams) throws Exception {

        JSONArray jsonArray = JSONObject.parseObject(JSON.toJSON(logParams).toString()).getJSONArray("logParams");
        List<Map<String, String>> logParamsResolve = JSON.toJavaObject(jsonArray, List.class);

        for (Map<String, String> logParam : logParamsResolve) {
            System.out.println(logParam);
            System.out.println(JSON.toJSONString(logParam));
        }
        return ResponseUtil.success(logParams);
    }

    @ApiOperation(value = "批量保存日志")
    @PostMapping(value = "public/v1/log/batch")
    public ResponseData SaveLog(@ApiParam(value = "日志参数说明请查看接口public/v1/log/parameter") @RequestParam("logParams") String logParams) throws Exception {

        String unescapeString = StringEscapeUtils.unescapeJava(logParams);
        JSONArray jsonArray = JSONObject.parseArray(unescapeString);
        List<Map<String, String>> logParamsResolve = JSON.toJavaObject(jsonArray, List.class);

        for (Map<String, String> logParam : logParamsResolve) {
            System.out.println(logParam);
            System.out.println(JSON.toJSONString(logParam));
        }
        return ResponseUtil.success(logParamsResolve);
    }

    @GetMapping("/createIndex")
    public void createIndex() {
        String collectionName = "threadSafeTest";

        Index index = new Index();
        index.on("filePath", Sort.Direction.ASC);
        //index.on("fileName", Sort.Direction.ASC);
        mongoTemplate.indexOps(collectionName).ensureIndex(index);
    }

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

    @PostMapping(value = "/uploadFile", consumes = MediaType.ALL_VALUE)
    public String getData(@RequestParam("file") MultipartFile file) throws IOException {
        //picId转Url
        runner.uploadFile(file);
        return "success";
    }

    @Autowired
    PicUtil picUtil;

    @GetMapping(value = "/pic/speed", consumes = MediaType.ALL_VALUE)
    public String speed() throws IOException {
        //picId转Url
        picUtil.getPicUrl();
        return "success";
    }

    @GetMapping(value = "/getData", consumes = MediaType.ALL_VALUE)
    public List<String> getData(@RequestParam("id") String picIds) throws IOException {
        //picId转Url
        return strogeService.createUrlByPicId(new ArrayList<>(Arrays.asList(picIds.split(","))));
    }

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
