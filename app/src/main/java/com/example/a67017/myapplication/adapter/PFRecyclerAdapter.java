package com.example.a67017.myapplication.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.dao.GdUser;

import java.util.List;

/**
 * Created by LiuKuo at 2018/6/7
 */

public class PFRecyclerAdapter extends BaseQuickAdapter<GdUser, BaseViewHolder> {
    public PFRecyclerAdapter(@LayoutRes int layoutResId, @Nullable List<GdUser> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GdUser item) {
        helper.setText(R.id.tv_id, item.getId() + "")
                .setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_age, item.getAge() + "");
    }
}
