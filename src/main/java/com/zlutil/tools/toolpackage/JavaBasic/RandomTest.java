package com.zlutil.tools.toolpackage.JavaBasic;

import java.math.BigDecimal;
import java.util.Random;

public class RandomTest {
    public static void main(String[] args) {
        /*for (int i = 0; i < 10; i++) {
            System.out.println(new Random().nextInt(10));
        }*/
        BigDecimal b=new BigDecimal(1.0);
        BigDecimal valueBD = new BigDecimal("1.96:1").divide(b, 2, BigDecimal.ROUND_HALF_UP);
        System.out.println(valueBD);
    }
}
