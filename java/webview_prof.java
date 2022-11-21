package com.example.mcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//교수 홈페이지
public class webview_prof extends AppCompatActivity {

    private WebView webview;
    private String url = "https://www.mju.ac.kr/mjukr/index.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_prof);

        WebView webview = findViewById(R.id.webview_prof);
        //자바스크립트 허용
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(url);
        //크롬 사용 설정
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClientClass());
        //줌 설정 여부
        webview.getSettings().setSupportZoom(false);
        //줌 확대/축소 버튼 여부
        webview.getSettings().setBuiltInZoomControls(false);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()){
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {//현재 페이지에 url 읽기
            view.loadUrl(url);
            return true;
        }
    }
}