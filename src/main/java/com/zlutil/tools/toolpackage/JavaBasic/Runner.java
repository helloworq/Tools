package com.zlutil.tools.toolpackage.JavaBasic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Runner {
    public static void main(String[] args) {
        //jdk高级特性再测试
        String testjsonstr = "son.testString";
        //json字符串转json对象
        JSONObject jsonObject = JSONObject.parseObject(testjsonstr);
        //System.out.println(jsonObject.get("message"));
        //获取json对象里的json对象
        JSONObject jsonObjectone = (JSONObject) jsonObject.get("data");
        JSONObject jsonObjecttwo = (JSONObject) jsonObject.get("cityInfo");
        //System.out.println(jsonObjectone.get("quality"));
        //获取json对象里的json对象数组,获取完后可以像数组那样直接获取
        JSONArray jsonArray = jsonObjectone.getJSONArray("forecast");
        //System.out.println(jsonArray.get(1));
        //json对象转普通对象
        //构建json结构数据
        JSONObject jsonObjectres = new JSONObject();
        jsonObjectres.put("message", jsonObject.get("message").toString());
        jsonObjectres.put("city", jsonObjecttwo.get("city").toString());
        jsonObjectres.put("citykey", jsonObjecttwo.get("citykey").toString());
        //将数据转换为目标类
        POJO pojo = JSONObject.parseObject(jsonObjectres.toJSONString(), POJO.class);
        System.out.println(pojo.getCity());
    }
}