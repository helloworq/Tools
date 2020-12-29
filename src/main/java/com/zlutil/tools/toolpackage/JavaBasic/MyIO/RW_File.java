package com.zlutil.tools.toolpackage.JavaBasic.MyIO;

import java.io.*;

/**
 * 文件读写工具
 */
public class RW_File {
     /**
     * 根据File_Path获取目标文件，并且返回目标文件的字符串形式
     * @param File_Path
     * @return
     * @throws IOException
     */
    public static String read_txt(String File_Path) throws IOException {
        File file = new File(File_Path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String str = "";
        String prev = "";//使用中间变量，防止文件指针偏移
        while ((prev = bufferedReader.readLine()) != null) {
            str += (prev + "\n");
        }
        fileReader.close();
        bufferedReader.close();
        return str;
    }

    /**
     * 根据File_Path获取目标文件，并且返回目标文件的字符串形式
     * @param File_Path
     * @return
     * @throws IOException
     */
    public static StringBuilder stringBuilderRead(String File_Path) throws IOException {
        File file = new File(File_Path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder str = new StringBuilder();
        String prev = "";//使用中间变量，防止文件指针偏移
        while ((prev = bufferedReader.readLine()) != null) {
            str.append(prev + "\n");
        }
        fileReader.close();
        bufferedReader.close();
        return str;
    }

    /**
     * 写入-flag为true则为追加写入
     * @param File_Path
     * @param str
     * @param flag
     * @throws IOException
     */
    public static void write_txt(String File_Path, String str, boolean flag) throws IOException {
        //String str = new re().read_txt("C:\\Users\\12733\\Desktop\\Thread\\Thread.txt");//读取的数据路径
        File file = new File(File_Path);
        //如果未发现文件则创建文件
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file, flag);
        fileWriter.write(str);
        fileWriter.close();
    }
}
