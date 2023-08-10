package com.allhdvideofree.downloader.videodownloader.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.documentfile.provider.DocumentFile;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.gms.ads.AdUtils;
import com.google.gms.ads.MainAds;
import com.allhdvideofree.downloader.videodownloader.databinding.DialogSaveVideoBinding;
import com.preference.PowerPreference;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.model.Edge;
import com.allhdvideofree.downloader.videodownloader.model.ItemModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import inet.ipaddr.Address;

public class MyUtils {
    public static final String ROOTDIRECTORYCHINGARI = "/HDTubeVideoDownloader/Chingari/";
    public static final File ROOTDIRECTORYCHINGARISHOW = new File(Environment.getExternalStorageDirectory() + "/Download/HDTubeVideoDownloader/Chingari");
    public static String RootDirectoryFacebook = "/HDTubeVideoDownloader/Facebook/";
    public static final File ROOTDIRECTORYWEBFB = new File(Environment.getExternalStorageDirectory() + "/Download/HDTubeVideoDownloader/Webfacebook");
    public static final File ROOTDIRECTORYWEBINSTA = new File(Environment.getExternalStorageDirectory() + "/Download/HDTubeVideoDownloader/Webinsta");
    public static File RootDirectoryFacebookShow = new File(Environment.getExternalStorageDirectory() + "/Download/HDTubeVideoDownloader/Facebook");
    public static String RootDirectoryInsta = "/HDTubeVideoDownloader/Insta/";
    public static File RootDirectoryInstaShow = new File(Environment.getExternalStorageDirectory() + "/Download/HDTubeVideoDownloader/Insta");
    public static String RootDirectoryShareChat = "/HDTubeVideoDownloader/ShareChat/";
    public static File RootDirectoryShareChatShow = new File(Environment.getExternalStorageDirectory() + "/Download/HDTubeVideoDownloader/ShareChat");
    public static String RootDirectoryTwitter = "/HDTubeVideoDownloader/Twitter/";
    public static File RootDirectoryTwitterShow = new File(Environment.getExternalStorageDirectory() + "/Download/HDTubeVideoDownloader/Twitter");
    public static File downloadWhatsAppDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), DhiApplication.getInstance().getString(R.string.app_name));
    private static Context context;
    public static Dialog customDialog;
    public static String mPath;
    public static int downloadIdNew = 0;
    private boolean isDownloadStart = false;
    public static Dialog mLoadDialog;
    public static TextView TV_diloagValue, TV_DialogMsg;
    public static final String My_JAVASCRIPT = "javascript:(function(){function dload_timer(){setTimeout(dload_timer,300);video_dloader();getImagesSrcSet();startListners();}function video_dloader(){var el = document.querySelectorAll('video');for(var i=0;i<el.length; i++){var src = el[i].src;el[i].parentElement.parentElement.parentElement.parentElement.setAttribute('onClick', 'setTimeout(function(){},500);FBDownloader.processVideo(\\\"'+src+'\\\");');}}function clickListner(){FBDownloader.processPhoto(this.getAttribute(\"srcset\"));} function setLeftListners(){getImagesSrcSet();var left = document.getElementsByClassName(\"coreSpriteLeftChevron\");if(left[0] !== undefined){left[0].removeEventListener(\"click\", setRightListners);left[0].addEventListener(\"click\", setRightListners);}}function setRightListners(){getImagesSrcSet();var right = document.getElementsByClassName(\"coreSpriteRightChevron\");if(right[0] !== undefined){right[0].removeEventListener(\"click\", setLeftListners);right[0].addEventListener(\"click\", setLeftListners);}}function startListners(){var right = document.getElementsByClassName(\"coreSpriteRightChevron\");if(right[0] !== undefined){\tright[0].addEventListener(\"click\", setLeftListners);}}function getImagesSrcSet(){var div = document.querySelectorAll('img[srcset]');for(i=0;i<div.length;i++){div[i].setAttribute(\"style\",\"z-index:100000\");div[i].removeEventListener(\"click\", clickListner);div[i].addEventListener(\"click\", clickListner);}}dload_timer();})()";

    public static ThinDownloadManager downloadManager;
    private static final int DOWNLOAD_THREAD_POOL_SIZE = 4;


    public MyUtils(Context context2) {
        context = context2;
    }

    public static void setToast(Context context2, String str) {
        Toast makeText = Toast.makeText(context2, str, Toast.LENGTH_SHORT);
        makeText.setGravity(17, 0, 0);
        makeText.show();
    }

    public static void createFileFolder() {
        if (!RootDirectoryFacebookShow.exists()) {
            RootDirectoryFacebookShow.mkdirs();
        }
        if (!RootDirectoryInstaShow.exists()) {
            RootDirectoryInstaShow.mkdirs();
        }

        if (!ROOTDIRECTORYWEBFB.exists()) {
            ROOTDIRECTORYWEBFB.mkdirs();
        }

        if (!ROOTDIRECTORYWEBINSTA.exists()) {
            ROOTDIRECTORYWEBINSTA.mkdirs();
        }

//        if (!RootDirectoryTikTokShow.exists()) {
//            RootDirectoryTikTokShow.mkdirs();
//        }
        if (!RootDirectoryTwitterShow.exists()) {
            RootDirectoryTwitterShow.mkdirs();
        }
        if (!downloadWhatsAppDir.exists()) {
            downloadWhatsAppDir.mkdirs();
        }
//        if (!RootDirectoryLikeeShow.exists()) {
//            RootDirectoryLikeeShow.mkdirs();
//        }
//        if (!RootDirectoryLikeeShow.exists()) {
//            RootDirectoryLikeeShow.mkdirs();
//        }
        if (!RootDirectoryShareChatShow.exists()) {
            RootDirectoryShareChatShow.mkdirs();
        }
//        if (!RootDirectoryRoposoShow.exists()) {
//            RootDirectoryRoposoShow.mkdirs();
//        }
//        if (!RootDirectorySnackVideoShow.exists()) {
//            RootDirectorySnackVideoShow.mkdirs();
//        }
//        File file = ROOTDIRECTORYJOSHSHOW;
//        if (!file.exists()) {
//            file.mkdirs();
//        }
        File file2 = ROOTDIRECTORYCHINGARISHOW;
        if (!file2.exists()) {
            file2.mkdirs();
        }
//        File file3 = ROOTDIRECTORYMITRONSHOW;
//        if (!file3.exists()) {
//            file3.mkdirs();
//        }
//        File file4 = ROOTDIRECTORYMXSHOW;
//        if (!file4.exists()) {
//            file4.mkdirs();
//        }
//        File file5 = ROOTDIRECTORYMOJSHOW;
//        if (!file5.exists()) {
//            file5.mkdirs();
//        }
    }

    public static void showProgressDialog(Activity activity) {
        System.out.println("Show");
        Dialog dialog = customDialog;
        if (dialog != null) {
            dialog.dismiss();
            customDialog = null;
        }
        customDialog = new Dialog(activity);
        ViewGroup viewGroup = null;
        View inflate = LayoutInflater.from(activity).inflate(R.layout.progress_dialog, (ViewGroup) null);
        customDialog.setCancelable(false);
        customDialog.setContentView(inflate);
        if (!customDialog.isShowing() && !activity.isFinishing()) {
            customDialog.show();
        }
    }

    public static void hideProgressDialog(Activity activity) {
        System.out.println("Hide");
        Dialog dialog = customDialog;
        if (dialog != null && dialog.isShowing()) {
            customDialog.dismiss();
        }
    }

    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static void startNewDownload(String str, String str2, Activity context2, String str3) {


        mLoadDialog = ShowLoading(context2);
        TV_diloagValue = mLoadDialog.findViewById(R.id.TV_diloagValue);
        TV_DialogMsg = mLoadDialog.findViewById(R.id.TV_DialogMsg);
        TV_diloagValue.setVisibility(View.VISIBLE);
        TV_DialogMsg.setText("Downloading...");


        downloadIdNew = PRDownloader.download(String.valueOf(Uri.parse(str)), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + str2, str3).build().setOnStartOrResumeListener(new OnStartOrResumeListener() {
            @Override
            public void onStartOrResume() {
            }
        }).setOnPauseListener(new OnPauseListener() {
            @Override
            public void onPause() {
            }
        }).setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel() {
                downloadIdNew = 0;

                HideLoading(mLoadDialog);
            }
        }).setOnProgressListener(new OnProgressListener() {
            @Override
            public void onProgress(final Progress progress) {
                context2.runOnUiThread(new Runnable() {
                    public void run() {

                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;

                        TV_diloagValue.setText("" + progressPercent + " %");
//                                Log.e("DownloadVideo", "Percent : " + progressPercent + "%");

                    }
                });
            }
        }).start(new OnDownloadListener() {
            @Override
            public void onDownloadComplete() {
                HideLoading(mLoadDialog);
                Toast.makeText(context2, "Downloading Complete", Toast.LENGTH_SHORT).show();
//                        Log.e("DownloadVideo", "Download Complete Successfully");

            }

            @Override
            public void onError(Error error) {

            }


        });


    }


    public static void startDownloadFromLink(List<Edge> list, String str, String str2, Activity context2, String str3, String str4, String str5) {

        mLoadDialog = ShowLoading(context2);
        TV_diloagValue = mLoadDialog.findViewById(R.id.TV_diloagValue);
        TV_DialogMsg = mLoadDialog.findViewById(R.id.TV_DialogMsg);
        TV_diloagValue.setVisibility(View.VISIBLE);
        TV_DialogMsg.setText("Downloading...");

        String str6;
        String str7;
        String str8;
        String str9;
//        setToast(context2, context2.getResources().getString(R.string.download_started));
        if (Build.VERSION.SDK_INT >= 29) {
            ContentResolver contentResolver = context2.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", "json.json");
            contentValues.put("mime_type", "application/json");
            contentValues.put("is_pending", (Integer) 1);
            contentValues.put("relative_path", Environment.DIRECTORY_DOWNLOADS + MyUtils.RootDirectoryInsta);
            Uri insert = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
            Log.d("mediaUri", "mediaUri " + insert);
            try {
                OutputStream openOutputStream = contentResolver.openOutputStream(insert);
                try {
                    openOutputStream.write(str4.getBytes());
                    if (openOutputStream != null) {
                        openOutputStream.close();
                    }
                } catch (Throwable th) {
                    if (openOutputStream != null) {
                        try {
                            openOutputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("is_pending", (Integer) 0);
            contentResolver.update(insert, contentValues2, null, null);
        } else {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + MyUtils.RootDirectoryInsta);
            if (!file.exists()) {
                Log.d("dir", "" + file);
                file.mkdirs();
            }

        }
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
        int i = 3;
//        request.setAllowedNetworkTypes(3);
//        request.setNotificationVisibility(1);
//        request.setTitle(str3 + "");
//        request.setVisibleInDownloadsUi(true);
//        String str10 = Environment.DIRECTORY_DOWNLOADS;
//        request.setDestinationInExternalPublicDir(str10, str2 + str3);
//        ((DownloadManager) context2.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
        int size = list.size();
        String str11 = ".jpeg";
        String str12 = str11;
        String str13 = "image";
        int i2 = 0;
        while (i2 < size) {
            if (list.get(i2).getNode().isIs_video()) {
                str8 = list.get(i2).getNode().getVideo_url();
                str7 = ".mp4";
                str9 = "video";
            } else {
                str8 = list.get(i2).getNode().getDisplay_resources().get(list.get(i2).getNode().getDisplay_resources().size() - 1).getSrc();
                str7 = str11;
                str9 = "image";
            }
//            DownloadManager.Request request2 = new DownloadManager.Request(Uri.parse(str8));
//            request2.setAllowedNetworkTypes(i);
//            request2.setNotificationVisibility(1);
            StringBuilder sb = new StringBuilder();
            sb.append(str9);
            sb.append("");
            i2++;
            sb.append(i2);
            sb.append("");
            String str14 = str7;
            sb.append(str14);
            sb.append("");


            downloadIdNew = PRDownloader.download(String.valueOf(Uri.parse(str8)), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + str2 + str9 + "" + i2 + "" + str14, sb.toString()).build().setOnStartOrResumeListener(new OnStartOrResumeListener() {
                @Override
                public void onStartOrResume() {
                }
            }).setOnPauseListener(new OnPauseListener() {
                @Override
                public void onPause() {
                }
            }).setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel() {
                    downloadIdNew = 0;

                    HideLoading(mLoadDialog);
                }
            }).setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(final Progress progress) {
                    context2.runOnUiThread(new Runnable() {
                        public void run() {

                            long progressPercent = progress.currentBytes * 100 / progress.totalBytes;

                            TV_diloagValue.setText("" + progressPercent + " %");
//                                Log.e("DownloadVideo", "Percent : " + progressPercent + "%");

                        }
                    });
                }
            }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    HideLoading(mLoadDialog);
                    Toast.makeText(context2, "Downloading Complete", Toast.LENGTH_SHORT).show();
//                            Log.e("DownloadVideo", "Download Complete Successfully");

                }

                @Override
                public void onError(Error error) {

                }


            });

//            request2.setTitle(sb.toString());
//            request2.setVisibleInDownloadsUi(true);
//            String str15 = Environment.DIRECTORY_DOWNLOADS;
//            str11 = str11;
//            request2.setDestinationInExternalPublicDir(str15, str2 + "/" + str9 + "" + i2 + "" + str14);
//            ((DownloadManager) context2.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request2);
            str12 = str14;
            str13 = str9;
            i = 3;


        }
        String str16 = str11;
    }


    public static void startDownloadLogin(List<ItemModel> list, String str, String str2, Activity context2, String str3, String str4) {
        Log.d("TAG_startDownload", "startDownload: startDownloadLogin");
        String str6;
        String str7;
        String str8;
        // setToast(context2, context2.getResources().getString(R.string.download_started));
        int i = 0;
        if (Build.VERSION.SDK_INT >= 29) {
            ContentResolver contentResolver = context2.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", "json.json");
            contentValues.put("mime_type", "application/json");
            contentValues.put("is_pending", (Integer) 1);
            contentValues.put("relative_path", Environment.DIRECTORY_DOWNLOADS + MyUtils.RootDirectoryInsta);
            Uri insert = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
            Log.d("mediaUri", "mediaUri " + insert);

            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("is_pending", (Integer) 0);
            contentResolver.update(insert, contentValues2, null, null);
        } else {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + MyUtils.RootDirectoryInsta);
            if (!file.exists()) {
                Log.d("dir", "" + file);
                file.mkdirs();
            }

        }
        int i2 = 3;

        mLoadDialog = ShowLoading(context2);
        TV_diloagValue = mLoadDialog.findViewById(R.id.TV_diloagValue);
        TV_DialogMsg = mLoadDialog.findViewById(R.id.TV_DialogMsg);
        TV_diloagValue.setVisibility(View.VISIBLE);
        TV_DialogMsg.setText("Downloading...");


        int size = list.size();
        String str10 = ".jpeg";
        String str11 = str10;
        String str12 = "image";
        int i3 = 0;
        while (i3 < size) {
            if (list.get(i3).getMedia_type() == 2) {
                str8 = list.get(i3).getVideo_versions().get(i).getUrl();
                str7 = ".mp4";
                String name = String.valueOf(System.currentTimeMillis());
                str12 = name + "_video";
            } else {
                str8 = list.get(i3).getImage_versions2().getCandidates().get(i).getUrl();
                str7 = str10;
                String name = String.valueOf(System.currentTimeMillis());
                str12 = name + "_image";
            }
            i3++;

            String finalStr = str8;
            String finalStr1 = str12;
            int finalI = i3;
            String finalStr2 = str7;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    animation_view.setVisibility(View.GONE);
//                    rcprogress.setVisibility(View.VISIBLE);
                    String destinationPath = Environment.getExternalStorageDirectory() + "/Download";
                    Uri downloadUri = Uri.parse(finalStr);
                    Uri destinationUri = Uri.parse(destinationPath + str2 + finalStr1 + "" + finalI + "" + finalStr2);
                    Log.d("TAG_startDownloadLogin", "startDownloadLogin: " + destinationUri);
                    DownloadRequest downloadRequest = new DownloadRequest(downloadUri).addCustomHeader("Auth-Token", "myTokenKey").setRetryPolicy(new DefaultRetryPolicy()).setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH).setDownloadContext(context2).setDownloadListener(new DownloadStatusListener() {
                        @Override
                        public void onDownloadComplete(int id) {
                            HideLoading(mLoadDialog);
                            Toast.makeText(context2, "Downloading Complete", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                            Toast.makeText(context2, "Failed", Toast.LENGTH_SHORT).show();
                            HideLoading(mLoadDialog);
                        }

                        @Override
                        public void onProgress(int id, long totalBytes, long downlaodedBytes, int progress) {
                            long progressPercent = downlaodedBytes * 100 / totalBytes;

                            TV_diloagValue.setText("" + progressPercent + " %");
                        }
                    });

                    ThinDownloadManager downloadManager = new ThinDownloadManager(1);
                    downloadManager.add(downloadRequest);
                }
            }, 1000);

            str11 = str7;
            i2 = 3;
            i = 0;

        }

    }

    public static void shareImage(Context context2, String str) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.TEXT", context2.getResources().getString(R.string.share_txt));
            String str2 = null;
            intent.putExtra("android.intent.extra.STREAM", Uri.parse(MediaStore.Images.Media.insertImage(context2.getContentResolver(), str, "", (String) null)));
            intent.setType("image/*");
            context2.startActivity(Intent.createChooser(intent, context2.getResources().getString(R.string.share_image_via)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shareImageVideoOnWhatsapp(Context context2, String str, boolean z) {
        Uri parse = Uri.parse(str);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.setPackage("com.whatsapp");
        intent.putExtra("android.intent.extra.TEXT", "");
        intent.putExtra("android.intent.extra.STREAM", parse);
        if (z) {
            intent.setType("video/*");
        } else {
            intent.setType("image/*");
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context2.startActivity(intent);
        } catch (Exception unused) {
            setToast(context2, context2.getResources().getString(R.string.whatsapp_not_installed));
        }
    }

    public static void shareVideo(Context context2, String str) {
        Uri parse = Uri.parse(str);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(MimeTypes.VIDEO_MP4);
        intent.putExtra("android.intent.extra.STREAM", parse);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context2.startActivity(Intent.createChooser(intent, "Share Video using"));
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(context2, context2.getResources().getString(R.string.no_app_installed), Toast.LENGTH_SHORT).show();
        }
    }

    public static void OpenApp(Context context2, String str) {
        Intent launchIntentForPackage = context2.getPackageManager().getLaunchIntentForPackage(str);
        if (launchIntentForPackage != null) {
            context2.startActivity(launchIntentForPackage);
        } else {
            setToast(context2, context2.getResources().getString(R.string.app_not_available));
        }
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.equalsIgnoreCase(Address.OCTAL_PREFIX);
    }

    public static List<String> extractUrls(String str) {
        ArrayList arrayList = new ArrayList();
        Matcher matcher = Pattern.compile("((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)", 2).matcher(str);
        while (matcher.find()) {
            arrayList.add(str.substring(matcher.start(0), matcher.end(0)));
        }
        return arrayList;
    }

    public static void infoDialog(Context context2, String str, String str2) {
        new AlertDialog.Builder(context2).setTitle(str).setMessage(str2).setPositiveButton(context2.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    public static String getBack(String str, String str2) {
        Matcher matcher = Pattern.compile(str2).matcher(str);
        return matcher.find() ? matcher.group(1) : "";
    }

    public static boolean download(Context context2, String str) {
        return copyFileInSavedDir(context2, str);
    }

    public static boolean isVideoFile(Context context2, String str) {
        if (str.startsWith("content")) {
            String type = DocumentFile.fromSingleUri(context2, Uri.parse(str)).getType();
            if (type == null || !type.startsWith(MimeTypes.BASE_TYPE_VIDEO)) {
                return false;
            }
            return true;
        }
        String guessContentTypeFromName = URLConnection.guessContentTypeFromName(str);
        if (guessContentTypeFromName == null || !guessContentTypeFromName.startsWith(MimeTypes.BASE_TYPE_VIDEO)) {
            return false;
        }
        return true;
    }

    public static boolean copyFileInSavedDir(Context context2, String str) {
        String str2;
        if (isVideoFile(context2, str)) {
            str2 = getDir(context2, "Videos").getAbsolutePath();
        } else {
            str2 = getDir(context2, "Images").getAbsolutePath();
        }
        Uri fromFile = Uri.fromFile(new File(str2 + File.separator + new File(str).getName()));
        try {
            InputStream openInputStream = context2.getContentResolver().openInputStream(Uri.parse(str));
            OutputStream openOutputStream = context2.getContentResolver().openOutputStream(fromFile, "w");
            byte[] bArr = new byte[1024];
            while (true) {
                int read = openInputStream.read(bArr);
                if (read > 0) {
                    openOutputStream.write(bArr, 0, read);
                } else {
                    openInputStream.close();
                    openOutputStream.flush();
                    openOutputStream.close();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(fromFile);
                    context2.sendBroadcast(intent);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    static File getDir(Context context2, String str) {
//        File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "Download" + File.separator + context2.getResources().getString(R.string.app_name) + File.separator + str);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), DhiApplication.getInstance().getString(R.string.app_name));
        file.mkdirs();
        return file;
    }

    public static void setLanguage(Context context2, String str) {
        Locale locale = new Locale(str);
        Resources resources = context2.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public static boolean appInstalledOrNot(Context context2, String str) {
        try {
            context2.getPackageManager().getPackageInfo(str, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static void shareFile(Context context2, boolean z, String str) {
        Uri uri;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        if (z) {
            intent.setType("Video/*");
        } else {
            intent.setType("image/*");
        }
        if (str.startsWith("content")) {
            uri = Uri.parse(str);
        } else {
            uri = FileProvider.getUriForFile(context2, context2.getApplicationContext().getPackageName() + ".provider", new File(str));
        }
        intent.putExtra("android.intent.extra.STREAM", uri);
        context2.startActivity(intent);
    }

    public static void repostWhatsApp(Context context2, boolean z, String str) {
        Uri uri;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        if (z) {
            intent.setType("Video/*");
        } else {
            intent.setType("image/*");
        }
        if (str.startsWith("content")) {
            uri = Uri.parse(str);
        } else {
            uri = FileProvider.getUriForFile(context2, context2.getApplicationContext().getPackageName() + ".provider", new File(str));
        }
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.setPackage("com.whatsapp");
        context2.startActivity(intent);
    }


    public static Dialog ShowLoading(Activity activity) {
       /* Dialog dialog = new Dialog(activity);
        try {
            dialog.setContentView(R.layout.dialog_save_video);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


            new MainAds().showNativeAds(activity, dialog, null, AdUtils.AD_LARGE);

        } catch (Exception e) {
            e.printStackTrace();

        }*/

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


}
