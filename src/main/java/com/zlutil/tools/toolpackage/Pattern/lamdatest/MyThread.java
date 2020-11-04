package com.zlutil.tools.toolpackage.Pattern.lamdatest;

public class MyThread {

    // 成员变量
    private MyRunnable target;

    public MyThread() {
    }

    // 构造方法，接收外部传递的出行策略
    public MyThread(MyRunnable target) {
        this.target = target;
    }

    // MyThread自己的run()，现在基本不用了
    public void run() {
    }

    // 如果外部传递了出行策略，就会调用该策略里的run()
    public void start() {
        if (target != null) {
            target.run();
        }
    }
}