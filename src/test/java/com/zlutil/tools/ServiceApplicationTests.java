package com.zlutil.tools;

import com.zlutil.tools.toolpackage.Minio.MinioRunnerVersion8;
import com.zlutil.tools.toolpackage.Yield.Dao.YieldHeadDao;
import com.zlutil.tools.toolpackage.Yield.entity.YieldHeadEntity;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.Data;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class ServiceApplicationTests {

    @Autowired
    YieldHeadDao yieldHeadDao;

    @Test
    void test() throws IOException {

    }

    @Test//增加田长信息
    void addYieldHead() throws IOException {
        for (int i = 0; i < 10; i++) {
            YieldHeadEntity yieldHeadEntity=new YieldHeadEntity();
            yieldHeadEntity.setName(String.valueOf(i));
            yieldHeadEntity.setCounty(String.valueOf(i));
            yieldHeadEntity.setTown(String.valueOf(i));
            yieldHeadEntity.setVillage(String.valueOf(i));
            yieldHeadEntity.setTitle(String.valueOf(i));
            yieldHeadEntity.setLeader(String.valueOf(i));
            yieldHeadEntity.setYieldBlockId(String.valueOf(i));
            yieldHeadDao.save(yieldHeadEntity);
        }
    }

}
