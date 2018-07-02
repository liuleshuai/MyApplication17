package com.example.mylibrary;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.example.common.GlideApp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = "/libraryActivity/main")
public class MainActivity extends AppCompatActivity {
    String url = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1530522551&di=bdaf6a2d07d8b40e732ee0de02c89688&src=http://www.08lr.cn/uploads/allimg/170822/1-1FR21A020.jpg";

    @BindView(R2.id.img)
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syj_activity_main);
        ButterKnife.bind(this);

        String json = "{\"是大赛打死\":\"dsadasdas\"}";

        // 利用fragment捆绑生命周期
        Fragment fragment = new TestFragment();
        FragmentManager fm = this.getSupportFragmentManager();
        fm.beginTransaction().add(fragment, null).commitAllowingStateLoss();

        SparseArray<String> map = new SparseArray<>();
        String s = "101";
        map.put(1, s);

        int hashCode = s.hashCode();
        Log.d("LLK", "" + hashCode);
        Log.d("LKK", Build.VERSION.SDK_INT + "");//手机的

//        imageView = findViewById(R2.id.image);
//        imageView.setImageResource(R.drawable.asd);
        //V3
//        Glide.with(this).asBitmap().load(url).into(imageView);
        // v4
        GlideApp.with(this).asBitmap()
                .override(200, 200)
                .load(url)
                .placeholder(R.drawable.ic_launcher)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(new ImageViewTarget<Bitmap>(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        imageView.setImageBitmap(resource);
                    }
                });
//        GlideApp.with(this).asBitmap().load(url).placeholder(R.drawable.ic_launcher).into(imageView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // 如果响应方法一个不写，会报错
    @Subscribe
    public void responseEvent(String s) {

    }
}
