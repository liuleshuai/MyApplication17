package com.example.a67017.myapplication.fragment;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.tool.dpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author 67017
 */
public class Fragment10 extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.iv3)
    ImageView iv3;

    private boolean start = true;
    private boolean start2 = true;
    private AnimatorSet set;
    private AnimatorSet set2;
    private ObjectAnimator animator;
    private ObjectAnimator animator1;
    private ObjectAnimator animator2;
    private ObjectAnimator animator3;
    private float scaleX;
    private float scaleY;
    private float scale2X;
    private float scale2Y;
    private float ivHeight;
    private float ivWidth;
    private float iv2Height;
    private float iv2Width;
    private float screenWidth;
    private float screenHeight;
    private int iv3Top;
    private int iv3Left;
    private int iv3Right;
    private int iv3Bottom;
    private int imgWidth;
    private int imgHeight;
    private AnimatorSet set1;
    private AnimatorSet animatorSet;

    public Fragment10() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment10, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv3.setImageResource(R.mipmap.ic_launcher);
        set = new AnimatorSet();
        set2 = new AnimatorSet();
        set1 = new AnimatorSet();
        animatorSet = new AnimatorSet();
        screenWidth = dpUtils.getScreenWidth(getContext());
        screenHeight = dpUtils.getScreenHeight(getContext());
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ivHeight = layout1.getMeasuredHeight();
                ivWidth = layout1.getMeasuredWidth();
                layout2.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                iv2Height = layout2.getMeasuredHeight();
                iv2Width = layout2.getMeasuredWidth();
                scaleX = screenWidth / ivHeight;
                scaleY = screenHeight / ivWidth;
                scale2X = screenWidth / iv2Width;
                scale2Y = screenHeight / iv2Height;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv, R.id.iv2, R.id.iv3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv:
                if (!set.isRunning()) {
                    float top = layout1.getTop();
                    float move = screenHeight / 2 - top - ivHeight / 2;
                    if (start) {
                        start = false;
                        animator = ObjectAnimator.ofFloat(layout1, "rotation", 0, 90);
                        animator1 = ObjectAnimator.ofFloat(layout1, "scaleX", 1, scaleY);
                        animator2 = ObjectAnimator.ofFloat(layout1, "scaleY", 1, scaleX);
                        animator3 = ObjectAnimator.ofFloat(layout1, "translationY", 0, move);
                        set.playTogether(animator, animator1, animator2, animator3);
                        set.setDuration(1000);
                        set.start();
                    } else {
                        start = true;
                        animator = ObjectAnimator.ofFloat(layout1, "rotation", 90, 0);
                        animator1 = ObjectAnimator.ofFloat(layout1, "scaleX", scaleY, 1);
                        animator2 = ObjectAnimator.ofFloat(layout1, "scaleY", scaleX, 1);
                        animator3 = ObjectAnimator.ofFloat(layout1, "translationY", move, 0);
                        set.playTogether(animator, animator1, animator2, animator3);
                        set.setDuration(1000);
                        set.start();
                    }
                }
                break;
            case R.id.iv2:
                if (!set2.isRunning()) {
                    if (start2) {
                        start2 = false;
                        animator1 = ObjectAnimator.ofFloat(layout2, "scaleX", 1, scale2X);
                        animator2 = ObjectAnimator.ofFloat(layout2, "scaleY", 1, scale2Y);
                        set2.playTogether(animator1, animator2);
                        set2.setDuration(1000);
                        set2.start();
                    } else {
                        start2 = true;
                        animator1 = ObjectAnimator.ofFloat(layout2, "scaleX", scale2X, 1);
                        animator2 = ObjectAnimator.ofFloat(layout2, "scaleY", scale2Y, 1);
                        set2.playTogether(animator1, animator2);
                        set2.setDuration(1000);
                        set2.start();
                    }
                }
                break;
            case R.id.iv3:
                View popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_layout, null);
                final ImageView img = (ImageView) popupView.findViewById(R.id.image);
                final View white = (View) popupView.findViewById(R.id.white);

                iv3.setDrawingCacheEnabled(true);
                Bitmap imageBitmap = iv3.getDrawingCache();
                final Bitmap cloneBitmap = Bitmap.createBitmap(imageBitmap);
                iv3.setDrawingCacheEnabled(false);

                img.setImageBitmap(cloneBitmap);
                imgWidth = iv3.getWidth();
                imgHeight = iv3.getHeight();
                iv3Top = iv3.getTop();
                iv3Left = iv3.getLeft();
                iv3Right = iv3.getRight();
                iv3Bottom = iv3.getBottom();
                final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                popupWindow.setOutsideTouchable(true);

                popupWindow.showAtLocation(iv3, Gravity.CENTER, 0, 0);

                ObjectAnimator animatorRotation = ObjectAnimator.ofFloat(img, "rotation", 0, 90);
                ObjectAnimator animatorTranslation = ObjectAnimator.ofFloat(img, "translationY", iv3Top, screenHeight / 2 - imgHeight / 2);
                ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(img, "scaleX", 1, Math.min(scaleX, scaleY));
                ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(img, "scaleY", 1, Math.min(scaleX, scaleY));
                ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(white, "alpha", 0, 1);

                iv3.setVisibility(View.GONE);
                if (!animatorSet.isRunning() && !set1.isRunning()) {
                    animatorSet.setDuration(500);
                    animatorSet.playTogether(animatorRotation, animatorTranslation, animatorScaleX, animatorScaleY, animatorAlpha);
                    animatorSet.start();
                }
                popupView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!animatorSet.isRunning() && !set1.isRunning()) {
                            ObjectAnimator animatorRotation = ObjectAnimator.ofFloat(img, "rotation", 90, 0);
                            ObjectAnimator animatorTranslation = ObjectAnimator.ofFloat(img, "translationY", screenHeight / 2 - imgHeight / 2, iv3Top);
                            ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(img, "scaleX", Math.min(scaleX, scaleY), 1);
                            ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(img, "scaleY", Math.min(scaleX, scaleY), 1);
                            ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(img, "alpha", 1, 0);
                            ObjectAnimator animatorAlpha1 = ObjectAnimator.ofFloat(white, "alpha", 1, 0);
                            set1.setDuration(500);
                            set1.playTogether(animatorRotation, animatorTranslation, animatorScaleX, animatorScaleY, animatorAlpha, animatorAlpha1);
                            set1.start();
                            set1.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    popupWindow.dismiss();
                                    iv3.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
