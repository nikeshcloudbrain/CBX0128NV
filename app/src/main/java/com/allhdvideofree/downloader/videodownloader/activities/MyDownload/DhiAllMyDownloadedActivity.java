package com.allhdvideofree.downloader.videodownloader.activities.MyDownload;

import static com.allhdvideofree.downloader.videodownloader.Utils.Constant.setToolBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gms.ads.AdUtils;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.allhdvideofree.downloader.videodownloader.databinding.ActivityDhiAllMyDownloadedBinding;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;

public class DhiAllMyDownloadedActivity extends DhiActivityBase {
    FrameLayout lin_insta, lin_fb, lin_wtsup, lin_twitter, lin_sharechat, lin_chingari;
    ActivityDhiAllMyDownloadedBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDhiAllMyDownloadedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        lin_insta = findViewById(R.id.lin_insta);
        lin_fb = findViewById(R.id.lin_fb);
        lin_wtsup = findViewById(R.id.lin_wtsup);
        lin_twitter = findViewById(R.id.lin_twitter);
        lin_sharechat = findViewById(R.id.lin_sharechat);
        lin_chingari = findViewById(R.id.lin_chingari);

        setToolBar(DhiAllMyDownloadedActivity.this,"All Video Downloader");

        lin_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MainAds().showInterAds(DhiAllMyDownloadedActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        Intent intent = new Intent(DhiAllMyDownloadedActivity.this, DhiInstaDownloadActivity.class);
                        startActivity(intent);

                    }
                });


            }
        });

        lin_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(DhiAllMyDownloadedActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        Intent intent = new Intent(DhiAllMyDownloadedActivity.this, DhiFBDownloadActivity.class);
                        startActivity(intent);

                    }
                });


            }
        });

        lin_wtsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MainAds().showInterAds(DhiAllMyDownloadedActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        Intent intent = new Intent(DhiAllMyDownloadedActivity.this, DhiWtsappDownloadActivity.class);
                        startActivity(intent);

                    }
                });


            }
        });

        lin_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MainAds().showInterAds(DhiAllMyDownloadedActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(DhiAllMyDownloadedActivity.this, DhiTwitterDownloadActivity.class);
                        startActivity(intent);

                    }
                });


            }
        });

        lin_sharechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MainAds().showInterAds(DhiAllMyDownloadedActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(DhiAllMyDownloadedActivity.this, DhiShareChDownloadActivity.class);
                        startActivity(intent);

                    }
                });


            }
        });


        lin_chingari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new MainAds().showInterAds(DhiAllMyDownloadedActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(DhiAllMyDownloadedActivity.this, DhiChingariDownloadActivity.class);
                        startActivity(intent);

                    }
                });


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        new MainAds().showNativeAds(this, null, null, AdUtils.AD_LARGE);
        new MainAds().showBannerAds(this, binding.includedBanner.adFrameMini);

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