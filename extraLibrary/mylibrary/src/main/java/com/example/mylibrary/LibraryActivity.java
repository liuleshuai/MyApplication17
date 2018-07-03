package com.example.mylibrary;

import android.os.Bundle;
import android.support.transition.AutoTransition;
import android.support.transition.Slide;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/libraryActivity/library")
public class LibraryActivity extends AppCompatActivity {

    @BindView(R2.id.button)
    Button button;
    @BindView(R2.id.activity_library)
    RelativeLayout activityLibrary;
    @BindView(R2.id.tv)
    TextView tv;
    @BindView(R2.id.slide)
    Button slide;
    @BindView(R2.id.autoTransition)
    Button autoTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syj_activity_library);
        ButterKnife.bind(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "测试，测试！", Toast.LENGTH_SHORT).show();
                ARouter.getInstance().build("/libraryActivity/main").navigation();
            }
        });
        Cat cat = new Cat(2, "green");
        System.out.println(cat.getColor());
//        Executor executor = new ThreadPoolExecutor(4,5,400, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
//        Executor executor = Executors.newCachedThreadPool();
    }

    private void setAnim(Transition... transition) {
        TransitionSet set = new TransitionSet();
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        set.setDuration(1000);
        set.setInterpolator(new LinearInterpolator());
        for (Transition item : transition) {
            set.addTransition(item);
        }
        TransitionManager.beginDelayedTransition(activityLibrary, set);
        if (tv.getVisibility() == View.VISIBLE) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R2.id.slide, R2.id.autoTransition})
    public void onViewClicked(View view) {
        int i = view.getId();
        //注意此处是R，不是R2
        if (i == R.id.slide) {
            Slide slide = new Slide(Gravity.END);
            //AutoTransition可以使其余的控件随动，不加的话特别生硬
            AutoTransition auto = new AutoTransition();
            setAnim(slide, auto);
        } else if (i == R.id.autoTransition) {
            AutoTransition auto = new AutoTransition();
            setAnim(auto);
        }
    }

    public class Cat {
        int age;
        String color;

        public Cat(int age, String color) {
            this.age = age;
            this.color = color;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
