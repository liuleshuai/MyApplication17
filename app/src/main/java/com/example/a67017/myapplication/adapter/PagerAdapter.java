package com.example.a67017.myapplication.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a67017.myapplication.R;

import java.util.List;

/**
 * Created by 67017 on 2017/11/9.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private List<Class> list;
    private Context context;
    private List<String> title;
    private List<Integer> images;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public PagerAdapter(Context context, FragmentManager fm, List<Class> list, List<String> title) {
        super(fm);
        this.context = context;
        this.list = list;
        this.title = title;
    }

    public PagerAdapter(Context context, FragmentManager fm, List<Class> list, List<String> title, List<Integer> images) {
        super(fm);
        this.list = list;
        this.context = context;
        this.title = title;
        this.images = images;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        String className = list.get(position).getName();
        return Fragment.instantiate(context, className);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    /**
     * 自定义Tab
     *
     * @param position
     * @return
     */
    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        ImageView iv = view.findViewById(R.id.iv);
        iv.animate().rotation(360).setDuration(2000).setInterpolator(new AnticipateOvershootInterpolator());
        TextView tv = view.findViewById(R.id.tv);
        iv.setImageResource(images.get(position));
        tv.setText(title.get(position));
        return view;
    }
}
