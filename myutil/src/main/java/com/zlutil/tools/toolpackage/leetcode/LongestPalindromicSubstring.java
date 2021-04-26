package com.zlutil.tools.toolpackage.leetcode;

import java.util.ArrayList;

/**
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 * <p>
 * 示例 1：
 * <p>
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 * <p>
 * 输入: "cbbd"
 * 输出: "bb"
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LongestPalindromicSubstring {
    public static void main(String[] args) {
        solution solution = new solution();
        System.out.println(solution.getDeepth());
    }

}

class solution {
    int deepth = 0;
    int deepthoushu = 0;
    String s = "eerreerreeuueywu";

    String getDeepth() {
        //从第二个元素开始到倒数第二个元素，同时从元素左边和右边前进，判断是否回文
        char[] sarray = s.toCharArray();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 1; i < s.length() - 1; i++) {
            deepth = 0;
            System.out.println(ArrayLeftAndRightEqualjishu(sarray, i, 1));
            arrayList.add(s.substring(i - deepth, i + deepth + 1));
            System.out.println(s.substring(i - deepth, i + deepth + 1));
        }
        return "";
    }

    int ArrayLeftAndRightEqualjishu(char[] a, int elementOrder, int step) {
        //确保elementOrder不在首末的位置
        if (elementOrder - step >= 0 && elementOrder + step <= a.length - 1) {
            int left = a[elementOrder - step];
            int right = a[elementOrder + step];
            if (left == right) {
                deepth++;
                step++;
                ArrayLeftAndRightEqualjishu(a, elementOrder, step);
            }
        }
        return deepth;
    }

    int ArrayLeftAndRightEqualoushu(char[] a) {
        //判断是否存在偶数串
        //rrrreeee
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] == a[i + 1])
                ;
        }
        return deepth;
    }
}