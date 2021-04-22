package com.zlutil.tools.toolpackage.ZipReader;

import cn.hutool.core.io.FileUtil;
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
        File file=new File("C:\\Users\\12733\\Desktop\\目录\\测试.zip");

        //FileUtil.del("C:\\Users\\12733\\Desktop\\目录\\测试2");
        File fileUnzipFile = ZipUtil.unzip(file, new File("C:\\Users\\12733\\Desktop\\目录\\"), Charset.forName("GBK"));
        System.out.println(fileUnzipFile.getPath());
        String fileUnzipFilePath = fileUnzipFile.getPath() + File.separator + file.getName().split("\\.")[0];
        System.out.println(fileUnzipFilePath);

        File file1=FileUtil.rename(new File(fileUnzipFilePath), "测试2", false);
        System.out.println(file1.getPath());
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
