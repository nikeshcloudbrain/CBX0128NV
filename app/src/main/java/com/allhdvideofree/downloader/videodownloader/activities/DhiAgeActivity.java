package com.allhdvideofree.downloader.videodownloader.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.allhdvideofree.downloader.videodownloader.databinding.ActivityDhiAgeBinding;
import com.google.gms.ads.AdUtils;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.R;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

public class DhiAgeActivity extends DhiActivityBase {
    ActivityDhiAgeBinding binding;
    boolean done=true;
    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        new MainAds().showNativeAds(this, null, null, AdUtils.AD_LARGE);
        new MainAds().showBannerAds(this,binding.includedBanner.adFrameMini);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDhiAgeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done = true;

                binding.ready.setBackgroundResource(R.drawable.selected);
                binding.twenty.setTextColor(getResources().getColor(R.color.colorWhite));
                binding.err.setBackgroundResource(R.drawable.unselected);
                binding.belowtwenty.setTextColor(getResources().getColor(R.color.startcolor));

            }
        });
        binding.err.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done = false;

                binding.err.setBackgroundResource(R.drawable.selected);
                binding.belowtwenty.setTextColor(getResources().getColor(R.color.colorWhite));
                binding.ready.setBackgroundResource(R.drawable.unselected);
                binding.twenty.setTextColor(getResources().getColor(R.color.startcolor));

            }
        });
        binding.llStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (done){
                    new MainAds().showInterAds(DhiAgeActivity.this, new InterAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            startActivity(new Intent(DhiAgeActivity.this, DhiGenderActivity.class));
                            finish();
                        }
                    });
                }else {
                    Toast.makeText(DhiAgeActivity.this, "This app only for 12+ Age", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (PowerPreference.getDefaultFile().getBoolean(Constant.ContinousScreenOnOff, true)||PowerPreference.getDefaultFile().getBoolean(Constant.StartScreenOnOff, true)) {


            new MainAds().showBackInterAds(DhiAgeActivity.this, new BackInterAds.OnAdClosedListener() {
                @Override
                public void onAdClosed() {
                    finish();

                }
            });
        } else {
            Constant.showRateDialog(DhiAgeActivity.this,true);
        }
    }


}