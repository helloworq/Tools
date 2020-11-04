package com.zlutil.tools.toolpackage.JavaBasic.JDK8Stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class testStream {

    public static void main(String[] args) {
        //集合可以转换成stream
        List<String> list = Arrays.asList(StringData.NK_PLACE_INFO.split(" "));
        //filter过滤器(定义变量q,大意是流里的单个元素，然后条件q.length判断是否大于5)
        //System.out.println("流内元素数量: "+list.stream().count());


        System.out.println("www" + "dsadsadasds".join(","));
        //同样如果不是集合，而是数组的话可以这么写
        Stream<String> streamArray = Stream.of(StringData.NK_PLACE_INFO.split(" "));
        long j = streamArray.filter(q -> q.length() > 5).count();

        String[] strSS = StringData.WORLD_PLACE_INFO.split("\n");

    }

    public static void testworld() {
        String after_Str = StringData.WORLD_PLACE_INFO.replace("\n", " ");
        String[] str_Arr = after_Str.split(" ");
        System.out.println(str_Arr.length);
    }
}
