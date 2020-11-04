package com.zlutil.tools.toolpackage.DataStructure.InitTree;

public class TreeUntis {
    //static DataStructure.TreeNode head=new DataStructure.TreeNode();//头节点
    static int order = 0;
    int invokeOrder = 0;

    /**
     * 普通测试案例，用来近似可视化查看树的生成路径
     *
     * @param head
     * @param valueOfSize
     */
    public void initTree(TreeNode head, int valueOfSize) {
        head.data = valueOfSize;
        valueOfSize--;
        if (valueOfSize > 0) {
            head.Lchild = new TreeNode();
            initTree(head.Lchild, valueOfSize);
        }
        if (valueOfSize > 0) {
            head.Rchild = new TreeNode();
            initTree(head.Rchild, valueOfSize);
        }
    }

    /**
     * 生成一颗树，参数分别为指向树的头节点和将要生成的树里的数据格式为数组形式
     *
     * @param head
     * @param data
     */
    public void initTree(TreeNode head, int[] data) {
        head.data = data[order];
        order++;
        if (order < data.length) {
            head.Lchild = new TreeNode();
            initTree(head.Lchild, data);
        }
        if (order < data.length) {
            head.Rchild = new TreeNode();
            initTree(head.Rchild, data);
        }
    }

    /**
     * 获取头节点然后递归遍历树，打印语句放在开头，所以这是先序遍历
     *
     * @param head
     */
    public void readTree(TreeNode head) {
        //先序遍历树
        System.out.print(head.data);
        if (head.Lchild != null) {
            System.out.print("-");
            readTree(head.Lchild);
        }
        if (head.Rchild != null) {
            System.out.print("-");
            readTree(head.Rchild);
        }
    }

    /**
     * 获取头节点然后递归遍历树，打印语句放在开头，所以这是先序遍历
     * 此方法可以跟踪打印的顺序
     *
     * @param head
     */
    public void readTreeOrderable(TreeNode head) {
        //先序遍历树
        System.out.print(head.data);
        if (head.Lchild != null) {
            System.out.print("-" + "involeOrder:" + invokeOrder + ":Lchild-\n");
            invokeOrder++;
            readTreeOrderable(head.Lchild);
        }
        if (head.Rchild != null) {
            System.out.print("-" + "involeOrder:" + invokeOrder + ":Rchild-\n");
            invokeOrder++;
            readTreeOrderable(head.Rchild);
        }
    }
}
