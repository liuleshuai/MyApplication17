package com.example.a67017.myapplication.fragment;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.customeview.MyCardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment12Child extends Fragment {

    @BindView(R.id.iv)
    MyCardView iv;
    @BindView(R.id.tv)
    TextView tv;
    Unbinder unbinder;

    public Fragment12Child() {
        // Required empty public constructor
    }

    static public Fragment12Child getInstance(int imgId, String text) {
        Fragment12Child fragment = new Fragment12Child();
        Bundle bundle = new Bundle();
        bundle.putInt("image", imgId);
        bundle.putString("text", text);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment12_child, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv.setImageResource(getArguments().getInt("image"));
        tv.setText(getArguments().getString("text"));
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "rotation", 0f, 10f);
        animator.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
