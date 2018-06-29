package com.example.a67017.myapplication.main.handler;

/**
 * handler缩减版
 * Created by LiuKuo at 2018/6/26
 */

public class TestMain {
    public static void main(String[] args) {
        //在线程里进行，防止卡死（Handler在Android为什么不会卡死，https://www.zhihu.com/question/34652589/answer/59614987）
        final Thread thread = new Thread("LK") {
            @Override
            public void run() {
                super.run();
                Looper.prepareMainLoop();
                Looper.loop();
                System.out.println(Thread.currentThread().getName());
            }
        };
        thread.start();
        while (Looper.getMainLooper() == null) {
            try {
                System.out.println("sleep-----" + Thread.currentThread().getName());
                //当前线程睡眠
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                System.out.println(message.getMsg());
            }
        };
        Message message1 = new Message(0, "I am the first message!");
        handler.sendMessage(message1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message2 = new Message(1, "I am the second message!");
                handler.sendMessage(message2);
            }
        }).start();
    }
}
