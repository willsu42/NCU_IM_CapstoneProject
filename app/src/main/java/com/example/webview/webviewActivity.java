package com.example.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class webviewActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Bundle bundle = getIntent().getExtras();
        int key =bundle.getInt("key");




        webView = findViewById(R.id.webview2);
        webView.setWebViewClient(new WebViewClient());

        if(key==0){
            webView.loadUrl("https://goose168.com");
        }
        else if(key==1){ webView.loadUrl("https://www.cmoney.tw/app/");
        }
        else{ webView.loadUrl("https://rich01.com");}

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Button button = findViewById(R.id.button9);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(webviewActivity.this, ArticleActivity.class);
                startActivity(intent);
                webviewActivity.this.finish();
            }
        });
    }
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
