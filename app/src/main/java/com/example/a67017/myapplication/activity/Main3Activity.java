package com.example.a67017.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.MyApplication;
import com.example.a67017.myapplication.R;
import com.squareup.leakcanary.RefWatcher;

/**
 * 内存溢出第三方
 *
 * @author 67017
 */
@Route(path = "/activity/3")
public class Main3Activity extends AppCompatActivity {
    private static Dog dog;

    class Dog {
        public void say() {
            System.out.println("Dog!!!!!!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        dog = new Dog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
