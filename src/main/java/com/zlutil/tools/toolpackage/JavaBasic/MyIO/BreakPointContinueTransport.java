package com.zlutil.tools.toolpackage.JavaBasic.MyIO;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.*;

/**
 * 断点续传工具类
 */
public class BreakPointContinueTransport {
    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\12733\\Desktop\\z.jpg";
        String filePathDest = "C:\\Users\\12733\\Desktop\\a.jpg";
        String filePathNew = "C:\\Users\\12733\\Desktop\\t.jpg";
        /*List<byte[]> list = splitFile(filePath);
        mergeFile(list, filePathDest);*/
        Map<Integer, byte[]> map = splitFileOrdered(filePath);
        mergeFileOrdered(map, filePathDest);
    }

    /**
     * 文件切片,返回带序号的map，以便后续断点续传
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Map<Integer, byte[]> splitFileOrdered(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        Map<Integer, byte[]> map = new LinkedHashMap<>();//linkedHashmap保证存入顺序准确

        int available = fileInputStream.available();
        int splitLength = 10000;//分10000个字节
        int countTimes = (available / splitLength + 1);

        for (int i = 0; i < countTimes; i++) {
            if (available < splitLength) {
                splitLength = available;//如果剩下的可读比特数小于分片大小则将分片大小重置为可读数
            }
            byte[] bytes = new byte[splitLength];
            fileInputStream.read(bytes);
            if (countTimes - i == 1) {
                map.put(-1, bytes);//如果读到最后一个分片则将序号设置为-1作为结束标记并直接结束循环
                break;
            }
            map.put(i, bytes);
            available -= splitLength;
        }
        fileInputStream.close();
        return map;
    }

    /**
     * 获取目标文件指定部分分片的数据
     *
     * @param jsonObject
     * @param destFilePath
     * @throws IOException
     */
    public static List<byte[]> getSpecificPartFile(JSONObject jsonObject, String destFilePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(destFilePath));
        int available = fileInputStream.available();
        int currentPosition = available / 10000;//不用加一，多余字符直接抛弃

        return null;
    }

    /**
     * 合并分片文件-map格式
     * 此方法读取未写入完全的文件，根据已传输大小判断待传入的序号数据
     *
     * @param jsonObject
     * @param destFilePath
     * @throws IOException
     */
    public static void compareAndRetransform(JSONObject jsonObject, String destFilePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(destFilePath));
        int available = fileInputStream.available();
        int currentPosition = available / 10000;//不用加一，多余字符直接抛弃



    }

    /**
     * 合并分片文件-map格式
     *
     * @param map
     * @param destFilePath
     * @throws IOException
     */
    public static void mergeFileOrdered(Map<Integer, byte[]> map, String destFilePath) throws IOException {
        int startPosition = 0;
        int available = map.values().stream().mapToInt(bytes -> bytes.length).sum();//获取总比特数以便创建bytes接收结果集
        byte[] bytes = new byte[available];

        for (byte[] b : map.values()) {
            System.arraycopy(b, 0, bytes, startPosition, b.length);
            startPosition += 10000;
        }
        FileUtil.writeBytes(bytes, new File(destFilePath));
    }

    /**
     * 文件切片
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<byte[]> splitFile(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        List<byte[]> list = new ArrayList<>();
        int available = fileInputStream.available();
        int splitLength = 10000;
        int countTimes = (available / splitLength + 1);
        //分10000个字节
        for (int i = 0; i < countTimes; i++) {
            if (available < splitLength) {
                splitLength = available;
            }
            byte[] bytes = new byte[splitLength];
            fileInputStream.read(bytes);
            list.add(bytes);
            available -= splitLength;
        }
        fileInputStream.close();
        return list;
    }

    /**
     * 合并分片文件
     *
     * @param list
     * @param destFilePath
     * @throws IOException
     */
    public static void mergeFile(List<byte[]> list, String destFilePath) throws IOException {
        int startPosition = 0;
        int available = list.stream().mapToInt(bytes -> bytes.length).sum();//获取总比特数以便创建bytes接收结果集
        byte[] bytes = new byte[available];

        for (byte[] b : list) {
            System.arraycopy(b, 0, bytes, startPosition, b.length);
            startPosition += 10000;
        }
        FileUtil.writeBytes(bytes, new File(destFilePath));
    }
}
