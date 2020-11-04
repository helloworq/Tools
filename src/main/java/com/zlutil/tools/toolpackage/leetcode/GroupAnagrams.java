package com.zlutil.tools.toolpackage.leetcode;

import java.util.*;

/**
 * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
 * <p>
 * 示例:
 * <p>
 * 输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出:
 * [
 * ["ate","eat","tea"],
 * ["nat","tan"],
 * ["bat"]
 * ]
 * 说明：
 * <p>
 * 所有输入均为小写字母。
 * 不考虑答案输出的顺序。
 * 通过次数116,993提交次数182,993
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/group-anagrams
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class GroupAnagrams {
    public static void main(String[] args) {
        String[] a = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(groupAnagrams(a));
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new Hashtable<>();
        for (String str : strs) {
            char[] element = str.toCharArray();
            Arrays.sort(element);
            String prev = String.valueOf(element);
            if (!map.containsKey(prev)) {
                List<String> list = new ArrayList<>();
                list.add(str);
                map.put(prev, list);
            } else {
                map.get(prev).add(str);
            }
        }
        return new ArrayList<>(map.values());
    }
}
