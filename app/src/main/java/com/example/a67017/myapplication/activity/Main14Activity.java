package com.example.a67017.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.tool.StatusBarUtils;
import com.example.a67017.myapplication.tool.Utils;
import com.github.mmin18.widget.RealtimeBlurView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/activity/14")
public class Main14Activity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.blur_view)
    RealtimeBlurView blurView;
    @BindView(R.id.web_view)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main14);
        ButterKnife.bind(this);
        init();
        immersive();
    }

    /**
     * setWebViewClient
     * <p>
     * 1.若没有设置 WebViewClient 则在点击链接之后由系统处理该 url，通常是使用浏览器打开或弹出浏览器选择对话框。
     * 2.若设置 WebViewClient 且该方法返回 true ，则说明由应用的代码处理该 url，WebView 不处理。
     * 3.若设置 WebViewClient 且该方法返回 false，则说明由 WebView 处理该 url，即用 WebView 加载该 url。
     */
    private void init() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl(String.format(Locale.CHINA,
                        "javascript:document.body.style.paddingTop='%fpx'; void 0",
                        (float) Utils.px2dip(Main14Activity.this, webView.getPaddingTop()
                                + StatusBarUtils.getStatusBarHeight(Main14Activity.this))
                ));
            }
        });
        webView.loadUrl("https://github.com/scwang90/SmartRefreshLayout");
    }


    private void immersive() {
        StatusBarUtils.setTranslucentStatusBarPadding(this, 0, toolbar);
        StatusBarUtils.setTranslucentStatusBarPadding(this, 0, blurView);
    }
}
