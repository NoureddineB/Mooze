package com.project.mooze.Utils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.project.mooze.R;

public class DetailsActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mWebView = findViewById(R.id.activity_detail_webview);
        this.displayWebview();
    }
    //Display Webview
    private void displayWebview(){
        String url = "https://moozeyourphone.com/conditions-dutilisation/";
        CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView,true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient());


    }
}
