package com.zlutil.tools.toolpackage.JavaBasic.Server;

import com.zlutil.tools.toolpackage.Collectionutil.ListUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Collectors;

public class server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(13);
        while (true) {
            System.out.println("正在侦听...");
            String command=new server().listenCommand(serverSocket);
            System.out.println(command);
        }
    }

    /**
     * 读取客户端命令
     * @param serverSocket
     * @throws IOException
     */
    public String listenCommand(ServerSocket serverSocket) throws IOException {
        Socket connetion = serverSocket.accept();
        byte[] bytes=new byte[1024];
        int len =connetion.getInputStream().read(bytes);
        return new String(bytes,0,len);
    }

    public void objServer(String filePath,ServerSocket serverSocket) throws IOException {
        Socket connetion = serverSocket.accept();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connetion.getOutputStream(), "UTF-8"));

        File file=new File("C:\\Users\\12733\\Pictures\\Saved Pictures"+File.separator+filePath);
        out.write(ListUtil.listToString(Arrays.stream(file.list()).map(d-> d+"\n").collect(Collectors.toList())).replace(",",""));
        out.flush();
        connetion.close();
    }
}
