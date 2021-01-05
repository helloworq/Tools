package com.zlutil.tools.toolpackage.HighConcurrence;

import lombok.SneakyThrows;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorTest {
    public static void main(String[] args) {
        /*AtomicInteger atomicInteger=new AtomicInteger(0);
        atomicInteger.incrementAndGet();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> System.out.println("SSSS"));*/
        Aobing a=new Aobing();
        a.start();
        for (;;){
            if (a.isFlag()){
                System.out.println("有点东西");
            }
        }
    }
}
class Aobing extends Thread{
    private volatile boolean flag=false;

    public boolean isFlag(){
        return flag;
    }

    @SneakyThrows
    @Override
    public void run(){
        String a;
        Thread.sleep(1000);
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        flag=true;
        System.out.println("flag="+flag);
    }
}
