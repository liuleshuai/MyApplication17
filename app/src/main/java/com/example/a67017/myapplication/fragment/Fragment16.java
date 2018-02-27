package com.example.a67017.myapplication.fragment;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.adapter.DragAdapter;
import com.example.a67017.myapplication.adapter.RecyclerAdapter;
import com.example.a67017.myapplication.bean.JumpBean;
import com.example.a67017.myapplication.bean.RecyclerBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment16 extends Fragment {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private List<RecyclerBean> data;
    private JumpBean activityList;

    public Fragment16() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment16, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        init();
        quickMaker();
    }

    private void dragAndSwipeMaker() {
        DragAdapter adapter = new DragAdapter(R.layout.recycler_item, data);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(20);
        paint.setColor(Color.BLACK);

        adapter.enableDragItem(itemTouchHelper, R.id.card_view, true);
        adapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {

            }
        });
        adapter.enableSwipeItem();
        itemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        adapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void quickMaker() {
        RecyclerAdapter adapter = new RecyclerAdapter(R.layout.recycler_item, data);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv) {
                    Toast.makeText(getActivity(), "你点击了图片！", Toast.LENGTH_SHORT).show();
                } else if (view.getId() == R.id.button) {
                    ARouter.getInstance().build(activityList.getActivity(position)).navigation();
                }
            }
        });
//        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{
                        ObjectAnimator.ofFloat(view, "scaleX", 1.2f, 1.0f),
                        ObjectAnimator.ofFloat(view, "scaleY", 1.2f, 1.0f)
                };
            }
        });
        adapter.isFirstOnly(false);
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_layout, null);
        adapter.addHeaderView(headView);
    }

    private void init() {
        data = new ArrayList<>();
        activityList = new JumpBean(new ArrayList<String>(), new ArrayList<String>());
        addActivity();
        for (int i = 0; i < activityList.getSize(); i++) {
            RecyclerBean bean = new RecyclerBean("跳转啊~~~~~" + activityList.getJumpText(i), R.mipmap.ic_launcher, "Go!");
            data.add(bean);
        }
    }

    /******************
     * 添加
     *******************/
    private void addActivity() {
        activityList.add("/activity/2", "内存溢出");
        activityList.add("/activity/3", "内存溢出第三方");
        activityList.add("/activity/4", "webView浏览器");
        activityList.add("/activity/5", "联动Toolbar");
        activityList.add("/activity/6", "联动+侧边");
        activityList.add("/activity/15", "fake联动");
        activityList.add("/activity/7", "DataBinding");
        activityList.add("/activity/8", "Drag");
        activityList.add("/activity/9", "Drag2");
        activityList.add("/activity/10", "Drag3");
        activityList.add("/activity/11", "拍照");
        activityList.add("/activity/12", "活动动画");
        activityList.add("/activity/13", "Android枚举");
        activityList.add("/activity/14", "BlurView");
        activityList.add("/libraryActivity/library", "library");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
