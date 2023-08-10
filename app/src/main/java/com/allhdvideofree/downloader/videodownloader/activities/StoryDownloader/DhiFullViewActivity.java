package com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader;



import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.adapter.HDStoryAdepter.DhiShowImagesAdapter;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;
import com.allhdvideofree.downloader.videodownloader.Utils.AppLangSessionManager;
import com.allhdvideofree.downloader.videodownloader.Utils.MyUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;

public class DhiFullViewActivity extends DhiActivityBase {
    public int Position = 0;
    AppLangSessionManager appLangSessionManager;
    public ArrayList<File> fileArrayList;
    public ImageView imClose;
    public FloatingActionButton imDelete;
    public FloatingActionButton imShare;
    public FloatingActionButton imWhatsappShare;
    public LinearLayout lnrFooter;
    DhiShowImagesAdapter dhiShowImagesAdapter;
    public ViewPager vpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_full_view);

        imClose =  findViewById(R.id.im_close);
        imDelete =  findViewById(R.id.imDelete);
        imShare =  findViewById(R.id.imShare);
        imWhatsappShare =  findViewById(R.id.imWhatsappShare);
        lnrFooter =  findViewById(R.id.lnr_footer);
        vpView =  findViewById(R.id.vp_view);
        AppLangSessionManager appLangSessionManager2 = new AppLangSessionManager(this);
        appLangSessionManager = appLangSessionManager2;
        setLocale(appLangSessionManager2.getLanguage());
        if (getIntent().getExtras() != null) {
            fileArrayList = (ArrayList) getIntent().getSerializableExtra("ImageDataFile");
            Position = getIntent().getIntExtra("Position", 0);
        }
        initViews();

        imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void initViews() {
        DhiShowImagesAdapter dhiShowImagesAdapter2 = new DhiShowImagesAdapter(this, this.fileArrayList, this);
        dhiShowImagesAdapter = dhiShowImagesAdapter2;
        vpView.setAdapter(dhiShowImagesAdapter2);
        vpView.setCurrentItem(this.Position);
        vpView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                DhiFullViewActivity.this.Position = i;
                PrintStream printStream = System.out;
                printStream.println("Current position==" + DhiFullViewActivity.this.Position);
            }
        });
        imDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DhiFullViewActivity.this);
                builder.setPositiveButton((CharSequence) DhiFullViewActivity.this.getResources().getString(R.string.yes), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (DhiFullViewActivity.this.fileArrayList.get(DhiFullViewActivity.this.Position).delete()) {
                            DhiFullViewActivity.this.deleteFileAA(DhiFullViewActivity.this.Position);
                        }
                    }
                });
                builder.setNegativeButton((CharSequence) DhiFullViewActivity.this.getResources().getString(R.string.no), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog create = builder.create();
                create.setTitle(DhiFullViewActivity.this.getResources().getString(R.string.do_u_want_to_dlt));
                create.show();
            }
        });
        imShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (DhiFullViewActivity.this.fileArrayList.get(DhiFullViewActivity.this.Position).getName().contains(".mp4")) {
                    Log.d("SSSSS", "onClick: " + DhiFullViewActivity.this.fileArrayList.get(DhiFullViewActivity.this.Position) + "");
                    DhiFullViewActivity dhiFullViewActivity = DhiFullViewActivity.this;
                    MyUtils.shareVideo(dhiFullViewActivity, dhiFullViewActivity.fileArrayList.get(DhiFullViewActivity.this.Position).getPath());
                    return;
                }
                DhiFullViewActivity dhiFullViewActivity2 = DhiFullViewActivity.this;
                MyUtils.shareImage(dhiFullViewActivity2, dhiFullViewActivity2.fileArrayList.get(DhiFullViewActivity.this.Position).getPath());
            }
        });
       imWhatsappShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (DhiFullViewActivity.this.fileArrayList.get(DhiFullViewActivity.this.Position).getName().contains(".mp4")) {
                    DhiFullViewActivity dhiFullViewActivity = DhiFullViewActivity.this;
                    MyUtils.shareImageVideoOnWhatsapp(dhiFullViewActivity, dhiFullViewActivity.fileArrayList.get(DhiFullViewActivity.this.Position).getPath(), true);
                    return;
                }
                DhiFullViewActivity dhiFullViewActivity2 = DhiFullViewActivity.this;
                MyUtils.shareImageVideoOnWhatsapp(dhiFullViewActivity2, dhiFullViewActivity2.fileArrayList.get(DhiFullViewActivity.this.Position).getPath(), false);
            }
        });
        this.imClose.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DhiFullViewActivity.this.onBackPressed();
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

    public void deleteFileAA(int i) {
        this.fileArrayList.remove(i);
        this.dhiShowImagesAdapter.notifyDataSetChanged();
        MyUtils.setToast(this, getResources().getString(R.string.file_deleted));
        if (this.fileArrayList.size() == 0) {
            onBackPressed();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void setLocale(String str) {
        Locale locale = new Locale(str);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }
}