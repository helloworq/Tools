package com.zlutil.tools.toolpackage.JavaBasic;

import com.alibaba.fastjson.JSON;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class father {
    public father(String fatherSaying1) {
        this.fatherSaying = fatherSaying1;
    }

    String fatherSaying = "i am your father";

    public String fatherSay() {
        System.out.println(fatherSaying);
        return fatherSaying;
    }

    public static void main(String[] args) {
        int i = 0;
        Stack<Integer> stack = new Stack<>();
        Queue<Integer> queue = new ArrayDeque<>();
        for (; i < 10; i++) {
            //stack.push(i);
            queue.add(i);
        }
        queue.peek();
        System.out.println(JSON.toJSONString(queue));
        queue.element();
        System.out.println(JSON.toJSONString(queue));
        queue.poll();
        System.out.println(JSON.toJSONString(queue));
        queue.poll();
        System.out.println(JSON.toJSONString(queue));
        /*System.out.println(JSON.toJSONString(stack));

        stack.pop();

        System.out.println(JSON.toJSONString(stack));
        System.out.println(stack.isEmpty());


        System.out.println(new Object().hashCode());
        System.out.println(new Object().hashCode());

        System.out.println(Arrays.toString("ab,a".split(",")));*/

    }
}
