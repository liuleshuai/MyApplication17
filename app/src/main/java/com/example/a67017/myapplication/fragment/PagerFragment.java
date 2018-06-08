package com.example.a67017.myapplication.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.adapter.PFRecyclerAdapter;
import com.example.a67017.myapplication.dao.GdUser;
import com.example.a67017.myapplication.tool.GdUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PagerFragment extends Fragment {
    private List<GdUser> list;

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.age)
    EditText age;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.remove)
    Button remove;
    @BindView(R.id.update)
    Button update;
    Unbinder unbinder;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    private PFRecyclerAdapter adapter;

    public PagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        list = new ArrayList<>();
        adapter = new PFRecyclerAdapter(R.layout.pf_recycler_item, list);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.add, R.id.remove, R.id.update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add:
                if (name.getText().toString().trim().length() != 0 && age.getText().toString().trim().length() != 0) {
                    GdUser user = new GdUser();
                    user.setName(name.getText().toString());
                    user.setAge(Integer.valueOf(age.getText().toString()));
                    GdUtil.insert(user);
                    refrushList();
                }
                break;
            case R.id.remove:
                break;
            case R.id.update:
                break;
            default:
                break;
        }
    }

    public void refrushList() {
        List<GdUser> tempList = GdUtil.queryAll();
        list.clear();
        list.addAll(tempList);
        adapter.notifyDataSetChanged();
    }
}
