package com.zlutil.tools.toolpackage.WebSocket;

import com.zlutil.tools.toolpackage.JavaBasic.MyIO.MyIOUtil;
import com.zlutil.tools.toolpackage.JavaBasic.MyIO.RW_File;

import java.io.*;

public class TestSpeed {
    public static void main(String[] args) throws IOException {
        String filePath="C:\\Users\\12733\\Desktop\\new.sql";

        for (int i = 0; i < 30; i++) {
        /*long a=System.currentTimeMillis();
        System.out.println(RW_File.read_txt(filePath).length());
        System.out.println("String时间"+(System.currentTimeMillis()-a));*/

            long b = System.currentTimeMillis();
            RW_File.readTxtWithStringBuilder(filePath);
            System.out.print("StringBuilder时间: " + (System.currentTimeMillis() - b)+"     ");

            long c = System.currentTimeMillis();
            RW_File.readTxtWithStringBuffer(filePath);
            System.out.println("StringBuffer时间: " + (System.currentTimeMillis() - c));
        }
/*        String filePath="C:\\Users\\12733\\Desktop\\计算机网络.pdf";

        long a=System.currentTimeMillis();
        File file = new File(filePath);
        System.out.println("执行时间: "+(System.currentTimeMillis()-a));

        long b=System.currentTimeMillis();
        InputStream in = new FileInputStream(file);
        System.out.println("执行时间: "+(System.currentTimeMillis()-b));
        //异步将输入流写到输出流供浏览器下载，防止服务器长时间阻塞

        String filePathnew="C:\\Users\\12733\\Desktop\\b.pdf";

        long c=System.currentTimeMillis();
        OutputStream out=new FileOutputStream(new File(filePathnew));
        System.out.println("执行时间: "+(System.currentTimeMillis()-c));

        long d=System.currentTimeMillis();
        MyIOUtil.inputStreamWriteToOutputStream(in,out);
        System.out.println("执行时间: "+(System.currentTimeMillis()-d));*/
    }
}
