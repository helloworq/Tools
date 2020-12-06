package com.zlutil.tools.toolpackage.JavaBasic;

import com.zlutil.tools.toolpackage.JavaBasic.MyIO.RW_File;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class EnumTest {
    public enum direction {WEST, EAST, SOUTH, NORTH}

    /**
     * 格式相对固定，只需要在id后加上primary key
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\12733\\Desktop\\SQL\\";
        File file = new File(filePath);
        file.listFiles();
        Arrays.stream(file.list()).forEach(d->{
            try {
                new EnumTest().extractTable(filePath, 0, d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void extractTable(String filePath, int begin, String fileName) throws IOException {
        //先提取出每一张表
        //String fileName = "bms";
        //String sumData = RW_File.read_txt(filePath + "bms\\bms.sql");
        String sumData = RW_File.read_txt(filePath + fileName + File.separator + fileName + ".sql");
        int start = sumData.indexOf("create table", begin);
        int end = sumData.indexOf(";", start);
        String prev = sumData.substring(start, end) + ";";
        //给每一段表加上primary key
        //定位not null位置
        int position = prev.indexOf("not null");
        StringBuffer stringBuffer = new StringBuffer().append(prev);
        stringBuffer.insert(position + "not null".length(), "\n                          primary key");
        System.out.println(stringBuffer.toString());
        RW_File.write_txt(filePath + fileName + File.separator + fileName + "new.sql", stringBuffer.toString() + "\n", true);
        if (sumData.indexOf("create table", end) != -1) {
            extractTable(filePath, end,fileName);
        }
    }
}
