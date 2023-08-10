package com.allhdvideofree.downloader.videodownloader.activities.AllVideoPlayer;


import static com.allhdvideofree.downloader.videodownloader.Utils.Constant.setToolBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gms.ads.AdUtils;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;

public class DhiAllFolderVideoActivity extends DhiActivityBase {

    LinearLayout img_allfolder, img_allvideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_all_folder_video);



        img_allfolder = findViewById(R.id.img_allfolder);
        img_allvideo = findViewById(R.id.img_allvideo);


        setToolBar(DhiAllFolderVideoActivity.this,"Video Player");

        img_allfolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(DhiAllFolderVideoActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(DhiAllFolderVideoActivity.this, DhiMyDashActivitiy.class);
                        startActivity(intent);

                    }
                });


            }
        });


        img_allvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MainAds().showInterAds(DhiAllFolderVideoActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        Intent intent = new Intent(DhiAllFolderVideoActivity.this, DhiAllVideoListActivity.class);
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