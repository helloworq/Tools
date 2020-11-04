package com.zlutil.tools.toolpackage.JavaBasic.JDK8Stream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zlutil.tools.toolpackage.JavaBasic.son;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NewRunner {
    public static void main(String[] args) {
        String data = son.readFileContent("C:\\Users\\12733\\Desktop\\处理json数据\\原始多年份多省份数据.json");
        JSONArray jsonArray = JSON.parseObject(data).getJSONObject("data").getJSONObject("data").getJSONArray("data");
        List<JSONObject> list = new ArrayList(jsonArray);
        //System.out.println(list.stream().map(d->d.getJSONObject("jcz")).count());
        //list.stream().map(d->d.getJSONObject("jcz")).forEach(System.out::println);
        String[] arr = new String[]{"jcz", "r", "t"};
        List<String> jsonObjectList = new ArrayList<>();
        //list.stream().forEach(System.out::println);
        //list.stream().map(d->d.keySet().contains("jcz")).forEach(System.out::println);
        Map map = list.stream()
                .filter(d -> d.remove("unit") != null)
                .filter(d -> d.remove("i") != null)
                .filter(d -> d.remove("updateTime") != null)
                .filter(d -> d.remove("id") != null)
                .collect(Collectors.groupingBy(d -> d.keySet()));
        System.out.println(JSON.toJSONString(map));

    }
}
