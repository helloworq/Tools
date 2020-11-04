package com.zlutil.tools.toolpackage.leetcode;

import java.util.*;

/**
 * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,1,2]
 * 输出：
 * [[1,1,2],
 * [1,2,1],
 * [2,1,1]]
 * 示例 2：
 * <p>
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutations-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class PermuteUnique {
    public static Stack<Integer> stack = new Stack<Integer>();
    static List<List<Integer>> list = new ArrayList<>();

    public static void main(String[] args) {
        int shu[] = {1, 3, 3};
        f(shu, 3, 0);
        System.out.println(groupAnagrams(shu));
    }

    public static List<List<Integer>> groupAnagrams(int[] strs) {
        f(strs, 4, 0);
        Map<String, List<Integer>> map = new Hashtable<>();
        for (List<Integer> lis : list) {
            String str = Arrays.toString(lis.toArray())
                    .replace(",", "")
                    .replace(" ", "")
                    .replace("[", "")
                    .replace("]", "");
            if (!map.containsKey(str)) {
                map.put(str, lis);
            }
        }
        return new ArrayList<>(map.values());
    }

    /**
     * @param shu  待选择的数组
     * @param targ 要选择多少个次
     * @param cur  当前选择的是第几次
     */
    private static void f(int[] shu, int targ, int cur) {
        // TODO Auto-generated method stub
        if (cur == targ) {
            list.add(new ArrayList<>(stack));
            return;
        }

        for (int i = 0; i < shu.length; i++) {
            if (!stack.contains(shu[i])) {
                stack.add(shu[i]);
                f(shu, targ, cur + 1);
                stack.pop();
            }
        }
    }
}
