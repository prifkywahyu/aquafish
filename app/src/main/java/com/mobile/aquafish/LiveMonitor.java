package com.mobile.aquafish;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class LiveMonitor extends AppCompatActivity {

    private WebView view;
    private ProgressBar bar;
    public FragmentMain main;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_monitor);

        bar = findViewById(R.id.progressLive);
        view = findViewById(R.id.webView);
        view.setWebViewClient(new myWebClient());
        view.loadUrl("http://cctv.balitower.co.id/Bendungan-Hilir-003-700014_4/embed.html");

        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            bar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    @Override
    public void onBackPressed() {
        if (view.canGoBack()) {
            view.goBack();
            main.getSupportFragmentManager().findFragmentById(R.id.navigation_profile);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && view.canGoBack()) {
            view.goBack();
            main.getSupportFragmentManager().findFragmentById(R.id.navigation_profile);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
