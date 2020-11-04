package com.zlutil.tools.toolpackage.JavaBasic.MyIO;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Download_links {
    //此类用来获取目标网页链接
    public static String downloadLinks(String URL) {
        //通过目标链接获取网页源码
        String targetHTML = (getURLMessage.getMessage(URL));
        //开始解析，获取网页内所有的链接。这里的正则表达式属于拿来主义。。
        String patt = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(patt);
        Matcher matcher = pattern.matcher(targetHTML);
        HashSet<String> hashSet = new HashSet<>();
        while (matcher.find()) {
            //结果集筛选，符合长度的字符留下存入hashset确保结果的唯一性
            //知乎网页的标准形式：
            //https://www.zhihu.com/question/58498720/answer/617768326
            //https://pic3.zhimg.com/50/v2-4fd5bfe8b9094f011f7210358449df8a_hd.jpg
            if (matcher.group().length() == DownLoad_My_Configs.zhihu_standard_picLinks.length()) {
                hashSet.add(matcher.group());
            }
        }
        //链接获取完毕，写入到txt文件。
        Iterator iterator = hashSet.iterator();
        int count = 0;
        try {
            for (int i = 0; i < hashSet.size(); i++) {
                new RW_File().write_txt(DownLoad_My_Configs.links_Local_save_path,
                        iterator.next().toString() + "\n", true);
            }
        } catch (IOException e) {
            System.out.println("写入失败！");
            e.printStackTrace();
        }
        return "htmlLinks写入完成！";
    }
}
