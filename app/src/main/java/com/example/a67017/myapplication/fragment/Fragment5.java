package com.example.a67017.myapplication.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.customeview.CameraSurfaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 *
 * @author 67017
 */
public class Fragment5 extends Fragment {

    @BindView(R.id.btn_switch_camera)
    Button mSwitchCamera;
    @BindView(R.id.btn_capture)
    Button mCapture;
    @BindView(R.id.sv_camera)
    CameraSurfaceView mSurfaceView;
    Unbinder unbinder;

    public Fragment5() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment5, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSurfaceView.autoFocus();
            }
        });


        mSwitchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSurfaceView.switchCamera();
            }
        });

        mCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSurfaceView.capture(v);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mSurfaceView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSurfaceView.releaseCamera();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mSurfaceView != null) {
            mSurfaceView.releaseCamera();
        }
        unbinder.unbind();
    }
}
