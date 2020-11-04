package com.zlutil.tools.toolpackage.JavaBasic.MyIO;/*
package MyIO;

import HighConcurrence.simpleClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class GetWeiBoUserMainInfo {
    */
/**
 * 测试样例i
 * URI uri=new URIBuilder("https://m.weibo.cn/api/container/getIndex")
 * .setParameter("uid","7022472393")
 * .setParameter("type","uid")
 * .setParameter("value","7022472393")
 * .setParameter("containerid","1005057022472393").build();
 *
 * @param uri
 * @return
 * @throws URISyntaxException
 * 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格
 * 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格
 *//*

    public String getUserMainInfo(URI uri) throws URISyntaxException {
        JSONObject jsonObject=JSONObject.parseObject(new simpleClient().sendGet(uri));
        JSONObject res=new JSONObject();
        JSONObject jsonObjectUserInfo=jsonObject.getJSONObject("data").getJSONObject("userInfo");
        res.put("描述",jsonObjectUserInfo.getString("description"));
        res.put("头像链接",jsonObjectUserInfo.getString("avatar_hd"));
        res.put("名字",jsonObjectUserInfo.getString("screen_name"));
        res.put("关注人数",jsonObjectUserInfo.getString("follow_count"));
        res.put("粉丝数",jsonObjectUserInfo.getString("followers_count"));
        res.put("目标ID",jsonObjectUserInfo.getString("id"));
        JSONArray jsonArrayTabs = jsonObject.getJSONObject("data").getJSONObject("tabsInfo").getJSONArray("tabs");

        //ID长度均为16，为了保证更好获取到id值选择采取indexof方法，不采取spilt数组获取
        res.put("主页containerID",jsonArrayTabs.get(0).toString().substring(
                jsonArrayTabs.get(0).toString().indexOf("containerid")+14,
                jsonArrayTabs.get(0).toString().indexOf("containerid")+30)
        );
        res.put("微博containerID",jsonArrayTabs.get(1).toString().substring(
                jsonArrayTabs.get(1).toString().indexOf("containerid")+14,
                jsonArrayTabs.get(1).toString().indexOf("containerid")+30)
        );
        res.put("相册containerID",jsonArrayTabs.get(2).toString().substring(
                jsonArrayTabs.get(2).toString().indexOf("containerid")+14,
                jsonArrayTabs.get(2).toString().indexOf("containerid")+30)
        );
        System.out.println(res.toJSONString());

        return res.toJSONString();
    }


    //Unicode转中文方法
    private static String unicodeToCn(String unicode) {
        */
/** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*//*

        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }
}
*/
