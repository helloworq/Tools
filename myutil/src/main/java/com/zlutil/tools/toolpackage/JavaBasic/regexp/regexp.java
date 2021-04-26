package com.zlutil.tools.toolpackage.JavaBasic.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexp {
    public static void main(String[] args) {
        String targetHTML = "<bpmn2:condi{m==1}tion{m{m==1}==1}Expression {m==1}xsi:type=\"bpmn2:tForma" +
                "lExpression\">{m==1}</bpmn2:cond{m{{m==1}m==1}==1}itionExp{m==1}ression>";
        String pattern = "\\{([^}])*\\}";//提取指定字符串里的数据
        System.out.println(getTargetTextByRegexp(pattern, targetHTML));
    }

    public static String getTargetTextByRegexp(String patt, String originString) {
        String res = "";
        Matcher matcher = Pattern.compile(patt).matcher(originString);
        while (matcher.find()) {
            res += (matcher.group() + "\n");
        }
        return res;
    }
}