package com.zlutil.tools.toolpackage.Pattern.SimpleFactory;

public class MuiltCalOperation extends CalOperation {
    public Integer calculate() {
        if (null == numA || null == numB) {
            System.out.println("存在未赋值数据");
            return null;
        } else {
            result = numA * numB;
            return result;
        }
    }
}
