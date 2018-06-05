package com.example.a67017.myapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.customeview.MyDragScrollView;
import com.example.a67017.myapplication.customeview.VideoLayout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/activity/10")
public class Main10Activity extends AppCompatActivity {

    @BindView(R.id.layout)
    VideoLayout videoLayout;
    @BindView(R.id.mask)
    MyDragScrollView mask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
        ButterKnife.bind(this);
        videoLayout.setMyDragListener(new VideoLayout.OnMyDragListener() {
            @Override
            public void close() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 500);
            }

            @Override
            public void changeSize(int top) {
                float radio = 1 - ((float) top) / videoLayout.getRootView().getHeight();
                float scale = Math.max(radio, 0.85f);
                mask.setScaleX(scale);
                mask.setScaleY(scale);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ExecutorService threadPool = Executors.newFixedThreadPool(4);
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    
                }
            });
        }
    };
}
