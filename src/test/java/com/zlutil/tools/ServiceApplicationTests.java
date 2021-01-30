package com.zlutil.tools;

import com.zlutil.tools.toolpackage.Minio.MinioRunnerVersion8;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceApplicationTests {

//    @Autowired
//    MinioRunnerVersion8 minioClient;

    @Resource
    MinioClient minioClient;

    @Test
    void test() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        System.out.println("开始");
        //minioClient.getConfigValue();
        minioClient.listBuckets();
        System.out.println("结束");
    }
}