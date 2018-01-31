package com.example.a67017.myapplication.fragment;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.customeview.ProgressView;
import com.example.a67017.myapplication.tool.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author 67017
 */
public class Fragment1 extends Fragment {
    @BindView(R.id.button)
    AppCompatButton button;
    Unbinder unbinder;
    @BindView(R.id.hello)
    TextView hello;
    @BindView(R.id.progress)
    ProgressView progress;
    @BindView(R.id.version)
    TextView version;
    // TODO: Rename parameter arguments, choose names that match

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        unbinder = ButterKnife.bind(this, view);
        progress.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        version.setText(MyUtils.s);
/*        boolean debug = Boolean.valueOf(BuildConfig.debug);
        if (debug) {
            Toast.makeText(getActivity(), "debug!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "not debug!", Toast.LENGTH_SHORT).show();
        }*/
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
//        hello.animate().rotation(360).setDuration(2000).setInterpolator(new AnticipateOvershootInterpolator());
//        ObjectAnimator animator = ObjectAnimator.ofFloat(hello, "rotation", 0, 360);
//        animator.setDuration(2000);
//        animator.setInterpolator(new LinearInterpolator());
//        animator.setRepeatMode(ValueAnimator.RESTART);
//        animator.start();

//        ObjectAnimator animator1 = ObjectAnimator.ofFloat(progress, "progress", 0, 100);
//        animator1.setDuration(2000);
//        animator1.start();

        Keyframe kf1 = Keyframe.ofFloat(0, 0);
        Keyframe kf2 = Keyframe.ofFloat(0.5f, 100);
        Keyframe kf3 = Keyframe.ofFloat(1, 80);
        PropertyValuesHolder pvh = PropertyValuesHolder.ofKeyframe("progress", kf1, kf2, kf3);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(progress, pvh);
        animator.setDuration(2000);
        animator.start();
    }
}
