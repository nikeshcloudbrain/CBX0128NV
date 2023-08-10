package com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader;

import static com.allhdvideofree.downloader.videodownloader.Utils.Constant.setToolBar;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.FrameLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.gms.ads.AdUtils;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.allhdvideofree.downloader.videodownloader.activities.ExtraScreen.DhiChingariIntro;
import com.allhdvideofree.downloader.videodownloader.activities.ExtraScreen.DhiFacebookIntro;
import com.allhdvideofree.downloader.videodownloader.activities.ExtraScreen.DhiInstaIntro;
import com.allhdvideofree.downloader.videodownloader.activities.ExtraScreen.DhiSharechatIntro;
import com.allhdvideofree.downloader.videodownloader.activities.ExtraScreen.DhiTwitterIntro;
import com.allhdvideofree.downloader.videodownloader.activities.ExtraScreen.DhiWhatsupIntro;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.selection.DhiFBSelectionActivity;
import com.allhdvideofree.downloader.videodownloader.activities.selection.DhiInstaSelectionActivity;
import com.allhdvideofree.downloader.videodownloader.activities.selection.DhiWtsSelectionActivity;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;
import com.allhdvideofree.downloader.videodownloader.Utils.ClipboardListener;
import com.allhdvideofree.downloader.videodownloader.Utils.MyUtils;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;

public class DhiAllStoryActivity extends DhiActivityBase {

    FrameLayout lin_insta, lin_fb, lin_wtsup, lin_twitter, lin_sharechat, lin_chingari;
    public ClipboardManager clipBoard;
    String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    String CopyKey = "";
    String CopyValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_all_story);


        lin_insta = findViewById(R.id.lin_insta);
        lin_fb = findViewById(R.id.lin_fb);
        lin_wtsup = findViewById(R.id.lin_wtsup);
        lin_twitter = findViewById(R.id.lin_twitter);
        lin_sharechat = findViewById(R.id.lin_sharechat);
        lin_chingari = findViewById(R.id.lin_chingari);


        setToolBar(DhiAllStoryActivity.this, "All Video Downloader");

        initViews();
    }


    public void initViews() {
        this.clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (getIntent().getExtras() != null) {
            for (String str : getIntent().getExtras().keySet()) {
                this.CopyKey = str;
                String string = getIntent().getExtras().getString(this.CopyKey);
                if (this.CopyKey.equals("android.intent.extra.TEXT")) {
                    String string2 = getIntent().getExtras().getString(this.CopyKey);
                    this.CopyValue = string2;
                    this.CopyValue = extractLinks(string2);
                    callText(string);
                } else {
                    this.CopyValue = "";
                    callText(string);
                }
            }
        }
        ClipboardManager clipboardManager = this.clipBoard;
        if (clipboardManager != null) {
            clipboardManager.addPrimaryClipChangedListener(new ClipboardListener() {
                public void onPrimaryClipChanged() {
                    try {
                        CharSequence text = clipBoard.getPrimaryClip().getItemAt(0).getText();
                        Objects.requireNonNull(text);
                        showNotification(text.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions(0);
        }
        lin_insta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new MainAds().showInterAds(DhiAllStoryActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        if (Build.VERSION.SDK_INT >= 23) {
                            checkPermissions(101);
                        } else {
                            callInstaActivity();
                        }

                    }
                });


            }
        });
        lin_wtsup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                new MainAds().showInterAds(DhiAllStoryActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        if (Build.VERSION.SDK_INT >= 23) {
                            checkPermissions(102);
                        } else {
                            callWhatsappActivity();
                        }

                    }
                });


            }
        });
        lin_fb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                new MainAds().showInterAds(DhiAllStoryActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        if (Build.VERSION.SDK_INT >= 23) {
                            checkPermissions(104);
                        } else {
                            callFacebookActivity();
                        }

                    }
                });


            }
        });
        lin_twitter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                new MainAds().showInterAds(DhiAllStoryActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        if (Build.VERSION.SDK_INT >= 23) {
                            checkPermissions(106);
                        } else {
                            callTwitterActivity();
                        }

                    }
                });


            }
        });

        lin_sharechat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                new MainAds().showInterAds(DhiAllStoryActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        if (Build.VERSION.SDK_INT >= 23) {
                            checkPermissions(107);
                        } else {
                            callShareChatActivity();
                        }

                    }
                });


            }
        });

        lin_chingari.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                new MainAds().showInterAds(DhiAllStoryActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        if (Build.VERSION.SDK_INT >= 23) {
                            checkPermissions(111);
                        } else {
                            callChingariActivity();
                        }

                    }
                });


            }
        });
        MyUtils.createFileFolder();
    }

    private void callText(String str) {
        try {
            if (!str.contains("instagram.com")) {
                if (!str.contains("facebook.com") && !str.contains("fb")) {
                    if (str.contains("twitter.com")) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            checkPermissions(106);
                            return;
                        } else {
                            callTwitterActivity();
                            return;
                        }
                    } else if (str.contains("sharechat")) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            checkPermissions(107);
                            return;
                        } else {
                            callShareChatActivity();
                            return;
                        }
                    } else if (!str.contains("roposo")) {
                        if (!str.contains("snackvideo") && !str.contains("sck.io")) {
                            if (str.contains("josh")) {
                                if (Build.VERSION.SDK_INT >= 23) {
                                    checkPermissions(110);
                                    return;
                                } else {
                                    callJoshActivity();
                                    return;
                                }
                            } else if (str.contains("chingari")) {
                                if (Build.VERSION.SDK_INT >= 23) {
                                    checkPermissions(111);
                                    return;
                                } else {
                                    callChingariActivity();
                                    return;
                                }
                            } else if (str.contains("mitron")) {
                                if (Build.VERSION.SDK_INT >= 23) {
                                    checkPermissions(112);
                                    return;
                                } else {
                                    callMitronActivity();
                                    return;
                                }
                            } else if (!str.contains("moj")) {
                                return;
                            }
                        }
                    } else if (Build.VERSION.SDK_INT >= 23) {
                        checkPermissions(108);
                        return;
                    } else {
//                        callRoposoActivity();
                        return;
                    }
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    checkPermissions(104);
                } else {
                    callFacebookActivity();
                }
            } else if (Build.VERSION.SDK_INT >= 23) {
                checkPermissions(101);
            } else {
                callInstaActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        new MainAds().showNativeAds(this, null, null, AdUtils.AD_LARGE);
        new MainAds().showBannerAds(this, null);
    }

    public boolean checkPermissions(int i) {
        ArrayList arrayList = new ArrayList();
        for (String str : this.permissions) {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                arrayList.add(str);
            }
        }
        if (!arrayList.isEmpty()) {
            ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[arrayList.size()]), i);
            return false;
        } else if (i == 101) {
            callInstaActivity();
            return true;
        } else if (i == 102) {
            callWhatsappActivity();
            return true;
        } else if (i == 104) {
            callFacebookActivity();
            return true;
        } else if (i == 105) {
//            callGalleryActivity();
            return true;
        } else if (i == 106) {
            callTwitterActivity();
            return true;
        } else if (i == 107) {
            callShareChatActivity();
            return true;
        } else if (i == 108) {
//            callRoposoActivity();
            return true;
        } else if (i == 110) {
            callJoshActivity();
            return true;
        } else if (i == 111) {
            callChingariActivity();
            return true;
        } else {
            if (i == 112) {
                callMitronActivity();
            }
            return true;
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 101) {
            if (iArr.length > 0 && iArr[0] == 0) {
                callInstaActivity();
            }
        } else if (i == 102) {
            if (iArr.length > 0 && iArr[0] == 0) {
                callWhatsappActivity();
            }
        } else if (i == 104) {
            if (iArr.length > 0 && iArr[0] == 0) {
                callFacebookActivity();
            }
        } else if (i == 105) {
            if (iArr.length > 0 && iArr[0] == 0) {
//                callGalleryActivity();
            }
        } else if (i == 106) {
            if (iArr.length > 0 && iArr[0] == 0) {
                callTwitterActivity();
            }
        } else if (i == 107) {
            if (iArr.length > 0 && iArr[0] == 0) {
                callShareChatActivity();
            }
        } else if (i == 108) {
            if (iArr.length > 0 && iArr[0] == 0) {
//                callRoposoActivity();
            }
        } else if (i == 110) {
            if (iArr.length > 0 && iArr[0] == 0) {
                callJoshActivity();
            }
        } else if (i == 111) {
            if (iArr.length > 0 && iArr[0] == 0) {
                callChingariActivity();
            }
        } else if (i == 112 && iArr.length > 0 && iArr[0] == 0) {
            callMitronActivity();
        }
    }

    public static String extractLinks(String str) {
        Matcher matcher = Patterns.WEB_URL.matcher(str);
        if (!matcher.find()) {
            return "";
        }
        String group = matcher.group();
//        Log.d("New URL", "URL extracted: " + group);
        return group;
    }

    public void callJoshActivity() {
//        Intent intent = new Intent(this, JoshActivity.class);
//        intent.putExtra("CopyIntent", this.CopyValue);
//        startActivity(intent);
    }

    public void callChingariActivity() {

        if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff,false)) {
            Intent intent = new Intent(this, DhiChingariIntro.class);

            startActivity(intent);
        } else {
            startActivity(new Intent(this, DhiChingariActivity.class).putExtra("CopyIntent", this.CopyValue));


        }
    }

    public void callMitronActivity() {

//        Intent intent = new Intent(this, MitronActivity.class);
//        intent.putExtra("CopyIntent", this.CopyValue);
//        startActivity(intent);
    }

    public void callInstaActivity() {
        if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff,false)) {
            Intent intent = new Intent(this, DhiInstaIntro.class);
            startActivity(intent);

        } else {
            startActivity(new Intent(this, DhiInstaSelectionActivity.class));


        }
    }

    public void callWhatsappActivity() {

        if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff,false)) {
            startActivity(new Intent(this, DhiWhatsupIntro.class));
        } else {
            startActivity(new Intent(this, DhiWtsSelectionActivity.class));

        }
    }

    public void callFacebookActivity() {
        if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff,false)) {
            Intent intent = new Intent(this, DhiFacebookIntro.class);
            startActivity(intent);
        } else {
            startActivity(new Intent(this, DhiFBSelectionActivity.class));


        }

    }

    public void callTwitterActivity() {

        if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff,false)) {
            Intent intent = new Intent(this, DhiTwitterIntro.class);
            startActivity(intent);

        } else {
            startActivity(new Intent(this, DhiTwitterActivity.class).putExtra("CopyIntent", this.CopyValue));


        }
    }


    public void callShareChatActivity() {

        if (PowerPreference.getDefaultFile().getBoolean(Constant.ExtraInnerScreenOnOff,false)) {
            Intent intent = new Intent(this, DhiSharechatIntro.class);
            startActivity(intent);

        } else {
            startActivity(new Intent(this, DhiShareChatActivity.class).putExtra("CopyIntent", this.CopyValue));


        }
    }

    public void showNotification(String str) {
        if (str.contains("instagram.com") || str.contains("facebook.com") || str.contains("fb") || str.contains("tiktok.com") || str.contains("twitter.com") || str.contains("likee") || str.contains("sharechat") || str.contains("roposo") || str.contains("snackvideo") || str.contains("sck.io") || str.contains("chingari") || str.contains("myjosh") || str.contains("mitron")) {
            Intent intent = new Intent(this, DhiAllStoryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Notification", str);
            PendingIntent activity = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            NotificationManager notificationManager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            }
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel notificationChannel = new NotificationChannel(getResources().getString(R.string.app_name), getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.setLockscreenVisibility(1);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            notificationManager.notify(1, new NotificationCompat.Builder((Context) this, getResources().getString(R.string.app_name)).setAutoCancel(true).setSmallIcon((int) R.drawable.icon).setColor(getResources().getColor(R.color.black)).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon)).setDefaults(-1).setPriority(1).setContentTitle("Copied text").setContentText(str).setChannelId(getResources().getString(R.string.app_name)).setFullScreenIntent(activity, true).build());
        }
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