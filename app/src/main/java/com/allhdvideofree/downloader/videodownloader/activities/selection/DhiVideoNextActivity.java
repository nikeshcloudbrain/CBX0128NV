package com.allhdvideofree.downloader.videodownloader.activities.selection;

import static com.allhdvideofree.downloader.videodownloader.Utils.Constant.setToolBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gms.ads.AdUtils;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;
import com.allhdvideofree.downloader.videodownloader.activities.AllVideoPlayer.DhiAllFolderVideoActivity;

public class DhiVideoNextActivity extends DhiActivityBase {
    RelativeLayout nxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_video_next);

        nxt = findViewById(R.id.nxt);

        setToolBar(DhiVideoNextActivity.this, "All Video Downloader");

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainAds().showInterAds(DhiVideoNextActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(DhiVideoNextActivity.this, DhiAllFolderVideoActivity.class);
                        startActivity(intent);

                    }
                });
            }
        });
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