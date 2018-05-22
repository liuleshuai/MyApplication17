package com.example.a67017.myapplication.main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuKuo at 2018/4/17
 */

public class Test6 {
    interface Game {
        void play();
    }

    interface Program {
        void code();
    }

    public static class People<T extends Program & Game> {
        private T mPeople;

        public People(T people) {
            mPeople = people;
        }

        public void habit() {
            mPeople.code();
            mPeople.play();
        }
    }

    public static class Aa implements Program, Game {
        public Aa() {
        }

        @Override
        public void play() {
            System.out.println("Game play!");
        }

        @Override
        public void code() {
            System.out.println("Program code!");
        }
    }

    public static void main(String[] arg1) {
        Aa a = new Aa();
        People<Aa> people = new People<>(a);
        people.habit();
        List<String> list = new ArrayList<>();
        List list1 = list;
//        List<Object> list2 = list;
    }
}
