package com.zlutil.tools.controller.CloudDisk;

import cn.hutool.core.io.FileUtil;
import com.zlutil.tools.toolpackage.JavaBasic.NetTools.DownLoad_My_Configs;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;

public class CloudDisk {
    private JPanel test;
    private JProgressBar progressBar1;
    private JTextField textField1;
    private JButton 下载Button;
    private JButton 取消Button;

    boolean flag = true;

    public CloudDisk() {

        下载Button.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                String md5 = textField1.getText().trim();
                String path = "E:\\【搜狐】孤独的美食家1-4季\\cc\\1.mp4";

                while (flag) {
                    Thread.sleep(1000);
                    File file = new File(path);

                    int length = FileUtil.readBytes(path).length;
                    System.out.println(length);
                    progressBar1.setValue(Integer.parseInt(String.valueOf(length)));

                    URI uri = new URIBuilder("http://localhost:8888/download")
                            .setParameter("fileMd5", md5)
                            .setParameter("fileSize", String.valueOf(length)).build();
                    HttpClient httpClient = HttpClients.createDefault();
                    HttpGet httpGet = new HttpGet(uri);
                    httpGet.addHeader("User-Agent", DownLoad_My_Configs.httpGet_Header);
                    HttpResponse response = httpClient.execute(httpGet);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    response.getEntity().writeTo(outputStream);//转换成输出流以便直接获取字节

                    FileUtil.writeBytes(outputStream.toByteArray(), file);
                    outputStream.close();
                }
            }
        });

        取消Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CloudDisk");
        frame.setContentPane(new CloudDisk().test);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
