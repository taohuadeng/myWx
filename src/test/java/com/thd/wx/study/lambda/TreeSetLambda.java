package com.thd.wx.study.lambda;


import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class TreeSetLambda {
    public static void main(String[] args) {
//       Set<String> set = new TreeSet<>(new Sort01());
//        set.add("abc");
//        set.add("df");
//        set.add("hjkl");
//        set.add("hjuhg");
//        System.out.println(set);
    }

}

class Sort01 implements Comparator<String> {

    @Override
    public int compare(String s1,String s2) {
        int length = s1.length() - s2.length();
        return length == 0?s1.compareTo(s2):length;
    }
}
