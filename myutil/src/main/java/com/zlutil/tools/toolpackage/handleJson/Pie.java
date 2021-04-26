package com.zlutil.tools.toolpackage.handleJson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Pie {
    static String text = "[\n" +
            "    {\n" +
            "      \"name\": \"污水\",\n" +
            "      \"region\": \"云南省\",\n" +
            "      \"value\": \"98\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"重金属\",\n" +
            "      \"region\": \"贵州省\",\n" +
            "      \"value\": \"37\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"危险废物\",\n" +
            "      \"region\": \"四川省\",\n" +
            "      \"value\": \"75\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"废气\",\n" +
            "      \"region\": \"重庆市\",\n" +
            "      \"value\": \"81\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"畜禽养殖\",\n" +
            "      \"region\": \"湖南省\",\n" +
            "      \"value\": \"5\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"废水\",\n" +
            "      \"region\": \"湖北省\",\n" +
            "      \"value\": \"128\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"危险废物\",\n" +
            "      \"region\": \"江西省\",\n" +
            "      \"value\": \"110\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"废气\",\n" +
            "      \"region\": \"安徽省\",\n" +
            "      \"value\": \"102\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"污水\",\n" +
            "      \"region\": \"江苏省\",\n" +
            "      \"value\": \"420\"\n" +
            "    }\n" +
            "  ]";

    public static void main(String[] args) {
        JSONArray jsonArray = JSON.parseArray(text);
        Map<String, Integer> map = new HashMap<>();
        int number = 0;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (map.get(jsonObject.getString("name")) != null) {
                number = map.get(jsonObject.getString("name"));
            }
            map.put(jsonObject.getString("name"), number + Integer.parseInt(jsonObject.getString("value")));
        }
        System.out.println(JSON.toJSONString(map));
    }
}
