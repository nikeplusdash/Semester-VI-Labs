package com.zespr.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        WebView wv = (WebView)findViewById(R.id.webview);
//        wv.loadUrl("https://developer.android.com/guide/webapps/webview");
        WebView myWebView = new WebView(getApplicationContext());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        setContentView(myWebView);
        myWebView.loadUrl("https://www.google.com");
    }
}