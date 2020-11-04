package com.zlutil.tools.toolpackage.Pattern.lamdatest;

import java.util.UUID;

public class LamdaRunner {
    public static void main(String[] args) {

        new MyThread(new MyRunnable() {
            @Override
            public void run() {
                System.out.println("不用买票");
                System.out.println("骑电瓶车...");
            }
        }).start();


        new MyThread(() -> {
            System.out.println("这是？");
            System.out.println("这就是神奇的lamda吗？");
        }).start();


        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID().compareTo(UUID.randomUUID()));
    }
}
