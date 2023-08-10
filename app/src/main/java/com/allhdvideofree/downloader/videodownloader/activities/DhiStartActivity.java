package com.allhdvideofree.downloader.videodownloader.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gms.ads.AdUtils;
import com.google.gms.ads.MainAds;
import com.allhdvideofree.downloader.videodownloader.activities.selection.DhiSelectionActivity;
import com.allhdvideofree.downloader.videodownloader.Utils.uiController;
import com.allhdvideofree.downloader.videodownloader.databinding.ActivityDhiStartBinding;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;


public class DhiStartActivity extends DhiActivityBase {
    ActivityDhiStartBinding binding;

    @Override
    protected void onResume() {
        super.onResume();


            if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }

//        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.QurekaOnOff, false)) {
//            binding.llad.setVisibility(View.VISIBLE);
//            showIcon(StartActivity.this);
//        } else {
//            binding.llad.setVisibility(View.GONE);
//        }

        new MainAds().showNativeAds(this, null, binding.includedNative.adFrameLarge, AdUtils.AD_NORMAL);
        new MainAds().showBannerAds(this, binding.includedBanner.adFrameMini);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDhiStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PowerPreference.getDefaultFile().getBoolean(Constant.AgeGenderScreenOnOff) && !PowerPreference.getDefaultFile().getBoolean(Constant.ageGanderChecked, false))
                    startActivity(new Intent(DhiStartActivity.this, DhiAgeActivity.class));
                else if (PowerPreference.getDefaultFile().getBoolean(Constant.NextScreenOnOff))
                    uiController.gotoActivity(DhiStartActivity.this, DhiNextContinueActivity.class, true, false);
                else
                    uiController.gotoActivity(DhiStartActivity.this, DhiSelectionActivity.class, true, false);
            }
        });

        binding.btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.shareTo(DhiStartActivity.this);
            }
        });


        binding.btnrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.gotoRateus(DhiStartActivity.this);
            }
        });



    }



    @Override
    public void onBackPressed() {
        if (PowerPreference.getDefaultFile().getBoolean(Constant.ContinousScreenOnOff, true)) {
            new MainAds().showBackInterAds(this, this::finish);
        } else
            Constant.showRateDialog(DhiStartActivity.this, true);
    }

}