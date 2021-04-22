package com.zlutil.tools.toolpackage.Stream;

import java.net.Socket;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Runner {
    public static void main(String[] args) {
        //随机生成100个数字
        List<Double> doubleList = Stream.generate(Math::random).limit(100).collect(Collectors.toList());

        //将生成的100个随机数转化为字符串并且随机截取一部分并按长度排序
        List<String> stringList = doubleList.stream()
                .map(String::valueOf)
                .map(ele -> ele.substring(new Random().nextInt(ele.length())))
                .sorted(Comparator.comparing(String::length))
                .collect(Collectors.toList());

        //将生成的100个随机数转化为map，(a,b)->a 参数表示遇到重复key取之前的值
        Map<String, String> stringMap = doubleList.stream()
                .map(String::valueOf)
                .collect(Collectors.toMap(String::toString, Function.identity(), (a, b) -> a));

        Socket s=new Socket();

    }
}
