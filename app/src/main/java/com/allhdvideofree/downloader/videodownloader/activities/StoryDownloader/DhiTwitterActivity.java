package com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader;




import static com.allhdvideofree.downloader.videodownloader.Utils.MyUtils.hideProgressDialog;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gms.ads.AdUtils;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.HDVideoAPI.CommonClassForAPI;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;
import com.allhdvideofree.downloader.videodownloader.model.TwitterResponse;
import com.allhdvideofree.downloader.videodownloader.Utils.AppLangSessionManager;
import com.allhdvideofree.downloader.videodownloader.Utils.MyUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import io.reactivex.observers.DisposableObserver;

public class DhiTwitterActivity extends DhiActivityBase {
    public LinearLayout LLHowToLayout;
    public LinearLayout LLHowToOne;
    public LinearLayout RLDownloadLayout;
    public RelativeLayout RLEdittextLayout;
    public RelativeLayout RLTopLayout;
    public String VideoUrl;
    AppLangSessionManager appLangSessionManager;
    private ClipboardManager clipBoard;
    CommonClassForAPI commonClassForAPI;
    int counter = 0;
    public EditText etText;
    public ImageView imAppIcon;
    public ImageView imBack;
    public LinearLayout lnrMain;
    public LinearLayout loginBtn1;
    private DisposableObserver<TwitterResponse> observer = new DisposableObserver<TwitterResponse>() {
        public void onNext(TwitterResponse twitterResponse) {
            hideProgressDialog(DhiTwitterActivity.this);
            try {
                VideoUrl = twitterResponse.getVideos().get(0).getUrl();
                if (twitterResponse.getVideos().get(0).getType().equals("image")) {
                    String str = DhiTwitterActivity.this.VideoUrl;
                    String str2 = MyUtils.RootDirectoryTwitter;
                    DhiTwitterActivity dhiTwitterActivity = DhiTwitterActivity.this;
                    MyUtils.startNewDownload(str, str2, dhiTwitterActivity, dhiTwitterActivity.getFilenameFromURL(dhiTwitterActivity.VideoUrl, "image"));
                    DhiTwitterActivity.this.etText.setText("");
                    return;
                }
                VideoUrl = twitterResponse.getVideos().get(twitterResponse.getVideos().size() - 1).getUrl();
                String str3 = DhiTwitterActivity.this.VideoUrl;
                String str4 =MyUtils.RootDirectoryTwitter;
                DhiTwitterActivity dhiTwitterActivity2 = DhiTwitterActivity.this;
                MyUtils.startNewDownload(str3, str4, dhiTwitterActivity2, dhiTwitterActivity2.getFilenameFromURL(dhiTwitterActivity2.VideoUrl, "mp4"));
                DhiTwitterActivity.this.etText.setText("");
            } catch (Exception e) {
                e.printStackTrace();
                DhiTwitterActivity dhiTwitterActivity3 = DhiTwitterActivity.this;
                MyUtils.setToast(dhiTwitterActivity3, dhiTwitterActivity3.getResources().getString(R.string.no_media_on_tweet));
            }
        }

        public void onError(Throwable th) {
            hideProgressDialog(DhiTwitterActivity.this);
            th.printStackTrace();
        }

        public void onComplete() {
            hideProgressDialog(DhiTwitterActivity.this);
        }
    };
    public TextView tvAppName;
    public LinearLayout tvPaste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_twitter);


        RLDownloadLayout = findViewById(R.id.RLDownloadLayout);
        RLEdittextLayout = findViewById(R.id.RLEdittextLayout);
        etText =  findViewById(R.id.et_text);
        imAppIcon =  findViewById(R.id.imAppIcon);
        imBack = findViewById(R.id.imBack);
        lnrMain =  findViewById(R.id.lnr_main);
        loginBtn1 =  findViewById(R.id.login_btn1);
        tvAppName =  findViewById(R.id.tvAppName);
        tvPaste =  findViewById(R.id.img_paste);
        commonClassForAPI = CommonClassForAPI.getInstance(this);
        MyUtils.createFileFolder();
        initViews();
        imAppIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_twitterrrr));
        tvAppName.setText(getResources().getString(R.string.twitter_app_name));
        AppLangSessionManager appLangSessionManager2 = new AppLangSessionManager(this);
        appLangSessionManager = appLangSessionManager2;
        setLocale(appLangSessionManager2.getLanguage());
    }
    public void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        this.clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        PasteText();
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

    private void initViews() {
        clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        imBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DhiTwitterActivity.this.onBackPressed();
            }
        });

        loginBtn1.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                String obj = DhiTwitterActivity.this.etText.getText().toString();
                if (obj.equals("")) {
                    DhiTwitterActivity dhiTwitterActivity = DhiTwitterActivity.this;
                    MyUtils.setToast(dhiTwitterActivity, dhiTwitterActivity.getResources().getString(R.string.enter_url));
                } else if (!Patterns.WEB_URL.matcher(obj).matches()) {
                    DhiTwitterActivity dhiTwitterActivity2 = DhiTwitterActivity.this;
                    MyUtils.setToast(dhiTwitterActivity2, dhiTwitterActivity2.getResources().getString(R.string.enter_valid_url));
                } else {
                    MyUtils.showProgressDialog(DhiTwitterActivity.this);
                    DhiTwitterActivity.this.GetTwitterData();
                }
            }
        });
        tvPaste.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DhiTwitterActivity.this.PasteText();
            }
        });
        imAppIcon.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MyUtils.OpenApp(DhiTwitterActivity.this, "com.twitter.android");
            }
        });
    }

    public void GetTwitterData() {
        try {
            MyUtils.createFileFolder();
            if (new URL(this.etText.getText().toString()).getHost().contains("twitter.com")) {
                Long tweetId = getTweetId(this.etText.getText().toString());
                if (tweetId != null) {
                    callGetTwitterData(String.valueOf(tweetId));
                    return;
                }
                return;
            }
            MyUtils.setToast(this, getResources().getString(R.string.enter_url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Long getTweetId(String str) {
        try {
            return Long.valueOf(Long.parseLong(str.split("\\/")[5].split("\\?")[0]));
        } catch (Exception e) {
//            Log.d("TAG", "getTweetId: " + e.getLocalizedMessage());
            return null;
        }
    }


    public void PasteText() {
        try {
            this.etText.setText("");
            String stringExtra = getIntent().getStringExtra("CopyIntent");
            if (stringExtra.equals("")) {
                if (!this.clipBoard.hasPrimaryClip()) {
                    return;
                }
                if (this.clipBoard.getPrimaryClipDescription().hasMimeType("text/plain")) {
                    ClipData.Item itemAt = this.clipBoard.getPrimaryClip().getItemAt(0);
                    if (itemAt.getText().toString().contains("twitter.com")) {
                        this.etText.setText(itemAt.getText().toString());
                    }
                } else if (this.clipBoard.getPrimaryClip().getItemAt(0).getText().toString().contains("twitter.com")) {
                    this.etText.setText(this.clipBoard.getPrimaryClip().getItemAt(0).getText().toString());
                }
            } else if (stringExtra.contains("twitter.com")) {
                this.etText.setText(stringExtra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callGetTwitterData(String str) {
        try {
            if (!new MyUtils(this).isNetworkAvailable()) {
                MyUtils.setToast(this, getResources().getString(R.string.no_net_conn));
            } else if (this.commonClassForAPI != null) {
                MyUtils.showProgressDialog(this);
                this.commonClassForAPI.callTwitterApi(this.observer, "https://twittervideodownloaderpro.com/twittervideodownloadv2/index.php", str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFilenameFromURL(String str, String str2) {
        if (str2.equals("image")) {
            try {
                return new File(new URL(str).getPath()).getName() + "";
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return System.currentTimeMillis() + ".jpg";
            }
        } else {
            try {
                return new File(new URL(str).getPath()).getName() + "";
            } catch (MalformedURLException e2) {
                e2.printStackTrace();
                return System.currentTimeMillis() + ".mp4";
            }
        }
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