package com.zlutil.tools.toolpackage.JavaBasic;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Random;

public class RandomTest {
    public static void main(String[] args) {
        /*for (int i = 0; i < 10; i++) {
            System.out.println(new Random().nextInt(10));
        }*/
        /*BigDecimal b=new BigDecimal(1.0);
        BigDecimal valueBD = new BigDecimal("1.96:1").divide(b, 2, BigDecimal.ROUND_HALF_UP);
        System.out.println(valueBD);*/
        A a=new A();
        a.setA("A");

        B b=new B();
        //BeanUtils.copyProperties(a,b);
        System.out.println(b.getA());
    }
}
@Data
class A{
    private String a;
}

@Data
class B{
    private String a;
}