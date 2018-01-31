package com.example.a67017.myapplication.fragment;


import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.customeview.PathView3;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {


    /*    @BindView(R.id.path_view)
        PathView pathView;*/
    Unbinder unbinder;
    @BindView(R.id.view2)
    PathView3 view2;

    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view2, "stop", 0, 1);
        animator.setDuration(1000);
        animator.setRepeatCount(-1);
        animator.setInterpolator(new MyInterpolator(1.8f));
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view2, "stop1", 0, 1);
        animator1.setDuration(1000);
        animator1.setRepeatCount(-1);
        animator1.setInterpolator(new MyInterpolator(1.6f));
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view2, "stop2", 0, 1);
        animator2.setDuration(1000);
        animator2.setRepeatCount(-1);
        animator2.setInterpolator(new MyInterpolator(1.4f));
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(view2, "stop3", 0, 1);
        animator3.setDuration(1000);
        animator3.setRepeatCount(-1);
        animator3.setInterpolator(new MyInterpolator(1.2f));
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(view2, "stop4", 0, 1);
        animator4.setDuration(1000);
        animator4.setRepeatCount(-1);
        animator4.setInterpolator(new MyInterpolator(1.0f));
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(animator, animator1, animator2, animator3, animator4);
//        set.start();
        animator.start();
        animator1.start();
        animator2.start();
        animator3.start();
        animator4.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    class MyInterpolator implements TimeInterpolator {
        private float factor;

        public MyInterpolator(float factor) {
            this.factor = factor;
        }

        @Override
        public float getInterpolation(float input) {
            if (factor == 1.0) {
                return (float) (1.0 - Math.pow((1.0 - input), 2));
            } else {
                return (float) (1.0 - Math.pow((1.0 - input), 2 * factor));
            }
        }
    }
}
