package com.example.a67017.myapplication.main;

/**
 * Created by LiuKuo at 2018/3/1
 */

public class TestThread {
    public static void main(String[] args) {
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("00000");
            }
        };
        thread0.start();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("11111");
            }
        });
        thread1.start();

        Thread thread2 = new MyThread();
        thread2.start();

        new Thread(thread3).start();
    }

    private static Thread thread3 = new Thread(){
        @Override
        public void run() {
            super.run();
            System.out.println("33333");
        }
    };

    static class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            System.out.println("22222");
        }
    }
}
