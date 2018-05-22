package com.example.a67017.myapplication.main;

/**
 * Created by LiuKuo at 2018/4/23
 */

public class Test7 {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        for (int i = 0; i < 10000; i++) {
            new Thread(new MyRunnable1(shareData)).start();
            new Thread(new MyRunnable2(shareData)).start();
        }
    }

}

class MyRunnable1 implements Runnable {
    private ShareData shareData;

    public MyRunnable1(ShareData shareData) {
        this.shareData = shareData;
    }

    @Override
    public void run() {
        shareData.inc();
    }

}

class MyRunnable2 implements Runnable {
    private ShareData shareData;

    public MyRunnable2(ShareData shareData) {
        this.shareData = shareData;
    }

    @Override
    public void run() {
        shareData.dec();
    }

}

class ShareData {
    private int j = 0;

    //增
    public synchronized void inc() {
        j++;
        System.out.println(Thread.currentThread().getName() + "inc" + j);
    }

    //减
    public synchronized void dec() {
        j--;
        System.out.println(Thread.currentThread().getName() + "dec" + j);
    }
}
