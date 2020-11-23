package com.zlutil.tools.toolpackage.JavaBasic;

public class EnumTest {
    public enum direction {WEST, EAST, SOUTH, NORTH}

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            try {
                if (i==2||i==5){
                    throw new RuntimeException();
                }
                System.out.println(i);
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("捕获错误"+i);
            }
        }
    }
}
