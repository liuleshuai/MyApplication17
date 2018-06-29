package com.example.a67017.myapplication.main.handler;

/**
 * Created by LiuKuo at 2018/6/26
 */

public class Looper {
    private static ThreadLocal<Looper> threadLocal = new ThreadLocal<>();
    MessageQueue queue;
    private static Looper looper;
    private Message message;
    private static Looper mainLooper;

    public Looper() {
        this.queue = new MessageQueue(2);
    }

    public static void prepareLoop() {
        if (threadLocal.get() != null) {
            throw new RuntimeException("Only one Looper can be created per thread");
        }
        looper = new Looper();
        threadLocal.set(looper);
    }

    public static Looper getLoop() {
        return threadLocal.get();
    }

    public static void prepareMainLoop() {
        prepareLoop();
        synchronized (Looper.class) {
            if (mainLooper != null) {
                throw new RuntimeException("The main Looper has already been prepared");
            }
        }
        mainLooper = getLoop();
    }

    public static Looper getMainLooper() {
        return mainLooper;
    }

    public static void loop() {
        Looper looper = getLoop();
        if (looper == null) {
            throw new NullPointerException("no Looper, prepareLoop() wasn't called in this thread");
        }
        while (true) {
            try {
                Message message = looper.queue.take();
                if (message != null) {
                    message.target.handleMessage(message);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
