package com.example.a67017.myapplication.main;

/**
 * Created by 67017 on 2017/12/27.
 */

public class Test4 {
    public static void main(String[] args) {
        Fish fish = new Fish();
        if(fish instanceof Animate){
            System.out.print("11111");
        }
    }
}

class Fish extends Animate{

}
class Animate{

}
