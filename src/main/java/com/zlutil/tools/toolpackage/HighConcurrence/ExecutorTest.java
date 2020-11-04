package com.zlutil.tools.toolpackage.HighConcurrence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> System.out.println("SSSS"));
    }
}
