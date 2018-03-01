package com.example.a67017.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.tool.HttpClientHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/activity/16")
public class Main16Activity extends AppCompatActivity {

    @BindView(R.id.button)
    Button buttonGet;
    @BindView(R.id.button2)
    Button buttonPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main16);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.button, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                HttpClientHelper.execute("http://www.wanandroid.com/tools/mockapi/2748/lk", false, null, new HttpClientHelper.HttpResult() {
                    @Override
                    public void success(String data) {
                        Toast.makeText(Main16Activity.this, data, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failed() {
                        Toast.makeText(Main16Activity.this, "连不上网络！", Toast.LENGTH_LONG).show();
                    }
                });


                break;
            case R.id.button2:
                HttpClientHelper.execute("http://172.16.96.81/eolinker_os/server/index.php?g=Web&c=Mock&o=simple&projectID=12&uri=lktest", true, null, new HttpClientHelper.HttpResult() {
                    @Override
                    public void success(String data) {
                        Toast.makeText(Main16Activity.this, data, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failed() {
                        Toast.makeText(Main16Activity.this, "连不上网络！", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            default:
                break;
        }
    }
}
