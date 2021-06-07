package com.zlutil.tools.toolpackage.leetcode;

import java.util.Arrays;

/**
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * <p>
 * 说明：
 * <p>
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 * <p>
 * 示例 1:
 * <p>
 * 输入: [2,2,1]
 * 输出: 1
 * 示例 2:
 * <p>
 * 输入: [4,1,2,1,2]
 * 输出: 4
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/single-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

public class SingleNumber {
    public static void main(String[] args) {
        System.out.println(singleNumber(new int[]{-1, 1, 1, 2, 2}));
    }
//-1,1,1,2,2
//1,1,2,2,4
//1,1,3,3,4,5,5,6,6

    public static int singleNumber(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i += 2) {
            if (nums[i] != nums[i + 1]) {
                if (nums[i + 1] == nums[i + 2]) {
                    return nums[i];
                }
            }
        }
        return nums[nums.length - 1];
    }
}
