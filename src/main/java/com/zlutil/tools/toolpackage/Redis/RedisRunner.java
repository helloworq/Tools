package com.zlutil.tools.toolpackage.Redis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RedisRunner {
    public static void main(String[] args) throws IOException {
//        for (int i = 0; i < 10; i++) {
//            long start1 = System.currentTimeMillis();
//            FileUtil.loopFiles("C:\\Users\\12733\\Desktop\\aspose")
//                    .stream().map(File::getName).forEach(System.out::print);
//            System.out.println();
//            //System.out.println("开始计算Hutool执行时间");
//            long end1 = System.currentTimeMillis();
//            System.out.println("Hutool执行时间: " + (end1 - start1));
//        }

        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            Files.walk(Paths.get("C:\\Users\\12733\\Desktop\\aspose"))
                    .map(Path::getFileName).forEach(System.out::print);
            System.out.println();
            System.out.println("开始计算NIO执行时间");
            long end = System.currentTimeMillis();
            System.out.println("NIO执行时间: " + (end - start));
        }

    }
}
