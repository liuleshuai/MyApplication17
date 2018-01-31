package com.example.a67017.myapplication.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 67017 on 2017/11/9.
 */

public class FragmentBean {
    private List<Class> fragmentClass;
    private List<String> fragmentTitle;
    private List<Integer> images;

    public FragmentBean() {
        fragmentClass = new ArrayList<>();
        fragmentTitle = new ArrayList<>();
        images = new ArrayList<>();
    }

    public List<Class> getFragmentClass() {
        return fragmentClass;
    }

    public List<String> getFragmentTitle() {
        return fragmentTitle;
    }

    public List<Integer> getImages() {
        return images;
    }

    public void add(Class fragment, String title) {
        fragmentClass.add(fragment);
        fragmentTitle.add(title);
    }

    public void addArray(Class[] fragment, String[] title) {
        fragmentClass.addAll(Arrays.asList(fragment));
        fragmentTitle.addAll(Arrays.asList(title));
    }

    public void add(Class fragment, String title, int resource) {
        fragmentClass.add(fragment);
        fragmentTitle.add(title);
        images.add(resource);
    }

    public void addArray(Class[] fragment, String[] title, Integer[] resource) {
        fragmentClass.addAll(Arrays.asList(fragment));
        fragmentTitle.addAll(Arrays.asList(title));
        images.addAll(Arrays.asList(resource));
    }

    public int getCount() {
        return fragmentClass.size();
    }

}
