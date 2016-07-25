package com.thd.wx.study.lambda;


public class ThreadDemo3 {
    public static void main(String[] args) {
        //Runnable r = new Runnable() {...}
        /*Runnable r = ()->{
            for (int i = 0; i < 100; i++) {
                System.out.println("run3... " + i);
            }
        };*/
//        Thread t = new Thread(() -> {
//            for (int i = 0; i < 100; i++) {
//                System.out.println("run3... " + i);
//            }
//        });

//        t.start();

        for (int i = 0; i < 100; i++) {
            System.out.println("main3... " + i);
        }
    }

}
