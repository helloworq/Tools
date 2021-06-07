package com.zlutil.tools.toolpackage.Stream;

import java.util.*;
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

        //flatMap(扁平化Map) 用法: flatMap会返回一个流，处理时会将入参拆开，然后如果处理时传了一个多流参数则会一对多处理如2所示
        List<String> list = new ArrayList<>(Arrays.asList("dasdas", "Dasdasds"));
        list.stream()
                .map(word -> word.split(""))
                .flatMap(e -> Stream.of(e))
                .forEach(System.out::println);
        //2
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(4, 5));
        list1.stream().flatMap(e -> list2.stream().map(ele -> "(" + e + "-" + ele + ")")).forEach(System.out::println);

        //统计字符出现次数
        Map map = doubleList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
