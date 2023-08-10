package com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader;


import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gms.ads.AdUtils;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.adapter.HDStoryAdepter.DhiFBStoriesListAdapter;
import com.allhdvideofree.downloader.videodownloader.adapter.HDStoryAdepter.DhiFbStoryUserListAdapter;
import com.allhdvideofree.downloader.videodownloader.HDVideoAPI.CommonClassForAPI;
import com.allhdvideofree.downloader.videodownloader.activities.MyDownload.DhiDownloadVideosMain;
import com.allhdvideofree.downloader.videodownloader.Myinterfaces.UserListInterface;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;
import com.allhdvideofree.downloader.videodownloader.model.FBStoryModel.EdgesModel;
import com.allhdvideofree.downloader.videodownloader.model.FBStoryModel.NodeModel;
import com.allhdvideofree.downloader.videodownloader.Utils.AppLangSessionManager;
import com.allhdvideofree.downloader.videodownloader.Utils.MyUtils;
import com.allhdvideofree.downloader.videodownloader.Utils.SharePrefs;
import com.allhdvideofree.downloader.videodownloader.model.TrayModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class DhiFacebookActivity extends DhiActivityBase implements UserListInterface {
    public LinearLayout LLHowToOne;
    public LinearLayout RLDownloadLayout;
    public RelativeLayout RLEdittextLayout;
    public RelativeLayout RLLoginInstagram;
    public RelativeLayout RLTopLayout;

    public RecyclerView RVStories;
    public RecyclerView RVUserList;
    public SwitchCompat SwitchLogin;
    public ImageView TVTitle;
    AppLangSessionManager appLangSessionManager;
    private ClipboardManager clipBoard;
    CommonClassForAPI commonClassForAPI;
    int counter = 0;
    ArrayList<NodeModel> edgeModelList;
    public EditText etText;
    DhiFBStoriesListAdapter dhiFbStoriesListAdapter;
    DhiFbStoryUserListAdapter dhiFbStoryUserListAdapter;
    public ImageView imBack;
    public LinearLayout lnrMain;
    public LinearLayout loginBtn1;
    public ProgressBar prLoadingBar;
    private String strName = "facebook";
    private String strNameSecond = "fb";
    public TextView tvAppName;
    public TextView tvLogin;
    public TextView tvLoginInstagram;
    public LinearLayout img_paste;
    public TextView tvViewStories;
    public String videoUrl;
    private String VideoTitle;
    private WebView mWebView;
    private String tempstring = "";
    LinearLayout login_lin;

    public void userListClick(int i, TrayModel trayModel) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_newfb);
        RLDownloadLayout = findViewById(R.id.RLDownloadLayout);
        RLEdittextLayout = findViewById(R.id.RLEdittextLayout);
        RLLoginInstagram = findViewById(R.id.RLLoginInstagram);
        login_lin = findViewById(R.id.login_lin);
        RVStories = findViewById(R.id.RVStories);
        RVUserList = findViewById(R.id.RVUserList);
        SwitchLogin = findViewById(R.id.SwitchLogin);
        TVTitle = findViewById(R.id.TVTitle);
        etText = findViewById(R.id.et_text);
        imBack = findViewById(R.id.imBack);
        lnrMain = findViewById(R.id.lnr_main);
        loginBtn1 = findViewById(R.id.login_btn1);
        prLoadingBar = findViewById(R.id.pr_loading_bar);
        tvAppName = findViewById(R.id.tvAppName);
        tvLogin = findViewById(R.id.tvLogin);
        tvLoginInstagram = findViewById(R.id.tvLoginInstagram);
        img_paste = findViewById(R.id.img_paste);
        tvViewStories = findViewById(R.id.tvViewStories);
        AppLangSessionManager appLangSessionManager2 = new AppLangSessionManager(this);
        appLangSessionManager = appLangSessionManager2;
        setLocale(appLangSessionManager2.getLanguage());
        commonClassForAPI = CommonClassForAPI.getInstance(this);
//        MyUtils.createFileFolder();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        this.clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        pasteText();
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

        tvAppName.setText(getResources().getString(R.string.facebook_app_name));

        TVTitle.setImageResource(R.drawable.fb);

        tvLoginInstagram.setText(getResources().getString(R.string.download_stories));
        imBack.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DhiFacebookActivity.this.onBackPressed();
            }
        });

        img_paste.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                pasteText();
            }
        });

        tvAppName.setText(getResources().getString(R.string.facebook_app_name));

        loginBtn1.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                String obj = etText.getText().toString();

                if (obj.contains("posts")) {
                    MyUtils.setToast(DhiFacebookActivity.this, getResources().getString(R.string.enter_valid_url));
                } else if (obj.equals("")) {
                    MyUtils.setToast(DhiFacebookActivity.this, getResources().getString(R.string.enter_url));
                } else if (!Patterns.WEB_URL.matcher(obj).matches()) {

                    MyUtils.setToast(DhiFacebookActivity.this, getResources().getString(R.string.enter_valid_url));
                } else {
                    String str4 = etText.getText().toString();
                    DhiDownloadVideosMain.Start(DhiFacebookActivity.this, str4, false);
//                    getFacebookData();
                }
            }
        });
        this.TVTitle.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MyUtils.OpenApp(DhiFacebookActivity.this, "com.facebook.katana");
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        RVUserList.setLayoutManager(gridLayoutManager);
        RVUserList.setNestedScrollingEnabled(false);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ArrayList<NodeModel> arrayList = new ArrayList<>();
        edgeModelList = arrayList;
        DhiFbStoryUserListAdapter dhiFbStoryUserListAdapter2 = new DhiFbStoryUserListAdapter(this, arrayList, this);
        dhiFbStoryUserListAdapter = dhiFbStoryUserListAdapter2;
        RVUserList.setAdapter(dhiFbStoryUserListAdapter2);
        if (SharePrefs.getInstance(this).getBoolean(SharePrefs.ISFBLOGIN).booleanValue()) {
            layoutCondition();
            getFacebookUserData();
            SwitchLogin.setChecked(true);
        } else {
            SwitchLogin.setChecked(false);
        }
        RLLoginInstagram.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                tvLogin.performClick();
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                if (!SharePrefs.getInstance(DhiFacebookActivity.this).getBoolean(SharePrefs.ISFBLOGIN).booleanValue()) {
                    DhiFacebookActivity.this.startActivityForResult(new Intent(DhiFacebookActivity.this, DhiFBLoginActivity.class), 100);
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(DhiFacebookActivity.this);
                builder.setPositiveButton(DhiFacebookActivity.this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        SharePrefs.getInstance(DhiFacebookActivity.this).putBoolean(SharePrefs.ISFBLOGIN, false);
                        SharePrefs.getInstance(DhiFacebookActivity.this).putString(SharePrefs.FBKEY, "");
                        SharePrefs.getInstance(DhiFacebookActivity.this).putString(SharePrefs.FBCOOKIES, "");
                        if (SharePrefs.getInstance(DhiFacebookActivity.this).getBoolean(SharePrefs.ISFBLOGIN).booleanValue()) {
                            DhiFacebookActivity.this.SwitchLogin.setChecked(true);
                        } else {
                            SwitchLogin.setChecked(false);
                            RVUserList.setVisibility(View.GONE);
                            RVStories.setVisibility(View.GONE);
                            tvLogin.setVisibility(View.VISIBLE);
                            tvViewStories.setText(DhiFacebookActivity.this.getResources().getText(R.string.view_stories));
                        }
                        dialogInterface.cancel();
                    }
                });
                builder.setNegativeButton(DhiFacebookActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog create = builder.create();
                create.setTitle(DhiFacebookActivity.this.getResources().getString(R.string.do_u_want_to_download_media_from_pvt));
                create.show();
            }
        });
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 2);
        RVStories.setLayoutManager(gridLayoutManager2);
        RVStories.setNestedScrollingEnabled(false);
        gridLayoutManager2.setOrientation(RecyclerView.VERTICAL);
    }

    public void layoutCondition() {
        tvViewStories.setText(getResources().getString(R.string.stories));
        tvLogin.setVisibility(View.GONE);
    }


    public static int ordinalIndexOf(String str, String str2, int i) {
        int i2 = -1;
        while (true) {
            i2 = str.indexOf(str2, i2 + 1);
            if (i <= 0 || i2 == -1) {
                break;
            }
            i--;
        }
        return i2;
    }


    public void pasteText() {
        try {
            this.etText.setText("");
            String extractLinks = DhiAllStoryActivity.extractLinks(getIntent().getStringExtra("CopyIntent"));
            if (extractLinks == null || extractLinks.equals("")) {
                if (!clipBoard.hasPrimaryClip()) {
                    Log.d("ContentValues", "PasteText");
                } else if (clipBoard.getPrimaryClipDescription().hasMimeType("text/plain")) {
                    ClipData.Item itemAt = this.clipBoard.getPrimaryClip().getItemAt(0);
                    if (itemAt.getText().toString().contains(this.strName)) {
                        etText.setText(itemAt.getText().toString());
                    } else if (itemAt.getText().toString().contains(this.strNameSecond)) {
                        etText.setText(itemAt.getText().toString());
                    }
                } else if (clipBoard.getPrimaryClip().getItemAt(0).getText().toString().contains(this.strName)) {
                    etText.setText(clipBoard.getPrimaryClip().getItemAt(0).getText().toString());
                } else if (clipBoard.getPrimaryClip().getItemAt(0).getText().toString().contains(this.strNameSecond)) {
                    etText.setText(clipBoard.getPrimaryClip().getItemAt(0).getText().toString());
                }
            } else if (extractLinks.contains(this.strName)) {
                etText.setText(extractLinks);
            } else if (extractLinks.contains(this.strNameSecond)) {
                etText.setText(extractLinks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fbUserListClick(int i, NodeModel nodeModel) {
        getFacebookUserStories(nodeModel.getNodeDataModel().getId());
    }


    public void setLocale(String str) {
        Locale locale = new Locale(str);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        try {
            super.onActivityResult(i, i2, intent);
            if (i != 100) {
                return;
            }
            if (i2 == -1) {
                if (SharePrefs.getInstance(this).getBoolean(SharePrefs.ISFBLOGIN).booleanValue()) {
                    this.SwitchLogin.setChecked(true);
                    layoutCondition();
                    getFacebookUserData();
                    return;
                }
                this.SwitchLogin.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFacebookUserData() {
        prLoadingBar.setVisibility(View.VISIBLE);

        AndroidNetworking.post("https://www.facebook.com/api/graphql/").addHeaders("accept-language", "en,en-US;q=0.9,fr;q=0.8,ar;q=0.7").addHeaders("cookie", SharePrefs.getInstance(this).getString(SharePrefs.FBCOOKIES)).addHeaders("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").addHeaders("Content-Type", "application/json").addBodyParameter("fb_dtsg", SharePrefs.getInstance(this).getString(SharePrefs.FBKEY)).addBodyParameter("variables", "{\"bucketsCount\":200,\"initialBucketID\":null,\"pinnedIDs\":[\"\"],\"scale\":3}").addBodyParameter("doc_id", "2893638314007950").setPriority(Priority.MEDIUM).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println("JsonResp- " + response);

                if (response != null) {

                    try {

                        JSONObject unified_stories_buckets = response.getJSONObject("data").getJSONObject("me").getJSONObject("unified_stories_buckets");

                        Gson gson = new Gson();
                        Type listType = new TypeToken<EdgesModel>() {
                        }.getType();
                        EdgesModel edgesModelNew = gson.fromJson(String.valueOf(unified_stories_buckets), listType);

                        if (edgesModelNew.getEdgeModel().size() > 0) {
                            edgeModelList.clear();
                            edgeModelList.addAll(edgesModelNew.getEdgeModel());
                            dhiFbStoryUserListAdapter.notifyDataSetChanged();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                RVUserList.setVisibility(View.VISIBLE);
                prLoadingBar.setVisibility(View.GONE);

            }

            @Override
            public void onError(ANError anError) {
                prLoadingBar.setVisibility(View.GONE);

            }
        });
    }


    public void getFacebookUserStories(String userId) {
        prLoadingBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post("https://www.facebook.com/api/graphql/").addHeaders("accept-language", "en,en-US;q=0.9,fr;q=0.8,ar;q=0.7").addHeaders("cookie", SharePrefs.getInstance(this).getString(SharePrefs.FBCOOKIES)).addHeaders("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").addHeaders("Content-Type", "application/json").addBodyParameter("fb_dtsg", SharePrefs.getInstance(this).getString(SharePrefs.FBKEY)).addBodyParameter("variables", "{\"bucketID\":\"" + userId + "\",\"initialBucketID\":\"" + userId + "\",\"initialLoad\":false,\"scale\":5}").addBodyParameter("doc_id", "2558148157622405").setPriority(Priority.MEDIUM).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println("JsonResp- " + response);

                prLoadingBar.setVisibility(View.GONE);

                if (response != null) {
                    try {

                        JSONObject edges = response.getJSONObject("data").getJSONObject("bucket").getJSONObject("unified_stories");
                        Gson gson = new Gson();
                        Type listType = new TypeToken<EdgesModel>() {
                        }.getType();
                        EdgesModel edgesModelNew = gson.fromJson(String.valueOf(edges), listType);
//                                ArrayList<MediaModel> attachmentsList = edgesModelNew.getEdgeModel().get(0).getNodeDataModel().getAttachmentsList();
                        dhiFbStoriesListAdapter = new DhiFBStoriesListAdapter(DhiFacebookActivity.this, edgesModelNew.getEdgeModel());
                        RVStories.setAdapter(dhiFbStoriesListAdapter);
                        dhiFbStoriesListAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onError(ANError anError) {
                prLoadingBar.setVisibility(View.GONE);
            }
        });
    }

}