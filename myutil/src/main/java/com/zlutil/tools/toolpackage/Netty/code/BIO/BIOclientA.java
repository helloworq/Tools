package com.zlutil.tools.toolpackage.Netty.code.BIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BIOclientA {
    public static void main(String[] args) throws IOException {

        Socket client = new Socket();
        client.connect(new InetSocketAddress("localhost",8080));

        client.getOutputStream().write(1);
        System.out.println(client.getInputStream().read());
    }
}