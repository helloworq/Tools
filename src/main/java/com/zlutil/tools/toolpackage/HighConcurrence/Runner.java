package com.zlutil.tools.toolpackage.HighConcurrence;


public class Runner {
    public static void main(String[] args) {
        System.out.println("开始运行");

        new Thread(() -> System.out.println("hello thread")).start();

        System.out.println("运行结束");
    }
}
