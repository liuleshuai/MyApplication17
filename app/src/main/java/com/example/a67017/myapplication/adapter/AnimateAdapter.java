package com.example.a67017.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a67017.myapplication.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LiuKuo at 2018/5/30
 */

public class AnimateAdapter extends RecyclerView.Adapter<AnimateAdapter.Holder> {
    private Context context;
    private List<Integer> images;

    public AnimateAdapter(Context context, List<Integer> images) {
        this.context = context;
        this.images = images;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity18_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.iv.setImageResource(images.get(position % 8));
    }

    @Override
    public int getItemCount() {
        return 60;
    }

    static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;

        Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
