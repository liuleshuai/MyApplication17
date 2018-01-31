package com.example.a67017.myapplication.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.bean.RecyclerBean;

import java.util.List;

/**
 * Created by LiuKuo at 2018/1/9
 */

public class DragAdapter extends BaseItemDraggableAdapter<RecyclerBean, BaseViewHolder> {

    public DragAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecyclerBean item) {
        helper.setText(R.id.tv, item.getText())
                .setImageResource(R.id.iv, item.getDrawable())
                .setText(R.id.button, item.getButton());
    }
}
