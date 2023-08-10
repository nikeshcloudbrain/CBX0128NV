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

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

public class DhiShareChatActivity extends DhiActivityBase {
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
        setContentView(R.layout.activity_dhi_sharechat);


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
        imAppIcon.setImageDrawable(getResources().getDrawable(R.drawable.sharechat));
        tvAppName.setText(getResources().getString(R.string.sharechat_app_name));
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
                onBackPressed();
            }
        });

        loginBtn1.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                String obj = DhiShareChatActivity.this.etText.getText().toString();
                if (obj.equals("")) {
                    MyUtils.setToast(DhiShareChatActivity.this, DhiShareChatActivity.this.getResources().getString(R.string.enter_url));
                } else if (!Patterns.WEB_URL.matcher(obj).matches()) {

                    MyUtils.setToast(DhiShareChatActivity.this, DhiShareChatActivity.this.getResources().getString(R.string.enter_valid_url));
                } else {
                    MyUtils.showProgressDialog(DhiShareChatActivity.this);
                    DhiShareChatActivity.this.GetSharechatData();
                }
            }
        });

        tvPaste.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DhiShareChatActivity.this.PasteText();
            }
        });

        imAppIcon.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MyUtils.OpenApp(DhiShareChatActivity.this, "in.mohalla.sharechat");
            }
        });

    }


    public void GetSharechatData() {
        try {
            MyUtils.createFileFolder();
            if (new URL(etText.getText().toString()).getHost().contains("sharechat.com")) {

//                Log.e("ShareChatURL", "--------url---------" + etText.getText().toString());

                MyUtils.showProgressDialog(this);
                new callGetShareChatData().execute(etText.getText().toString());
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
                    if (itemAt.getText().toString().contains("sharechat")) {
                        etText.setText(itemAt.getText().toString());
                    }
                } else if (clipBoard.getPrimaryClip().getItemAt(0).getText().toString().contains("sharechat")) {
                    etText.setText(clipBoard.getPrimaryClip().getItemAt(0).getText().toString());
                }
            } else if (stringExtra.contains("sharechat")) {
                etText.setText(stringExtra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class callGetShareChatData extends AsyncTask<String, Void, Document> {


        Document doc = null;
        String strHTML;

        callGetShareChatData() {

//            Log.e("ShareChatURL", "--------doc-----task4----");
        }


        public void onPreExecute() {
            super.onPreExecute();

//            Log.e("ShareChatURL", "--------doc-----task1----");
        }

        public Document doInBackground(String... strArr) {
//            Log.e("ShareChatURL", "--------doc-----task2----");

            try {
                Connection connection = Jsoup.connect(etText.getText().toString());
                connection.userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
                connection.referrer("http://www.google.com");
                doc = connection.get();


                strHTML = doc.title();

//                Log.e("ShareChatURL", "--------newdoc-----" + strHTML);

                Elements videoIdMeta1 = doc.select("script[type=application/ld+json]");
                for (Element link : videoIdMeta1) {
                    try {
                        JSONObject mobj = new JSONObject(link.data().toString());
                        VideoUrl = mobj.getString("contentUrl");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
///


//                Log.e("ShareChatURL", "--------videoid---22222222--" + VideoUrl);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }


            return doc;


        }

        public void onPostExecute(Document document) {

//            Log.e("ShareChatURL", "--------doc-----task3----");

            MyUtils.hideProgressDialog(DhiShareChatActivity.this);

            try {


                if (!VideoUrl.equals("")) {
                    try {
                        String str = VideoUrl;
                        String str2 = MyUtils.RootDirectoryShareChat;
                        MyUtils.startNewDownload(str, str2, DhiShareChatActivity.this, "sharechat_" + System.currentTimeMillis() + ".mp4");
                        VideoUrl = "";
                        etText.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            } catch (NullPointerException e2) {
                e2.printStackTrace();
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