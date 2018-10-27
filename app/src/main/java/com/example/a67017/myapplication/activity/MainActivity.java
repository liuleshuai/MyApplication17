package com.example.a67017.myapplication.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.PictureInPictureParams;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a67017.myapplication.MyApplication;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.adapter.PagerAdapter;
import com.example.a67017.myapplication.bean.FragmentBean;
import com.example.a67017.myapplication.customeview.CustomeViewPager;
import com.example.a67017.myapplication.event.Event1;
import com.example.a67017.myapplication.fragment.Fragment1;
import com.example.a67017.myapplication.fragment.Fragment10;
import com.example.a67017.myapplication.fragment.Fragment11;
import com.example.a67017.myapplication.fragment.Fragment12;
import com.example.a67017.myapplication.fragment.Fragment13;
import com.example.a67017.myapplication.fragment.Fragment14;
import com.example.a67017.myapplication.fragment.Fragment15;
import com.example.a67017.myapplication.fragment.Fragment16;
import com.example.a67017.myapplication.fragment.Fragment17;
import com.example.a67017.myapplication.fragment.Fragment18;
import com.example.a67017.myapplication.fragment.Fragment2;
import com.example.a67017.myapplication.fragment.Fragment3;
import com.example.a67017.myapplication.fragment.Fragment4;
import com.example.a67017.myapplication.fragment.Fragment5;
import com.example.a67017.myapplication.fragment.Fragment6;
import com.example.a67017.myapplication.fragment.Fragment7;
import com.example.a67017.myapplication.fragment.Fragment8;
import com.example.a67017.myapplication.fragment.Fragment9;
import com.example.a67017.myapplication.fragment.PagerFragment;
import com.example.common.tools.RxBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    CustomeViewPager pager;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    private PagerAdapter adapter;
    private FragmentBean fb;
    private List<MyTouchListener> myTouchListeners = new ArrayList<>();
    private Toast toast;
    private List<String> permissionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE); // 禁止截屏、录屏
        init();
//        StatusBarUtils.setColor(this, Color.BLUE, 1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }
        test();
    }

    private void checkPermission() {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        permissionList = new ArrayList<>();
        for (String item : permissions) {
            if (ContextCompat.checkSelfPermission(this, item) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(item);
            }
        }
        if (!permissionList.isEmpty()) {
            String[] requestPermissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, requestPermissions, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[i])) {
                            ActivityCompat.requestPermissions(this, new String[]{permissions[i]}, 0);
                        } else {
                            Toast.makeText(MainActivity.this, "您已禁止授予权限！", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                }
                // do something

                break;
            default:
                break;
        }
    }

    private void init() {
        fb = new FragmentBean();
        addPager();
        if (fb.getCount() > 5) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        if (fb.getImages() == null) {
            adapter = new PagerAdapter(this, getSupportFragmentManager(), fb.getFragmentClass(), fb.getFragmentTitle());
        } else {
            adapter = new PagerAdapter(this, getSupportFragmentManager(), fb.getFragmentClass(), fb.getFragmentTitle(), fb.getImages());
        }
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
            }
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ImageView iv = tab.getCustomView().findViewById(R.id.iv);
                ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "rotationY", 0, 360);
                animator.setDuration(1000);
                animator.start();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

/*        LinearLayout linearLayout = (LinearLayout) tabHost.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tab_line));
        linearLayout.setDividerPadding(dip2px(this, 10)); //设置分割线间隔*/

        int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        // 设置可以滑动
        pager.setScoll(true);
        pager.setPageMargin(pageMargin);
        pager.setOffscreenPageLimit(1);
        pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                if (position < -1 || position > 1) {
                    page.setScaleX(0.9F);
                    page.setScaleY(0.9F);
                } else {
                    float scaleFactor = Math.max(0.9F, 1 - Math.abs(position));

                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                }
            }
        });

        RxBus.getDefault().toFlowable(Event1.class).subscribe(new Consumer<Event1>() {
            @Override
            public void accept(@NonNull Event1 event1) throws Exception {
                Toast.makeText(getApplicationContext(), "事件触发啦！！！！", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addPager() {
        // 方式1：
        fb.add(Fragment16.class, "跳转", R.mipmap.ic_launcher);
        fb.add(PagerFragment.class, "null", R.mipmap.ic_launcher);
        fb.add(Fragment13.class, "RxJava+Retrofit", R.mipmap.ic_launcher);
        fb.add(Fragment17.class, "封装RxJava+Retrofit", R.mipmap.ic_launcher);
        fb.add(Fragment1.class, "百分比", R.mipmap.ic_launcher);
        fb.add(Fragment2.class, "颜色", R.drawable.tab_select);
        fb.add(Fragment3.class, "转球", R.mipmap.ic_launcher);
        fb.add(Fragment4.class, "OKHttp", R.mipmap.ic_launcher);
        fb.add(Fragment5.class, "Camera1", R.mipmap.ic_launcher);
        fb.add(Fragment6.class, "压缩", R.mipmap.ic_launcher);
        fb.add(Fragment7.class, "齿轮", R.mipmap.ic_launcher);
        fb.add(Fragment8.class, "水波纹", R.mipmap.ic_launcher);
        fb.add(Fragment9.class, "浏览器", R.mipmap.ic_launcher);
        fb.add(Fragment10.class, "旋转放大", R.mipmap.ic_launcher);
        fb.add(Fragment11.class, "PagerView", R.mipmap.ic_launcher);
        fb.add(Fragment12.class, "HeroLeague", R.mipmap.ic_launcher);
        fb.add(Fragment14.class, "嵌套", R.mipmap.ic_launcher);
        fb.add(Fragment15.class, "Recycle", R.mipmap.ic_launcher);
        fb.add(Fragment18.class, "RxJava", R.mipmap.ic_launcher);
        // 方式2：
/*        Class[] fClass = {Fragment2.class, Fragment3.class};
        String[] fTitle = {"篮球2", "视频2"};
        Integer[] images = {R.mipmap.ic_launcher, R.mipmap.ic_launcher};
        fb.addArray(fClass, fTitle, images);*/
    }

    /*************************
     * Fragment 设置onTouchEvent的方法
     ***************************************/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : myTouchListeners) {
            if (listener != null) {
                return listener.onTouch(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyTouchListener myOnTouchListener) {
        myTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyTouchListener myOnTouchListener) {
        myTouchListeners.remove(myOnTouchListener);
    }

    public interface MyTouchListener {
        public boolean onTouch(MotionEvent event);
    }

    private long onceTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - onceTime < 2000) {
            MyApplication.illegal = false;
            super.onBackPressed();
        } else {
            onceTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "再按一次返回键退出！", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onStop() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        Log.d("LK", "onStop!");
        super.onStop();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (Build.VERSION.SDK_INT >= 26) {
            PictureInPictureParams params = new PictureInPictureParams.Builder().build();
            enterPictureInPictureMode(params);
        }
    }

    public void test(){
        Log.d("syj","lklklklklk");
        final String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        Observable<String> letterSequence = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long position) throws Exception {
                        return letters[position.intValue()];
                    }
                }).take(letters.length);
        Log.d("syj","lklklklklk");
        Observable<Long> numberSequence = Observable.interval(2000, TimeUnit.MILLISECONDS).take(5);

        Observable.merge(letterSequence, numberSequence)
                .subscribe(new Observer<Serializable>() {
                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Error:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Serializable serializable) {
                        Log.d("syj",serializable.toString()+" ");
                    }
                });
    }
}
