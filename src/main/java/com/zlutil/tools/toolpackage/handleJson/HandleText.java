package com.zlutil.tools.toolpackage.handleJson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HandleText {
    public static void main(String[] args) {
        String resutl = HandleUtil.readFileContent("C:\\Users\\12733\\Desktop\\response_1602468490256.json");
        JSONArray jsonArrayRes = new JSONArray();
        JSONObject jsonObjectOri = JSON.parseObject(resutl);
        JSONArray jsonArray = jsonObjectOri.getJSONObject("data").getJSONObject("data").getJSONArray("data");
        //提取当前指标内数据
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", jsonArray.getJSONObject(i).getJSONObject("i").getString("label"));
            jsonObject.put("value", jsonArray.getJSONObject(i).getJSONObject("jcz").getString("label"));
            jsonObject.put("unit", jsonArray.getJSONObject(i).getJSONObject("unit").getString("label"));
            jsonObject.put("time", jsonArray.getJSONObject(i).getJSONObject("t").getString("label"));
            jsonArrayRes.add(jsonObject);
        }

        //数据提取完成之后根据年份分组
        Map<String, JSONArray> map = new HashMap<>();
        for (int i = 0; i < jsonArrayRes.size(); i++) {
            String time = jsonArrayRes.getJSONObject(i).getString("time");
            jsonArrayRes.getJSONObject(i).remove("time");

            if (!map.containsKey(time)) {
                JSONArray jsonArrayPrev = new JSONArray();
                jsonArrayPrev.add(jsonArrayRes.getJSONObject(i));
                map.put(time, jsonArrayPrev);
            } else {
                map.get(time).add(jsonArrayRes.getJSONObject(i));
            }

        }

        if (map.keySet().size() > 1) {
            int max = 0;
            for (String key : map.keySet()) {
                if (map.get(key).size() > max) {
                    max = map.get(key).size();
                }
            }
            System.out.println(max);
            for (String key : map.keySet()) {
                if (map.get(key).size() < max) {
                    int times = max - map.get(key).size();
                    for (int i = 0; i < times; i++) {
                        map.get(key).add(new JSONObject());
                    }
                }
            }
        }
        System.out.println(JSON.toJSONString(map));

    }
}


