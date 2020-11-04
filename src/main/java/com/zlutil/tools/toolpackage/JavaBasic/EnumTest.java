package com.zlutil.tools.toolpackage.JavaBasic;

import java.util.Arrays;

public class EnumTest {
    public enum direction {WEST, EAST, SOUTH, NORTH}

    public static void main(String[] args) {
        Arrays.stream(direction.values()).forEach(System.out::println);
        direction a = direction.EAST;
        System.out.println(a.ordinal());
    }
}
