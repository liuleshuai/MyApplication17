package com.example.a67017.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.customeview.CameraSurfaceView;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/activity/11")
public class Main11Activity extends AppCompatActivity {

    @BindView(R.id.btn_switch_camera)
    Button mSwitchCamera;
    @BindView(R.id.btn_capture)
    Button mCapture;
    @BindView(R.id.sv_camera)
    CameraSurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);
        ButterKnife.bind(this);
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
    protected void onResume() {
        super.onResume();
        mSurfaceView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSurfaceView.releaseCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSurfaceView != null) {
            mSurfaceView.releaseCamera();
        }
    }
}
