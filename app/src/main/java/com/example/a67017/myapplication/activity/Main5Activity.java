package com.example.a67017.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.customeview.CustomSwitchCompat;

import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = "/activity/5")
public class Main5Activity extends AppCompatActivity {

    @BindView(R.id.switch_compat)
    CustomSwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(R.color.colorPrimary);
        this.setSupportActionBar(toolbar);

        switchCompat.setSwitchListener(new CustomSwitchCompat.SwitchListener() {
            @Override
            public void onClick() {
                Log.d("LK", "2222222");
            }
        });
    }
}
