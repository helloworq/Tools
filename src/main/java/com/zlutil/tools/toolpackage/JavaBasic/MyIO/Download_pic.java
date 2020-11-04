package com.zlutil.tools.toolpackage.JavaBasic.MyIO;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Download_pic {
    //链接url下载图片
    static void downloadPicture(String urlList, String path) {
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            //将下载的文件的名字截取设置为文件名
            File file = new File(path + urlList.substring(30, 40) + ".jpg");
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            //将下载信息写入1.txt
            System.out.println(path + urlList.substring(30, 40) + ".jpg" + "---写入成功！");
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}