package com.zlutil.tools.toolpackage.JavaBasic.MyIO;

import cn.hutool.core.io.FileUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IOServer {
    public static void main(String[] args) throws IOException {
        //创建http服务器，绑定本地8888端口*
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8888), 0);
        //创建上下文监听,拦截包含/test的请求*
        httpServer.createContext("/rest", new TestHttpHandler());
        httpServer.setExecutor(new ThreadPoolExecutor(2, 3, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1)));
        httpServer.start();
    }
}

class TestHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.print("请求方法: " + exchange.getRequestMethod() + "   ");
        System.out.println("http请求携带的参数: " + exchange.getRequestURI().getQuery());

        String response = "test message";

        try {
            exchange.sendResponseHeaders(200, 0);
            OutputStream os = exchange.getResponseBody();


            os.write(FileUtil.readBytes(new File("E:\\迅雷下载\\ShareFolder\\大江大河2-21.mp4")));
            os.close();
        } catch (Exception e) {
            System.out.println("请求出错!");
        }

        /*
        //获得查询字符串(get)
        String queryString =  exchange.getRequestURI().getQuery();
        Map<String,String> queryStringInfo = formData2Dic(queryString);
        //获得表单提交数据(post)
        String postString = IOUtils.toString(exchange.getRequestBody());
        Map<String,String> postInfo = formData2Dic(postString);
        exchange.sendResponseHeaders(200, 0);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes("UTF-8"));
        os.close();*/
    }


    public static Map<String, String> formData2Dic(String formData) {
        Map<String, String> result = new HashMap<>();
        if (formData == null || formData.trim().length() == 0) {
            return result;
        }
        final String[] items = formData.split("&");
        Arrays.stream(items).forEach(item -> {
            final String[] keyAndVal = item.split("=");
            if (keyAndVal.length == 2) {
                try {
                    final String key = URLDecoder.decode(keyAndVal[0], "utf8");
                    final String val = URLDecoder.decode(keyAndVal[1], "utf8");
                    result.put(key, val);
                } catch (UnsupportedEncodingException e) {
                }
            }
        });
        return result;
    }
}
