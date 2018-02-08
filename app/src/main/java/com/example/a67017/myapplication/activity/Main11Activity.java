package com.example.a67017.myapplication.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.customeview.CameraSurfaceView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

@Route(path = "/activity/11")
public class Main11Activity extends AppCompatActivity {

    @BindView(R.id.btn_switch_camera)
    Button mSwitchCamera;
    @BindView(R.id.btn_capture)
    Button mCapture;
    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.ll)
    LinearLayout ll;
    private CameraSurfaceView mSurfaceView;
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);
        ButterKnife.bind(this);
        rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean granted) throws Exception {
                        if (granted) {
                            init();
                        } else {
                            Toast.makeText(Main11Activity.this, "获取不到权限！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void init() {
        mSurfaceView = new CameraSurfaceView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, R.id.ll);
        layout.addView(mSurfaceView, params);

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
        if (mSurfaceView != null) {
            mSurfaceView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSurfaceView != null) {
            mSurfaceView.releaseCamera();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSurfaceView != null) {
            mSurfaceView.releaseCamera();
        }
    }
}
