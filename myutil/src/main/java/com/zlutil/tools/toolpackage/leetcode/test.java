package com.zlutil.tools.toolpackage.leetcode;


import java.util.*;

public class test {
    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        a.add("1");
        a.add("2");
        a.add("3");
        a.add("4");
        a.add("5");

        List<String> b = new ArrayList<>();
        b.add("3");
        b.add("4");
        b.add("5");
        b.add("6");
        b.add("7");

        List<String> res = new ArrayList<>();


        res.addAll(a);

        for (String s : b) {
            if (a.contains(s)) {
                res.remove(s);
            }
        }


        String file = "abcdecfg";
        //System.out.println(file.lastIndexOf("c"));
        //System.out.println(file.indexOf("c"));

        //res.stream().forEach(System.out::println);


        Long aaa = 1L;
        //System.out.println("===>"+Long.valueOf(aaa));

        List<String> list = new ArrayList<>(Arrays.asList(
                new String[]{"abcde3331", "bcdef1116", "cdefg222"}));

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
