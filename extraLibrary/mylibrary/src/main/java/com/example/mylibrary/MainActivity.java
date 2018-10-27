package com.example.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@Route(path = "/libraryActivity/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syj_activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, JumpActivity.class), 0);
            }
        });
        findViewById(R.id.broadcost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/libraryActivity/broadcast").navigation();
            }
        });

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "返回！！！", Toast.LENGTH_SHORT).show();
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
