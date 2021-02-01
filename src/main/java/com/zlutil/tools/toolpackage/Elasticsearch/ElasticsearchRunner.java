package com.zlutil.tools.toolpackage.Elasticsearch;

import lombok.Data;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ElasticsearchRunner {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/creatIndex")
    public void save() throws IOException {
//        //创建索引请求
//        CreateIndexRequest request = new CreateIndexRequest("test_index2");
//        //执行请求indices,请求后获得响应
//        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
    }

    @GetMapping("/person/{id}")
    public Person findById(@PathVariable("id") Long id) {
        return null;
    }
}

@Data
class Person {
    public Person(String name, String age, String tall) {
        this.name = name;
        this.tall = tall;
        this.age = age;
    }

    String name;
    String age;
    String tall;
}