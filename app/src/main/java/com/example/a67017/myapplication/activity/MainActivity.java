package com.example.a67017.myapplication.activity;

import android.animation.ObjectAnimator;
import android.app.PictureInPictureParams;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import tools.RxBus;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabHost)
    TabLayout tabHost;
    @BindView(R.id.pager)
    CustomeViewPager pager;
    private PagerAdapter adapter;
    private FragmentBean fb;
    private List<MyTouchListener> myTouchListeners = new ArrayList<>();
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE); // 禁止截屏、录屏
        init();
//        StatusBarUtils.setColor(this, Color.BLUE, 1);

//        OutDialog outDialog = new OutDialog(this,R.style.MyDialog);
//        outDialog.show();
    }

    private void init() {
        fb = new FragmentBean();
        addPager();
        if (fb.getCount() > 5) {
            tabHost.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabHost.setTabMode(TabLayout.MODE_FIXED);
        }
        if (fb.getImages() == null) {
            adapter = new PagerAdapter(this, getSupportFragmentManager(), fb.getFragmentClass(), fb.getFragmentTitle());
        } else {
            adapter = new PagerAdapter(this, getSupportFragmentManager(), fb.getFragmentClass(), fb.getFragmentTitle(), fb.getImages());
        }
        pager.setAdapter(adapter);
        tabHost.setupWithViewPager(pager);
        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = tabHost.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
            }
        }
        tabHost.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        fb.add(PagerFragment.class, "GreenDao", R.mipmap.ic_launcher);
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
        boolean onTouch(MotionEvent event);
    }

    private long onceTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - onceTime < 2000) {
            MyApplication.illegal = false;
            toast = Toast.makeText(this, "双击退出！", Toast.LENGTH_LONG);
            super.onBackPressed();
        } else {
            onceTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "再按一次返回键退出！", Toast.LENGTH_LONG);
        }
        toast.show();
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
        Toast.makeText(this,"success!!!!",Toast.LENGTH_LONG).show();
    }
}
