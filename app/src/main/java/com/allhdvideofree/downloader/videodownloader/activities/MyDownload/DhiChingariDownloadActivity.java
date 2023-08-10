package com.allhdvideofree.downloader.videodownloader.activities.MyDownload;


import static com.allhdvideofree.downloader.videodownloader.Utils.Constant.setToolBar;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.adapter.HDStoryAdepter.DhiFileListAdapter;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;
import com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader.DhiFullViewActivity;
import com.allhdvideofree.downloader.videodownloader.Utils.MyUtils;
import com.allhdvideofree.downloader.videodownloader.Myinterfaces.FileListClickInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class DhiChingariDownloadActivity extends DhiActivityBase implements FileListClickInterface {
    int REQUEST_ACTION_OPEN_DOCUMENT_TREE = 101;
    public ArrayList<File> fileArrayList;
    DhiFileListAdapter dhiFileListAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    File mediaPath = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Chingari");
    public RecyclerView rvFileList;
    public SwipeRefreshLayout swiperefresh;
    public TextView tvNoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_chingari_downloads);


        rvFileList = findViewById(R.id.rv_fileList);
        swiperefresh = findViewById(R.id.swiperefresh);
        tvNoResult = findViewById(R.id.tv_NoResult);

        setToolBar(DhiChingariDownloadActivity.this, "Chingari Download");

        MyUtils.createFileFolder();


        getAllFiles();


        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public final void onRefresh() {
                DhiChingariDownloadActivity.this.getAllFiles();
                DhiChingariDownloadActivity.this.swiperefresh.setRefreshing(false);
            }
        });
        populateGrid();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == this.REQUEST_ACTION_OPEN_DOCUMENT_TREE && i2 == -1) {
            Uri data = intent.getData();
            Log.e("onActivityResult: ", "" + intent.getData());
            try {
                if (Build.VERSION.SDK_INT >= 19) {
                    getContentResolver().takePersistableUriPermission(data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            Prefences.setWATree(this, data.toString());
            populateGrid();
        }
    }

    private void populateGrid() {

        swiperefresh.setVisibility(View.VISIBLE);

    }

    public void getAllFiles() {
        fileArrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager = gridLayoutManager;
        rvFileList.setLayoutManager(gridLayoutManager);
        File[] listFiles = this.mediaPath.listFiles();
        if (listFiles != null) {
            fileArrayList.addAll(Arrays.asList(listFiles));
            DhiFileListAdapter dhiFileListAdapter2 = new DhiFileListAdapter(this, this.fileArrayList, this);
            dhiFileListAdapter = dhiFileListAdapter2;
            rvFileList.setAdapter(dhiFileListAdapter2);
            if (listFiles.length > 0) {
                tvNoResult.setVisibility(View.GONE);
                swiperefresh.setVisibility(View.VISIBLE);
                return;
            }
            swiperefresh.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.VISIBLE);
            return;
        }
        swiperefresh.setVisibility(View.GONE);
        tvNoResult.setVisibility(View.VISIBLE);
    }

    public void getPosition(final int i, File file) {

        Intent intent = new Intent(this, DhiFullViewActivity.class);
        intent.putExtra("ImageDataFile", this.fileArrayList);
        intent.putExtra("Position", i);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        super.onResume();
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