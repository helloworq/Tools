package com.zlutil.tools.toolpackage.ZipReader;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Runner {

    public final static String basePath="C:\\可视化资源文件列表\\";

    public static void main(String[] args) throws Exception {
        try {
            String a="C:\\Users\\12733\\Desktop\\a\\规划成果基本信息.txt";
            String b="C:\\Users\\12733\\Desktop\\a\\a\\";
            String c=new File(b).getPath();

            File file=FileUtil.copy(a,b,false);
            System.out.println(file.getName());
            //new Runner().readZipFileList("C:\\Users\\12733\\Desktop\\长江经济带国土空间规划.zip");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getRegionCodeFromZip(String filePath) throws IOException {

        //检测目标文件内的行政区划代码是否与regionCode一致
        ZipFile zipFile = new ZipFile(new File(filePath), Charset.forName("GBK"));//不添加文件编码无法获取数据
        InputStream in = new BufferedInputStream(new FileInputStream(new File(filePath)));
        ZipInputStream zin = new ZipInputStream(in, Charset.forName("GBK"));
        ZipEntry ze;
        String target = null;
        while ((ze = zin.getNextEntry()) != null) {
            System.out.println(ze.getName());
            //从根目录检测txt文件
            if (countChar(ze.getName(), "/") <= 1 && ze.getName().contains(".txt")) {
                InputStream inputStream = zipFile.getInputStream(ze);
                File file = FileUtil.createTempFile(new File(System.getProperty("user.dir")), false);

                FileUtils.copyInputStreamToFile(inputStream, file);
                List<String> list = FileUtil.readLines(file, Charset.forName("GBK"));
                String prev = list.stream().filter(d -> d.contains("项目名称=")).findFirst().get();
                target = prev.substring(prev.lastIndexOf("=") + 1);
                inputStream.close();
                file.delete();
                break;
            }
        }

        in.close();
        zin.close();
        System.out.println(target);
        return target;
    }


    //获取zip文件内的特定文件
    public String findSpecificFile(String zipFileName, String relativePath) throws IOException {
        ZipFile zipFile = new ZipFile(new File(zipFileName), Charset.forName("GBK"));
        InputStream in = new BufferedInputStream(new FileInputStream(new File(zipFileName)));
        ZipInputStream zin = new ZipInputStream(in, Charset.forName("GBK"));
        ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.getName().equals(relativePath)) {
                InputStream inputStream = zipFile.getInputStream(ze);
                File file = FileUtil.createTempFile(new File("C:\\Users\\12733\\Desktop"), false);
                FileUtils.copyInputStreamToFile(inputStream, file);
                List<String> list = FileUtil.readLines(file, Charset.forName("GBK"));
                System.out.println(list);
                System.out.println(JSON.toJSONString(list));
                break;
            }
        }

        in.close();
        zin.close();

        return null;
    }

    /**
     * 获取zip文件列表
     *
     * @param file
     * @throws Exception
     */
    public void readZipFileList(String file) throws Exception {
        ZipFile zipFile = new ZipFile(file);

        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in, Charset.forName("GBK"));

        ZipEntry ze;
        List<FileEntity> list = new ArrayList<>();
        while ((ze = zin.getNextEntry()) != null) {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setId(UUID.randomUUID().toString().replace("-", ""));
            fileEntity.setFileName(ze.getName().substring(ze.getName().lastIndexOf("/") + 1));
            fileEntity.setFilePath(ze.getName());

            zipFile.getInputStream(ze);
            list.add(fileEntity);
        }

        zin.closeEntry();
        FileEntity fileEntity = this.createTree(list);
        System.out.println(JSON.toJSONString(fileEntity));
    }

    public FileEntity createTree(List<FileEntity> list) {
        List<FileEntity> prev = new ArrayList<>();//接收结果集
        //计算层级
        int level = 1+list.stream().map(FileEntity::getFilePath).map(d -> countChar(d, "/")).max(Comparator.naturalOrder()).get();
        for (int i = 0; i < level; i++) {
            //找到当前层级的父节点(可能有多个文件或者文件夹)
            int finalI = i;
            FileEntity fileEntity = list.stream()
                    .filter(d -> d.getFileName().equals("") && countChar(d.getFilePath(), "/") == (finalI))
                    .findFirst().get();
            fileEntity.setParentCode(null);

            //找到当前层级的全部子节点，将其parentCode设为父节点id，此处peek有返回值，map无返回值
            List<FileEntity> fileEntities = list.stream()
                    .filter(d -> !d.getFileName().equals("") && countChar(d.getFilePath(), "/") == (finalI))
                    .peek(d -> d.setParentCode(fileEntity.getId()))
                    .collect(Collectors.toList());
            //将父节点的entitis数组填充进子节点
            fileEntity.setChild(fileEntities);
            prev.add(fileEntity);
        }

        for (int i = level; i > 1; i--) {
            //将res目录结果集封装成最终形式
            int finalI = i;
            List<FileEntity> listOne = prev.stream()
                    .filter(d -> d.getFileName().equals("") && countChar(d.getFilePath(), "/") == finalI)
                    .collect(Collectors.toList());
            prev.stream()
                    .map(d -> {
                        if (d.getFileName().equals("") && countChar(d.getFilePath(), "/") == finalI - 1) {
                            List<FileEntity> fileEntities = d.getChild();
                            listOne.stream().peek(e -> e.setId(d.getId()));//将文件夹节点的父code设为此id
                            fileEntities.addAll(listOne);
                            d.setChild(fileEntities);
                        }
                        return d;
                    }).collect(Collectors.toList());
        }
        return prev.stream().filter(d -> countChar(d.getFilePath(), "/") == 1).findFirst().get();
    }

    //计算字符串中某个字符出现的次数
    public Integer countChar(String str, String specficChar) {
        int oriLength = str.length();
        int finalLength = str.replace(specficChar, "").length();
        return oriLength - finalLength;
    }
//    public static void readZipFile(String file) throws Exception {
//        ZipFile zf = new ZipFile(file);
//        InputStream in = new BufferedInputStream(new FileInputStream(file));
//        ZipInputStream zin = new ZipInputStream(in);
//        ZipEntry ze;
//        while ((ze = zin.getNextEntry()) != null) {
//            if (ze.isDirectory()) {
//            } else {
//                System.err.println("file - " + ze.getName() + " : "
//                        + ze.getSize() + " bytes");
//                long size = ze.getSize();
//                if (size > 0) {
//                    BufferedReader br = new BufferedReader(
//                            new InputStreamReader(zf.getInputStream(ze)));
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        System.out.println(line);
//                    }
//                    br.close();
//                }
//                System.out.println();
//            }
//        }
//        zin.closeEntry();
//    }


    /**
     * 获取zip文件列表
     *
     * @param regionCode
     * @throws Exception
     */
//    public FileEntity readZipFileList(String regionCode) throws Exception {
//        ZipInfoEntity zipInfoEntity = zipInfoService.getZipInfo(regionCode);
//        if (Objects.isNull(zipInfoEntity)) {
//            return null;
//        }
//        String file = zipInfoEntity.getFilePath();
//        InputStream in = new BufferedInputStream(new FileInputStream(file));
//        ZipInputStream zin = new ZipInputStream(in, Charset.forName("GBK"));
//        ZipEntry ze;
//        List<FileEntity> list = new ArrayList<>();
//        while ((ze = zin.getNextEntry()) != null) {
//            FileEntity fileEntity = new FileEntity();
//            fileEntity.setId(UUID.randomUUID().toString().replace("-", ""));
//            fileEntity.setFileName(ze.getName().substring(ze.getName().lastIndexOf("/") + 1));
//            fileEntity.setFilePath(ze.getName());
//
//            list.add(fileEntity);
//        }
//
//        in.close();
//        zin.close();
//        FileEntity fileEntity = this.createTree(list);
//
//        fileEntity.setFileName((getRegionCodeFromZip(zipInfoEntity.getFilePath())));
//        return fileEntity;
//    }

//    public FileEntity createTree(List<FileEntity> list) {
//        List<FileEntity> prev = new ArrayList<>();//接收结果集
//        //计算层级
//        int level = list.stream().map(FileEntity::getFilePath).map(d -> countChar(d, "/")).max(Comparator.naturalOrder()).get();
//        for (int i = 0; i < level; i++) {
//            //找到当前层级的父节点
//            int finalI = i;
//            FileEntity fileEntity = list.stream()
//                    .filter(d -> d.getFileName().equals("") && countChar(d.getFilePath(), "/") == (finalI + 1))
//                    .findFirst().get();
//            fileEntity.setParentCode(null);
//            //找到当前层级的全部子节点，将其parentCode设为父节点id，此处peek有返回值，map无返回值
//            List<FileEntity> fileEntities = list.stream()
//                    .filter(d -> !d.getFileName().equals("") && countChar(d.getFilePath(), "/") == (finalI + 1))
//                    .peek(d -> d.setParentCode(fileEntity.getId()))
//                    .collect(Collectors.toList());
//            //将父节点的entitis数组填充进子节点
//            fileEntity.setChild(fileEntities);
//            prev.add(fileEntity);
//        }
//
//        for (int i = level; i > 1; i--) {
//            //将res目录结果集封装成最终形式
//            int finalI = i;
//            List<FileEntity> listOne = prev.stream()
//                    .filter(d -> d.getFileName().equals("") && countChar(d.getFilePath(), "/") == finalI)
//                    .collect(Collectors.toList());
//            prev.stream()
//                    .peek(d -> {
//                        if (d.getFileName().equals("") && countChar(d.getFilePath(), "/") == finalI - 1) {
//                            List<FileEntity> fileEntities = d.getChild();
//                            listOne.stream().peek(e -> e.setId(d.getId()));//将文件夹节点的父code设为此id
//                            fileEntities.addAll(listOne);
//                            d.setChild(fileEntities);
//                        }
//                    }).collect(Collectors.toList());
//        }
//        return prev.stream().filter(d -> countChar(d.getFilePath(), "/") == 1).findFirst().get();
//    }
}
