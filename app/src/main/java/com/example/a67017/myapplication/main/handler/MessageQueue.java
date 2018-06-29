package com.example.a67017.myapplication.main.handler;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by LiuKuo at 2018/6/26
 */

public class MessageQueue {
    private BlockingDeque<Message> deque;

    public MessageQueue(int size) {
        deque = new LinkedBlockingDeque<>(size);
    }

    public Message take() throws InterruptedException {
        return deque.take();
    }

    public void put(Message message) throws InterruptedException {
        deque.put(message);
    }
}
