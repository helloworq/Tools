package com.zlutil.tools.toolpackage.DataStructure.Map;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Transactional
public class MyMap {
    public static void main(String[] args) {
        //System.out.println(1<<4);
        Set<String> hashSet = new HashSet<>();


        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("1","2");

        Map<String, String> hashtable = new Hashtable<>();

        Map<String, String> linkedHashMap = new LinkedHashMap<>();

        Map<String, String> treeMap = new TreeMap<>();

        Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();
    }
}
