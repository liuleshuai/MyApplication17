package com.example.a67017.myapplication.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.bean.RecyclerBean;

import java.util.List;

/**
 * Created by LiuKuo at 2018/1/9
 */

public class RecyclerAdapter extends BaseQuickAdapter<RecyclerBean, BaseViewHolder> {
    public RecyclerAdapter(@LayoutRes int layoutResId, @Nullable List<RecyclerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecyclerBean item) {
        helper.setText(R.id.tv, item.getText())
                .setImageResource(R.id.iv, item.getDrawable())
                .setText(R.id.button, item.getButton())
                .addOnClickListener(R.id.iv)
                .addOnClickListener(R.id.button);
    }

/*    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5) {
            anim.setStartDelay(index * 150);
        }
    }*/
}
