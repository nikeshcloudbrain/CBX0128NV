package com.allhdvideofree.downloader.videodownloader.activities;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.allhdvideofree.downloader.videodownloader.Encrypt.EasyAES;
import com.allhdvideofree.downloader.videodownloader.adapter.VPNAdapter;
import com.allhdvideofree.downloader.videodownloader.databinding.ActivityDhiVpnBinding;
import com.allhdvideofree.downloader.videodownloader.model.VpnListCountry;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.allhdvideofree.downloader.videodownloader.activities.selection.DhiSelectionActivity;
import com.google.android.gms.ads.MobileAds;
import com.google.gms.ads.AdUtils;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.google.gms.ads.OpenAds;
import com.google.gson.GsonBuilder;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.model.DhiUpdateData;
import com.allhdvideofree.downloader.videodownloader.R;

import java.util.ArrayList;
import java.util.List;

import unified.vpn.sdk.AuthMethod;
import unified.vpn.sdk.Callback;
import unified.vpn.sdk.CompletableCallback;
import unified.vpn.sdk.HydraTransport;
import unified.vpn.sdk.OpenVpnTransportConfig;
import unified.vpn.sdk.ProcessUtils;
import unified.vpn.sdk.SessionConfig;
import unified.vpn.sdk.TrackingConstants;
import unified.vpn.sdk.UnifiedSdk;
import unified.vpn.sdk.User;
import unified.vpn.sdk.VpnException;
import unified.vpn.sdk.VpnState;

public class DhiVPNActivity extends DhiActivityBase {

    ActivityDhiVpnBinding binding;
    VPNAdapter adapterVpn;

    List<VpnListCountry> arrayList = new ArrayList<>();

    String currentVPN = "US";

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        updateUI();
    }

    @Override
    public void onBackPressed() {
        Constant.showRateDialog(this, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDhiVpnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String data = PowerPreference.getDefaultFile().getString(Constant.mAds, "");

        final DhiUpdateData appData = new GsonBuilder().create().fromJson((EasyAES.decryptString(data)), DhiUpdateData.class);
        this.arrayList.addAll(appData.getVpnListCountry());

        Glide.with(this).load(PowerPreference.getDefaultFile().getString(Constant.Default_Vpn_img)).diskCacheStrategy(DiskCacheStrategy.ALL).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(binding.flag);

        binding.vpnCountryName.setText(PowerPreference.getDefaultFile().getString(Constant.Default_Vpn_Country));

        currentVPN = PowerPreference.getDefaultFile().getString(Constant.VpnCountryMain, PowerPreference.getDefaultFile().getString(Constant.Default_Country_code, "us"));

        binding.btnConnect1.setOnClickListener(v -> {
            binding.vpnStatus.setText("Connecting..");
            binding.vpnStatus.setTextColor(ContextCompat.getColor(DhiVPNActivity.this, R.color.blue));
            PowerPreference.getDefaultFile().putString(Constant.VpnCountryMain, currentVPN);
            binding.layLoader.setVisibility(View.VISIBLE);
            login();
        });


        binding.selectCountry.setLayoutManager(new LinearLayoutManager(this));
        adapterVpn = new VPNAdapter(this, arrayList, new VPNAdapter.ItemClickListener() {
            @Override
            public void onClick(String s) {
                binding.selectCountry.post(new Runnable() {
                    @Override
                    public void run() {
                        adapterVpn.notifyDataSetChanged();
                    }
                });
                currentVPN = s;
            }
        });
        binding.selectCountry.setAdapter(adapterVpn);
        binding.vcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layLoader.setVisibility(View.VISIBLE);
                gotoSkip();
            }
        });
    }

    public void login() {
        AuthMethod authMethod = AuthMethod.anonymous();
        UnifiedSdk.getInstance().getBackend().login(authMethod, new Callback<User>() {
            @Override
            public void success(@NonNull User user) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startVpn();
                    }
                }, 2000);
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

        UnifiedSdk.getInstance().getVpn().start(new SessionConfig.Builder().withReason(TrackingConstants.GprReasons.M_UI).withTransportFallback(fallbackOrder).withTransport(HydraTransport.TRANSPORT_ID).withVirtualLocation(PowerPreference.getDefaultFile().getString(Constant.VpnCountryMain, currentVPN)).build(), new CompletableCallback() {
            @Override
            public void complete() {

                PowerPreference.getDefaultFile().putBoolean(Constant.VPN_CONNECTED, true);
                binding.btnConnect1.setImageResource(R.drawable.vp_connected);

                binding.vpnStatus.setText("Connected");
                binding.vpnStatus.setTextColor(ContextCompat.getColor(DhiVPNActivity.this, R.color.connect_vpn));
                binding.layLoader.setVisibility(View.VISIBLE);
                gotoSkip();

                Toast.makeText(DhiVPNActivity.this, "VPN Connected Successfully. You can continue to application", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void error(@NonNull VpnException e) {
                gotoSkip();
            }

        });
    }

    public void gotoSkip() {
        if (PowerPreference.getDefaultFile().getInt(Constant.VpnDialogTime, 0) == 1) {
            PowerPreference.getDefaultFile().putBoolean(Constant.Vpnaccept, true);
        }

        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.AdsOnOff, false)) {
            if (ProcessUtils.isMainProcess(DhiVPNActivity.this)) {
                try {
                    ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
                    ai.metaData.putString("com.google.android.gms.ads.APPLICATION_ID", PowerPreference.getDefaultFile().getString(AdUtils.APPID, "ca-app-pub-3940256099942544~3347511713"));
                } catch (PackageManager.NameNotFoundException e) {
                    AdUtils.showLog("Failed to load meta-data, NameNotFound: " + e.getMessage());
                } catch (NullPointerException e) {
                    AdUtils.showLog("Failed to load meta-data, NullPointer: " + e.getMessage());
                }
                MobileAds.initialize(DhiVPNActivity.this, PowerPreference.getDefaultFile().getString(AdUtils.APPID, "ca-app-pub-3940256099942544~3347511713"));
            }
            new MainAds().loadAds(DhiVPNActivity.this);
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            binding.layLoader.setVisibility(View.GONE);
            if (PowerPreference.getDefaultFile().getInt(AdUtils.WhichOneSplashAppOpen, 0) == 1) {
                new MainAds().showSplashInterAds(this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        goNext();
                    }
                });
            } else if (PowerPreference.getDefaultFile().getInt(AdUtils.WhichOneSplashAppOpen, 0) == 2) {
                new MainAds().showOpenAds(this, new OpenAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        goNext();
                    }
                });
            } else {
                goNext();
            }

        }, 4000);
    }

    public void goNext() {
//        if (PowerPreference.getDefaultFile().getBoolean(Constant.CustomAdsOnOff, false))
//            startActivity(new Intent(VPNActivity.this, CustomAdsActivity.class));
        if (PowerPreference.getDefaultFile().getBoolean(Constant.ContinousScreenOnOff))
            startActivity(new Intent(DhiVPNActivity.this, DhiSkipActivity.class));
        else if (PowerPreference.getDefaultFile().getBoolean(Constant.StartScreenOnOff))
            startActivity(new Intent(DhiVPNActivity.this, DhiStartActivity.class));
        else if (PowerPreference.getDefaultFile().getBoolean(Constant.AgeGenderScreenOnOff) && !PowerPreference.getDefaultFile().getBoolean(Constant.ageGanderChecked, false))
            startActivity(new Intent(DhiVPNActivity.this, DhiAgeActivity.class));
        else if (PowerPreference.getDefaultFile().getBoolean(Constant.NextScreenOnOff))
            startActivity(new Intent(DhiVPNActivity.this, DhiNextContinueActivity.class));
        else
            startActivity(new Intent(DhiVPNActivity.this, DhiSelectionActivity.class));
    }

    public void updateUI() {
        UnifiedSdk.getVpnState(new Callback<VpnState>() {
            @Override
            public void success(@NonNull VpnState vpnState) {
                if (vpnState == VpnState.CONNECTED) {
                    binding.btnConnect1.setImageResource(R.drawable.vp_connected);
                    binding.vpnStatus.setText("Connected");
                    binding.vpnStatus.setTextColor(ContextCompat.getColor(DhiVPNActivity.this, R.color.connect_vpn));
                    binding.vcontinue.setVisibility(View.VISIBLE);

                } else {
                    binding.btnConnect1.setImageResource(R.drawable.vp_connectedn);
                    binding.vpnStatus.setText("Not Connected");
                    binding.vpnStatus.setTextColor(ContextCompat.getColor(DhiVPNActivity.this, R.color.teal_200));
                    binding.vcontinue.setVisibility(View.GONE);
                }
            }

            @Override
            public void failure(@NonNull VpnException e) {
                binding.btnConnect1.setImageResource(R.drawable.vp_connectedn);
                binding.vpnStatus.setText("Not Connected");
                binding.vpnStatus.setTextColor(ContextCompat.getColor(DhiVPNActivity.this, R.color.teal_200));
                binding.vcontinue.setVisibility(View.GONE);
            }
        });
    }

}