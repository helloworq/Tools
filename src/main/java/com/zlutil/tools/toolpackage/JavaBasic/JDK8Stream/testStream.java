package com.zlutil.tools.toolpackage.JavaBasic.JDK8Stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * filter——接受Lambda，从流中排除某些元素。在本例中，通过传递lambda d ->d.getCalories() >300，选择出热量超过300卡路里的菜肴。
 *
 * map——接受一个Lambda，将元素转换成其他形式或提取信息。在本例中，通过传递方法引用Dish : : getName，相当于Lambda d -> d.getName()，提取了每道菜的菜名。
 *
 *
 */
public class testStream {

    public static void main(String[] args) {
        //集合可以转换成stream
        List<String> list1 = Arrays.asList(StringData.NK_PLACE_INFO.split(" "));
        //filter过滤器(定义变量q,大意是流里的单个元素，然后条件q.length判断是否大于5)
        //System.out.println("流内元素数量: "+list.stream().count());
        List<String> list = Arrays.stream(StringData.Emperores.split(" ")).collect(Collectors.toList());
        //list.stream().filter(d->d.length()==2).forEach(System.out::println);

        list.stream().filter(d->d.length()>10).forEach(System.out::println);
    }
}
