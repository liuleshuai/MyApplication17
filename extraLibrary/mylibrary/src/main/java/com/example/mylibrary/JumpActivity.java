package com.example.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.common.dao.GdUser;
import com.example.common.tools.GdUserUtil;

import java.util.ArrayList;
import java.util.List;

public class JumpActivity extends AppCompatActivity {

    private EditText name;
    private EditText age;
    private RecyclerView recycler;
    private List<GdUser> list;
    private Button add, remove, update;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syj_activity_jump);
        init();
    }

    private void init() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                setResult(1, i);
                finish();
            }
        });
        list = new ArrayList<>();
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GdUser user = new GdUser();
                user.setName(name.getText().toString());
                user.setAge(age.getText().toString());
                GdUserUtil.insertData(user);
                refrushList();
            }
        });
        remove = findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GdUserUtil.removeData(list.get(0).getId());
                refrushList();
            }
        });
        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GdUser user = list.get(0);
                user.setName("孙一嘉");
                GdUserUtil.updateData(user);
                refrushList();
            }
        });
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new MyAdapter(list);
        recycler.setAdapter(adapter);
    }

    private void refrushList() {
        list.clear();
        list.addAll(GdUserUtil.queryAllData());
        adapter.notifyDataSetChanged();
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
        private List<GdUser> list;

        public MyAdapter(List<GdUser> list) {
            this.list = list;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.syj_jump_item, parent, false);
            Holder holder = new Holder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.id.setText(String.valueOf(list.get(position).getId()));
            holder.name.setText(list.get(position).getName());
            holder.age.setText(list.get(position).getAge());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class Holder extends RecyclerView.ViewHolder {
            public TextView id, name, age;

            Holder(View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.id);
                name = itemView.findViewById(R.id.name);
                age = itemView.findViewById(R.id.age);
            }
        }
    }
}
