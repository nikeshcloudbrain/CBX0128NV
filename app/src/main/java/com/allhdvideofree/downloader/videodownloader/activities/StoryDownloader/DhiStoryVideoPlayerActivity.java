package com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.widget.AppCompatImageView;

import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;

public class DhiStoryVideoPlayerActivity extends DhiActivityBase {
    VideoView videoView;
    AppCompatImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_story_video_player);


        videoView = findViewById(R.id.videoView);
        img_back = findViewById(R.id.img_back);


        String stringExtra = getIntent().getStringExtra("PathVideo");
        try {
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            Uri parse = Uri.parse(stringExtra);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(parse);
            videoView.start();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void onResume() {
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