package com.allhdvideofree.downloader.videodownloader.activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.allhdvideofree.downloader.videodownloader.Encrypt.EasyAES;
import com.allhdvideofree.downloader.videodownloader.databinding.DialogNotesBinding;
import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.google.android.gms.ads.MobileAds;
import com.google.gms.ads.AdUtils;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.google.gms.ads.OpenAds;
import com.google.gson.GsonBuilder;
import com.allhdvideofree.downloader.videodownloader.activities.selection.DhiSelectionActivity;
import com.allhdvideofree.downloader.videodownloader.databinding.ActivityDhiSplashBinding;
import com.allhdvideofree.downloader.videodownloader.databinding.DialogInternetBinding;
import com.google.gson.JsonObject;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.API.RetrofitClient;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.model.DhiUpdateData;
import com.allhdvideofree.downloader.videodownloader.Utils.VPNCheckService;
import com.allhdvideofree.downloader.videodownloader.BuildConfig;
import com.allhdvideofree.downloader.videodownloader.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unified.vpn.sdk.AuthMethod;
import unified.vpn.sdk.ClientInfo;
import unified.vpn.sdk.CompletableCallback;
import unified.vpn.sdk.HydraTransport;
import unified.vpn.sdk.HydraTransportConfig;
import unified.vpn.sdk.OpenVpnTransportConfig;
import unified.vpn.sdk.SdkNotificationConfig;
import unified.vpn.sdk.SessionConfig;
import unified.vpn.sdk.TrackingConstants;
import unified.vpn.sdk.TransportConfig;
import unified.vpn.sdk.UnifiedSdk;
import unified.vpn.sdk.UnifiedSdkConfig;
import unified.vpn.sdk.User;
import unified.vpn.sdk.VpnException;

public class DhiSplashActivity extends AppCompatActivity {

    ActivityDhiSplashBinding binding;
    private static final String CHANNEL_ID = "vpn";
    int VERSION = 0;
    Dialog dialog;
    public static String referrerUrl = "NA";
    InstallReferrerClient referrerClient;
    public static Dialog dialogNote;
    String notes;


    private boolean isMyServiceRunning(Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                Log.e("ServiceStatus", "Running");
                return true;
            }
        }
        Log.e("ServiceStatus", "Not running");
        return false;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               if (PowerPreference.getDefaultFile().getBoolean(Constant.VpnOnOff, false) && PowerPreference.getDefaultFile().getBoolean(Constant.VpnDialog, false))
                    startActivity(new Intent(DhiSplashActivity.this, DhiVPNActivity.class));
                else if (PowerPreference.getDefaultFile().getBoolean(Constant.ContinousScreenOnOff))
                    startActivity(new Intent(DhiSplashActivity.this, DhiSkipActivity.class));
                else if (PowerPreference.getDefaultFile().getBoolean(Constant.StartScreenOnOff, true))
                    startActivity(new Intent(DhiSplashActivity.this, DhiStartActivity.class));
                else if (PowerPreference.getDefaultFile().getBoolean(Constant.AgeGenderScreenOnOff) && !PowerPreference.getDefaultFile().getBoolean(Constant.ageGanderChecked, false))
                    startActivity(new Intent(DhiSplashActivity.this, DhiAgeActivity.class));
                else if (PowerPreference.getDefaultFile().getBoolean(Constant.NextScreenOnOff))
                    startActivity(new Intent(DhiSplashActivity.this, DhiNextContinueActivity.class));
                else startActivity(new Intent(DhiSplashActivity.this, DhiSelectionActivity.class));
            }
        }, 2000);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDhiSplashBinding.inflate(getLayoutInflater());



        referrerClient = InstallReferrerClient.newBuilder(this).build();
        referrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        Log.d("insref", "InstallReferrer Response.OK");
                        try {
                            ReferrerDetails response = referrerClient.getInstallReferrer();
                            referrerUrl = response.getInstallReferrer();

                        } catch (RemoteException e) {
                            Log.e("insref", "" + e.getMessage());
                        }
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        Log.w("insref", "InstallReferrer Response.FEATURE_NOT_SUPPORTED");
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        Log.w("insref", "InstallReferrer Response.SERVICE_UNAVAILABLE");
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED:
                        Log.w("insref", "InstallReferrer Response.SERVICE_DISCONNECTED");
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR:
                        Log.w("insref", "InstallReferrer Response.DEVELOPER_ERROR");
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                Log.w("insref", "InstallReferrer onInstallReferrerServiceDisconnected()");

            }
        });


        PowerPreference.getDefaultFile().putBoolean("isClosed", false);
        PowerPreference.getDefaultFile().putString(AdUtils.activitySplash, DhiSplashActivity.class.getName());


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isMyServiceRunning(VPNCheckService.class))
                    startService(new Intent(DhiSplashActivity.this, VPNCheckService.class));

                PowerPreference.getDefaultFile().putBoolean("running", true);
            }
        }, 2000);

        PowerPreference.getDefaultFile().putBoolean(Constant.VPN_CONNECTED, false);

        binding = ActivityDhiSplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        startSplash();

    }


    private void startSplash() {
        PackageManager manager = getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getPackageName(), 0);
            VERSION = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Constant.Log(e.toString());
            VERSION = BuildConfig.VERSION_CODE;
        }
        callUpdateApi();
    }

    private void callUpdateApi() {
        if (Constant.checkInternet(this)) {
            @SuppressLint("HardwareIds") String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            JsonObject object = new JsonObject();
            object.addProperty("VersionCode", VERSION);
            object.addProperty("PkgName", getPackageName());
            object.addProperty("AndroidId", deviceId);
            object.addProperty("Referral", referrerUrl);
            RetrofitClient.getInstance().getMyApi().data(EasyAES.encryptString(object.toString())).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        String responseText = response.body().string();
                        final DhiUpdateData appData = new GsonBuilder().create().fromJson((EasyAES.decryptString(responseText)), DhiUpdateData.class);

                        if(appData.getDialogForAppStopOnOff()){
                            notes=appData.getDialogForAppStopText();
                            mainDialog(DhiSplashActivity.this);
                            return;
                        }
                        PowerPreference.getDefaultFile().putString(Constant.mAds, responseText);

                        PowerPreference.getDefaultFile().putString(Constant.Default_Vpn_Country, appData.getVpnDefaultCountry().getName());
                        PowerPreference.getDefaultFile().putString(Constant.Default_Country_code, appData.getVpnDefaultCountry().getCode());
                        PowerPreference.getDefaultFile().putString(Constant.Default_Vpn_img, appData.getVpnDefaultCountry().getFlag());

                        PowerPreference.getDefaultFile().putInt(Constant.VpnDialogTime, appData.getVpnDialogTime());
                        PowerPreference.getDefaultFile().putBoolean(Constant.VpnOnOff, appData.getVpnOnOff());
                        PowerPreference.getDefaultFile().putBoolean(Constant.VpnDialog, appData.getVpnDialog());

                        if (PowerPreference.getDefaultFile().getBoolean(Constant.VpnOnOff, false)) {
                            if (!PowerPreference.getDefaultFile().getBoolean(Constant.VpnDialog, true)) {
                                PowerPreference.getDefaultFile().putString(Constant.VpnCountryMain, appData.getVpnDefaultCountry().getCode());
                            }
                        }
                        PowerPreference.getDefaultFile().putBoolean(Constant.StartScreenOnOff, appData.getLetsStartScreenOnOff());
                        PowerPreference.getDefaultFile().putBoolean(Constant.ContinousScreenOnOff, appData.getContinueScreenOnOff());
                        PowerPreference.getDefaultFile().putBoolean(Constant.AgeGenderScreenOnOff, appData.getAgeGenderScreenOnOff());
                        PowerPreference.getDefaultFile().putBoolean(Constant.ExtraInnerScreenOnOff, appData.getExtraInnerScreenOnOff());
                        PowerPreference.getDefaultFile().putBoolean(Constant.NextScreenOnOff, appData.getNextScreenOnOff());




                        PowerPreference.getDefaultFile().putBoolean(AdUtils.AdsOnOff, appData.getAdsOnOff());
                        PowerPreference.getDefaultFile().putInt(AdUtils.AppOpenTime, appData.getAppOpenTime());

                        PowerPreference.getDefaultFile().putInt(AdUtils.WhichOneSplashAppOpen, appData.getWhichOneSplashAppOpen());

                        PowerPreference.getDefaultFile().putInt(AdUtils.SERVER_INTERVAL_COUNT, appData.getInterIntervalCount());
                        PowerPreference.getDefaultFile().putInt(AdUtils.APP_INTERVAL_COUNT, 0);

                        PowerPreference.getDefaultFile().putInt(AdUtils.SERVER_BACK_COUNT, appData.getBackInterIntervalCount());
                        PowerPreference.getDefaultFile().putInt(AdUtils.APP_BACK_COUNT, 0);

                        PowerPreference.getDefaultFile().putBoolean(Constant.FULL_SCREEN, appData.getFullScreenOnOff());


                        PowerPreference.getDefaultFile().putInt(AdUtils.WhichOneBannerNative, appData.getWhichOneBannerNative());
                        PowerPreference.getDefaultFile().putInt(AdUtils.WhichOneAllNative, appData.getWhichOneAllNative());
                        PowerPreference.getDefaultFile().putInt(AdUtils.WhichOneListNative, appData.getWhichOneListNative());
                        PowerPreference.getDefaultFile().putInt(AdUtils.ListNativeAfterCount, appData.getListNativeAfterCount());
                        PowerPreference.getDefaultFile().putInt(AdUtils.StaticNativeCountPerPage, appData.getStaticNativeCountPerPage());

                        PowerPreference.getDefaultFile().putBoolean(AdUtils.NativeButtonTextOnOff, appData.getNativeButtonTextOnOff());
                        PowerPreference.getDefaultFile().putString(AdUtils.NativeButtonText, appData.getNativeButtonText());

                        PowerPreference.getDefaultFile().putString(AdUtils.NativeBackgroundColor, appData.getNativeBackgroundColor());
                        PowerPreference.getDefaultFile().putString(AdUtils.NativeButtonBackgroundColor, appData.getNativeButtonBackgroundColor());
                        PowerPreference.getDefaultFile().putString(AdUtils.NativeButtonTextColor, appData.getNativeButtonTextColor());
                        PowerPreference.getDefaultFile().putString(AdUtils.NativeTextColor, appData.getNativeTextColor());

                        PowerPreference.getDefaultFile().putBoolean(AdUtils.ExitDialogNativeOnOff, appData.getExitDialogNativeOnOff());


                        PowerPreference.getDefaultFile().putBoolean(AdUtils.ShowDialogBeforeAds, appData.getShowDialogBeforeAds());
                        PowerPreference.getDefaultFile().putDouble(AdUtils.DialogTimeInSec, appData.getDialogTimeInSec());

                        PowerPreference.getDefaultFile().putString(Constant.AppBackgroundColor, appData.getAppBackgroundColor());

                        PowerPreference.getDefaultFile().putString(AdUtils.INTERID, appData.getGoogleInterAds());
                        PowerPreference.getDefaultFile().putString(AdUtils.NATIVEID, appData.getGoogleNativeAds());
                        PowerPreference.getDefaultFile().putString(AdUtils.NATIVE2ID, appData.getGoogleNative2Ads());
                        PowerPreference.getDefaultFile().putString(AdUtils.OPENAD, appData.getGoogleAppOpenAds());
                        PowerPreference.getDefaultFile().putString(AdUtils.BANNERID, appData.getGoogleBannerAds());

                        PowerPreference.getDefaultFile().putString(AdUtils.INTERID2, appData.getGoogle2InterAds());
                        PowerPreference.getDefaultFile().putString(AdUtils.NATIVEID2, appData.getGoogle2NativeAds());
                        PowerPreference.getDefaultFile().putString(AdUtils.NATIVE2ID2, appData.getGoogle2Native2Ads());
                        PowerPreference.getDefaultFile().putString(AdUtils.OPENAD2, appData.getGoogle2AppOpenAds());
                        PowerPreference.getDefaultFile().putString(AdUtils.BANNERID2, appData.getGoogle2BannerAds());

                             /*   OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

                                OneSignal.initWithContext(AslApplication.getInstance());
                                OneSignal.setAppId(PowerPreference.getDefaultFile().getString(Constant.OneSignalAppId, ""));*/

                        PowerPreference.getDefaultFile().putString(AdUtils.APPID, appData.getGoogleAppIdAds());


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            CharSequence name = "Sample VPN";
                            String description = "VPN notification";
                            int importance = NotificationManager.IMPORTANCE_DEFAULT;
                            NotificationChannel channel = new NotificationChannel("vpn", name, importance);
                            channel.setDescription(description);
                            NotificationManager notificationManager = getSystemService(NotificationManager.class);
                            notificationManager.createNotificationChannel(channel);
                        }

                        ClientInfo clientInfo = ClientInfo.newBuilder().addUrl(appData.getVpnUrl()).carrierId(appData.getVpnCarrierId()).build();

                        List<TransportConfig> transportConfigList = new ArrayList<>();
                        transportConfigList.add(HydraTransportConfig.create());
                        transportConfigList.add(OpenVpnTransportConfig.tcp());
                        transportConfigList.add(OpenVpnTransportConfig.udp());
                        UnifiedSdk.update(transportConfigList, CompletableCallback.EMPTY);

                        SdkNotificationConfig notificationConfig = SdkNotificationConfig.newBuilder().title(getResources().getString(R.string.app_name)).channelId("vpn").build();

                        UnifiedSdk.update(notificationConfig);

                        UnifiedSdkConfig config = UnifiedSdkConfig.newBuilder().build();
                        UnifiedSdk.getInstance(clientInfo, config);

                        if (!appData.getTitle().equals("")) {
                            binding.txtName.setText(appData.getTitle());
                            binding.txtName.setVisibility(View.VISIBLE);
                        }
                        if (!appData.getDescription().equals("")) {
                            binding.txtDes.setText(appData.getDescription());
                            binding.txtDes.setVisibility(View.VISIBLE);
                        }
                        if (!appData.getButtonName().equals("")) {
                            binding.btnUpdate.setText(appData.getButtonName());
                        }
                        if (!appData.getButtonSkip().equals("")) {
                            binding.btnSkip.setText(appData.getButtonSkip());
                        }
                        String flag = appData.getFlag();
                        boolean flagCheck = true;
                        if (flag.equals("NORMAL")) {
                            binding.cvUpdate.setVisibility(View.GONE);
                            flagCheck = true;
                        } else if (flag.equals("SKIP")) {
                            if (VERSION != appData.getVersion()) {
                                binding.btnUpdate.setVisibility(View.VISIBLE);
                                binding.cvUpdate.setVisibility(View.VISIBLE);
                                binding.btnSkip.setVisibility(View.VISIBLE);
                                flagCheck = false;
                            } else {
                                binding.cvUpdate.setVisibility(View.GONE);
                                flagCheck = true;
                            }
                        } else if (flag.equals("MOVE")) {
                            binding.btnUpdate.setVisibility(View.VISIBLE);
                            binding.btnSkip.setVisibility(View.GONE);
                            binding.cvUpdate.setVisibility(View.VISIBLE);
                            flagCheck = false;
                        } else if (flag.equals("FORCE")) {
                            if (VERSION != appData.getVersion()) {
                                binding.btnUpdate.setVisibility(View.VISIBLE);
                                binding.btnSkip.setVisibility(View.GONE);
                                binding.cvUpdate.setVisibility(View.VISIBLE);
                                flagCheck = false;
                            } else {
                                binding.cvUpdate.setVisibility(View.GONE);
                            }
                        }
                        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (flag.equals("MOVE")) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appData.getLink())));
                                } else {
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                                    } catch (ActivityNotFoundException e) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                                    }
                                }
                            }
                        });
                        binding.btnSkip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                binding.cvUpdate.setVisibility(View.GONE);
                                checkVpn();
                            }
                        });
                        if (flagCheck) {
                            checkVpn();
                        }
                    } catch (Exception e) {
                        Constant.Log(e.toString());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    network_dialog(t.getMessage()).btnRetry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            if (Constant.checkInternet(DhiSplashActivity.this)) {
                                callUpdateApi();
                            } else dialog.show();
                        }
                    });
                }
            });

        } else {
            network_dialog(getResources().getString(R.string.error_internet)).btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (Constant.checkInternet(DhiSplashActivity.this)) {
                        callUpdateApi();
                    } else dialog.show();
                }
            });
        }
    }

    public void checkVpn() {
        Log.e("TAG", "onAdClosed: fetchFiles ");

        if (PowerPreference.getDefaultFile().getBoolean(Constant.VpnOnOff, false)) {
            if (!PowerPreference.getDefaultFile().getBoolean(Constant.VpnDialog, false)) {
                login();
            } else if (PowerPreference.getDefaultFile().getInt(Constant.VpnDialogTime, 1) == 1 && PowerPreference.getDefaultFile().getBoolean(Constant.Vpnaccept, false)) {
                login();
            } else {

                startActivity(new Intent(DhiSplashActivity.this, DhiVPNActivity.class));
            }
        } else
            gotoSkip();
    }

    public void login() {
        AuthMethod authMethod = AuthMethod.anonymous();
        UnifiedSdk.getInstance().getBackend().login(authMethod, new unified.vpn.sdk.Callback<User>() {
            @Override
            public void success(@NonNull User user) {
                new Handler().postDelayed(() -> startVpn(), 2000);
            }

            @Override
            public void failure(@NonNull VpnException e) {
                gotoSkip();
            }
        });
    }

    public void startVpn() {

        List<String> fallbackOrder = new ArrayList<>();
        fallbackOrder.add(HydraTransport.TRANSPORT_ID);
        fallbackOrder.add(OpenVpnTransportConfig.tcp().getName());
        fallbackOrder.add(OpenVpnTransportConfig.udp().getName());

        UnifiedSdk.getInstance().getVpn().start(new SessionConfig.Builder().withReason(TrackingConstants.GprReasons.M_UI).withTransportFallback(fallbackOrder).withTransport(HydraTransport.TRANSPORT_ID).withVirtualLocation(PowerPreference.getDefaultFile().getString(Constant.VpnCountryMain, PowerPreference.getDefaultFile().getString(Constant.Default_Country_code, "us"))).build(), new CompletableCallback() {
            @Override
            public void complete() {
                PowerPreference.getDefaultFile().putBoolean(Constant.VPN_CONNECTED, true);
                gotoSkip();
            }

            @Override
            public void error(@NonNull VpnException e) {
                gotoSkip();
            }
        });
    }

    public void gotoSkip() {
        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.AdsOnOff, false)) {
            try {
                ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
                ai.metaData.putString("com.google.android.gms.ads.APPLICATION_ID", PowerPreference.getDefaultFile().getString(AdUtils.APPID, "ca-app-pub-3940256099942544~3347511713"));
            } catch (PackageManager.NameNotFoundException e) {
                AdUtils.showLog("Failed to load meta-data, NameNotFound: " + e.getMessage());
            } catch (NullPointerException e) {
                AdUtils.showLog("Failed to load meta-data, NullPointer: " + e.getMessage());
            }

            MobileAds.initialize(DhiSplashActivity.this, PowerPreference.getDefaultFile().getString(AdUtils.APPID, "ca-app-pub-3940256099942544~3347511713"));

            new MainAds().loadAds(DhiSplashActivity.this);

        }

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PowerPreference.getDefaultFile().getInt(AdUtils.WhichOneSplashAppOpen, 0) == 1) {
                    new MainAds().showSplashInterAds(DhiSplashActivity.this, new InterAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            goNext();
                        }
                    });
                } else if (PowerPreference.getDefaultFile().getInt(AdUtils.WhichOneSplashAppOpen, 0) == 2) {
                    new MainAds().showOpenAds(DhiSplashActivity.this, new OpenAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            goNext();
                        }
                    });
                } else {
                    goNext();
                }
            }
        }, 4000);
    }

    public void goNext() {


        if (PowerPreference.getDefaultFile().getBoolean(Constant.ContinousScreenOnOff, false))
            startActivity(new Intent(DhiSplashActivity.this, DhiSkipActivity.class));
        else if (PowerPreference.getDefaultFile().getBoolean(Constant.StartScreenOnOff, false))
            startActivity(new Intent(DhiSplashActivity.this, DhiStartActivity.class));
        else if (PowerPreference.getDefaultFile().getBoolean(Constant.AgeGenderScreenOnOff, false) && !PowerPreference.getDefaultFile().getBoolean(Constant.ageGanderChecked, false))
            startActivity(new Intent(DhiSplashActivity.this, DhiAgeActivity.class));
        else if (PowerPreference.getDefaultFile().getBoolean(Constant.NextScreenOnOff))
            startActivity(new Intent(DhiSplashActivity.this, DhiNextContinueActivity.class));
        else startActivity(new Intent(DhiSplashActivity.this, DhiSelectionActivity.class));

    }

    public DialogInternetBinding network_dialog(String text) {
        dialog = new Dialog(this);
        DialogInternetBinding binding = DialogInternetBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding.getRoot());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        binding.txtError.setText(text);
        return binding;
    }


    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    protected void onDestroy() {
        super.onDestroy();
        Constant.stopVpn();
    }

    public void mainDialog(Activity activity){
        dialogNote = new Dialog(activity);
        DialogNotesBinding binding = DialogNotesBinding.inflate(activity.getLayoutInflater());
        dialogNote.setContentView(binding.getRoot());
        dialogNote.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogNote.setCancelable(false);
        dialogNote.setCanceledOnTouchOutside(false);
        dialogNote.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        binding.txtError.setText(notes);
        dialogNote.show();

    }
}
