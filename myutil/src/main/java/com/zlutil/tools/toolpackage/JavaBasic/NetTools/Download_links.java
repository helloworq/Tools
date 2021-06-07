package com.zlutil.tools.toolpackage.JavaBasic.NetTools;

import com.alibaba.fastjson.JSON;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 此类用来获取目标网页链接
 */
public class Download_links {
    public static void main(String[] args) {
        downloadLinks("https://www.zhihu.com/answer/1124569021");
    }
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
            //System.out.println(matcher.group());
            if (matcher.group().contains("jpg")||matcher.group().contains("zhimg")) {
                hashSet.add(matcher.group());
                Download_pic.downloadPicture(matcher.group(),"D:\\picBed\\Sync\\Pic\\");
            }
        }
        //链接获取完毕，写入到txt文件。
        System.out.println(JSON.toJSONString(hashSet));
//        Iterator iterator = hashSet.iterator();
//        int count = 0;
//        try {
//            for (int i = 0; i < hashSet.size(); i++) {
//                new RW_File().write_txt(DownLoad_My_Configs.links_Local_save_path,
//                        iterator.next().toString() + "\n", true);
//            }
//        } catch (IOException e) {
//            System.out.println("写入失败！");
//            e.printStackTrace();
//        }
        return "htmlLinks写入完成！";
    }
}
