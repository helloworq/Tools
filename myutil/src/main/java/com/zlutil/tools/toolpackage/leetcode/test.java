package com.zlutil.tools.toolpackage.leetcode;


import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.util.*;

public class test {
    public static void main(String[] args) throws IOException {
        Thumbnails.of("C:\\Users\\12733\\Desktop\\02+市域高程分析图.jpg")
                .scale(0.1f)
                .toFile("C:\\Users\\12733\\Desktop\\03+市域高程分析图.jpg");

        String file = "abcdecfg";
        //System.out.println(file.lastIndexOf("c"));
        //System.out.println(file.indexOf("c"));

        //res.stream().forEach(System.out::println);


        Long aaa = 1L;
        //System.out.println("===>"+Long.valueOf(aaa));

        List<String> list = new ArrayList<>(Arrays.asList("abcde3331", "bcdef1116", "cdefg222"));

        getSameStringBetween("cdeffg2221t2", "bcdeff1116t2");
    }

    /**
     * 通过传入字符串列表找到里面的最大相同字串
     * example；abcde3331,bcdef1116,cdefg222    res:cde
     *
     * @param list
     * @return
     */
    public String findSameStringFromList(List<String> list) {
        if (list.size() < 2 || null == list)
            return null;

        return null;
    }

    public static String getSameStringBetween(String strA, String strB) {
        String resStr = "";
        List<Map<Integer, Integer>> list = new ArrayList<>();
        if (strA.length() > strB.length()) {
            String prev = strA;
            strA = strB;
            strB = prev;
        }

        char[] ch = strA.toCharArray();
        String A = "   cdeffg2221t2     ,     bcdeff1116t2    ";
        for (int i = 0; i < ch.length; i++) {
            Map<Integer, Integer> map = new HashMap<>();
            if (strB.indexOf(ch[i]) != -1) {
                map.put(i, strB.indexOf(ch[i]));
                list.add(map);
            }
        }

        list.stream().forEach(System.out::print);
        return "null";
    }
}
