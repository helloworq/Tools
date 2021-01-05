package com.zlutil.tools.toolpackage.hutools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.ftp.Ftp;
import sun.net.ftp.FtpClient;

import java.io.IOException;

public class FtpServer {
    public static void main(String[] args) {

    }
    public void server() throws IOException {
        //匿名登录（无需帐号密码的FTP服务器）
        Ftp ftp = new Ftp("172.0.0.1");
//进入远程目录
        //ftp.cd("/opt/upload");
//上传本地文件
        //ftp.upload("/opt/upload", FileUtil.file("e:/test.jpg"));
//下载远程文件
        ftp.download("https://pic1.zhimg.com/", "test.jpg", FileUtil.file("v2-02f9cb6512ef1ca057b9dc56c8d9b5d0_540x450.png"));


//关闭连接
        ftp.close();
    }
}
