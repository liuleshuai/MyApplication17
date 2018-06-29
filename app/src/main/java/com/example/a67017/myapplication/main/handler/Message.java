package com.example.a67017.myapplication.main.handler;

/**
 * Created by LiuKuo at 2018/6/26
 */

public class Message {
    private String msg;
    private int code;
    public Handler target;

    public Message(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
