package com.example.a67017.myapplication.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.bean.User;
import com.example.a67017.myapplication.databinding.ActivityMain7Binding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Route(path = "/activity/7")
public class Main7Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain7Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_main7);
        User user = new User("liukuo");
        binding.setUser(user);

        Student s1 = new Student(91);
        Student s2 = new Student(95);
        List<Student> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        Collections.sort(list, new MyComparator());
    }

    public static class MyComparator implements Comparator<Student> {

        @Override
        public int compare(Student o1, Student o2) {
            return o1.getScore() - o2.getScore();
        }
    }

    public static class Student {
        private int score;

        public Student(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }
    }
}
