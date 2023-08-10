package com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
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
import com.allhdvideofree.downloader.videodownloader.Utils.AppLangSessionManager;
import com.allhdvideofree.downloader.videodownloader.Utils.MyUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

public class DhiChingariActivity extends DhiActivityBase {
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
    public TextView tvAppName;
    public LinearLayout tvPaste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_ching);


        RLDownloadLayout = findViewById(R.id.RLDownloadLayout);
        RLEdittextLayout = findViewById(R.id.RLEdittextLayout);
        etText = findViewById(R.id.et_text);
        imAppIcon = findViewById(R.id.imAppIcon);
        imBack = findViewById(R.id.imBack);
        lnrMain = findViewById(R.id.lnr_main);
        loginBtn1 = findViewById(R.id.login_btn1);
        tvAppName = findViewById(R.id.tvAppName);
        tvPaste = findViewById(R.id.img_paste);


        commonClassForAPI = CommonClassForAPI.getInstance(this);
        MyUtils.createFileFolder();
        initViews();
        tvAppName.setText(getResources().getString(R.string.chingari_app_name));
        AppLangSessionManager appLangSessionManager2 = new AppLangSessionManager(this);
        appLangSessionManager = appLangSessionManager2;
        setLocale(appLangSessionManager2.getLanguage());
    }

    private void initViews() {
        clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        imBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DhiChingariActivity.this.onBackPressed();
            }
        });

        loginBtn1.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                String obj = DhiChingariActivity.this.etText.getText().toString();
                if (obj.equals("")) {
                    MyUtils.setToast(DhiChingariActivity.this, DhiChingariActivity.this.getResources().getString(R.string.enter_url));
                } else if (!Patterns.WEB_URL.matcher(obj.trim()).matches()) {
                    MyUtils.setToast(DhiChingariActivity.this, DhiChingariActivity.this.getResources().getString(R.string.enter_valid_url));
                } else {
                    MyUtils.showProgressDialog(DhiChingariActivity.this);
                    getChingariData();
                }
            }
        });
        tvPaste.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DhiChingariActivity.this.PasteText();
            }
        });
        imAppIcon.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MyUtils.OpenApp(DhiChingariActivity.this, "io.chingari.app");
            }
        });
    }


    public void getChingariData() {
        try {
            MyUtils.createFileFolder();
            if (new URL(this.etText.getText().toString()).getHost().contains("chingari.io")) {
                MyUtils.showProgressDialog(this);
                new CallGetChingariData().execute(new String[]{this.etText.getText().toString()});
                return;
            }
            MyUtils.setToast(this, getResources().getString(R.string.enter_url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void PasteText() {
        try {
            etText.setText("");
            String stringExtra = getIntent().getStringExtra("CopyIntent");
            if (stringExtra.equals("")) {
                if (!clipBoard.hasPrimaryClip()) {
                    return;
                }
                if (clipBoard.getPrimaryClipDescription().hasMimeType("text/plain")) {
                    ClipData.Item itemAt = clipBoard.getPrimaryClip().getItemAt(0);
                    if (itemAt.getText().toString().contains("chingari.io")) {
                        etText.setText(itemAt.getText().toString());
                    }
                } else if (clipBoard.getPrimaryClip().getItemAt(0).getText().toString().contains("chingari.io")) {
                    etText.setText(this.clipBoard.getPrimaryClip().getItemAt(0).getText().toString());
                }
            } else if (stringExtra.contains("chingari.io")) {
                etText.setText(stringExtra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class CallGetChingariData extends AsyncTask<String, Void, Document> {
        Document chingariDoc;
        String strHTML;

        CallGetChingariData() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Document doInBackground(String... strArr) {


            try {


                Connection connection = Jsoup.connect(strArr[0]);


//                connection.userAgent("Mozilla/5.0");
                connection.userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");


                chingariDoc = connection.get();


                strHTML = chingariDoc.text();

//                Log.e("ChingariURL", "--------newdoc-----" + strHTML);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

//            try {
//                this.chingariDoc = Jsoup.connect(strArr[0]).get();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return this.chingariDoc;
        }

        public void onPostExecute(Document document) {
            MyUtils.hideProgressDialog(DhiChingariActivity.this);
            try {
//                ChingariActivity.this.VideoUrl = document.select("meta[property=\"og:video:secure_url\"]").last().attr("content");
//                VideoUrl = document.select("meta[property=\"og:video\"]").last().attr("content");
                VideoUrl = document.select("meta[property=og:video]").last().attr("content");

                if (!VideoUrl.equals("")) {
                    String str = VideoUrl;
                    MyUtils.startNewDownload(str, MyUtils.ROOTDIRECTORYCHINGARI, DhiChingariActivity.this, "chingari_" + System.currentTimeMillis() + ".mp4");
                    VideoUrl = "";
                    etText.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
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
}