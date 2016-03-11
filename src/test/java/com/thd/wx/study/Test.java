package com.thd.wx.study;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        String url = "html/test.do?id=003";
        int indexOf = url.indexOf("?");
        String substring = url.substring(indexOf + 1);
        System.out.println(substring);
    }

    // 4.请写出下列代码运行的结果。
    private static void method4() {
        int number = 1;
        switch (number) {
            case 0:
                System.out.println(0);
                return;
            case 1:
            case 2:
                System.out.println(2);
            case 3:
                System.out.println(3);
                break;
            case 4:
                System.out.println(4);
                break;
            default:
                System.out.println(-1);
        }
    }

    private static void arrayToList() {
        Integer[] a = {1, 5, 8, 2, 6, 9};
        List<Integer> list = Arrays.asList(a);
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

    private static void questionOne() {
        List<Integer> counts = new ArrayList<Integer>();
        counts.add(2);
        counts.add(1);
        counts.add(89);
        counts.add(43);
        counts.add(15);
        Collections.sort(counts);
        for (Integer count : counts) {
            System.out.println(count);
        }
    }
}