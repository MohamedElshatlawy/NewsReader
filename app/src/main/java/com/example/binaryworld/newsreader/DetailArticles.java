package com.example.binaryworld.newsreader;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dmax.dialog.SpotsDialog;

public class DetailArticles extends AppCompatActivity {

    WebView webView;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_articles);
        webView=findViewById(R.id.webView);
        alertDialog=new SpotsDialog(this);
        alertDialog.show();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                alertDialog.dismiss();
            }
        });

        if(getIntent() !=null){
                if(!getIntent().getStringExtra("webURL").isEmpty()){
                    webView.loadUrl(getIntent().getStringExtra("webURL"));
                }
        }

    }
}
