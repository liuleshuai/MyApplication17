package com.example.a67017.myapplication.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 内存溢出
 *
 * @author 67017
 */
@Route(path = "/activity/2")
public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.back)
    FloatingActionButton back;
    @BindView(R.id.activity_main2)
    RelativeLayout activityMain2;

    static Demo sDemo;

    class Demo {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        if (sDemo == null) {
            sDemo = new Demo();
        }
//        finish();
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
//        startActivity(new Intent(this, Main3Activity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sDemo = null;
    }
}
