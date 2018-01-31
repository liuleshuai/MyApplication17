package com.example.a67017.myapplication.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/activity/12")
public class Main12Activity extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);
        ButterKnife.bind(this);
        activityShow();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void activityShow() {
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(500);
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }

    @OnClick(R.id.iv)
    public void onViewClicked() {
        Intent intent = new Intent(Main12Activity.this, Main13Activity.class);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(iv, iv.getWidth() / 2, iv.getHeight() / 2, 0, 0);
        ActivityCompat.startActivity(this, intent, compat.toBundle());
    }
}
