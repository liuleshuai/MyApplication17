package com.example.mylibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/libraryActivity/library")
public class LibraryActivity extends AppCompatActivity {


    @BindView(R2.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
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
