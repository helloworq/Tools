package com.zlutil.tools.toolpackage.JavaBasic.extendstest;

import java.util.Arrays;

public class ChildLess extends Base{
    public ChildLess(){
        super("mark","tag");
    }

    public void printMsg() {
        Class class1=this.getClass();
        System.out.print("方法: ");
        Arrays.stream(class1.getMethods()).forEach(System.out::println);
        System.out.print("属性: ");
        Arrays.stream(class1.getFields()).forEach(System.out::println);
    }
}
