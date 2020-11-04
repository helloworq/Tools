package com.zlutil.tools.toolpackage.leetcode;

import com.alibaba.fastjson.JSONObject;

public class MultiLevelJSON {
    //测试查找多级json
    //通过输入name1.name2.name3=targetValue的方式修改json数据
    public static void main(String[] args) {
        String searchString = "m1.server.port=8088";
        searchJsonData(searchString);
    }

    public static void searchJsonData(String searchString) {
        JSONObject jsonObject = JSONObject.parseObject(jsonstring);

    }

    public static String jsonstring = "{\n" +
            "    \"m1\": {\n" +
            "        \"server\": {\n" +
            "            \"port\": 8080\n" +
            "        },\n" +
            "        \"dubbo\": {\n" +
            "            \"application\": {\n" +
            "                \"name\": \"boot-order-service-consumer\"\n" +
            "            },\n" +
            "            \"registry\": {\n" +
            "                \"address\": \"zookeeper://127.0.0.1:2181\",\n" +
            "                \"check\": false\n" +
            "            },\n" +
            "            \"monitor\": {\n" +
            "                \"protocol\": \"registry\"\n" +
            "            },\n" +
            "            \"consumer\": {\n" +
            "                \"timeout\": 30000\n" +
            "            }\n" +
            "        },\n" +
            "        \"spring\": {\n" +
            "            \"application\": {\n" +
            "                \"name\": \"openfeign-server\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"msg\": {\n" +
            "            \"user\": {\n" +
            "                \"id\": 777,\n" +
            "                \"age\": 10,\n" +
            "                \"arr\": [\n" +
            "                    1,\n" +
            "                    2,\n" +
            "                    3,\n" +
            "                    4,\n" +
            "                    5,\n" +
            "                    6\n" +
            "                ],\n" +
            "                \"arrlist\": [\n" +
            "                    1,\n" +
            "                    2,\n" +
            "                    3,\n" +
            "                    4,\n" +
            "                    5\n" +
            "                ],\n" +
            "                \"list\": [\n" +
            "                    {\n" +
            "                        \"id\": 2,\n" +
            "                        \"age\": \"三十\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 3,\n" +
            "                        \"age\": \"四十\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 4,\n" +
            "                        \"age\": \"五十\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 5,\n" +
            "                        \"age\": \"六十\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"haMap\": {\n" +
            "                    \"value1\": \"value1\",\n" +
            "                    \"value2\": \"value2\",\n" +
            "                    \"value3\": \"value3\"\n" +
            "                },\n" +
            "                \"arrHash\": [\n" +
            "                    {\n" +
            "                        \"value1\": \"张三\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"value2\": \"李斯\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"arrHashUser\": [\n" +
            "                    {\n" +
            "                        \"value1\": {\n" +
            "                            \"id\": 22,\n" +
            "                            \"age\": \"赵钱\"\n" +
            "                        }\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"value2\": {\n" +
            "                            \"id\": 33,\n" +
            "                            \"age\": \"孙李\"\n" +
            "                        }\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}";
}
