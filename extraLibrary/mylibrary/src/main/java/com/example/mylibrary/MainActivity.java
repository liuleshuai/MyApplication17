package com.example.mylibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@Route(path = "/libraryActivity/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String json = "{\"是大赛打死\":\"dsadasdas\"}";

        // 利用fragment捆绑生命周期
        Fragment fragment = new TestFragment();
        FragmentManager fm = this.getSupportFragmentManager();
        fm.beginTransaction().add(fragment, null).commitAllowingStateLoss();

        SparseArray<String> map = new SparseArray<>();
        String s = "101";
        map.put(1, s);

        int hashCode = s.hashCode();
        Log.d("LLK", "" + hashCode);
        String a = map.get(hashCode);
        Log.d("LLK", a);
//        Glide.with(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // 如果响应方法一个不写，会报错
    @Subscribe
    public void responseEvent(String s) {

    }
}
