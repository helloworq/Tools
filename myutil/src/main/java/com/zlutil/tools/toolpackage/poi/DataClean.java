package com.zlutil.tools.toolpackage.poi;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 数据清洗
 */
public class DataClean {
    List<String> year= stringToList(
            "2019," +
                    "2018," +
                    "2017," +
                    "2016," +
                    "2015," +
                    "2014," +
                    "2013," +
                    "2012," +
                    "2011," +
                    "2010," +
                    "2009," +
                    "2008");

    public static void main(String[] args) throws IOException {
        String filePath="C:\\Users\\12733\\Desktop\\data\\";//待读取目录，方法将递归读取目录下所有文件

        DataClean dataClean=new DataClean();
        dataClean.readFile(filePath);
    }

    public void readFile(String filePath) throws IOException {
        File file=new File(filePath);
        if (file.isDirectory()){
            for (File filenode: Objects.requireNonNull(file.listFiles())) {
                readFile(filePath+File.separator+filenode.getName());
            }
        }
        if (file.isFile()){
            if (file.getName().endsWith("xls")||file.getName().endsWith("XLS")||file.getName().endsWith("xlsx")||file.getName().endsWith("XLS")){
                ExcelReader reader = ExcelUtil.getReader(file, 0);
                DataClean dataClean=new DataClean();
                dataClean.readExcel(reader);
            }
        }

    }

    public void readExcel(ExcelReader reader) throws IOException {
        String resHead="";int i;int j;
        int rowCount=reader.getRowCount();
        int columnCount=reader.getColumnCount();
        String res="";
        for (i=0; i < rowCount; i++) {
            for (j = 0; j < columnCount; j++) {
                res+=reader.getCell(j,i)+"    ";
            }
            if (i==0){
                resHead+=res;
            }
            if (find(res)){
                resHead+=res;
            }
            if (!res.contains("收")&&res.contains("中")&&res.contains("国")&&(res.indexOf("国")-res.indexOf("中")>1)){
                //linkedList.add(resHead+res);
                write_txt("C:\\Users\\12733\\Desktop\\结果.txt",resHead+res+"\n",true);
                System.out.println("目标字符串===>"+resHead+res);
            }
            res="";
            //System.out.println();
        }
        System.out.println("完成一个");
    }

    public boolean find(String res){
        for (String years:year) {
            if (res.contains(years))
                return true;
        }
        return false;
    }

    /**
     * 写入-flag为true则为追加写入
     * @param File_Path
     * @param str
     * @param flag
     * @throws IOException
     */
    public static void write_txt(String File_Path, String str, boolean flag) throws IOException {
        File file = new File(File_Path);
        //如果未发现文件则创建文件
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file, flag);
        fileWriter.write(str);
        fileWriter.close();
    }

    /**
     * string 转 list
     */
    public static List<String> stringToList(String strs) {
        if (strs == null || strs.equals("")) {
            return null;
        }
        return Arrays.asList(strs.split(","));
    }
}
