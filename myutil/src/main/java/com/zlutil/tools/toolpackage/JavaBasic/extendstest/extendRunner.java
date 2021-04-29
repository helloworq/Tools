package com.zlutil.tools.toolpackage.JavaBasic.extendstest;

import java.util.Arrays;

public class extendRunner {
    public static void main(String[] args) {
        Child child = new Child();
        System.out.println(child.getClass().getFields().length);
        Arrays.stream(child.getClass().getFields()).forEach(
                System.out::println
        );
        System.out.println("===================================");
        System.out.println(child.getClass().getMethods().length);
        Arrays.stream(child.getClass().getMethods()).forEach(
                System.out::println
        );
    }
}
