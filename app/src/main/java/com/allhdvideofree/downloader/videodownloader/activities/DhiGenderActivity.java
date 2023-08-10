package com.allhdvideofree.downloader.videodownloader.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.google.gms.ads.AdUtils;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.selection.DhiSelectionActivity;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.allhdvideofree.downloader.videodownloader.Utils.uiController;
import com.allhdvideofree.downloader.videodownloader.databinding.ActivityDhiGenderBinding;
import com.preference.PowerPreference;


public class DhiGenderActivity extends DhiActivityBase {

    ActivityDhiGenderBinding binding;

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        new MainAds().showNativeAds(this, null, null, AdUtils.AD_LARGE);
        new MainAds().showBannerAds(this, binding.includedBanner.adFrameMini);
    }

    @Override
    public void onBackPressed() {
        if (PowerPreference.getDefaultFile().getBoolean(Constant.ContinousScreenOnOff, true)||PowerPreference.getDefaultFile().getBoolean(Constant.StartScreenOnOff, true)) {


            new MainAds().showBackInterAds(DhiGenderActivity.this, new BackInterAds.OnAdClosedListener() {
                @Override
                public void onAdClosed() {
                    finish();

                }
            });
        } else {
            Constant.showRateDialog(DhiGenderActivity.this,true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDhiGenderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(DhiGenderActivity.this, R.color.mclr));


        binding.llStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PowerPreference.getDefaultFile().putBoolean(Constant.ageGanderChecked, true);
                if (PowerPreference.getDefaultFile().getBoolean(Constant.NextScreenOnOff))
                    uiController.gotoActivity(DhiGenderActivity.this, DhiNextContinueActivity.class, true, false);

                else
                    uiController.gotoActivity(DhiGenderActivity.this, DhiSelectionActivity.class, true, false);
            }
        });

        binding.female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cselectage.setVisibility(View.VISIBLE);
                binding.eselectage.setVisibility(View.GONE);
                binding.lfemale.setBackgroundResource(R.drawable.selected);
                binding.gfemale.setTextColor(getResources().getColor(R.color.colorWhite));
                binding.lmale.setBackgroundResource(R.drawable.unselected);
                binding.gmale.setTextColor(getResources().getColor(R.color.startcolor));
            }
        });

        binding.male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cselectage.setVisibility(View.GONE);
                binding.eselectage.setVisibility(View.VISIBLE);
                binding.lmale.setBackgroundResource(R.drawable.selected);
                binding.gmale.setTextColor(getResources().getColor(R.color.colorWhite));
                binding.lfemale.setBackgroundResource(R.drawable.unselected);
                binding.gfemale.setTextColor(getResources().getColor(R.color.startcolor));
            }
        });

    }
}