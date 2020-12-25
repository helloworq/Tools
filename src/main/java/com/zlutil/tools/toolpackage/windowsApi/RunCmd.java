package com.zlutil.tools.toolpackage.windowsApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class RunCmd {
    public static void main(String[] args) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec("cmd.exe /c you-get https://www.bilibili.com/video/BV1w54y1t7Mo");
        int status = process.waitFor();

        System.out.println(status);
        InputStream in = process.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName("utf-8")));
        String line = br.readLine();
        while(line!=null) {
            System.out.println(line);
            line = br.readLine();
        }
    }
}
