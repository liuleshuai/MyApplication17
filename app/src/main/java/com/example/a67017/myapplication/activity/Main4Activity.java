package com.example.a67017.myapplication.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.event.Event1;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import tools.RxBus;


/**
 * webView浏览器
 *
 * @author 67017
 */
@Route(path = "/activity/4")
public class Main4Activity extends AppCompatActivity {
    @BindView(R.id.activity_main4)
    RelativeLayout activityMain4;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        webView = new WebView(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        activityMain4.addView(webView, layoutParams);
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.baidu.com/");
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，
            // 需要先onDetachedFromWindow()，再destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.removeAllViews();
            webView.destroy();
        }
        RxBus.getDefault().post(new Event1());
        super.onDestroy();
    }

    /*    一个异步任务的执行一般包括以下几个步骤：

                1.execute(Params... params)，执行一个异步任务，需要我们在代码中调用此方法，触发异步任务的执行。

                2.onPreExecute()，在execute(Params... params)被调用后立即执行，一般用来在执行后台任务前对UI做一些标记。

                3.doInBackground(Params... params)，在onPreExecute()完成后立即执行，用于执行较为费时的操作，此方法将接收输入参数和返回计算结果。在执行过程中可以调用publishProgress(Progress... values)来更新进度信息。

                4.onProgressUpdate(Progress... values)，当调用publishProgress(Progress... values)公布进度时，此方法被执行，直接将进度信息更新到UI组件上。

                5.onPostExecute(Result result)，当后台操作结束时，此方法将会被调用，计算结果将做为参数传递到此方法中，直接将结果显示到UI组件上。

        在使用的时候，有几点需要格外注意：

                1.异步任务的实例必须在UI线程中创建。

                2.execute(Params... params)方法必须在UI线程中调用。

                3.不要手动调用onPreExecute()，doInBackground(Params... params)，onProgressUpdate(Progress... values)，onPostExecute(Result result)这几个方法。

                4.不能在doInBackground(Params... params)中更改UI组件的信息。

                5.一个任务实例只能执行一次，如果执行第二次将会抛出异常。*/

    // new MyAsyncTask().execute("www.baidu.com");

    class MyAsyncTask extends AsyncTask<String, Integer, Map<String, Object>> {

        @Override
        protected Map<String, Object> doInBackground(String... params) {
            // 耗时在这里处理
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Map<String, Object> stringObjectMap) {
            super.onPostExecute(stringObjectMap);
            // doInBackground的处理结果在这里进行显示（UI）
        }
    }
}
