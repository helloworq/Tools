package com.zlutil.tools.toolpackage.hutools;

import cn.hutool.http.HttpUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HttpServer {
    public static void main(String[] args) {
        HttpUtil.createServer(8888)
                // 设置默认根目录
                .setRoot("E:\\【搜狐】孤独的美食家1-4季\\下载")
                .start();
    }
}
