package com.example.a67017.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.customeview.DragLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/activity/8")
public class Main8Activity extends AppCompatActivity {

    @BindView(R.id.drag_layout)
    DragLayout dragLayout;
    @BindView(R.id.view)
    TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        ButterKnife.bind(this);
        dragLayout.setCloseListener(new DragLayout.MyCloseListener() {
            @Override
            public void close() {

            }

            @Override
            public void change(int top) {
                float radio = 1 - ((float) top) / dragLayout.getRootView().getWidth();
                float scale = Math.max(radio, 0.85f);
                view.setScaleX(scale);
                view.setScaleY(scale);
            }
        });
    }
}
