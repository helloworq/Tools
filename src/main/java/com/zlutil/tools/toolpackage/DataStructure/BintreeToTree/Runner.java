package com.zlutil.tools.toolpackage.DataStructure.BintreeToTree;

import lombok.Data;

import java.util.ArrayList;

/**
 * 尝试构建树形结构数据
 * 配置parent以控制缩进量
 * 配置children以控制子节点量
 */
public class Runner {
    public static void main(String[] args) {
        Node one = new Node();
        one.content = "one";
        Node two = new Node();
        two.content = "two";
        Node three = new Node();
        three.content = "three";
        Node four = new Node();
        four.content = "four";
        Node five = new Node();
        five.content = "five";
        Node six = new Node();
        six.content = "six";
        Node seven = new Node();
        seven.content = "seven";
        Node eghit = new Node();
        eghit.content = "eghit";
        Node nine = new Node();
        nine.content = "nine";


        one.children = new ArrayList<>();
        one.children.add(two);
        one.children.add(three);


        two.children = new ArrayList<>();
        two.children.add(five);
        two.children.add(seven);
        two.parent = one;
        five.parent = two;
        seven.parent = two;


        three.children = new ArrayList<>();
        three.children.add(four);
        three.children.add(six);
        three.parent = one;
        four.parent = three;
        six.parent = three;

        five.children = new ArrayList<>();
        five.children.add(eghit);
        eghit.parent = five;

        eghit.children = new ArrayList<>();
        eghit.children.add(nine);
        nine.parent = eghit;

        Node finder = one;
        findData(finder);

        System.out.println(Double.valueOf("111.111"));
        System.out.println(Double.valueOf("111"));
        System.out.println(Double.parseDouble("111.1"));

    }

    /**
     * 递归打印数据
     *
     * @param finder
     */
    static void findData(Node finder) {
        printNT(getLayer(finder));
        System.out.print(finder.content);
        if (finder.children != null) {
            for (int i = 0; i < finder.children.size(); i++) {
                findData(finder.children.get(i));
            }
        }
    }

    /**
     * 打印空格换行符
     *
     * @param times
     */
    static void printNT(int times) {
        System.out.print("\n");
        for (int i = 0; i < times; i++) {
            System.out.print("\t");
        }
    }

    /**
     * 计算当前所处层级
     *
     * @param node
     * @return
     */
    static Integer getLayer(Node node) {
        int layer = 0;
        while (node.parent != null) {
            layer++;
            node = node.parent;
        }
        return layer;
    }
}

@Data
class Node {
    public Node parent;
    public ArrayList<Node> children;
    public Object content;
}