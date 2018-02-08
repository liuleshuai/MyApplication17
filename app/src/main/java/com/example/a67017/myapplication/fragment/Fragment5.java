package com.example.a67017.myapplication.fragment;


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.customeview.CameraSurfaceView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

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
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.layout)
    RelativeLayout layout;
    Unbinder unbinder;
    CameraSurfaceView mSurfaceView;
    RxPermissions rxPermissions;

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
        rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean granted) throws Exception {
                        if (granted) {
                            init();
                        } else {
                            Toast.makeText(getActivity(), "获取权限失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void init() {
        mSurfaceView = new CameraSurfaceView(getActivity());
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
    public void onResume() {
        super.onResume();
        if (mSurfaceView != null) {
            mSurfaceView.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSurfaceView != null) {
            mSurfaceView.releaseCamera();
        }
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
