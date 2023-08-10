package com.allhdvideofree.downloader.videodownloader.activities.selection;

import static com.allhdvideofree.downloader.videodownloader.Utils.Constant.setToolBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.gms.ads.AdUtils;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;
import com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader.DhiWhatsappActivity;
import com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader.DhiWtsBusActivity;

public class DhiWtsSelectionActivity extends DhiActivityBase {
    LinearLayoutCompat lin_wtsup, lin_wtsupbus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_wts_selection);


        setToolBar(DhiWtsSelectionActivity.this,"All Video Downloader");

        lin_wtsup = findViewById(R.id.lin_wtsup);
        lin_wtsupbus = findViewById(R.id.lin_wtsupbus);




        lin_wtsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainAds().showInterAds(DhiWtsSelectionActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(DhiWtsSelectionActivity.this, DhiWhatsappActivity.class);
                        startActivity(intent);


                    }
                });
            }
        });

        lin_wtsupbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainAds().showInterAds(DhiWtsSelectionActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(DhiWtsSelectionActivity.this, DhiWtsBusActivity.class);
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