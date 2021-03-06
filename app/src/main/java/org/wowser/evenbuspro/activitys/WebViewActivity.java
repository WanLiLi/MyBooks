package org.wowser.evenbuspro.activitys;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.wowser.evenbuspro.R;

public class WebViewActivity extends AppCompatActivity {
private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        init();
    }

    private void init(){
        webView = (WebView) findViewById(R.id.webview);

        Intent  intent = getIntent();
        String uriStr = intent.getStringExtra("handleResult");
        Log.d("wanli","结果uriStr："+uriStr);
       // String uribaidu = "http://www.baidu.com";
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(uriStr));

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return false;
            }
        });
        webView.loadUrl(uriStr);
    }
}
