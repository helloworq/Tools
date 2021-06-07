package com.zlutil.tools.toolpackage.Netty.code.BIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOserver {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost",8080));
        while (true) {
            Socket connectClient = serverSocket.accept();
            System.out.println("新连接接入,获取信息"+connectClient.getInputStream().read());
            connectClient.getOutputStream().write(1);
        }
    }
}
