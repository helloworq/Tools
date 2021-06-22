package com.zlutil.tools.toolpackage.leetcode.huawei;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        List<Integer> list = new ArrayList<>();
//        while (in.hasNextInt()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
//            list.add(in.nextInt());
//        }
        //1 2 3
        Scanner in = new Scanner(System.in);
        int range = in.nextInt();
        int position = in.nextInt();

        new Main().find(range,position);
    }

    public void find(int range, int position) {
        new Thread().start();
        HashMap hashMap=new HashMap();
        hashMap.size();
    }
    //通项

    //123 321
    //132 231
    //213 312
    //123 213 312
    //123   123 132
    //231   213 231
    //312   312 321

    //1 1 1*1
    //2 12 1*2
    //3 123 132 1*2*3
    //4 1234 1243 1324 1342 1432 1423 1*2*3*4
    //  2134 2143 2314 2341 2413 2431
    //5 12345 12354 12453 12435 12534 12543 24

    //1 1 2 6 10
    //213
    //3


    public static int jiecheng(int n, int res) {
        while (n > 1) {
            res = res * (n - 1);
            return jiecheng(n - 1, res);
        }
        return res;
    }
}

//public class Main {
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        List<Integer> list = new ArrayList<>();
//        while (in.hasNextInt()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
//            list.add(in.nextInt());
//        }
//        Map<Integer, Long> res = list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//        Long max = res.values().stream().max(Comparator.comparing(Long::intValue)).get();
//        //4
//        List<Integer> prevList = new ArrayList<>();
//        for (Map.Entry<Integer, Long> e : res.entrySet()) {
//            if (e.getValue().equals(max)) {
//                prevList.add(e.getKey());
//            }
//        }
//        List<Integer> resList = list.stream().filter(prevList::contains).collect(Collectors.toList());
//        //计算中位数
//        double middleValue;
//        int position = resList.size() / 2;
//        if (resList.size() % 2 == 0) {
//            middleValue = ((double) resList.get(position - 1) + (double) resList.get(position)) / 2;
//        } else {
//            middleValue = resList.get(position);
//        }
//        if (String.valueOf(middleValue).contains(".0")) {
//            System.out.println((int) middleValue);
//        }else {
//            System.out.println(middleValue);
//        }
//    }
//}
