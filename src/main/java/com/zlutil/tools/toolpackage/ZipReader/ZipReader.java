package com.zlutil.tools.toolpackage.ZipReader;

import cn.hutool.core.util.ZipUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

public class ZipReader {

    public final static String basePath = "C:\\可视化资源文件列表\\压缩包\\";
    public final static String unzipPath = "C:\\可视化资源文件列表\\解压目录\\";

    public static void main(String[] args) {
        try {
            String a="aaa=bbb";
            int b=a.indexOf("=");
            System.out.println(a.substring(b));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void readZip() {

    }

    public void uploadZip(MultipartFile file) throws IOException {
        if (Objects.isNull(file)) {
            return;
        }
        File fileNew = ZipUtil.unzip(file.getInputStream(), new File(unzipPath + getFilename(file.getOriginalFilename())), Charset.forName("GBK"));
        file.getInputStream().close();


    }

    public String getFilename(String filename) {
        return filename.substring(0, filename.indexOf(".") + 1);
    }
}
