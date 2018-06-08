package com.example.a67017.myapplication.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by LiuKuo at 2018/6/6
 */
@Entity
public class GdUser {
    //表示id会自增长
    @Id(autoincrement = true)
    private long id;
    private String name;
    private int age;
    @Generated(hash = 111789642)
    public GdUser(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    @Generated(hash = 1071838647)
    public GdUser() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
