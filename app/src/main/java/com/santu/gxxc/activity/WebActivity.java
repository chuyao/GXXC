package com.santu.gxxc.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.santu.gxxc.R;

import java.io.File;

public class WebActivity extends BaseActivity {

    private ProgressBar progressBar;
    private WebView webView;
    private String url;

    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        initViews();
        webView.loadUrl(url);
    }

    void initViews() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        webView = (WebView) findViewById(R.id.web);
        if(title != null) {
            setTitle(title);
        }
        webView.setWebViewClient(new MyWebClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        initWebCache();
    }

    void initWebCache() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        File cache = getExternalCacheDir();
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(cache.getPath());
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if(newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if(title == null)
                WebActivity.this.setTitle(title);
        }
    }
}
