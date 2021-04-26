package com.zlutil.tools.toolpackage.DataStructure.Sort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * 快速排序
 * [12,312,321,32,3,21,32,13,213,23,21323,3,12]
 * left                                        right
 */
public class QuickSort {
    public static void main(String[] args) {
        Map map=new HashMap();
        TreeSet treeSet=new TreeSet();



        int[] data = new int[]{
                1, 3, 2, 5, 4, 5, 5
        };
        sort(data);
        System.out.println(Arrays.toString(data));
    }

    public static void sort(int[] data) {
        int left = 0;
        int right = data.length - 1;
        qs(data, left, right);
    }

    public static void qs(int[] data, int l, int r) {
        if (l > r) {
            return;
        }
        int prev = data[l];
        int i = l;
        int j = r;
        while (i < j) {
            // 顺序很重要，先从右边开始往左找，直到找到比base值小的数
            while (data[j] >= prev && i < j) {
                j--;
            }

            // 再从左往右边找，直到找到比base值大的数
            while (data[i] <= prev && i < j) {
                i++;
            }

            if (i < j) {
                swap(i, j, data);
            }
        }
        // 将基准数放到中间的位置（基准数归位）
        swap(i, l, data);
        qs(data, l, i - 1);
        qs(data, i + 1, r);
    }

    public static void quickSort(int[] a, int l, int r) {

        if (l < r) {
            int i, j, x;

            i = l;
            j = r;
            x = a[i];
            while (i < j) {
                while (i < j && a[j] > x)
                    j--; // 从右向左找第一个小于x的数
                if (i < j)
                    a[i++] = a[j];
                while (i < j && a[i] < x)
                    i++; // 从左向右找第一个大于x的数
                if (i < j)
                    a[j--] = a[i];
            }
            a[i] = x;
            quickSort(a, l, i - 1); /* 递归调用 */
            quickSort(a, i + 1, r); /* 递归调用 */
        }
    }

    public static void swap(int a, int b, int[] data) {
        int prev = data[a];
        data[a] = data[b];
        data[b] = prev;
    }
}
