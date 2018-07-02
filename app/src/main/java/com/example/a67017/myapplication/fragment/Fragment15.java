package com.example.a67017.myapplication.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.adapter.DividerGridItemDecoration;
import com.example.a67017.myapplication.adapter.MyAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment15 extends Fragment {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;

    public Fragment15() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment15, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        MyAdapter adapter = new MyAdapter(getActivity());
        adapter.setListener(new MyAdapter.OnclickListener() {
            @Override
            public void onItemClick() {
                Toast.makeText(getActivity(), "你点击了！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick() {
                Toast.makeText(getActivity(), "你长按了！", Toast.LENGTH_SHORT).show();
            }
        });
        recycleView.setAdapter(adapter);
        recycleView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recycleView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
