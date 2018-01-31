package com.example.a67017.myapplication.fragment;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Button;

import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.customeview.GearView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment7 extends Fragment {


    @BindView(R.id.open)
    Button open;
    @BindView(R.id.close)
    Button close;
    Unbinder unbinder;
    @BindView(R.id.gear_view)
    GearView gearView;
    private ObjectAnimator objectAnimator;
    private ObjectAnimator rotateAnimator;
    private float endAngle;

    public Fragment7() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment7, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.open, R.id.close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open:
                if (objectAnimator != null && objectAnimator.isRunning()) {
                    objectAnimator.cancel();
                }
                objectAnimator = ObjectAnimator.ofFloat(gearView, "gearRadius", 0, 1);
                objectAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        rotate(50);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                objectAnimator.setDuration(1000);
                objectAnimator.start();
                break;
            case R.id.close:
                if (rotateAnimator != null && rotateAnimator.isRunning()) {
                    rotateAnimator.end();
                }
                objectAnimator = ObjectAnimator.ofFloat(gearView, "gearRadius", 1, 0);
                objectAnimator.setDuration(1000);
                objectAnimator.start();
                break;
            default:
                break;
        }
    }

    public void rotate(final int num) {
        rotateAnimator = ObjectAnimator.ofFloat(gearView, "rotation", 0, num * 360);
        rotateAnimator.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float x) {
                return (float) ((Math.cos((x + 1) * Math.PI) / 2.0) + 0.5);
            }
        });
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                endAngle = (float) animation.getAnimatedValue();
            }
        });
        rotateAnimator.setDuration(num * 1000);
        rotateAnimator.start();
    }
}
