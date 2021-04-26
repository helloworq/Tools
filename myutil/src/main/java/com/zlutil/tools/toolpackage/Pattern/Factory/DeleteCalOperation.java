package com.zlutil.tools.toolpackage.Pattern.Factory;


public class DeleteCalOperation extends CalOperation {
    public Integer calculate() {
        if (null == numA || null == numB) {
            System.out.println("存在未赋值数据");
            return null;
        } else {
            result = numA - numB;
            return result;
        }
    }
}
