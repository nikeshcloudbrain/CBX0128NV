package com.allhdvideofree.downloader.videodownloader.activities.ExtraScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.allhdvideofree.downloader.videodownloader.activities.selection.DhiWtsSelectionActivity;
import com.google.gms.ads.AdUtils;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.R;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

public class DhiWhatsupIntro extends DhiActivityBase {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_whatsupintro);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        findViewById(R.id.txt_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MainAds().showInterAds(DhiWhatsupIntro.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(DhiWhatsupIntro.this, DhiWtsSelectionActivity.class));
                    }
                });


            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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