package com.zlutil.tools.toolpackage.Feign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zlutil.tools.toolpackage.JavaBasic.MyIO.RW_File;
import jdk.nashorn.internal.scripts.JS;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeignRunner {
    public static void main(String[] args) throws IOException, InterruptedException {
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        LocalDateTime localDateTime=LocalDateTime.now();
        Thread.sleep(100);
        System.out.println(Duration.between(localDateTime,LocalDateTime.now()));
        //System.out.println(removeInnerPresent());
    }

    /**
     * 剔除全部数据内部的外部分类(现状，现势数据) 步骤：
     * 1.将现状，现势数据的子元素全部提取出来，插入到父元素之中
     * 2.所有子元素的parentId全部改成原始父元素的父元素id
     */
    public static Object removeInnerPresent() throws IOException{
        JSONObject jsonObject= JSON.parseObject(RW_File.read_txt("E:\\DistCode\\菏泽\\菏泽文档\\data.json"));
        JSONArray jsonArray=jsonObject.getJSONObject("data").getJSONArray("content");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object=jsonArray.getJSONObject(i);//父对象
            JSONArray jSONArrayFather=object.getJSONArray("children");
            //获取外部分类内部全部子children
            JSONArray list= getJSONArrayUnderCatlog(jSONArrayFather,object.getString("id"));
            //剔除外部分类
            jSONArrayFather.fluentClear();
            list.stream().forEach(element->jSONArrayFather.add(element));
        }

        return jsonArray;
    }

    /**
     * 从当前children获取子元素下的全部children,并将其父元素id传入
     * 此方法为协助removeInnerPresent()方法，不读取两层以下元素
     * @return
     */
    public static JSONArray getJSONArrayUnderCatlog(JSONArray jsonArray,String fatherId){
        List<JSONArray> list=new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONArray jsonArraySon=jsonArray.getJSONObject(i).getJSONArray("children");
            //修改子元素父id值
            for (int j = 0; j < jsonArraySon.size(); j++) {
                JSONObject jsonObject=jsonArraySon.getJSONObject(j);
                jsonObject.put("parentId",fatherId);
            }
            list.add(jsonArraySon);
        }
        //合并list
        List<JSONArray> listRes=new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            if (list.size()>1){
                list.get(0).addAll(list.get(i));
            }
        }
        return list.get(0);
    }
}
