package com.zlutil.tools.toolpackage.HighConcurrence;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPool {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new ThreadPool().func("2 2 3 0 4");
    }

    String a = "1000";

    public int func(String str) {
        //String str = "2 2 3 0 4";//1 2 2 0 1 3  :  1 2 2 1 3
        String[] strings = str.split(" ");
        int[] ints = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            ints[i] = Integer.parseInt(strings[i]);
        }
        //遍历元素，找到在可选范围内的最大位移位置
        walk(0, ints, 1, 1);
        System.out.println(a);
        return Integer.parseInt(a);
    }

    //              2 2 3 0 4 5 2 1 3
    public void walk(int current, int[] nums, int currentDeepth, int step) {
        //递归寻找步长相加大于等于length的序列
        for (int i = current + 1; i <= nums[current]; i++) {
            if (step + nums[i] >= nums.length) {
                if (Integer.parseInt(a) > currentDeepth) {
                    System.out.println(currentDeepth);
                    a = String.valueOf(currentDeepth);
                }
            }
            walk(i, nums, ++currentDeepth, step + i);
        }
    }

    /**
     *         int number = 1;
     *         //1 2 2 3 3 3 4 4 4 4 5 5 5 5 5
     *         //1 3 6 10
     *         //n(n+1)/2
     *         //计算出当前天数所在区间
     *         int n = 1;
     *         while (true) {
     *             int currentData = n * (n + 1) / 2;
     *             if (number < currentData) {
     *                 break;
     *             }
     *             n++;
     *         }
     *
     *         int result = 0;
     *         for (int i = 1; i <= n - 1; i++) {
     *             result = result + i * i;
     *         }
     *         number = number - (n * (n - 1) / 2);
     *         result = result + (number * n);
     *         System.out.println(result);
     */
}
