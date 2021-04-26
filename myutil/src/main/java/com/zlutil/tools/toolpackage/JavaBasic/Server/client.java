package com.zlutil.tools.toolpackage.JavaBasic.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class client {
    public final static String path="C:\\Users\\12733\\Desktop\\pic\\";
    public static void main(String[] args) throws IOException {
        try {
            Socket socket = new Socket("127.0.0.1", 13);
            socket.setSoTimeout(5000);
            socket.getOutputStream().write("ping".getBytes());
            //System.out.println(new client().getTextFromServer(socket));
        }catch (Exception e){
            System.err.println("链接失败");
        }
    }

    /**
     * 从服务器获取对象数据
     * @param socket
     * @return
     * @throws IOException
     */
    public void getObjFromServer(Socket socket) throws IOException {
        InputStream in=socket.getInputStream();
        DataInputStream data=new DataInputStream(in);
        //MyIOUtil.creatRandomNameFile(path+)

    }

    /**
     * 从服务器获取文本数据
     * @param socket
     * @return
     * @throws IOException
     */
    public String getTextFromServer(Socket socket) throws IOException {
        InputStream in=socket.getInputStream();
        InputStreamReader reader=new InputStreamReader(in,"UTF-8");
        StringBuilder time=new StringBuilder();
        for (int c = reader.read(); c !=-1; c=reader.read()) {
            time.append((char) c);
        }
        return "客户端收到:\n"+time.toString();
    }
}
