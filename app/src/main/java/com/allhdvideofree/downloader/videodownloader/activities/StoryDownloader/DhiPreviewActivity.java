package com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.documentfile.provider.DocumentFile;
import androidx.viewpager.widget.ViewPager;

import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.adapter.HDStoryAdepter.DhiPreviewAdapter;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;
import com.allhdvideofree.downloader.videodownloader.model.StatusModel;
import com.allhdvideofree.downloader.videodownloader.Utils.MyUtils;

import java.io.File;
import java.util.ArrayList;

public class DhiPreviewActivity extends DhiActivityBase {
    LinearLayout deleteIV;
    LinearLayout downloadIV;
    ArrayList<StatusModel> imageList;
    int position;
    DhiPreviewAdapter dhiPreviewAdapter;
    LinearLayout shareIV;
    String statusDownload;
    ViewPager viewPager;
    LinearLayout wAppIV;
    ImageView backIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_preview);

        backIV = findViewById(R.id.backIV);
        viewPager = findViewById(R.id.viewPager);
        shareIV = findViewById(R.id.shareIV);
        downloadIV = findViewById(R.id.downloadIV);
        deleteIV = findViewById(R.id.deleteIV);
        wAppIV = findViewById(R.id.repostIV);

        imageList = getIntent().getParcelableArrayListExtra("images");
        position = getIntent().getIntExtra("position", 0);
        String stringExtra = getIntent().getStringExtra("statusdownload");

        this.statusDownload = stringExtra;
        if (stringExtra.equals("download")) {
            downloadIV.setVisibility(View.GONE);
        } else {
            downloadIV.setVisibility(View.VISIBLE);
        }
        DhiPreviewAdapter dhiPreviewAdapter2 = new DhiPreviewAdapter(this, this.imageList);
        dhiPreviewAdapter = dhiPreviewAdapter2;
        viewPager.setAdapter(dhiPreviewAdapter2);
        viewPager.setCurrentItem(this.position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
            }
        });
        downloadIV.setOnClickListener(this.clickListener);
        shareIV.setOnClickListener(this.clickListener);
        deleteIV.setOnClickListener(this.clickListener);
        backIV.setOnClickListener(this.clickListener);
        wAppIV.setOnClickListener(this.clickListener);


    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.backIV:
                    onBackPressed();
                    return;
                case R.id.deleteIV:
                    if (imageList.size() > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DhiPreviewActivity.this);
                        builder.setTitle((int) R.string.confirm);
                        builder.setMessage((int) R.string.del_status);
                        builder.setPositiveButton((CharSequence) DhiPreviewActivity.this.getResources().getString(R.string.yes), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                if (DhiPreviewActivity.this.statusDownload.equals("download")) {
                                    File file = new File(DhiPreviewActivity.this.imageList.get(DhiPreviewActivity.this.viewPager.getCurrentItem()).getFilePath());
                                    if (file.exists()) {
                                        file.delete();
                                        delete(0);
                                        return;
                                    }
                                    return;
                                }
                                DocumentFile fromSingleUri = DocumentFile.fromSingleUri(DhiPreviewActivity.this, Uri.parse(DhiPreviewActivity.this.imageList.get(DhiPreviewActivity.this.viewPager.getCurrentItem()).getFilePath()));
                                if (fromSingleUri.exists()) {
                                    fromSingleUri.delete();
                                    delete(0);
                                }
                            }
                        });
                        builder.setNegativeButton((CharSequence) DhiPreviewActivity.this.getResources().getString(R.string.no), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                        return;
                    }
                    DhiPreviewActivity.this.finish();
                    return;
                case R.id.downloadIV:
                    if (imageList.size() > 0) {
                        try {
                            MyUtils.download(DhiPreviewActivity.this, DhiPreviewActivity.this.imageList.get(DhiPreviewActivity.this.viewPager.getCurrentItem()).getFilePath());
                            Toast.makeText(DhiPreviewActivity.this, DhiPreviewActivity.this.getResources().getString(R.string.saved_success), Toast.LENGTH_SHORT).show();
                            return;
                        } catch (Exception unused) {
                            Toast.makeText(DhiPreviewActivity.this, "Sorry we can't move file.try with other file.", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } else {
                        finish();
                        return;
                    }
                case R.id.repostIV:
                    MyUtils.repostWhatsApp(DhiPreviewActivity.this, MyUtils.isVideoFile(DhiPreviewActivity.this, DhiPreviewActivity.this.imageList.get(DhiPreviewActivity.this.viewPager.getCurrentItem()).getFilePath()), DhiPreviewActivity.this.imageList.get(DhiPreviewActivity.this.viewPager.getCurrentItem()).getFilePath());
                    return;
                case R.id.shareIV:
                    if (imageList.size() > 0) {
                        MyUtils.shareFile(DhiPreviewActivity.this, MyUtils.isVideoFile(DhiPreviewActivity.this, DhiPreviewActivity.this.imageList.get(DhiPreviewActivity.this.viewPager.getCurrentItem()).getFilePath()), DhiPreviewActivity.this.imageList.get(DhiPreviewActivity.this.viewPager.getCurrentItem()).getFilePath());
                        return;
                    }
                    finish();
                    return;
                default:
                    return;
            }
        }
    };

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void delete(int i) {
        if (imageList.size() > 0 && this.viewPager.getCurrentItem() < this.imageList.size()) {
            i = this.viewPager.getCurrentItem();
        }
        imageList.remove(this.viewPager.getCurrentItem());
        dhiPreviewAdapter = new DhiPreviewAdapter(this, this.imageList);
        viewPager.setAdapter(dhiPreviewAdapter);
        setResult(10, new Intent());
        if (imageList.size() > 0) {
            viewPager.setCurrentItem(i);
        } else {
            finish();
        }
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