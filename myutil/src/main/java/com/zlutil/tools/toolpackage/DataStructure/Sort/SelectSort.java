package com.zlutil.tools.toolpackage.DataStructure.Sort;

import java.util.Arrays;

/**
 * 选择排序
 * <p>
 * 一种最简单的排序算法是这样的:首先，找到数组中最小的那个元素，其次，将它和数组的第一个元素交换位置
 * （如果第一个元素就是最小元素那么它就和自己交换)。再次，在剩下的元素中找到最小的元素，
 * 将它与数组的第二个元素交换位置。如此往复，直到将整个数组排序。这种方法叫做选择排序，
 * 因为它在不断地选择剩余元素之中的最小者。
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] data = new int[]{
                3, 13, 12, 4, 34, 235, 43, 5, 46,
                456, 6, 76, 576, 57, 3123, 5425,
                5, 2423, 423, 25, 2, 5234, 62, 254, 41
        };
        sort(data);
        System.out.println(Arrays.toString(data));
    }

    public static void sort(int[] data) {
        for (int i = 0; i < data.length; i++) {
            int min = i;//保存每轮比较中最小的那个元素的位置
            for (int j = i; j < data.length; j++) {
                if (data[min] > data[j]) {
                    min = j;
                }
            }
            swap(i, min, data);
        }
    }

    public static void swap(int a, int b, int[] data) {
        int prev = data[a];
        data[a] = data[b];
        data[b] = prev;
    }
}
