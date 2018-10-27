package com.example.a67017.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.customeview.CustomSwitchCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = "/activity/5")
public class Main5Activity extends AppCompatActivity {

    @BindView(R.id.switch_compat)
    CustomSwitchCompat switchCompat;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(R.color.colorPrimary);
        this.setSupportActionBar(toolbar);

        switchCompat.setSwitchListener(new CustomSwitchCompat.SwitchListener() {
            @Override
            public void onClick() {
                Log.d("LK", "2222222");
/*                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(button, "TranslationX", 200);
                objectAnimator.setDuration(300);
                objectAnimator.start();*/
                button.setTranslationX(200);
            }
        });
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        Toast.makeText(this, button.getLeft() + "", Toast.LENGTH_SHORT).show();
    }
}
