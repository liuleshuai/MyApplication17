package com.example.a67017.myapplication.fragment;


import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;

import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.customeview.CircleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 *
 * @author 67017
 */
public class Fragment2 extends Fragment {


    @BindView(R.id.button)
    Button button;
    @BindView(R.id.circle)
    CircleView circle;
    Unbinder unbinder;

    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", 1.5f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", 1.5f);
        ObjectAnimator animator0 = ObjectAnimator.ofPropertyValuesHolder(button, holder1, holder2);
        animator0.setDuration(2000);
        animator0.setRepeatMode(ValueAnimator.REVERSE);
        animator0.setRepeatCount(Animation.INFINITE);
//        animator0.start();

/*        ObjectAnimator animator = ObjectAnimator.ofInt(circle, "color", 0xffff0000, 0xff00ff00);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(3000);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(Animation.INFINITE);
//        animator.start();

        AnimatorSet set = new AnimatorSet();
//        set.playSequentially(animator0,animator);
        set.playTogether(animator0,animator);
        set.start();*/

        Keyframe kf1 = Keyframe.ofInt(0, 0xffff0000);
        Keyframe kf2 = Keyframe.ofInt(0.5f, 0xff00ff00);
        Keyframe kf3 = Keyframe.ofInt(1, 0xff0000ff);
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("color", kf1, kf2, kf3);
        ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(circle, holder);
        animator1.setRepeatCount(-1);
        animator1.setDuration(20000);
        animator1.start();
    }
}
