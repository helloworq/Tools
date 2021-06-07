package com.zlutil.tools.toolpackage.Pattern.single;

public class Singleton {

    private Integer count = 0;

    private static Singleton singleton = new Singleton();

    private Singleton() {
    }

    public static Singleton getSingleton() {
        return singleton;
    }

    public void run() {
        System.out.println("当前count: " + count);
        count++;
    }
}
