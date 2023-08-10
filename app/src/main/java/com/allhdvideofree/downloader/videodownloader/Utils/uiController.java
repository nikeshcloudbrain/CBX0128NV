package com.allhdvideofree.downloader.videodownloader.Utils;

import android.app.Activity;
import android.content.Intent;

import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;

public class uiController {

    public static void onBackPressed(Activity activity) {
        new MainAds().showBackInterAds(activity, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                activity.finish();
            }
        });
    }

    public static void gotoActivity(Activity activity, Class<?> activityClass, boolean isAds, boolean isFinish) {
        sendIntent(activity, new Intent(activity, activityClass), isAds, isFinish);
    }

    public static void sendIntent(Activity activity, Intent intent, boolean isAds, boolean isFinish) {
        if (isAds) {
            new MainAds().showInterAds(activity, new InterAds.OnAdClosedListener() {
                @Override
                public void onAdClosed() {
                    activity.startActivity(intent);
                    if (isFinish) activity.finish();
                }
            });
        } else {
            activity.startActivity(intent);
            if (isFinish) activity.finish();
        }
    }
}
