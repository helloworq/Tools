package com.zlutil.tools.toolpackage.Minio;//package com.zlutil.tools.toolpackage.Minio;
//
//import cn.hutool.core.io.FileUtil;
//import io.minio.MinioClient;
//import io.minio.PutObjectOptions;
//import io.minio.messages.Bucket;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.PostConstruct;
//import java.io.InputStream;
//import java.util.List;
////Minio Version 7+
//@Slf4j
//public class MinioRunnerVersion7 {
//    public static String Url = "http://127.0.0.1:9000";
//    public static String AccessKey = "minioadmin";
//    public static String SecretKey = "minioadmin";
//    public static String BucketName = "filebed";
//
//    public static MinioClient minioClient;
//
//    public static void main(String[] args) {
//        init();
//    }
//
//    @PostConstruct
//    public static void init() {
//        try {
//            log.info("初始化MinioClient");
//            minioClient = MinioClient.builder()
//                    .endpoint(Url)
//                    .credentials(AccessKey, SecretKey)
//                    .build();
//            createBucket(BucketName);
//            log.info("初始化完成");
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("初始化失败!");
//        }
//        log.info("测试文件上传");
//        upload("filebed", "test.txt", "C:\\Users\\12733\\Desktop\\response_1611285362029.json");
//    }
//
//    /**
//     * 文件上传
//     *
//     * @param bucketName: 桶名
//     * @param fileName:   文件名
//     * @param filePath:   文件路径
//     * @return: void
//     */
//    @SneakyThrows(Exception.class)
//    public static void upload(String bucketName, String fileName, String filePath) {
//        minioClient.putObject(bucketName, fileName, filePath, null);
//    }
//
//    /**
//     * 文件上传
//     *
//     * @param bucketName: 桶名
//     * @param fileName:   文件名
//     * @param stream:     文件流
//     * @return: java.lang.String : 文件url地址
//     */
//    @SneakyThrows(Exception.class)
//    public static String upload(String bucketName, String fileName, InputStream stream) {
//        minioClient.putObject(bucketName, fileName, stream, new PutObjectOptions(stream.available(), -1));
//        return getFileUrl(bucketName, fileName);
//    }
//
//    /**
//     * 文件上传
//     *
//     * @param bucketName: 桶名
//     * @param file:       文件
//     * @return: java.lang.String : 文件url地址
//     */
//    @SneakyThrows(Exception.class)
//    public static String upload(String bucketName, MultipartFile file) {
//        final InputStream is = file.getInputStream();
//        final String fileName = file.getOriginalFilename();
//        minioClient.putObject(bucketName, fileName, is, new PutObjectOptions(is.available(), -1));
//        is.close();
//        return getFileUrl(bucketName, fileName);
//    }
//
//    /**
//     * 创建 bucket
//     *
//     * @param bucketName: 桶名
//     * @return: void
//     */
//    @SneakyThrows(Exception.class)
//    public static void createBucket(String bucketName) {
//        boolean isExist = minioClient.bucketExists(bucketName);
//        if (!isExist) {
//            minioClient.makeBucket(bucketName);
//        }
//    }
//
//    /**
//     * 获取全部bucket
//     *
//     * @param :
//     * @return: java.util.List<io.minio.messages.Bucket>
//     */
//    @SneakyThrows(Exception.class)
//    public static List<Bucket> getAllBuckets() {
//        return minioClient.listBuckets();
//    }
//
//    /**
//     * 删除文件
//     *
//     * @param bucketName: 桶名
//     * @param fileName:   文件名
//     * @return: void
//     */
//    @SneakyThrows(Exception.class)
//    public static void deleteFile(String bucketName, String fileName) {
//        minioClient.removeObject(bucketName, fileName);
//    }
//
//    /**
//     * 获取minio文件的下载地址
//     *
//     * @param bucketName: 桶名
//     * @param fileName:   文件名
//     * @return: java.lang.String
//     * @date : 2020/8/16 22:07
//     */
//    @SneakyThrows(Exception.class)
//    public static String getFileUrl(String bucketName, String fileName) {
//        return minioClient.presignedGetObject(bucketName, fileName);
//    }
//}
