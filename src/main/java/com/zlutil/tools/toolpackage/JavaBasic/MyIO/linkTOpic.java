package com.zlutil.tools.toolpackage.JavaBasic.MyIO;/*
package MyIO;



import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class linkTOpic {
    //此类是根据网页链接获取图片链接再下载，而Download_pic是直接根据图片链接下载图片
    //大致和Download_links差不多，只是这个是下载图片
    public String linkTOpic(String URL) throws Exception{
        //通过目标链接获取网页源码
        String targetHTML=(getURLMessage.getMessage(URL));
        //开始解析，获取网页内所有的链接。这里的正则表达式属于拿来主义。。
        String patt="(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        Pattern pattern=Pattern.compile(patt);
        Matcher matcher=pattern.matcher(targetHTML);
        HashSet<String> hashSet=new HashSet<>();;
        while (matcher.find()) {
            //结果集筛选，符合长度的字符留下存入hashset确保结果的唯一性
            //知乎网页的标准形式：
            //https://www.zhihu.com/question/58498720/answer/617768326
            //https://pic3.zhimg.com/50/v2-4fd5bfe8b9094f011f7210358449df8a_hd.jpg
            if(matcher.group().length()==DownLoad_My_Configs.zhihu_standard_picLinks.length()){
                hashSet.add(matcher.group());
            }
        }
        //图片链接存储获取完毕，开始下载
        //使用迭代器遍历hashset结果集并且下载图片
        Iterator iterator=hashSet.iterator();
        for(int i=0;i<hashSet.size();i++){
            Thread.sleep(1000);
            //开始下载图片，本地地址不写满使用下载文件名进行赋值
            //直接往一下链接目录写入文件。需要修改路径时只需要修改这里。文件名根据获取到的数据自动赋值
            Download_pic.downloadPicture(iterator.next().toString(), DownLoad_My_Configs.pic_Local_save_path);
        }
        return "图片下载完毕！";
    }
}
*/
