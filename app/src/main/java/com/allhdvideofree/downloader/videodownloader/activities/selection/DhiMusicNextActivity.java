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
import com.allhdvideofree.downloader.videodownloader.activities.HDMusicplayer.DhiMyMusicActivity;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;

public class DhiMusicNextActivity extends DhiActivityBase {
    RelativeLayout nxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_music_next);

        nxt = findViewById(R.id.nxt);

        setToolBar(DhiMusicNextActivity.this, "All Video Downloader");

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainAds().showInterAds(DhiMusicNextActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(DhiMusicNextActivity.this, DhiMyMusicActivity.class);
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