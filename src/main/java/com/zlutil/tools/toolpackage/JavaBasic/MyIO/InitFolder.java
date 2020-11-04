package com.zlutil.tools.toolpackage.JavaBasic.MyIO;/*
package MyIO;

import java.io.*;
import java.nio.*;
public class InitFolder{
    */
/**
 * mkdirs的意思是：根据给定的路径创建的文件夹的时候如果目标文件夹的父目录或者啥目录都没有的话
 * 也没关系，系统自动给你把路径上的名字把这一路上的文件夹都给你创建安排上
 * mkdir的作用就相对少一点，你路径没给好就不给你创建
 *
 * @return
 * @throws FileNotFoundException
 *//*

    public static String initFolder() throws IOException {
        DataInputStream dataInputStream=
                new DataInputStream(new FileInputStream(DownLoad_My_Configs.IO_path+"model.jpg"));
        File file=new File(DownLoad_My_Configs.IO_path+"aaa.jpg");

        if(!file.exists())
            file.createNewFile();
        DataOutputStream dataOutputStream=new DataOutputStream(new FileOutputStream(file));
        dataOutputStream.write(dataInputStream.readAllBytes());

        return "";
    }
}
*/
