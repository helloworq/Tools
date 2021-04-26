package com.zlutil.tools.toolpackage.JavaBasic;

import java.util.concurrent.locks.Lock;

public class StaticTest {
    public static int count=0;
    public static void inc(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
        System.out.println("线程->"+Thread.currentThread().getName()+"->完成运算，此时count值->"+count);

    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(StaticTest::inc).start();
        }
        System.out.println("循环结束");
    }

}

class StaticClass{
    public static int filedB;
    public static int filedC;
    public static int filedD;
    public static int filedE;
    public static int filedF;

    public int getFiledB() {
        return filedB;
    }

    public void setFiledB(int filedB) {
        StaticClass.filedB = filedB;
    }

    public static int getFiledC() {
        return filedC;
    }

    public static void setFiledC(int filedC) {
        StaticClass.filedC = filedC;
    }

    public static int getFiledD() {
        return filedD;
    }

    public static void setFiledD(int filedD) {
        StaticClass.filedD = filedD;
    }

    public static int getFiledE() {
        return filedE;
    }

    public static void setFiledE(int filedE) {
        StaticClass.filedE = filedE;
    }

    public static int getFiledF() {
        return filedF;
    }

    public static void setFiledF(int filedF) {
        StaticClass.filedF = filedF;
    }
}
