package com.allhdvideofree.downloader.videodownloader.activities;


import android.os.Bundle;
import android.view.View;

import com.google.gms.ads.AdUtils;
import com.google.gms.ads.MainAds;
import com.allhdvideofree.downloader.videodownloader.activities.selection.DhiSelectionActivity;
import com.allhdvideofree.downloader.videodownloader.Utils.uiController;
import com.allhdvideofree.downloader.videodownloader.databinding.ActivityDhiSkipBinding;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;

public class DhiSkipActivity extends DhiActivityBase {

    ActivityDhiSkipBinding binding;

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        new MainAds().showNativeAds(this, null, null, AdUtils.AD_NORMAL);
        new MainAds().showBannerAds(this, binding.includedBanner.adFrameMini);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDhiSkipBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        setToolBar(SkipActivity.this,"HD Tube Video Downloader");
        binding.llStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PowerPreference.getDefaultFile().getBoolean(Constant.StartScreenOnOff, false)) {
                    uiController.gotoActivity(DhiSkipActivity.this, DhiStartActivity.class, true, false);

                } else if (PowerPreference.getDefaultFile().getBoolean(Constant.AgeGenderScreenOnOff, false) && !PowerPreference.getDefaultFile().getBoolean(Constant.ageGanderChecked, false)) {
                    uiController.gotoActivity(DhiSkipActivity.this, DhiAgeActivity.class, true, false);
                } else if (PowerPreference.getDefaultFile().getBoolean(Constant.NextScreenOnOff, false)) {
                    uiController.gotoActivity(DhiSkipActivity.this, DhiNextContinueActivity.class, true, false);

                } else {
                    uiController.gotoActivity(DhiSkipActivity.this, DhiSelectionActivity.class, true, false);

                }
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        if (PowerPreference.getDefaultFile().getBoolean(Constant.CustomAdsOnOff, false)) {
//            new MainAds().showBackInterAds(this, new BackInterAds.OnAdClosedListener() {
//                @Override
//                public void onAdClosed() {
//                    finish();
//                }
//            });
//        } else {
//            Constant.showExitDialog(this);
//        }
//    }

    @Override
    public void onBackPressed() {
//

        Constant.showRateDialog(this, true);


    }
}