package com.allhdvideofree.downloader.videodownloader.activities;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;

public class DhiActivityBase extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor(PowerPreference.getDefaultFile().getString(Constant.AppBackgroundColor, "#FFFFFF")));
    }
}
