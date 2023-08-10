package com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.Utils.SharePrefs;

public class DhiLoginActivity extends AppCompatActivity {
    public String cookies;
    public SwipeRefreshLayout swipeRefreshLayout;
    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_login);


        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        webView = findViewById(R.id.webView);

        LoadPage();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                LoadPage();
            }
        });
    }

    public void LoadPage() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.setWebViewClient(new MyBrowser());
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().removeAllCookie();
        webView.loadUrl("https://www.instagram.com/accounts/login/");

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(true);
                }
            }
        });

    }

    public String getCookie(String str, String str2) {

        String cookie = CookieManager.getInstance().getCookie(str);

        if (cookie == null || cookie.isEmpty()) {
            return null;
        }
        for (String str3 : cookie.split(";")) {
            if (str3.contains(str2)) {
                return str3.split("=")[1];
            }
        }
        return null;
    }

    private class MyBrowser extends WebViewClient {
        private MyBrowser() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }

        public void onLoadResource(WebView webView, String str) {
            super.onLoadResource(webView, str);
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);

            cookies = CookieManager.getInstance().getCookie(str);

            try {
                String cookie = DhiLoginActivity.this.getCookie(str, "sessionid");
                String cookie2 = DhiLoginActivity.this.getCookie(str, "csrftoken");
                String cookie3 = DhiLoginActivity.this.getCookie(str, "ds_user_id");
                if (cookie != null && cookie2 != null && cookie3 != null) {
                    SharePrefs.getInstance(DhiLoginActivity.this).putString(SharePrefs.COOKIES, cookies);
                    SharePrefs.getInstance(DhiLoginActivity.this).putString(SharePrefs.CSRF, cookie2);
                    SharePrefs.getInstance(DhiLoginActivity.this).putString(SharePrefs.SESSIONID, cookie);
                    SharePrefs.getInstance(DhiLoginActivity.this).putString(SharePrefs.USERID, cookie3);
                    SharePrefs.getInstance(DhiLoginActivity.this).putBoolean(SharePrefs.ISINSTALOGIN, true);
                    webView.destroy();
                    Intent intent = new Intent();
                    intent.putExtra("result", "result");
                    DhiLoginActivity.this.setResult(-1, intent);
                    DhiLoginActivity.this.finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
        }

        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            return super.shouldInterceptRequest(webView, webResourceRequest);
        }

        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            return super.shouldOverrideUrlLoading(webView, webResourceRequest);
        }
    }
}