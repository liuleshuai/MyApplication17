package com.example.a67017.myapplication.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.tool.MyOkHttp;
import com.example.a67017.myapplication.tool.MyThreadPool;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment {


    @BindView(R.id.button)
    AppCompatButton button;
    @BindView(R.id.text)
    TextView text;
    Unbinder unbinder;
    @BindView(R.id.layout)
    LinearLayout layout;

    public Fragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment4, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        MyThreadPool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    MyOkHttp.getInstance().getHttp(new URL("http://www.baidu.com/"), new MyOkHttp.ResponseListener() {
                        @Override
                        public void success(final String json) {
                            text.setText(json);
                        }

                        @Override
                        public void failed(Request request, IOException e) {
                            Snackbar.make(layout, "失败了！", Snackbar.LENGTH_SHORT).show();
                        }

                        @Override
                        public void error(String json, int code) {
                            text.setText(json);
                            Snackbar.make(layout, "出错了！", Snackbar.LENGTH_SHORT).show();
                        }

                    });

                    Log.d("LK", MyThreadPool.getInstance().getThreadName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setText(String s) {
        text.setText(s);
    }
}
