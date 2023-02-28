package com.example.is4448project.Activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.is4448project.R;

public class CovidWebviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_covidwebview);

        String type = getIntent().getStringExtra("type");

        WebView browser = findViewById(R.id.webView);
        browser.setWebViewClient(new MyBrowser());
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


        if (type.equals("Symptoms")) {
            browser.loadUrl("https://www2.hse.ie/conditions/coronavirus/symptoms.html");
        } else if (type.equals("Prevention")) {
            browser.loadUrl("https://www2.hse.ie/conditions/coronavirus/protect-yourself-and-others.html");
        }
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
