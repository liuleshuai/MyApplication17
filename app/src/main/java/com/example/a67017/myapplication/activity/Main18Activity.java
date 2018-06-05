package com.example.a67017.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.adapter.AnimateAdapter;
import com.example.a67017.myapplication.adapter.MySnapHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/activity/18")
public class Main18Activity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    private Integer[] images = {R.mipmap.jdzz, R.mipmap.ccdzz, R.mipmap.dfh,
            R.mipmap.dlzs, R.mipmap.sgkptt, R.mipmap.ttxss, R.mipmap.zmq, R.mipmap.zzhx};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main18);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        AnimateAdapter adapter = new AnimateAdapter(this, Arrays.asList(images));
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        MySnapHelper snapHelper = new MySnapHelper();
        snapHelper.attachToRecyclerView(rv);
        String ss = "{\"name\":\"liukuo\",\"age\":\"18\",\"sex\":\"man\"}";
        Mine mine = JSON.parseObject(ss, Mine.class);
        try {
            JSONObject object = new JSONObject(ss);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String sss = JSON.toJSONString(mine);
    }

    static class Mine {
        private String name;
        private String age;
        private String sex;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
