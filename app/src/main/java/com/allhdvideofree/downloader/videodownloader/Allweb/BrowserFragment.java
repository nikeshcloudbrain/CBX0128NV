package com.allhdvideofree.downloader.videodownloader.Allweb;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.google.gms.ads.AdUtils;
import com.google.gms.ads.MainAds;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.Utils.MyUtils;
import com.allhdvideofree.downloader.videodownloader.databinding.DialogSaveVideoBinding;
import com.preference.PowerPreference;

import org.json.JSONObject;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BrowserFragment extends Fragment implements View.OnClickListener {
    public static WebView webViewBrowser;
    private TranslateAnimation anim;

    public ImageView buttonDownload;

    public int counter = 0;

    public boolean download = false;

    public String downloadUrl = "";

    public LinearLayout layoutProgress;
    public static int downloadIdNew = 0;
    public static TextView TV_diloagValue, TV_DialogMsg;
    public static Dialog mLoadDialog;

    static  int access$508(BrowserFragment x0) {
        int i = x0.counter;
        x0.counter = i + 1;
        return i;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browser, container, false);
        initView(view);
        return view;
    }

    @SuppressLint("JavascriptInterface")
    private void initView(View view) {

        webViewBrowser = (WebView) view.findViewById(R.id.webView_browser);
        this.layoutProgress = (LinearLayout) view.findViewById(R.id.layout_progress);
        this.buttonDownload = (ImageView) view.findViewById(R.id.button_download);
        this.buttonDownload.setOnClickListener(this);
        webViewBrowser.getSettings().setJavaScriptEnabled(true);
        setDesktopMode(webViewBrowser, true);
        webViewBrowser.getSettings().setPluginState(WebSettings.PluginState.ON);
        webViewBrowser.getSettings().setSupportMultipleWindows(false);
        webViewBrowser.getSettings().setBuiltInZoomControls(true);
        webViewBrowser.getSettings().setDisplayZoomControls(true);
        webViewBrowser.getSettings().setUseWideViewPort(true);
        webViewBrowser.getSettings().setLoadWithOverviewMode(true);
        webViewBrowser.setWebViewClient(new MyWebViewClient());
        webViewBrowser.getSettings().setDefaultTextEncodingName("UTF-8");
        webViewBrowser.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 4.4; sdk Build/KRT16L) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");
        WebSettings localWebSettings = webViewBrowser.getSettings();
        localWebSettings.setSupportZoom(true);
        localWebSettings.setBuiltInZoomControls(true);
        localWebSettings.setDomStorageEnabled(true);
        localWebSettings.setAllowFileAccess(true);
//        webViewBrowser.addJavascriptInterface(this, "Android");
        webViewBrowser.clearCache(false);
        CookieSyncManager.createInstance(getActivity());
        CookieManager.getInstance().setAcceptCookie(true);
        CookieSyncManager.getInstance().startSync();
        webViewBrowser.loadUrl("www.facebook.com");
        webViewBrowser.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 85 || newProgress <= 1) {
                    BrowserFragment.this.layoutProgress.setVisibility(View.GONE);
                } else {
                    BrowserFragment.this.layoutProgress.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @RequiresApi(api = 24)
    public void onClick(View v) {
        if (v == this.buttonDownload) {
            String url = webViewBrowser.getUrl();
            if (!url.contains("instagram.com")) {

//                Log.e("DownloadUrl", "OntherUrl : " + url);

                if (DBDownloadManager.getInstance(getActivity()).isUrlContains(url)) {

                    DhiFbBrowserActivity.getInstance().showFileExistPopUpDialog("", "");
                    showDownloadBubble(false);

                } else if (!url.contains("dailymotion.com/video/") && !url.contains("vimeo.com") && !this.downloadUrl.contains("https://tubidy.mobi/watch.php?id=") && !url.contains("facebook.com") && !this.downloadUrl.contains("muscdn.com")) {
                    Toast.makeText(getContext(), "opps!!!video link not found", Toast.LENGTH_SHORT).show();
                } else if (url.contains("vimeo.com")) {
                    if (url.contains("https://vimeo.com/watch")) {
                        Toast.makeText(getContext(), "Video link not found", Toast.LENGTH_SHORT).show();
                    } else if (DBDownloadManager.getInstance(getActivity()).isUrlContains(url)) {
                        showDownloadBubble(false);
                        DhiFbBrowserActivity.getInstance().showFileExistPopUpDialog("", "");
                    } else {
                        callVimeoVideoAPI(new File(url).getName());
                    }
                } else if (this.downloadUrl.contains("https://tubidy.mobi/watch.php?id=")) {
                    String tudibyVideoUrl = webViewBrowser.getUrl();
                    if (!tudibyVideoUrl.endsWith(".mp4")) {
                        Toast.makeText(getContext(), "Video Link not found", Toast.LENGTH_SHORT).show();
                        showDownloadBubble(false);
                        this.buttonDownload.setVisibility(View.INVISIBLE);
                        return;
                    }
                    showDownloadManager(tudibyVideoUrl);
                } else if (this.downloadUrl.contains("muscdn.com")) {
                    showDownloadManager(this.downloadUrl);
                } else if (url.contains("facebook.com")) {
                    String url2 = this.downloadUrl;
                    if (DBDownloadManager.getInstance(getActivity()).isUrlContains(url2)) {
                        showDownloadBubble(false);
                        DhiFbBrowserActivity.getInstance().showFileExistPopUpDialog("", "");
                    } else if (url2.startsWith("https://video.") || url2.contains(".mp4?_")) {
//                        showFaceBookDownloadManager(url2);
                        startDownload(url2, getActivity());

                    } else {
                        Toast.makeText(getContext(), "Video link not found", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (DBDownloadManager.getInstance(getActivity()).isUrlContains(url)) {
                DhiFbBrowserActivity.getInstance().showFileExistPopUpDialog("", "");
                showDownloadBubble(false);
            } else if (!this.downloadUrl.equals("")) {
                String url3 = this.downloadUrl;

//                Log.e("InstagramBro", "=======url3=====" + url3);

                if (url3.contains("https://scontent.cdninstagram.com/")) {

//                    Log.e("InstagramBro", "=======Link==11====" + downloadUrl);

                    showDownloadManager(this.downloadUrl);
                    return;
                }
                Log.e("DownloadUrl", "MyInsta : " + url3);

                if (this.downloadUrl.contains(".mp4?_nc_ht=")) {

//                    Log.e("InstagramBro", "=======Link===22====" + downloadUrl);

                    showDownloadManager(this.downloadUrl);
                } else {
                    Toast.makeText(getContext(), "video link not found", Toast.LENGTH_SHORT).show();
                }

//                if (this.downloadUrl.contains("?__a=1&__d=dis")) {
//                    Log.e("InstagramBro", "=======Link===22=" + downloadUrl);
//                    showDownloadManager(this.downloadUrl);
//                } else {
//                    Toast.makeText(getContext(), "video link not found", Toast.LENGTH_SHORT).show();
//                }

            } else {
                Toast.makeText(getContext(), "video link not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class MyWebViewClient extends WebViewClient {
        private Map<String, Boolean> loadedUrls;

        private MyWebViewClient() {
            this.loadedUrls = new HashMap();
        }

        public void onLoadResource(WebView view, String url) {
            try {
                if (view.getUrl() != null) {
                    if (BrowserFragment.this.getActivity() != null) {
                        DhiFbBrowserActivity.et_text_fb.setSelection(DhiFbBrowserActivity.et_text_fb.getText().length());
                    }
                    if (view.getUrl().contains("https://www.instagram.com/")) {
                        BrowserFragment.this.showDownloadBubble(true);
                        BrowserFragment.this.buttonDownload.setVisibility(View.VISIBLE);
                        String newUA = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1";
                        view.getSettings().setUserAgentString(newUA);
//                        view.evaluateJavascript("document.querySelector('meta[name=\"viewport\"]').setAttribute('content', 'width=1024px, initial-scale=' + (document.documentElement.clientWidth / 1024));", null);

                    }
                    if (view.getUrl().contains("dailymotion.com/video/")) {
                        DhiFbBrowserActivity.et_text_fb.setText(view.getUrl());
                        BrowserFragment.this.showDownloadBubble(true);
                    } else {
                        if (!view.getUrl().equals("https://vimeo.com/watch/") && !view.getUrl().equals("https://vimeo.com/home") && !view.getUrl().equals("https://vimeo.com/")) {
                            if (!view.getUrl().equals("https://vimeo.com/search")) {
                                if (!url.contains("https://tubidy.mobi/watch.php?id=")) {
                                    if (!url.contains("muscdn.com")) {
                                        if (url.contains("fna.fbcdn.net")) {
                                            DhiFbBrowserActivity.et_text_fb.setText(url);
                                            BrowserFragment.this.showDownloadBubble(true);
                                            BrowserFragment.this.buttonDownload.setVisibility(View.VISIBLE);
                                            DhiFbBrowserActivity.et_text_fb.setText(url);
                                            String unused = BrowserFragment.this.downloadUrl = url;
                                            Log.d("link-----", url);
                                        } else {
                                            if (!url.contains("https://scontent.cdninstagram.com/")) {
                                                if (!url.contains("https://www.instagram.com/")) {
                                                    BrowserFragment.this.showDownloadBubble(false);
                                                }
                                            }
                                            BrowserFragment.this.showDownloadBubble(true);
                                            BrowserFragment.this.buttonDownload.setVisibility(View.VISIBLE);
                                            DhiFbBrowserActivity.et_text_fb.setText(url);
                                            if (BrowserFragment.this.download) {
                                                boolean unused2 = BrowserFragment.this.download = false;
                                                super.onLoadResource(view, url);
                                            } else if (url.toUpperCase().contains(".MP4") && !url.toUpperCase().endsWith(".ANI") && !url.toUpperCase().endsWith(".BMP") && !url.toUpperCase().endsWith(".CAL") && !url.toUpperCase().endsWith(".FAX") && !url.toUpperCase().endsWith(".GIF") && !url.toUpperCase().endsWith(".IMG") && !url.toUpperCase().endsWith(".JBG") && !url.toUpperCase().endsWith(".JPE") && !url.toUpperCase().endsWith(".JPEG") && !url.toUpperCase().endsWith(".JPG") && !url.toUpperCase().endsWith(".MAC") && !url.toUpperCase().endsWith(".PBM") && !url.toUpperCase().endsWith(".PCD") && !url.toUpperCase().endsWith(".PCX") && !url.toUpperCase().endsWith(".PCT") && !url.toUpperCase().endsWith(".PGM") && !url.toUpperCase().endsWith(".PNG") && !url.toUpperCase().endsWith(".PPM") && !url.toUpperCase().endsWith(".PSD") && !url.toUpperCase().endsWith(".RAS") && !url.toUpperCase().endsWith(".TGA") && !url.toUpperCase().endsWith(".TIFF")) {
                                                if (!url.toUpperCase().endsWith(".WMF")) {
                                                    BrowserFragment.access$508(BrowserFragment.this);
                                                    if (BrowserFragment.this.counter == 1) {
                                                        try {
                                                            String unused3 = BrowserFragment.this.downloadUrl = url;
                                                            boolean unused4 = BrowserFragment.this.download = true;
                                                            int unused5 = BrowserFragment.this.counter = 0;
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                BrowserFragment.this.showDownloadBubble(true);
                                BrowserFragment.this.buttonDownload.setVisibility(View.VISIBLE);
                                String unused6 = BrowserFragment.this.downloadUrl = url;
                                Log.d("download----", BrowserFragment.this.downloadUrl);
                            }
                        }
                        BrowserFragment.this.showDownloadBubble(true);
                        BrowserFragment.this.buttonDownload.setVisibility(View.VISIBLE);
                    }
                } else {
                    BrowserFragment.this.buttonDownload.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            super.onLoadResource(view, url);
        }

        public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap) {
            if (BrowserFragment.this.getActivity() != null) {
                DhiFbBrowserActivity.et_text_fb.setText(paramString);
                DhiFbBrowserActivity.et_text_fb.setSelection(DhiFbBrowserActivity.et_text_fb.getText().length());
            }
        }

        public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString) {
            if (!paramString.contains("http://tubidy.mobi/watch.php/id=")) {
                return false;
            }
            if (BrowserFragment.this.download) {
                boolean unused = BrowserFragment.this.download = false;
                return super.shouldOverrideUrlLoading(paramWebView, paramString);
            } else if (!paramString.toUpperCase().contains(".MP4") && !paramString.toUpperCase().contains(".3GP") && !paramString.toUpperCase().contains(".PDF") && !paramString.toUpperCase().contains(".APK") && !paramString.toUpperCase().contains(".MP3") && !paramString.toUpperCase().contains(".APE") && !paramString.toUpperCase().contains(".AVI") && !paramString.toUpperCase().contains(".WMV") && !paramString.toUpperCase().contains(".WAV") && !paramString.toUpperCase().contains(".ASF") && !paramString.toUpperCase().contains(".MPG") && !paramString.toUpperCase().contains(".AMR") && !paramString.toUpperCase().contains(".OGG") && !paramString.toUpperCase().contains(".OGA") && !paramString.toUpperCase().contains(".OGV") && !paramString.toUpperCase().contains(".WMA") && !paramString.toUpperCase().contains(".DOC") && !paramString.toUpperCase().contains(".PPT") && !paramString.toUpperCase().contains(".PPS") && !paramString.toUpperCase().contains(".PPX") && !paramString.toUpperCase().contains(".XLS") && !paramString.toUpperCase().contains(".CHM") && !paramString.toUpperCase().contains(".TXT") && !paramString.toUpperCase().contains(".ZIP") && !paramString.toUpperCase().contains(".RAR") && !paramString.toUpperCase().contains(".MKV") && !paramString.toUpperCase().contains(".SWF") && !paramString.toUpperCase().contains(".FLV") && !paramString.toUpperCase().contains(".PCS") && !paramString.toUpperCase().contains(".MOV")) {
                return super.shouldOverrideUrlLoading(paramWebView, paramString);
            } else {
                BrowserFragment.access$508(BrowserFragment.this);
                if (BrowserFragment.this.counter == 1) {
                    try {
                        String unused2 = BrowserFragment.this.downloadUrl = String.valueOf(Uri.parse(paramString));
                        boolean unused3 = BrowserFragment.this.download = true;
                        int unused4 = BrowserFragment.this.counter = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        }

        @TargetApi(11)
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

            return super.shouldInterceptRequest(view, url);
        }
    }

    public void showDownloadBubble(boolean isShow) {
        if (isShow) {
            this.buttonDownload.setVisibility(View.VISIBLE);
            if (this.anim == null) {
                this.anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, 15.0f);
                this.anim.setRepeatCount(-1);
                this.anim.setRepeatMode(2);
                this.anim.setDuration(100);
                this.anim.setFillAfter(true);
                this.buttonDownload.setClickable(true);
                this.buttonDownload.startAnimation(this.anim);
                return;
            }
            return;
        }
        this.buttonDownload.setVisibility(View.GONE);
        TranslateAnimation translateAnimation = this.anim;
        if (translateAnimation != null && translateAnimation.isInitialized()) {
            this.anim.cancel();
            this.anim = null;
        }
    }

    private void callVimeoVideoAPI(String videoId) {
        try {
            AndroidUtils.vimeoVodeoCallAPI("http://player.vimeo.com/video/" + videoId + "/").vimeoVideoUrl().enqueue(new Callback<ResponseBody>() {
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            BrowserFragment.this.showDownloadManager(new JSONObject(responseBody.string()).getJSONObject("request").getJSONObject("files").getJSONArray("progressive").getJSONObject(0).getString("url"));
                            return;
                        }
                        Toast.makeText(BrowserFragment.this.getContext(), "Video link not found", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    AndroidUtils.dismissProgress();
                    t.printStackTrace();
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e2) {
            e2.printStackTrace();
        }
    }


    public void showDownloadManager(String url) {
//        if (MimeTypeMap.getFileExtensionFromUrl(url).equals("")) {
//        }
//        MimeTypeMap singleton = MimeTypeMap.getSingleton();
////        if (Nammu.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE")) {
//        try {
//            if (Application.getInstance().downloadIntent == null) {
//                Application.getInstance().downloadIntent = new Intent(getActivity(), DownloadService.class);
//                getActivity().bindService(Application.getInstance().downloadIntent, Application.getInstance().downloadConnection, 1);
//                getActivity().startService(Application.getInstance().downloadIntent);
//            }
//        } catch (Exception se) {
//            Log.d("Test", se.getMessage());
//            se.printStackTrace();
//        }
//        try {
//            String fileName = Application.getInstance().downloadService.getFileName(url.replace("%20", " "));
//            Application.getInstance().downloadService.addDownloadData(url, "", fileName, new File(Application.getInstance().downloadService.getFilePath(fileName)).getAbsolutePath());
//            showDownloadBubble(false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        } else {
//            Nammu.askForPermission((Activity) getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE", NewBrowserActivity.getInstance().permissionStorageCallback);
//        }
    }


    public static void startDownload(String url, Activity activity) {


//        Log.e("MyVideoDownload", "==>" + new Gson().toJson(video));


        mLoadDialog = ShowLoading(activity);
        TV_diloagValue = mLoadDialog.findViewById(R.id.TV_diloagValue);
        TV_DialogMsg = mLoadDialog.findViewById(R.id.TV_DialogMsg);
        TV_diloagValue.setVisibility(View.VISIBLE);
        TV_DialogMsg.setText("Downloading...");


        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension.equals("")) {
            extension = ".mp4";
        }

        String fileName = "facebook" + url.substring(url.length() - 8) + "." + extension;


        downloadIdNew = PRDownloader.download(url, MyUtils.RootDirectoryFacebookShow.getAbsolutePath(), fileName)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {
                        downloadIdNew = 0;

                        HideLoading(mLoadDialog);
                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(final Progress progress) {
                        activity.runOnUiThread(new Runnable() {
                            public void run() {

                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;

                                TV_diloagValue.setText("" + progressPercent + " %");
//                                Log.e("DownloadVideo", "Percent : " + progressPercent + "%");

                            }
                        });
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        HideLoading(mLoadDialog);

                        Toast.makeText(activity, "Downloading Complete", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Error error) {

                    }


                });


        Toast.makeText(activity, "Download is started", Toast.LENGTH_LONG).show();
    }

    public static Dialog ShowLoading(Activity activity) {
        Dialog dialog = new Dialog(activity);


        try {
            DialogSaveVideoBinding binding = DialogSaveVideoBinding.inflate(activity.getLayoutInflater());
            dialog.setContentView(binding.getRoot());

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                    }
                }
            });

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    if (PowerPreference.getDefaultFile().getBoolean(AdUtils.FULL_SCREEN, true)) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                    }
                    if (PowerPreference.getDefaultFile().getBoolean(AdUtils.ExitDialogNativeOnOff, true)) {
                        new MainAds().showNativeAds(activity, dialog, binding.includedAd.adFrameLarge, AdUtils.AD_NORMAL);
                    } else {
                        binding.includedAd.adFrameLarge.setVisibility(View.GONE);
                        binding.includedAd.adSpaceLarge.setVisibility(View.GONE);
                    }
                }
            });



            dialog.show();

        } catch (Exception e) {
            Constant.showLog(e.getMessage());
        }
        return dialog;
    }

    public static void HideLoading(Dialog d) {
        if (d != null && d.isShowing()) {
            Log.w("Dialog", "..........Hide");
            d.dismiss();
        }
    }

    public void setDesktopMode(WebView webView, boolean enabled) {
        String newUserAgent = webView.getSettings().getUserAgentString();
        if (enabled) {
            try {
                String ua = webView.getSettings().getUserAgentString();
                String androidOSString = webView.getSettings().getUserAgentString().substring(ua.indexOf("("), ua.indexOf(")") + 1);
                newUserAgent = webView.getSettings().getUserAgentString().replace(androidOSString, "(X11; Linux x86_64)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            newUserAgent = null;
        }

        webView.getSettings().setUserAgentString(newUserAgent);
        webView.getSettings().setUseWideViewPort(enabled);
        webView.getSettings().setLoadWithOverviewMode(enabled);
        webView.reload();
    }
}
