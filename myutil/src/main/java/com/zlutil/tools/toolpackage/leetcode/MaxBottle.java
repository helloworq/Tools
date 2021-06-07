package com.zlutil.tools.toolpackage.leetcode;

/**
 * 有这样一道智力题：“某商店规定：三个空汽水瓶可以换一瓶汽水。小张手上有十个空汽水瓶，她最多可以换多少瓶汽水喝？”答案是5瓶，方法如下：先用9个空瓶子换3瓶汽水，喝掉3瓶满的，喝完以后4个空瓶子，用3个再换一瓶，喝掉这瓶满的，这时候剩2个空瓶子。然后你让老板先借给你一瓶汽水，喝掉这瓶满的，喝完以后用3个空瓶子换一瓶满的还给老板。如果小张手上有n个空汽水瓶，最多可以换多少瓶汽水喝？
 * <p>
 * 输入描述:
 * 输入文件最多包含10组测试数据，每个数据占一行，仅包含一个正整数n（1<=n<=100），表示小张手上的空汽水瓶数。n=0表示输入结束，你的程序不应当处理这一行。
 * <p>
 * <p>
 * 输出描述:
 * 对于每组测试数据，输出一行，表示最多可以喝的汽水瓶数。如果一瓶也喝不到，输出0。
 */
public class MaxBottle {


    int num = 0;
    public static void main(String[] args) {
        int empty_number = 10;

        new MaxBottle().drinkRest(empty_number, 3);
        //System.out.println(num);
    }

    public void drinkRest(int empty_bottle, int rate) {
        //bottle必须是3的倍数,计算3的倍数的满饮料瓶能剩多少
        int chu_num = empty_bottle / rate;//判断可换的可乐数量
        int res_empty_bottle = chu_num == 0 ? empty_bottle : empty_bottle - rate * chu_num;

        num += chu_num;
        if (chu_num == 0) {
            System.out.println(num);
        } else if (chu_num == 1) {
            if (res_empty_bottle == 2) {
                num += 1;
            }
        } else if (chu_num > 1 && res_empty_bottle == 0) {
            num += chu_num;
        } else if (chu_num > 1 && res_empty_bottle > 0) {
            num += chu_num;
            drinkRest(chu_num + empty_bottle, 3);
        }
        System.out.println(num);
    }
}
