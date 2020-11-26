package com.zlutil.tools.toolpackage.JavaBasic.NetTools;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class InitBanner {
    public final static String httpGet_Header = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36";

    public static void main(String[] args) {
        Map<String, String> type = new HashMap<>();
        type.put("Big", "@@");//根据需要更换样式
        System.out.print(getChar(type, 'A'));
        System.out.print(getChar(type, 'B'));
        System.out.print(getChar(type, 'C'));
    }

    static String getChar(Map<String, String> charType, char ch) {
        String urlPreffix = "http://patorjk.com/software/taag/fonts/";
        String urlSuffix = ".flf";
        int MagicNumber = 32;//ascii字符相较于目标数据的偏移量
        String content = sendGet(urlPreffix + charType.keySet().toArray()[0] + urlSuffix);
        String splitString = charType.get(charType.keySet().toArray()[0]);
        return content.split(splitString)[Integer.valueOf(ch) - MagicNumber]
                .replace("@", " ")
                .replace("#", " ")
                .replace("$", " ");
    }

    public static String sendGet(String URL) {
        String contents = null;
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(URL);
            httpGet.addHeader("User-Agent", httpGet_Header);
            HttpResponse response = httpClient.execute(httpGet);
            contents = EntityUtils.toString(response.getEntity(), "utf-8");//utf-8
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }
}
