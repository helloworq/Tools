package com.zlutil.tools.toolpackage.Pattern.single;

public class Runner {
    public static void main(String[] args) {
        Singleton singleton=Singleton.getSingleton();
        singleton.run();

        Singleton singletonNew=Singleton.getSingleton();
        singletonNew.run();
    }
}
