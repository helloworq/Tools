package com.zlutil.tools.toolpackage.RE;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReRunner {
    public static void main(String[] args) {
        String str = "[\n" +
                "          \"86056331-0aae-4b72-a973-e718e4148053\",\n" +
                "          \"5f84060c-70a5-4f8d-88c6-cdd04df27c71\",\n" +
                "          \"2b0f464a-466a-40f9-8f11-2fab07267e33\",\n" +
                "          \"102e360f-cc71-447e-8b53-382f22f51fb2\",\n" +
                "          \"a15989c6-0019-4386-804d-55608941ef88\"\n" +
                "        ]";
        List<String> list=Arrays.asList(str.replace("[","").replace("]","").trim().split(","));
        list.forEach(System.out::println);

    }

    public static String getAppointmentString(String appointmentChar,String handle){
        if (handle.contains(appointmentChar) && handle.split(appointmentChar).length > 1) {
            return handle.split("###")[1];
        }else {
            return "";
        }
    }


    /**
     * 获取指定字符串前limit位的中文串
     *
     * @param handle
     * @param limit
     * @return
     */
    public static String getZhCharWithRegex(String handle, Integer limit) {
        String pattern = "[\\u4E00-\\u9FA5]+";
        if (Objects.isNull(handle) || handle.length() <= 0) {
            return null;
        }
        String[] splitStr = handle.split("");
        List<String> list = Arrays.stream(splitStr)
                .filter(ele -> Pattern.matches(pattern, ele))
                .collect(Collectors.toList());
        if (list.size() <= 0) {
            return null;
        }

        String res = String.join("", list);

        if (res.length() > limit) {
            return res.substring(0, limit);
        }
        return res;
    }
}
