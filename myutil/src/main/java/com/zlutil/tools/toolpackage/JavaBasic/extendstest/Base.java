package com.zlutil.tools.toolpackage.JavaBasic.extendstest;

public class Base {

    public Base(String mark,String tag){
        this.mark=mark;
        this.tag=tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    private String tag;
    private String mark;

    public void printMsg() {
        System.out.println(("This is Base"));
    }
}
