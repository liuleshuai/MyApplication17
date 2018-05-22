package com.example.a67017.myapplication.main;

/**
 * Created by LiuKuo at 2018/4/19
 */

public class Person {
    public static final int MALE = 0;
    public static final int FEMALE = 1;

    private int sex;

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSexDes() {
        if(sex == 0) {
            return "男";
        }else if(sex == 1){
            return "女";
        }else {
            throw new IllegalArgumentException("什么鬼性别？");
        }
    }

    public static void main(String[] arg0){
        Person person = new Person();
        person.setSex(0);
        System.out.print(person.getSexDes());
    }
}
