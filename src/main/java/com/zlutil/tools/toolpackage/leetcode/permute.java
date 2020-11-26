package com.zlutil.tools.toolpackage.leetcode;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
 *
 * 示例:
 *
 * 输入: [1,2,3]
 * 输出:
 * [
 *   [1,2,3],
 *   [1,3,2],
 *   [2,1,3],
 *   [2,3,1],
 *   [3,1,2],
 *   [3,2,1]
 * ]
 * 通过次数213,781提交次数277,288
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutations
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class permute {
    public static void main(String[] args) {
        int[] nums=new int[]{1,2,3};
        List<Integer> list= new ArrayList();
        for (int n:nums) {
            list.add(n);
        }
        //permute(nums);
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<Integer> list= new ArrayList();
        for (int n:nums) {
            list.add(n);
        }
        for (int i = 0; i < list.size(); i++) {
            List<Integer> integerList=new ArrayList<>();
            integerList.add(list.get(i));
        }
        return null;
    }

    List<Node> list=new ArrayList<>();
    public void initTree(ArrayList<Integer> nums,ArrayList<Node> points){

    }
}

@Data
class Node {
    public Node parent;
    public ArrayList<Node> children;
    public Object content;
}