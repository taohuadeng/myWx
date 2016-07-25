package com.thd.wx.study.lambda;


class Demo1 implements Runnable {

    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("run... " + i);
        }
    }
}

public class ThreadDemo1 {
    public static void main(String[] args) {
        Thread t = new Thread(new Demo1());
        t.start();

        for (int i = 0; i < 100; i++) {
            System.out.println("main... " + i);
        }
    }

}
