package com.allhdvideofree.downloader.videodownloader.Utils;

import android.content.Context;
import android.text.TextUtils;

import androidx.multidex.MultiDex;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gms.ads.CustomApplication;
import com.allhdvideofree.downloader.videodownloader.Allweb.BrowserFragment;


public class DhiApplication extends CustomApplication {
    public static final String TAG = DhiApplication.class.getSimpleName();
    public static DhiApplication mInstance;
    public static Context mActivity;
    public static boolean activity = false;
    private RequestQueue mRequestQueue;
    public BrowserFragment browserFragment = null;

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mActivity = this;

        new PrefShar(this);

        this.browserFragment = null;

    }

    public static synchronized DhiApplication getInstance() {
        return mInstance;
    }

    public static synchronized Context getContext() {
        return getInstance().getApplicationContext();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {

        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}
