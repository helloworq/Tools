package com.zlutil.tools.toolpackage.Minio;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Methods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

//Minio Version 8+
@Slf4j
@Service
public class MinioRunnerVersion8 {

//    @Value("${minio.Url}")
//    public String Url;
//
//    @Value("${minio.AccessKey}")
//    public String AccessKey;
//
//    @Value("${minio.SecretKey}")
//    public String SecretKey;
//
//    @Value("${minio.BucketName}")
//    public String BucketName;

    public String Url = "http://127.0.0.1:9000";

    public String AccessKey = "minioadmin";

    public String SecretKey = "minioadmin";

    public String BucketName = "filebed";

    public MinioClient minioClient;
    //public static MinioClient minioClient;

    public static void main(String[] args) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InsufficientDataException, ErrorResponseException {
        new MinioRunnerVersion8().init();
    }

    public String getConfigValue() {
        return
                "Url         :        " + this.Url + "\n" +
                        "AccessKey   :        " + this.AccessKey + "\n" +
                        "BucketName  :        " + this.BucketName + "\n" +
                        "SecretKey   :        " + this.SecretKey;
    }

    @PostConstruct
    public void init() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InternalException {
        try {
            minioClient = MinioClient.builder()
                    .endpoint(Url)
                    .credentials(AccessKey, SecretKey)
                    .build();
            this.createBucket(BucketName);
            log.info("初始化MinioClient完成");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("初始化MinioClient失败!");
        }
        log.info("开始上传");
        //uploadFile("yyy.jpg", "C:\\Users\\12733\\Desktop\\777.jpg");
        //System.out.println(getFileInfo("666"));
        //System.out.println(getFileUrl("yyy.jpg"));
    }

    /**
     * 创建 bucket
     *
     * @param bucketName: 桶名
     */
    @SneakyThrows(Exception.class)
    private void createBucket(String bucketName) {
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 获取全部bucket
     */
    @SneakyThrows(Exception.class)
    public List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }

    /**
     * 获取目标文件信息
     *
     * @param ObjectId
     * @return
     */
    @SneakyThrows(Exception.class)
    public String getFileInfo(String ObjectId) {
        StatObjectResponse stat = minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(BucketName)
                        .object(ObjectId)
                        .build()
        );
        return getFileInfoWithFormat(stat);
    }

    private String getFileInfoWithFormat(StatObjectResponse stat) {
        return
                "file.userMetadata()      :     " + stat.userMetadata() + "\n" +
                        "file.versionId()         :     " + stat.versionId() + "\n" +
                        "file.contentType()       :     " + stat.contentType() + "\n" +
                        "file.size()              :     " + stat.size() + "\n" +
                        "file.lastModified()      :     " + stat.lastModified();
    }

    /**
     * 下载文件
     *
     * @param objectId       文件对象Id
     * @param targetFilePath 下载路径
     */
    @SneakyThrows
    public void downloadFile(String objectId, String targetFilePath) {
        DownloadObjectArgs downloadObjectArgs;
        if (targetFilePath == null) {
            downloadObjectArgs = DownloadObjectArgs.builder()
                    .bucket(BucketName)
                    .object(objectId)
                    .filename(targetFilePath)
                    .build();
        } else {
            downloadObjectArgs = DownloadObjectArgs.builder()
                    .bucket(BucketName)
                    .object(objectId)
                    .filename(System.getProperty("user.dir"))
                    .build();
        }
        minioClient.downloadObject(downloadObjectArgs);
    }

    public void downloadFile(String objectId) {
        this.downloadFile(objectId);
    }

    /**
     * 文件上传
     *
     * @param objectId    文件对象Id (上传时指定的)
     * @param filePath    文件路径
     * @param contentType 文件类型
     */
    @SneakyThrows(Exception.class)
    public String uploadFile(String objectId, String filePath, String contentType, Map<String, String> fileInfo) {
        UploadObjectArgs uploadObjectArgs = null;
        if (Objects.nonNull(contentType) && Objects.nonNull(fileInfo)) {
            uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(BucketName)
                    .object(objectId)
                    .filename(filePath)
                    .userMetadata(fileInfo)
                    .contentType(contentType)
                    .build();
        } else if (Objects.isNull(contentType) && Objects.isNull(fileInfo)) {
            uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(BucketName)
                    .object(objectId)
                    .filename(filePath)
                    .build();
        } else {
            uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(BucketName)
                    .object(objectId)
                    .filename(filePath)
                    .userMetadata(fileInfo)
                    .build();
        }
        ObjectWriteResponse response = minioClient.uploadObject(uploadObjectArgs);
        log.info("上传完成" + response.versionId());
        return "s";
    }

    public void uploadFile(String objectId, String filePath) {
        this.uploadFile(objectId, filePath, null, null);
    }

    public void uploadFileWithMeta(String objectId, String filePath, Map<String, String> fileInfo) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        this.uploadFile(objectId, filePath, null, fileInfo);
    }

    public void uploadFileStream(String objectId, InputStream inputStream) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        PutObjectArgs putObjectArgs;
        putObjectArgs = PutObjectArgs.builder()
                .bucket(BucketName)
                .object(objectId)
                .stream(inputStream, inputStream.available(), -1)
                .build();
        minioClient.putObject(putObjectArgs);
        inputStream.close();
    }

    /**
     * 删除文件
     *
     * @param objectId: 对象Id
     * @return: void
     */
    @SneakyThrows(Exception.class)
    public void deleteFile(String objectId) {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(BucketName).object(objectId).build();
        minioClient.removeObject(removeObjectArgs);
    }

    /**
     * 获取minio文件的下载地址
     *
     * @param objectId: 对象Id
     */
    @SneakyThrows(Exception.class)
    public String getFileUrl(String objectId, Integer expiryTime) {
        GetPresignedObjectUrlArgs presignedObjectUrlArgs = null;
        if (Objects.nonNull(expiryTime)) {
            presignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .method(Method.DELETE)
                    .bucket(BucketName)
                    .object(objectId)
                    .expiry(expiryTime)
                    .build();
        } else {
            presignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(BucketName)
                    .object(objectId)
                    .expiry(24 * 60 * 60)
                    .build();
        }
        return minioClient.getPresignedObjectUrl(presignedObjectUrlArgs);
    }

    public String getFileUrl(String objectId) {
        return this.getFileUrl(objectId, null);
    }
}
//    public String Url = "http://127.0.0.1:9000";
//
//    public String AccessKey = "minioadmin";
//
//    public String SecretKey = "minioadmin";
//
//    public String BucketName = "filebed";