package com.zlutil.tools.toolpackage.JavaBasic.extendstest;

public class extendRunner {
    public static void main(String[] args) {
        Child child=new Child();
        child.printMsg();
        System.out.println("===================");
        ChildLess childLess=new ChildLess();
        childLess.printMsg();
    }
}
