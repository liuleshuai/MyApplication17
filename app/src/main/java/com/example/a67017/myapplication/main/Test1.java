package com.example.a67017.myapplication.main;

import com.alibaba.fastjson.JSON;
import com.example.a67017.myapplication.bean.Student;
import com.google.gson.Gson;

import java.lang.reflect.Constructor;

/**
 * Created by 67017 on 2017/11/21.
 */

public class Test1 {

    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("com.example.a67017.myapplication.bean.Student");
            Constructor constructors = clazz.getDeclaredConstructor(int.class, String.class, String.class);
            constructors.setAccessible(true);
            Object mStudent = constructors.newInstance(27, "小文", "北京市海定区XX号");
            Gson gson = new Gson();
             /******    Gson   ********/
            // 序列化
            String s = gson.toJson(mStudent);
            // 反序列化
            Student student = gson.fromJson(s, Student.class);
            /******    Fastjson   ********/
            // 序列化
            String ss = JSON.toJSONString(mStudent);
            System.out.println(ss);
            // 反序列化
            Student object = JSON.parseObject(ss, Student.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
