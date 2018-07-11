package com.example.a67017.myapplication.main;

import java.util.ArrayList;
import java.util.List;

/**
 * Compareable和Compartor区别（Main7Activity）
 * Created by LiuKuo at 2018/3/1
 */

public class Test5 {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        Student s1 = new Student(91);
        Student s2 = new Student(95);
        Student s3 = new Student(97);
        Student s4 = new Student(98);
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        for (Student s : list) {
            System.out.println(s);
        }
    }

    public static class Student implements Comparable<Student> {
        private int score;

        public Student(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(Student o) {
            return -(score - o.getScore());
        }

        @Override
        public String toString() {
            return "Student{" +
                    "score=" + score +
                    '}';
        }
    }
}
