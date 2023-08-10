package com.allhdvideofree.downloader.videodownloader.activities.Permission;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.selection.DhiSelectionActivity;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;

public class DhiPrStorageActivity extends DhiActivityBase {
    AppCompatTextView txt_storage, txt_next;
    private static final int PERMISSION_REQUEST_CODESTORAGE = 230;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_pr_storage);


        txt_storage = findViewById(R.id.txt_storage);
        txt_next = findViewById(R.id.txt_next);

        txt_storage.setVisibility(View.VISIBLE);
        txt_next.setVisibility(View.GONE);

        txt_storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissionstorage()) {
                    txt_next.setVisibility(View.VISIBLE);
                    txt_storage.setVisibility(View.GONE);
                } else {
                    requestPermissionstorage();
                }
            }
        });

        txt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainAds().showInterAds(DhiPrStorageActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(DhiPrStorageActivity.this, DhiSelectionActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });


            }
        });
    }

    private boolean checkPermissionstorage() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);


        return result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;


    }


    private void requestPermissionstorage() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODESTORAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {


            case PERMISSION_REQUEST_CODESTORAGE:
                if (grantResults.length > 0) {


                    boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;


                    boolean writeD = shouldShowRequestPermissionRationale(permissions[0]);
                    boolean readD = shouldShowRequestPermissionRationale(permissions[1]);


                    if (write && read) {
                        txt_next.setVisibility(View.VISIBLE);
                        txt_storage.setVisibility(View.GONE);

                    } else if (!writeD && !readD) {

                        rePermissionDialog("You need to allow access to the permissions. Without this permission you can't access your storage. Are you sure deny this permission?", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        });


                    } else {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) || shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {

                                showMessageOKCancel("You need to allow access to the permissions", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODESTORAGE);
                                        }
                                    }
                                });


                                return;
                            }


                        }

                    }
                }
                break;

        }
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

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(DhiPrStorageActivity.this).setMessage(message).setPositiveButton("OK", okListener).setNegativeButton("Cancel", null).create().show();
    }

    private void rePermissionDialog(String message, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(DhiPrStorageActivity.this).setTitle("Permission Denied").setMessage(message).setPositiveButton("Give Permission", okListener).setNegativeButton("Deny Permission", null).create().show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}