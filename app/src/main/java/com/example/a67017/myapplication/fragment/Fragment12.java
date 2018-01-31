package com.example.a67017.myapplication.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.activity.MainActivity;
import com.zhouwei.blurlibrary.EasyBlur;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment12 extends Fragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;
    @BindView(R.id.gaos)
    ImageView gaos;

    private List<Fragment> fragments = new ArrayList<>();

    public static final int[] IMG = new int[]{R.mipmap.skin2, R.mipmap.skin3, R.mipmap.skin4
            , R.mipmap.skin5, R.mipmap.skin6, R.mipmap.skin7, R.mipmap.skin8, R.mipmap.skin9
            , R.mipmap.skin10, R.mipmap.skin11};

    public static final String[] TITLE = new String[]{"哥特萝莉", "小红帽", "安妮梦游仙境",
            "舞会公主", "冰爽烈焰", "安博斯与提妮", "科学怪熊的新娘", "你见过我的熊猫吗？安妮",
            "甜心宝贝", "海克斯科技"};


    public Fragment12() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment12, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        // 设置间隙有两种方法：1.代码设置。2.xml里设置Item最外层的margin。
/*        int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);*/

        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                if (position < -1 || position > 1) {
                    page.setScaleX(0.8f);
                    page.setScaleY(0.8f);
                } else {
                    float scale = Math.max(0.8f, 1 - Math.abs(position));
                    page.setScaleX(scale);
                    page.setScaleY(scale);
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), IMG[position]);
                Bitmap gaosBitmap = EasyBlur.with(getActivity())
                        .bitmap(bitmap)
                        .radius(20)
                        .scale(4)
                        .blur();
                gaos.setImageBitmap(gaosBitmap);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), IMG[0]);
        Bitmap gaosBitmap = EasyBlur.with(getActivity())
                .bitmap(bitmap)
                .radius(20)
                .scale(4)
                .blur();
        gaos.setImageBitmap(gaosBitmap);

        /**
         * Fragment 设置onTouchEvent的方法
         */
//        makeTouch();
    }

    private void makeTouch() {
        MainActivity.MyTouchListener touchListener = new MainActivity.MyTouchListener() {
            @Override
            public boolean onTouch(MotionEvent event) {
                return viewPager.onTouchEvent(event);
            }
        };

        ((MainActivity) getActivity()).registerMyOnTouchListener(touchListener);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            for (int i = 0; i < IMG.length; i++) {
                fragments.add(Fragment12Child.getInstance(IMG[i], TITLE[i]));
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
