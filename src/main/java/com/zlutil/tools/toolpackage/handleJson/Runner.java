package com.zlutil.tools.toolpackage.handleJson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Runner {
    public static void main(String[] args) {
        String patt = "\\{([^}])*\\}";
        Pattern pattern = Pattern.compile(patt);
        Matcher matcher = pattern.matcher("\n" +
                "    },\n" +
                "            \"label\": \"湖南省\",\n" +
                "            \"value\": \"430000000000\"\n" +
                "          },\n" +
                "          \"t\": {\n" +
                "            \"label\": \"2015年\",\n" +
                "            \"value\": \"2015\"\n" +
                "          },\n" +
                "          \"i\": {\n" +
                "            \"label\": \"高污染企业个数\",\n" +
                "            \"value\": \"500871\"\n" +
                "          },\n" +
                "          \"updateTime\": 1596691923000,\n" +
                "          \"id\": \"5f2b95d38064860f9ffafc39\",\n" +
                "          \"hcqfw\": {\n" +
                "            \"label\": \"2_5公里范围\",\n" +
                "            \"value\": \"5glfw\"\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"jcz\": {\n" +
                "            \"label\": \"227: \"2010\"\n" +
                "          },\n" +
                "          \"i\": {\n" +
                "            \"label\": \"高污染企业个数\",\n" +
                "            \"value\": \"500871\"\n" +
                "          },\n" +
                "          \"updateTime\": 1596691922000,\n" +
                "          \"id\": \"5f2b95d28064860f9ffafbff\",\n" +
                "          \"hcqfw\": {\n" +
                "            \"label\": \"1公里范围\",\n" +
                "            \"value\": \"1glfw\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"status\": \"200\",\n" +
                "  \"message\": \"success\"\n" +
                "");
        while (matcher.find()) {
            //结果集筛选，符合长度的字符留下存入hashset确保结果的唯一性
            //知乎网页的标准形式：
            //https://www.zhihu.com/question/58498720/answer/617768326
            //https://pic3.zhimg.com/50/v2-4fd5bfe8b9094f011f7210358449df8a_hd.jpg
            System.out.println(matcher.group());
        }
    }


    /**
     * @param jsonString 查询指标值返回结果 json字符串数据
     * @param dynamic
     * @return
     */
    public static Object handleJson(String jsonString, String dynamic, String defaultDynamicValue) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONObject("data").getJSONArray("data");
        JSONArray jsonArrayres = new JSONArray();
        String dynamicInnerValue = "";
        if (dynamic != "" && jsonArray.size() > 0) {
            dynamicInnerValue = jsonArray.getJSONObject(0).getJSONObject(dynamic).getString("label");
        } else {
            dynamicInnerValue = defaultDynamicValue;
        }

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObjectprev = jsonArray.getJSONObject(i);
            String year = jsonObjectprev.getJSONObject("t").getString("label");
            String prov = jsonObjectprev.getJSONObject("r").getString("label");
            String jcz = jsonObjectprev.getJSONObject("jcz").getString("value");
            if (null == getYearIndex(jsonArrayres, year) && null == getProvIndex(jsonArrayres, year, prov)) {
                //加入stack成员
                JSONObject stackObj = new JSONObject();
                stackObj.put("name", dynamicInnerValue);
                stackObj.put("value", jcz);
                JSONArray stack = new JSONArray();
                stack.add(stackObj);

                //加入datas成员
                JSONObject datasObj = new JSONObject();
                datasObj.put("name", prov);
                datasObj.put("stacks", stack);
                JSONArray datas = new JSONArray();
                datas.add(datasObj);

                //加入总成员
                JSONObject res = new JSONObject();
                res.put("name", year);
                res.put("datas", datas);
                jsonArrayres.add(res);
            } else if (null != getYearIndex(jsonArrayres, year) && null == getProvIndex(jsonArrayres, year, prov)) {
                //加入stack成员
                JSONObject stackObj = new JSONObject();
                stackObj.put("name", dynamicInnerValue);
                stackObj.put("value", jcz);
                JSONArray stack = new JSONArray();
                stack.add(stackObj);

                //加入datas成员
                JSONObject datasObj = new JSONObject();
                datasObj.put("name", prov);
                datasObj.put("stacks", stack);

                Integer yearIndex = getYearIndex(jsonArrayres, year);
                JSONObject jsonObjectres = jsonArrayres.getJSONObject(yearIndex);
                jsonObjectres.getJSONArray("datas").add(datasObj);

            } else if (null != getYearIndex(jsonArrayres, year) && null != getProvIndex(jsonArrayres, year, prov)) {
                //加入stack成员
                JSONObject stackObj = new JSONObject();
                stackObj.put("name", dynamicInnerValue);
                stackObj.put("value", jcz);
                JSONArray stack = new JSONArray();
                stack.add(stackObj);

                Integer yearIndex = getYearIndex(jsonArrayres, year);
                Integer provIndex = getProvIndex(jsonArrayres, year, prov);
                JSONObject jsonObjectlinshi = jsonArrayres.getJSONObject(yearIndex);
                JSONObject jsonObjectres = jsonObjectlinshi.getJSONArray("datas").getJSONObject(provIndex);

                jsonObjectres.getJSONArray("stacks").add(stack);
            }
        }
        return JSON.toJSON(jsonArrayres);
    }

    public static Integer getYearIndex(JSONArray jsonArray, String year) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String year1 = jsonObject.getString("name");
            if (year1.equals(year)) {
                return i;
            }
        }
        return null;
    }

    public static Integer getProvIndex(JSONArray jsonArray, String year, String prov) {
        Integer yearIndex = getYearIndex(jsonArray, year);
        if (null == yearIndex) {
            return null;
        }
        JSONArray newJsonArray = jsonArray.getJSONObject(yearIndex).getJSONArray("datas");
        for (int i = 0; i < newJsonArray.size(); i++) {
            JSONObject jsonObject = newJsonArray.getJSONObject(i);
            if (jsonObject.getString("name").equals(prov)) {
                return i;
            }
        }
        return null;
    }

    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
}
