package com.zlutil.tools.toolpackage.Netty.code.BIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BIOclientB {
    public static void main(String[] args) throws IOException {

        Socket client = new Socket();
        client.connect(new InetSocketAddress("localhost",8080));
        client.getOutputStream().write(2);
    }
}
