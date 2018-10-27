package com.example.a67017.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 万能适配器
 * 使用
 * 1.
 * mAdapter = new QuickAdapter<String>(data) {
 *
 * @param <T>
 * @Override public int getLayoutId(int viewType) {
 * return R.layout.item;
 * }
 * @Override public void convert(VH holder, String data, int position) {
 * holder.setText(R.id.text, data);
 * //holder.itemView.setOnClickListener(); 此处还可以添加点击事件
 * }
 * };
 * 2.
 * mAdapter = new QuickAdapter<Model>(data) {
 * @Override public int getLayoutId(int viewType) {
 * switch(viewType){
 * case TYPE_1:
 * return R.layout.item_1;
 * case TYPE_2:
 * return R.layout.item_2;
 * }
 * }
 * @Override public int getItemViewType(int position) {
 * if(position % 2 == 0){
 * return TYPE_1;
 * } else{
 * return TYPE_2;
 * }
 * }
 * @Override public void convert(VH holder, Model data, int position) {
 * int type = getItemViewType(position);
 * switch(type){
 * case TYPE_1:
 * holder.setText(R.id.text, data.text);
 * break;
 * case TYPE_2:
 * holder.setImage(R.id.image, data.image);
 * break;
 * }
 * }
 * };
 */
public abstract class QuickAdapter<T> extends RecyclerView.Adapter<QuickAdapter.VH> {

    private List<T> datas;

    public QuickAdapter(List<T> datas) {
        this.datas = datas;
    }

    public abstract int getLayoutId(int viewType);

    public abstract void convert(VH holder, T data, int position);

    @Override
    public QuickAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return VH.get(parent, getLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull QuickAdapter.VH holder, int position) {
        convert(holder, datas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews;
        private View itemView;

        private VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mViews = new SparseArray<>();
        }

        public static VH get(ViewGroup parent, int layoutId) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new VH(view);
        }

        public <T extends View> T getView(int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mViews.put(id, view);
            }
            return (T) view;
        }

        public void setText(int id, String value) {
            TextView tv = getView(id);
            tv.setText(value);
        }

        public void setImage(int id, int resId) {
            ImageView iv = getView(id);
            iv.setImageResource(resId);
        }
    }
}
