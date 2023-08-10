package com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.Utils.MyUtils;
import com.allhdvideofree.downloader.videodownloader.Utils.SharePrefs;

import java.io.PrintStream;

public class DhiFBLoginActivity extends AppCompatActivity {
    public String cookies;
    public SwipeRefreshLayout swipeRefreshLayout;
    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_login);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        webView = findViewById(R.id.webView);

        web_FB_Login();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public final void onRefresh() {
                web_FB_Login();
            }
        });
    }

    @SuppressLint("JavascriptInterface")
    public void web_FB_Login() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().removeAllCookie();
        WebSettings settings = this.webView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setSupportMultipleWindows(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.60 Safari/537.36");
        webView.addJavascriptInterface(this, "Android");
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this.webView, true);
            settings.setMixedContentMode(2);
        }

        settings.setPluginState(WebSettings.PluginState.ON);

        settings.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= 19) {
            Paint paint = null;
            this.webView.setLayerType(View.LAYER_TYPE_HARDWARE, (Paint) null);
        } else {
            Paint paint2 = null;
            this.webView.setLayerType(View.LAYER_TYPE_SOFTWARE, (Paint) null);
        }

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    DhiFBLoginActivity.this.swipeRefreshLayout.setRefreshing(false);
                } else {
                    DhiFBLoginActivity.this.swipeRefreshLayout.setRefreshing(true);
                }
            }
        });
        webView.setWebViewClient(new MyBrowser());
        webView.loadUrl("https://mbasic.facebook.com/");
    }

    private class MyBrowser extends WebViewClient {
        private MyBrowser() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            if (Build.VERSION.SDK_INT < 21) {
                return true;
            }
            webView.loadUrl(webResourceRequest.getUrl().toString());
            DhiFBLoginActivity.this.cookies = CookieManager.getInstance().getCookie(webResourceRequest.getUrl().toString());
            if (!MyUtils.isNullOrEmpty(DhiFBLoginActivity.this.cookies) && DhiFBLoginActivity.this.cookies.contains("c_user")) {
                SharePrefs.getInstance(DhiFBLoginActivity.this).putString(SharePrefs.FBCOOKIES, DhiFBLoginActivity.this.cookies);
            }
            return true;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            DhiFBLoginActivity.this.cookies = CookieManager.getInstance().getCookie(str);
            if (!MyUtils.isNullOrEmpty(DhiFBLoginActivity.this.cookies) && DhiFBLoginActivity.this.cookies.contains("c_user")) {
                SharePrefs.getInstance(DhiFBLoginActivity.this).putString(SharePrefs.FBCOOKIES, DhiFBLoginActivity.this.cookies);
            }
            return true;
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            cookies = CookieManager.getInstance().getCookie(str);
            webView.loadUrl("javascript:Android.resultOnFinish();");
            webView.loadUrl("javascript:var el = document.querySelectorAll('input[name=fb_dtsg]');Android.resultOnFinish(el[0].value);");

            if (str.length() >= 15) {
                try {
                    if (!MyUtils.isNullOrEmpty(cookies) && cookies.contains("c_user")) {
                        SharePrefs.getInstance(DhiFBLoginActivity.this).putString(SharePrefs.FBKEY, str);
                        SharePrefs.getInstance(DhiFBLoginActivity.this).putBoolean(SharePrefs.ISFBLOGIN, true);
                        PrintStream printStream = System.out;
                        printStream.println("Key - " + str);
                        Intent intent = new Intent();
                        intent.putExtra("result", "result");
                        setResult(-1, intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        @Override
        public void onLoadResource(WebView webView, String str) {
            Log.v("onLoadUrl==>", str);
            if (str.contains(".jpg") || str.contains(".mp4")) {
                Log.d("URL_CHECK", "onLoadResource: " + str);
            }
            webView.loadUrl("javascript:(function prepareVideo() {var el =document.querySelectorAll('div[data-sigil]');for(var i=0;i<el.length; i++){var sigil = el[i].dataset.sigil;if(sigil.indexOf('inlineVideo') > -1){delete el[i].dataset.sigil;console.log(i);var jsonData =JSON.parse(el[i].dataset.store);el[i].setAttribute('onClick','FBDownloader.processVideo(\"'+jsonData['src']+'\",\"'+jsonData['videoID']+'\");');}}})()");
            webView.loadUrl("javascript:(window.onload=prepareVideo;)()");
        }

    }


}