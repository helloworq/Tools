package com.zlutil.tools.toolpackage.Constant;

import lombok.AllArgsConstructor;

public class ConstantTest {

    public static void main(String[] args) {
        System.out.println(Fruit.Apple.name());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(Season.Autumn + " is " + Season.Autumn.seasonName);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(Car.Auto + " price is " + Car.Auto.s + " and the price unit is " + Car.Auto.unit);
    }

    enum Fruit {

        Apple,
        Orange,
        WaterMeleon

    }

    @AllArgsConstructor
    enum Season {

        Spring("春天"),
        Summer("夏天"),
        Autumn("秋天"),
        Winter("冬天");

        private final String seasonName;

//        public String getSeasonName() {
//            return seasonName;
//        }
//
//        Season(String seasonName) {
//            this.seasonName = seasonName;
//        }
    }

    @AllArgsConstructor
    enum Car {

        Benz("100", "$"),
        Bmw("10", "$"),
        Auto("1", "$");

        private final String s;
        private final String unit;
    }
}
