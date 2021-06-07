package com.zlutil.tools.controller;

import lombok.Data;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class FileInfo {
    private String filePath;
    private String fileName;
    private Long fileSize;

    public FileInfo() {
    }

    public FileInfo(String filePath, String fileName, Long fileSize) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\12733\\Desktop\\s.txt";

        Map<String, String> mapIn = new ConcurrentHashMap<>();
        mapIn.put("aa", "2");
        Map<String, String> mapIn2 = new ConcurrentHashMap<>();
        mapIn2.put("aa", "2");
        Map<String, String> mapIn3 = new ConcurrentHashMap<>();
        mapIn3.put("aa", "3");
        Map<String, String> mapIn4 = new ConcurrentHashMap<>();
        mapIn4.put("aa", "5");
        Map<String, String> mapIn5 = new ConcurrentHashMap<>();
        mapIn5.put("aa", "5");
        Map<String, String> mapIn6 = new ConcurrentHashMap<>();
        mapIn6.put("aa", "7");

        Map<String, Map<String, String>> map = new ConcurrentHashMap<>();
        map.put("1", mapIn);
        map.put("5", mapIn2);
        map.put("3", mapIn3);
        map.put("10", mapIn4);
        map.put("15", mapIn5);
        map.put("6", mapIn6);

        map.forEach((k, v) -> System.out.println(k + "-" + v));
        System.out.println("============================");
        map.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getValue().get("aa")))
                .skip(3L).forEach(System.out::println);

//        Files.readAllLines(Paths.get(path)).stream()
//                .filter(e -> e.contains("upper"))
//                .map(e -> e.substring(e.indexOf("upper")))
//                .map(e -> e.replace("upper", "").replace("('", "").replace("');", ""))
//                .map(e -> "drop table" + e + ";")
//                .forEach(System.out::println);
        //FileUtil.writeUtf8Lines(list, path);

//        List<String> list1 = Files.readAllLines(Paths.get(path)).stream()
//                .map(e -> {
//                    if (e.contains("drop")) {
//                        return e.replace("if exists ", "");
//                    }
//                    return e;
//                })
//                .collect(Collectors.toList());
        //FileUtil.writeUtf8Lines(list1, path);


//        String path = "C:\\Users\\12733\\Desktop\\res-oracle.txt";
//        StringBuilder stringBuilder = new StringBuilder();
//        FileUtil.readUtf8Lines(path).forEach(e -> stringBuilder.append(e).append("\n"));
//
//        int start = 0;
//        String beginString = "create table ";
//        String endString = "------------------";
//        while (stringBuilder.indexOf(beginString, start) != -1) {
//            int startPosition = stringBuilder.indexOf(beginString, start);
//            int endPosition = stringBuilder.indexOf(endString, startPosition);
//
//            String coreString = stringBuilder.substring(startPosition, endPosition);
//            //拼接头尾
//            int tableNameEnd = coreString.indexOf("(");
//
//            String tableName = coreString.substring(beginString.length(), tableNameEnd).trim();
//            String tableNameRemoveChar = tableName.replace("\"", "");
//            String end = "';end if;";
//            String fullFormatString =
//                    "        select count(*) into countTab from user_tables t where t.table_name = " +
//                            "upper('" + tableNameRemoveChar + "');" +
//                            "        if countTab = 0 then \n" +
//                            "execute immediate '"
//                            + coreString.replace(tableName, tableNameRemoveChar).replace(";","")
//                            + end;
//
//            System.out.println(fullFormatString);
//            start = endPosition;
//        }


    }
}
