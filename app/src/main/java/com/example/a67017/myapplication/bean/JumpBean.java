package com.example.a67017.myapplication.bean;

import java.util.List;

/**
 * Created by LiuKuo at 2018/1/12
 */

public class JumpBean {
    private List<String> activity;
    private List<String> jumpText;

    public JumpBean(List<String> activity, List<String> jumpText) {
        this.activity = activity;
        this.jumpText = jumpText;
    }

    public void add(String activity, String text) {
        this.activity.add(activity);
        this.jumpText.add(text);
    }

    public int getSize() {
        return activity.size();
    }

    public String getActivity(int i) {
        return activity.get(i);
    }

    public String getJumpText(int i) {
        return jumpText.get(i);
    }
}
