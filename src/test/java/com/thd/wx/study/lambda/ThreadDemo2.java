package com.thd.wx.study.lambda;


public class ThreadDemo2 {
    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("run2... " + i);
                }
            }
        });

        t.start();

        for (int i = 0; i < 100; i++) {
            System.out.println("main2... " + i);
        }
    }

}
