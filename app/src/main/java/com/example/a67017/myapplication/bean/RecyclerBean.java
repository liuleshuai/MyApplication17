package com.example.a67017.myapplication.bean;

/**
 * Created by LiuKuo at 2018/1/9
 */

public class RecyclerBean {
    private int drawable;
    private String text;
    private String button;

    public RecyclerBean(String text, int drawable, String button) {
        this.text = text;
        this.drawable = drawable;
        this.button = button;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }
}
