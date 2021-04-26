package com.zlutil.tools.toolpackage.leetcode;

/**
 * Merge two sorted linked lists and return it as a new sorted list. The new list should be made by splicing together the nodes of the first two lists.
 * <p>
 * Example:
 * <p>
 * Input: 1->2->4, 1->3->4
 * Output: 1->1->2->3->4->4
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-two-sorted-lists
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MergeTwoSortedList {
    public static void main(String[] args) {
        ListNode listNode = mergeTwoLists();
        while (listNode.next != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    public static ListNode mergeTwoLists() {
        ListNode head = new ListNode();
        ListNode res = new ListNode();

        ListNode l1 = new ListNode(1, new ListNode());
        l1.next = new ListNode(2, new ListNode());
        l1.next.next = new ListNode(4, new ListNode());
        ListNode l2 = new ListNode(1, new ListNode());
        l2.next = new ListNode(3, new ListNode());
        l2.next.next = new ListNode(4, new ListNode());

        if (l1.val <= l2.val) res = l1;
        else res = l2;
        while (l1.next != null) {
            if (l1.val < l2.val) {
                head = l1.next;
                l1.next = l2;
            }
            l2 = l2.next;
            l1 = head;
        }
        return res;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}