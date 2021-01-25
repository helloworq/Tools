package com.zlutil.tools;

import com.zlutil.tools.toolpackage.Minio.MinioRunnerVersion8;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceApplicationTests {

    @Autowired
    MinioRunnerVersion8 minioClient;

    @Test
    void test() {
        System.out.println("开始");
        minioClient.getConfigValue();
        System.out.println("结束");
    }
}