package com.example.a67017.myapplication.main.handler;

/**
 * Created by LiuKuo at 2018/6/26
 */

public abstract class Handler {
    private MessageQueue queue;

    //默认主线程
    public Handler() {
        queue = Looper.getMainLooper().queue;
    }

    public Handler(Looper looper) {
        queue = looper.queue;
    }

    public void sendMessage(Message message) {
        message.target = this;
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void handleMessage(Message message);
}
