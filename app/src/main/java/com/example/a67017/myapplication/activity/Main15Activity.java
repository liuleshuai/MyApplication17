package com.example.a67017.myapplication.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.tool.dpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/activity/15")
public class Main15Activity extends AppCompatActivity {

    @BindView(R.id.parallax)
    ImageView parallax;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main15);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = dpUtils.dip2px(Main15Activity.this, 170);
            private int color = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    title.setAlpha(1f * scrollY / h);
                    toolbar.setBackgroundColor(((255 * scrollY / h) << 24) | color);
                }
                lastScrollY = scrollY;
            }
        });
        title.setAlpha(0);
        toolbar.setBackgroundColor(0);
    }
}
