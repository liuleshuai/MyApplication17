package com.example.a67017.myapplication.main;

/**
 * Created by LiuKuo at 2018/5/3
 */

public class Test {
    public static void main(String[] args) {
        String a = "hello2";
        final String b = "hello";
        String d = "hello";
        String c = b + 2;
        String e = d + 2;
        System.out.println(b == d);
        System.out.println(a == c);
        System.out.println(a == e);
        System.out.println(a.equals(e));
    }
}
