package com.allhdvideofree.downloader.videodownloader.activities.selection;


import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.allhdvideofree.downloader.videodownloader.Utils.Constant.setToolBar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.google.gms.ads.AdUtils;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.allhdvideofree.downloader.videodownloader.activities.Permission.DhiPrStorageActivity;
import com.allhdvideofree.downloader.videodownloader.Utils.uiController;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.activities.HDMusicplayer.DhiMyMusicActivity;
import com.allhdvideofree.downloader.videodownloader.activities.MyDownload.DhiAllMyDownloadedActivity;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;
import com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader.DhiAllStoryActivity;
import com.allhdvideofree.downloader.videodownloader.activities.AllVideoPlayer.DhiAllFolderVideoActivity;

public class DhiSelectionActivity extends DhiActivityBase {

    LinearLayout lin_hddownloader, lin_vidplayer, lin_musicplayer, lin_mydownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_selection);


        setToolBar(DhiSelectionActivity.this, "All Video Downloader");

        lin_hddownloader = findViewById(R.id.lin_hddownloader);
        lin_vidplayer = findViewById(R.id.lin_vidplayer);
        lin_musicplayer = findViewById(R.id.lin_musicplayer);
        lin_mydownload = findViewById(R.id.lin_mydownload);


        lin_vidplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(DhiSelectionActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff, false)) {
                            if (pagerChange()) {
                                Intent intent = new Intent(DhiSelectionActivity.this, DhiVideoNextActivity.class);
                                startActivity(intent);
                            } else {
                                startActivity(new Intent(DhiSelectionActivity.this, DhiPrStorageActivity.class));

                            }

                        } else {
                            if (pagerChange()) {
                                Intent intent = new Intent(DhiSelectionActivity.this, DhiAllFolderVideoActivity.class);
                                startActivity(intent);
                            } else {
                                startActivity(new Intent(DhiSelectionActivity.this, DhiPrStorageActivity.class));
                            }

                        }

                    }
                });
            }
        });


        lin_mydownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(DhiSelectionActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        if (pagerChange()) {
                            Intent intent = new Intent(DhiSelectionActivity.this, DhiAllMyDownloadedActivity.class);
                            startActivity(intent);
                        } else {
                            startActivity(new Intent(DhiSelectionActivity.this, DhiPrStorageActivity.class));

                        }


                    }
                });
            }
        });

        lin_hddownloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(DhiSelectionActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff, false)) {
                            if (pagerChange()) {
                                Intent intent = new Intent(DhiSelectionActivity.this, DhiStoryNextActivity.class);
                                startActivity(intent);
                            } else {
                                startActivity(new Intent(DhiSelectionActivity.this, DhiPrStorageActivity.class));

                            }

                        } else {
                            if (pagerChange()) {
                                Intent intent = new Intent(DhiSelectionActivity.this, DhiAllStoryActivity.class);
                                startActivity(intent);
                            } else {
                                startActivity(new Intent(DhiSelectionActivity.this, DhiPrStorageActivity.class));
                            }

                        }

                    }
                });
            }
        });


        lin_musicplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(DhiSelectionActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff, false)) {
                            if (pagerChange()) {
                                Intent intent = new Intent(DhiSelectionActivity.this, DhiMusicNextActivity.class);
                                startActivity(intent);
                            } else {
                                startActivity(new Intent(DhiSelectionActivity.this, DhiPrStorageActivity.class));

                            }

                        } else {
                            if (pagerChange()) {
                                Intent intent = new Intent(DhiSelectionActivity.this, DhiMyMusicActivity.class);
                                startActivity(intent);
                            } else {
                                startActivity(new Intent(DhiSelectionActivity.this, DhiPrStorageActivity.class));
                            }

                        }

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
        if (PowerPreference.getDefaultFile().getBoolean(Constant.StartScreenOnOff, false)
                || PowerPreference.getDefaultFile().getBoolean(Constant.ContinousScreenOnOff, false)|| PowerPreference.getDefaultFile().getBoolean(Constant.NextScreenOnOff) || PowerPreference.getDefaultFile().getBoolean(Constant.AgeGenderScreenOnOff) && !PowerPreference.getDefaultFile().getBoolean(Constant.ageGanderChecked, false))
        {
            uiController.onBackPressed(this);
        } else Constant.showRateDialog(this, true);
    }


    private boolean checkPermission() {

        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;

    }

    private boolean pagerChange() {

        return checkPermission();

    }
}