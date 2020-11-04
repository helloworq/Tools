package com.zlutil.tools.toolpackage.JavaBasic.NetTools;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.net.URI;


public class simpleClient {
    public String getTranslationInfo(String param){
        String contents=null;
        try {
            // 多参数情况必须自定义请求的参数
            URI uri = new URIBuilder("http://api.microsofttranslator.com/V2/Ajax.svc/Translate")
                    .setParameter("appId", "A4D660A48A6A97CCA791C34935E4C02BBB1BEC1C")
                    .setParameter("from","")
                    .setParameter("to","zh-cn")
                    .setParameter("text",param).build();
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("User-Agent", DownLoad_My_Configs.httpGet_Header);
            HttpResponse response = httpClient.execute(httpGet);
            contents = EntityUtils.toString(response.getEntity(), "utf-8");//utf-8
        }catch (Exception e){
            e.printStackTrace();
        }
        return contents;
    }
    public String sendPost(String URL){
        String contents=null;
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(URL);
            httpPost.setHeader("User-Agent", DownLoad_My_Configs.httpGet_Header);
            HttpResponse response = httpClient.execute(httpPost);
            contents = EntityUtils.toString(response.getEntity(), "utf-8");//utf-8
        }catch (Exception e){
            e.printStackTrace();
        }
        return contents;
    }
    public String sendGet(String URL){
        String contents=null;
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(URL);
            httpGet.addHeader("User-Agent", DownLoad_My_Configs.httpGet_Header);
            httpGet.addHeader("","");
            HttpResponse response = httpClient.execute(httpGet);
            contents = EntityUtils.toString(response.getEntity(), "utf-8");//utf-8
        }catch (Exception e){
            e.printStackTrace();
        }
        return contents;
    }

    public String sendGet(URI URL){
        String contents=null;
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(URL);
            httpGet.addHeader("User-Agent", DownLoad_My_Configs.httpGet_Header);
            httpGet.setHeader("Content-Type","application/json; charset=utf-8");
            httpGet.setHeader("Vary","Accept-Encoding");
            HttpResponse response = httpClient.execute(httpGet);
            contents = EntityUtils.toString(response.getEntity(), "GBK");//utf-8
            contents=JSON.parseObject(contents).toJSONString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return contents;
    }
}
