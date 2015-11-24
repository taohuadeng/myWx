package com.thd.wx.study;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalTest {

    //创建一个Integer型的线程本地变量
    public static final ThreadLocal<Integer> local = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    //创建一个Integer型的线程本地变量
    public static final ThreadLocal<Map<String, String>> mapLocal = new ThreadLocal<Map<String, String>>() {
        @Override
        protected Map<String, String> initialValue() {
            return new HashMap<String, String>(0);
        }
    };

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[5];
        for (int j = 0; j < 5; j++) {
            threads[j] = new Thread(new Runnable() {
                @Override
                public void run() {
                    //获取当前线程的本地变量，然后累加5次
                    int num = local.get();
                    for (int i = 0; i < 5; i++) {
                        num++;
                    }
                    //重新设置累加后的本地变量
                    local.set(num);
                    System.out.println(Thread.currentThread().getName() + " : " + local.get());
                }
            }, "Thread-" + j);
        }

        for (Thread thread : threads) {
            thread.start();
        }


//        MyThread thread1 = new MyThread("1");
//        MyThread thread2 = new MyThread("2");
//        MyThread thread3 = new MyThread("3");
//        MyThread thread4 = new MyThread("4");
//        MyThread thread5 = new MyThread("5");
//        Thread th1 = new Thread(thread1);
//        Thread th2 = new Thread(thread2);
//        Thread th3 = new Thread(thread3);
//        Thread th4 = new Thread(thread4);
//        Thread th5 = new Thread(thread5);
//        th1.start();
//        th2.start();
//        th3.start();
//        th4.start();
//        th5.start();
//        th1.run();
//        th2.run();
//        th3.run();
//        th4.run();
//        th5.run();
    }

    static class MyThread implements Runnable {
        private String threadId;

        MyThread() {
        }

        MyThread(String threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {
            Map<String, String> map = mapLocal.get();
            map.put("abc", Thread.currentThread().getName());
            mapLocal.set(map);

            Map<String, String> map1 = mapLocal.get();
            System.out.println(Thread.currentThread().getName() + " : " + map1.get("abc"));
            //System.out.println(threadId + " : " + map1.get("abc"));
        }
    }
}
