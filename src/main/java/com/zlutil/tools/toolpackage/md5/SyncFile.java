package com.zlutil.tools.toolpackage.md5;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 同步工具类
 * 同步是文件夹之间的同步，例如本地有一个文件夹，服务器端也有一个文件夹
 * 同步规定：
 *    1.以服务器端文件夹数据为基准，本地文件夹依据服务器文件夹数据进行同步
 *    2.
 */
public class SyncFile implements ISyncFile {
    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\12733\\Desktop\\本地文件夹";
        String filePathCloud = "C:\\Users\\12733\\Desktop\\云文件夹";
        new SyncFile().uploadFolder(filePath);
    }

    @Override
    public String getFileMd5Value(String filePath) throws IOException {
        return DigestUtils.md5Hex(new FileInputStream(filePath));
    }

    @Override
    public List<String> getFileList(String filePath) {
        return null;
    }

    @Override
    public void uploadFolder(String filePath) {
        File file = new File(filePath);
        if (file.isDirectory() && Objects.nonNull(file.list()) ) {
            //开始比对
            List<File> files = Arrays.stream(file.listFiles()).collect(Collectors.toList());
            files.stream().forEach(d-> {
                if (d.isFile()){
                    System.out.println(d.getPath().replace(filePath,""));
                }
                else {
                    getFileName(d);
                }
                System.out.println(d.getName().replace(filePath,""));
                System.out.println(d.getFreeSpace());
                System.out.println("======================");
            });
        }
    }

    public void getFileName(File file){
        Arrays.stream(file.listFiles()).forEach(d->{
            System.out.println(d.getPath());
        });
    }
}
