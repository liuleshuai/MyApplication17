package com.example.a67017.myapplication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/activity/6")
public class Main6Activity extends AppCompatActivity {

    @BindView(R.id.tool_bar_layout)
    CollapsingToolbarLayout toolBarLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        toolBarLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        toolBarLayout.setExpandedTitleColor(Color.parseColor("#000000"));

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Main6Activity.this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Logger.d(appBarLayout.getTotalScrollRange());
                if (appBarLayout.getTotalScrollRange() <= Math.abs(verticalOffset)) {
                    toggle.setDrawerIndicatorEnabled(true);
                } else {
                    toggle.setDrawerIndicatorEnabled(false);
                }
            }
        });
    }
}
