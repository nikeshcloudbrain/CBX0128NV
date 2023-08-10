package com.allhdvideofree.downloader.videodownloader.activities.AllVideoPlayer;



import static com.allhdvideofree.downloader.videodownloader.Utils.Constant.setToolBar;

import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;
import com.allhdvideofree.downloader.videodownloader.fragment.VideoFragment.MainActivityFragment;

public class DhiMyDashActivitiy extends DhiActivityBase {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_dash_activitiy);


        setToolBar(DhiMyDashActivitiy.this,"All Video Downloader");

        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.myfrag, new MainActivityFragment());
        beginTransaction.commit();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
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