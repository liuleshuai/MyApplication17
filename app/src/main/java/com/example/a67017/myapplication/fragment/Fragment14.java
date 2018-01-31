package com.example.a67017.myapplication.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.customeview.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment14 extends Fragment {


    @BindView(R.id.list)
    MyListView list;
    ArrayAdapter<String> adapter;
    List<String> data = new ArrayList<>();
    Unbinder unbinder;

    public Fragment14() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment14, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i < 20; i++) {
            data.add("水果");
            data.add("香蕉");
            data.add("鸭梨");
        }
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data);
        list.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
