package com.example.a67017.myapplication.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.bean.User;
import com.example.a67017.myapplication.databinding.ActivityMain7Binding;

@Route(path = "/activity/7")
public class Main7Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain7Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_main7);
        User user = new User("liukuo");
        binding.setUser(user);
    }
}
