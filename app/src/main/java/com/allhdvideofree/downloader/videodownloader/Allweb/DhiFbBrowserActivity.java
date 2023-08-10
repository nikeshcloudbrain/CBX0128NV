package com.allhdvideofree.downloader.videodownloader.Allweb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.R;

import java.io.File;

public class DhiFbBrowserActivity extends AppCompatActivity {
    AppCompatImageView img_back;
    public  static AppCompatEditText et_text_fb;
    private static AppCompatActivity activity;
    //    public AllDownloaderSharedpref sharedpref;
    LinearLayoutCompat lin_download;

    public static DhiFbBrowserActivity getInstance() {
        return (DhiFbBrowserActivity) activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_fb_browser);

        activity = this;
//        sharedpref = new AllDownloaderSharedpref(NewBrowserActivity.this);

        img_back=findViewById(R.id.img_back);
        et_text_fb=findViewById(R.id.et_text_fb);
        lin_download=findViewById(R.id.lin_download);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, new BrowserFragment()).commit();


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void showFileExistPopUpDialog(String fileName, String filePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DhiFbBrowserActivity.this);
        builder.setTitle((CharSequence) "File Exist!");
        builder.setMessage((CharSequence) "File already exist you want to re-download it?");
        builder.setCancelable(false);
        builder.setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) null);
        builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) null);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {


            public final void onShow(DialogInterface dialogInterface) {
                alertDialog.getButton(-1).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        alertDialog.dismiss();
//                        if (Nammu.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE")) {
                        playFile(DhiFbBrowserActivity.this, filePath);
                        return;
//                        }
//                        NewBrowserActivity mainActivity = NewBrowserActivity.this;
//                        Nammu.askForPermission((NewBrowserActivity) mainActivity, "android.permission.WRITE_EXTERNAL_STORAGE", mainActivity.permissionStorageCallback);
                    }
                });
                alertDialog.getButton(-2).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

//              lambda$showFileExistPopUpDialog$13$MainActivity(this.f$1, this.f$2, dialogInterface);
            }
        });
        alertDialog.show();
    }

    public static void playFile(Context context, String mLocalUri) {
        try {
            context.startActivity(Intent.createChooser(playFileIntent(context, mLocalUri), "Play With"));
        } catch (Exception e) {
            Toast.makeText(context, "No App Found", Toast.LENGTH_LONG).show();
        }
    }


    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public static Intent playFileIntent(Context context, String mLocalUri) {
        String contain = mLocalUri;
        Uri uri = Uri.parse(mLocalUri);
        Intent intent = new Intent("android.intent.action.VIEW");
        if (contain.contains(".zip") || contain.contains(".rar")) {
            intent.setDataAndType(uri, "application/x-wav");
        } else if (contain.contains(".wav") || contain.contains(".mp3")) {
            Uri fileUri = FileProvider.getUriForFile(context, "com.browservideo.storydownloader.fileprovider", new File(String.valueOf(uri)));
            intent.setAction("android.intent.action.VIEW");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(fileUri, "audio/*");
        } else if (contain.contains(".gif")) {
            intent.setDataAndType(uri, "image/gif");
        } else if (contain.contains(".jpg") || contain.contains(".jpeg") || contain.contains(".png")) {
            intent.setDataAndType(uri, "image/jpeg");
        } else if (contain.contains(".txt")) {
            intent.setDataAndType(uri, "text/plain");
        } else if (contain.contains(".3gp") || contain.contains(".mpg") || contain.contains(".mpeg") || contain.contains(".mpe") || contain.contains(".mp4") || contain.contains(".avi")) {
            Uri fileUri2 = FileProvider.getUriForFile(context, "com.browservideo.storydownloader.fileprovider", new File(String.valueOf(uri)));
            intent.setAction("android.intent.action.VIEW");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(fileUri2, "video/mp4");
        } else {
            intent.setDataAndType(uri, "*/*");
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
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
        finish();
    }
}