package com.allhdvideofree.downloader.videodownloader.activities;

import android.os.Bundle;
import android.view.View;


import com.google.gms.ads.AdUtils;
import com.google.gms.ads.MainAds;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.activities.selection.DhiSelectionActivity;
import com.allhdvideofree.downloader.videodownloader.Utils.uiController;
import com.allhdvideofree.downloader.videodownloader.databinding.ActivityDhiNextContinueBinding;
import com.preference.PowerPreference;

public class DhiNextContinueActivity extends DhiActivityBase {

    ActivityDhiNextContinueBinding gbvNextBinding;

    @Override
    protected void onResume() {
        super.onResume();

        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        new MainAds().showNativeAds(this, null, null, AdUtils.AD_NORMAL);
        new MainAds().showBannerAds(this, gbvNextBinding.includedBanner.adFrameMini);

    }

    @Override
    public void onBackPressed() {
        if (PowerPreference.getDefaultFile().getBoolean(Constant.StartScreenOnOff, false)||PowerPreference.getDefaultFile().getBoolean(Constant.ContinousScreenOnOff, false)||PowerPreference.getDefaultFile().getBoolean(Constant.AgeGenderScreenOnOff, false)&&!PowerPreference.getDefaultFile().getBoolean(Constant.ageGanderChecked, false)) {
            uiController.onBackPressed(this);
        } else Constant.showRateDialog(this, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gbvNextBinding = ActivityDhiNextContinueBinding.inflate(getLayoutInflater());
        setContentView(gbvNextBinding.getRoot());
//        gbvNextBinding.includedToolbar.ivBackMain.setOnClickListener(v -> onBackPressed());
        gbvNextBinding.llStart.setOnClickListener(v -> uiController.gotoActivity(this, DhiSelectionActivity.class, true, false));
        gbvNextBinding.llStart.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {



               uiController.gotoActivity(DhiNextContinueActivity.this, DhiSelectionActivity.class, true, false);


       }
   });

    }
}