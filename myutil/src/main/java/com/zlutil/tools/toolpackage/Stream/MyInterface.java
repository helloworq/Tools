package com.zlutil.tools.toolpackage.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MyInterface {

    List<String> list = new ArrayList<>();

    public static void main(String[] args) {

    }

    public <T> void cal(Predicate<T> predicate) {
        T a = null;
        a.equals("1");
        predicate.test(a);
    }

}











