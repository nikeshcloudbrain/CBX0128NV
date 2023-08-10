package com.allhdvideofree.downloader.videodownloader.activities.selection;

import static com.allhdvideofree.downloader.videodownloader.Utils.Constant.setToolBar;
import static com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader.DhiAllStoryActivity.extractLinks;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.NotificationCompat;

import com.google.gms.ads.AdUtils;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.Allweb.DhiInstaWebActivity;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;
import com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader.DhiAllStoryActivity;
import com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader.DhiInstagramActivity;
import com.allhdvideofree.downloader.videodownloader.Utils.ClipboardListener;

import java.util.Objects;

public class DhiInstaSelectionActivity extends DhiActivityBase {

    LinearLayoutCompat lin_instalink, lin_instaweb;
    String CopyKey = "";
    String CopyValue = "";
    public ClipboardManager clipBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_insta_selection);

        lin_instalink = findViewById(R.id.lin_instalink);
        lin_instaweb = findViewById(R.id.lin_instaweb);

        setToolBar(DhiInstaSelectionActivity.this,"All Video Downloader");

        clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (getIntent().getExtras() != null) {
            for (String str : getIntent().getExtras().keySet()) {
                this.CopyKey = str;
                String string = getIntent().getExtras().getString(this.CopyKey);
                if (this.CopyKey.equals("android.intent.extra.TEXT")) {
                    String string2 = getIntent().getExtras().getString(this.CopyKey);
                    this.CopyValue = string2;
                    this.CopyValue = extractLinks(string2);
                    callText(string);
                } else {
                    this.CopyValue = "";
                    callText(string);
                }
            }
        }
        ClipboardManager clipboardManager = this.clipBoard;
        if (clipboardManager != null) {
            clipboardManager.addPrimaryClipChangedListener(new ClipboardListener() {
                public void onPrimaryClipChanged() {
                    try {
                        CharSequence text = clipBoard.getPrimaryClip().getItemAt(0).getText();
                        Objects.requireNonNull(text);
                        showNotification(text.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }



        lin_instalink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callInstaActivity();
            }
        });

        lin_instaweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainAds().showInterAds(DhiInstaSelectionActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(DhiInstaSelectionActivity.this, DhiInstaWebActivity.class);
                        startActivity(intent);


                    }
                });
            }
        });

    }

    private void callText(String str) {
        try {
            if (!str.contains("instagram.com")) {

                callInstaActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callInstaActivity() {
        Intent intent = new Intent(this, DhiInstagramActivity.class);
        intent.putExtra("CopyIntent", this.CopyValue);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        new MainAds().showNativeAds(this, null, null, AdUtils.AD_LARGE);
        new MainAds().showBannerAds(this, null);

    }

    public void showNotification(String str) {
        if (str.contains("instagram.com") || str.contains("facebook.com") || str.contains("fb") || str.contains("tiktok.com") || str.contains("twitter.com") || str.contains("likee") || str.contains("sharechat") || str.contains("roposo") || str.contains("snackvideo") || str.contains("sck.io") || str.contains("chingari") || str.contains("myjosh") || str.contains("mitron")) {
            Intent intent = new Intent(this, DhiAllStoryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Notification", str);
            PendingIntent activity = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            NotificationManager notificationManager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            }
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel notificationChannel = new NotificationChannel(getResources().getString(R.string.app_name), getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.setLockscreenVisibility(1);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            notificationManager.notify(1, new NotificationCompat.Builder((Context) this, getResources().getString(R.string.app_name)).setAutoCancel(true).setSmallIcon((int) R.drawable.icon).setColor(getResources().getColor(R.color.black)).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon)).setDefaults(-1).setPriority(1).setContentTitle("Copied text").setContentText(str).setChannelId(getResources().getString(R.string.app_name)).setFullScreenIntent(activity, true).build());
        }
    }

    @Override
    public void onBackPressed() {
        new MainAds().showBackInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });

    }
}