package com.allhdvideofree.downloader.videodownloader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DhiUpdateData {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("buttonName")
    @Expose
    private String buttonName;
    @SerializedName("buttonSkip")
    @Expose
    private String buttonSkip;
    @SerializedName("GoogleBannerAds")
    @Expose
    private String googleBannerAds;
    @SerializedName("GoogleInterAds")
    @Expose
    private String googleInterAds;
    @SerializedName("GoogleNativeAds")
    @Expose
    private String googleNativeAds;
    @SerializedName("GoogleNative2Ads")
    @Expose
    private String googleNative2Ads;
    @SerializedName("GoogleAppOpenAds")
    @Expose
    private String googleAppOpenAds;
    @SerializedName("GoogleAppIdAds")
    @Expose
    private String googleAppIdAds;
    @SerializedName("Google2BannerAds")
    @Expose
    private String google2BannerAds;
    @SerializedName("Google2InterAds")
    @Expose
    private String google2InterAds;
    @SerializedName("Google2NativeAds")
    @Expose
    private String google2NativeAds;
    @SerializedName("Google2Native2Ads")
    @Expose
    private String google2Native2Ads;
    @SerializedName("Google2AppOpenAds")
    @Expose
    private String google2AppOpenAds;
    @SerializedName("Google2AppIdAds")
    @Expose
    private String google2AppIdAds;
    @SerializedName("WhichOneBannerNative")
    @Expose
    private Integer whichOneBannerNative;
    @SerializedName("WhichOneAllNative")
    @Expose
    private Integer whichOneAllNative;
    @SerializedName("WhichOneListNative")
    @Expose
    private Integer whichOneListNative;
    @SerializedName("ListNativeAfterCount")
    @Expose
    private Integer listNativeAfterCount;
    @SerializedName("StaticNativeCountPerPage")
    @Expose
    private Integer staticNativeCountPerPage;
    @SerializedName("InterIntervalCount")
    @Expose
    private Integer interIntervalCount;
    @SerializedName("BackInterIntervalCount")
    @Expose
    private Integer backInterIntervalCount;
    @SerializedName("WhichOneSplashAppOpen")
    @Expose
    private Integer whichOneSplashAppOpen;
    @SerializedName("NativeButtonTextOnOff")
    @Expose
    private Boolean nativeButtonTextOnOff;
    @SerializedName("NativeButtonText")
    @Expose
    private String nativeButtonText;
    @SerializedName("ExitDialogNativeOnOff")
    @Expose
    private Boolean exitDialogNativeOnOff;
    @SerializedName("AppOpenTime")
    @Expose
    private Integer appOpenTime;
    @SerializedName("NativeBackgroundColor")
    @Expose
    private String nativeBackgroundColor;
    @SerializedName("NativeTextColor")
    @Expose
    private String nativeTextColor;
    @SerializedName("NativeButtonBackgroundColor")
    @Expose
    private String nativeButtonBackgroundColor;
    @SerializedName("NativeButtonTextColor")
    @Expose
    private String nativeButtonTextColor;
    @SerializedName("ShowDialogBeforeAds")
    @Expose
    private Boolean showDialogBeforeAds;
    @SerializedName("DialogTimeInSec")
    @Expose
    private Double dialogTimeInSec;
    @SerializedName("AppBackgroundColor")
    @Expose
    private String appBackgroundColor;
    @SerializedName("AdsOnOff")
    @Expose
    private Boolean adsOnOff;
    @SerializedName("FullScreenOnOff")
    @Expose
    private Boolean fullScreenOnOff;
    @SerializedName("ContinueScreenOnOff")
    @Expose
    private Boolean continueScreenOnOff;
    @SerializedName("LetsStartScreenOnOff")
    @Expose
    private Boolean letsStartScreenOnOff;
    @SerializedName("AgeGenderScreenOnOff")
    @Expose
    private Boolean ageGenderScreenOnOff;
    @SerializedName("NextScreenOnOff")
    @Expose
    private Boolean nextScreenOnOff;
    @SerializedName("ExtraInnerScreenOnOff")
    @Expose
    private Boolean extraInnerScreenOnOff;
    @SerializedName("VpnOnOff")
    @Expose
    private Boolean vpnOnOff;
    @SerializedName("VpnDialog")
    @Expose
    private Boolean vpnDialog;
    @SerializedName("VpnDialogTime")
    @Expose
    private Integer vpnDialogTime;
    @SerializedName("VpnDefaultCountry")
    @Expose
    private VpnDefaultCountry vpnDefaultCountry;
    @SerializedName("VpnListCountry")
    @Expose
    private List<VpnListCountry> vpnListCountry;
    @SerializedName("VpnUrl")
    @Expose
    private String vpnUrl;
    @SerializedName("VpnCarrierId")
    @Expose
    private String vpnCarrierId;
    @SerializedName("DialogForAppStopText")
    @Expose
    private String dialogForAppStopText;
    @SerializedName("DialogForAppStopOnOff")
    @Expose
    private Boolean dialogForAppStopOnOff;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonSkip() {
        return buttonSkip;
    }

    public void setButtonSkip(String buttonSkip) {
        this.buttonSkip = buttonSkip;
    }

    public String getGoogleBannerAds() {
        return googleBannerAds;
    }

    public void setGoogleBannerAds(String googleBannerAds) {
        this.googleBannerAds = googleBannerAds;
    }

    public String getGoogleInterAds() {
        return googleInterAds;
    }

    public void setGoogleInterAds(String googleInterAds) {
        this.googleInterAds = googleInterAds;
    }

    public String getGoogleNativeAds() {
        return googleNativeAds;
    }

    public void setGoogleNativeAds(String googleNativeAds) {
        this.googleNativeAds = googleNativeAds;
    }

    public String getGoogleNative2Ads() {
        return googleNative2Ads;
    }

    public void setGoogleNative2Ads(String googleNative2Ads) {
        this.googleNative2Ads = googleNative2Ads;
    }

    public String getGoogleAppOpenAds() {
        return googleAppOpenAds;
    }

    public void setGoogleAppOpenAds(String googleAppOpenAds) {
        this.googleAppOpenAds = googleAppOpenAds;
    }

    public String getGoogleAppIdAds() {
        return googleAppIdAds;
    }

    public void setGoogleAppIdAds(String googleAppIdAds) {
        this.googleAppIdAds = googleAppIdAds;
    }

    public String getGoogle2BannerAds() {
        return google2BannerAds;
    }

    public void setGoogle2BannerAds(String google2BannerAds) {
        this.google2BannerAds = google2BannerAds;
    }

    public String getGoogle2InterAds() {
        return google2InterAds;
    }

    public void setGoogle2InterAds(String google2InterAds) {
        this.google2InterAds = google2InterAds;
    }

    public String getGoogle2NativeAds() {
        return google2NativeAds;
    }

    public void setGoogle2NativeAds(String google2NativeAds) {
        this.google2NativeAds = google2NativeAds;
    }

    public String getGoogle2Native2Ads() {
        return google2Native2Ads;
    }

    public void setGoogle2Native2Ads(String google2Native2Ads) {
        this.google2Native2Ads = google2Native2Ads;
    }

    public String getGoogle2AppOpenAds() {
        return google2AppOpenAds;
    }

    public void setGoogle2AppOpenAds(String google2AppOpenAds) {
        this.google2AppOpenAds = google2AppOpenAds;
    }

    public String getGoogle2AppIdAds() {
        return google2AppIdAds;
    }

    public void setGoogle2AppIdAds(String google2AppIdAds) {
        this.google2AppIdAds = google2AppIdAds;
    }

    public Integer getWhichOneBannerNative() {
        return whichOneBannerNative;
    }

    public void setWhichOneBannerNative(Integer whichOneBannerNative) {
        this.whichOneBannerNative = whichOneBannerNative;
    }

    public Integer getWhichOneAllNative() {
        return whichOneAllNative;
    }

    public void setWhichOneAllNative(Integer whichOneAllNative) {
        this.whichOneAllNative = whichOneAllNative;
    }

    public Integer getWhichOneListNative() {
        return whichOneListNative;
    }

    public void setWhichOneListNative(Integer whichOneListNative) {
        this.whichOneListNative = whichOneListNative;
    }

    public Integer getListNativeAfterCount() {
        return listNativeAfterCount;
    }

    public void setListNativeAfterCount(Integer listNativeAfterCount) {
        this.listNativeAfterCount = listNativeAfterCount;
    }

    public Integer getStaticNativeCountPerPage() {
        return staticNativeCountPerPage;
    }

    public void setStaticNativeCountPerPage(Integer staticNativeCountPerPage) {
        this.staticNativeCountPerPage = staticNativeCountPerPage;
    }

    public Integer getInterIntervalCount() {
        return interIntervalCount;
    }

    public void setInterIntervalCount(Integer interIntervalCount) {
        this.interIntervalCount = interIntervalCount;
    }

    public Integer getBackInterIntervalCount() {
        return backInterIntervalCount;
    }

    public void setBackInterIntervalCount(Integer backInterIntervalCount) {
        this.backInterIntervalCount = backInterIntervalCount;
    }

    public Integer getWhichOneSplashAppOpen() {
        return whichOneSplashAppOpen;
    }

    public void setWhichOneSplashAppOpen(Integer whichOneSplashAppOpen) {
        this.whichOneSplashAppOpen = whichOneSplashAppOpen;
    }

    public Boolean getNativeButtonTextOnOff() {
        return nativeButtonTextOnOff;
    }

    public void setNativeButtonTextOnOff(Boolean nativeButtonTextOnOff) {
        this.nativeButtonTextOnOff = nativeButtonTextOnOff;
    }

    public String getNativeButtonText() {
        return nativeButtonText;
    }

    public void setNativeButtonText(String nativeButtonText) {
        this.nativeButtonText = nativeButtonText;
    }

    public Boolean getExitDialogNativeOnOff() {
        return exitDialogNativeOnOff;
    }

    public void setExitDialogNativeOnOff(Boolean exitDialogNativeOnOff) {
        this.exitDialogNativeOnOff = exitDialogNativeOnOff;
    }

    public Integer getAppOpenTime() {
        return appOpenTime;
    }

    public void setAppOpenTime(Integer appOpenTime) {
        this.appOpenTime = appOpenTime;
    }

    public String getNativeBackgroundColor() {
        return nativeBackgroundColor;
    }

    public void setNativeBackgroundColor(String nativeBackgroundColor) {
        this.nativeBackgroundColor = nativeBackgroundColor;
    }

    public String getNativeTextColor() {
        return nativeTextColor;
    }

    public void setNativeTextColor(String nativeTextColor) {
        this.nativeTextColor = nativeTextColor;
    }

    public String getNativeButtonBackgroundColor() {
        return nativeButtonBackgroundColor;
    }

    public void setNativeButtonBackgroundColor(String nativeButtonBackgroundColor) {
        this.nativeButtonBackgroundColor = nativeButtonBackgroundColor;
    }

    public String getNativeButtonTextColor() {
        return nativeButtonTextColor;
    }

    public void setNativeButtonTextColor(String nativeButtonTextColor) {
        this.nativeButtonTextColor = nativeButtonTextColor;
    }

    public Boolean getShowDialogBeforeAds() {
        return showDialogBeforeAds;
    }

    public void setShowDialogBeforeAds(Boolean showDialogBeforeAds) {
        this.showDialogBeforeAds = showDialogBeforeAds;
    }

    public Double getDialogTimeInSec() {
        return dialogTimeInSec;
    }

    public void setDialogTimeInSec(Double dialogTimeInSec) {
        this.dialogTimeInSec = dialogTimeInSec;
    }

    public String getAppBackgroundColor() {
        return appBackgroundColor;
    }

    public void setAppBackgroundColor(String appBackgroundColor) {
        this.appBackgroundColor = appBackgroundColor;
    }

    public Boolean getAdsOnOff() {
        return adsOnOff;
    }

    public void setAdsOnOff(Boolean adsOnOff) {
        this.adsOnOff = adsOnOff;
    }

    public Boolean getFullScreenOnOff() {
        return fullScreenOnOff;
    }

    public void setFullScreenOnOff(Boolean fullScreenOnOff) {
        this.fullScreenOnOff = fullScreenOnOff;
    }

    public Boolean getContinueScreenOnOff() {
        return continueScreenOnOff;
    }

    public void setContinueScreenOnOff(Boolean continueScreenOnOff) {
        this.continueScreenOnOff = continueScreenOnOff;
    }

    public Boolean getLetsStartScreenOnOff() {
        return letsStartScreenOnOff;
    }

    public void setLetsStartScreenOnOff(Boolean letsStartScreenOnOff) {
        this.letsStartScreenOnOff = letsStartScreenOnOff;
    }

    public Boolean getAgeGenderScreenOnOff() {
        return ageGenderScreenOnOff;
    }

    public void setAgeGenderScreenOnOff(Boolean ageGenderScreenOnOff) {
        this.ageGenderScreenOnOff = ageGenderScreenOnOff;
    }

    public Boolean getNextScreenOnOff() {
        return nextScreenOnOff;
    }

    public void setNextScreenOnOff(Boolean nextScreenOnOff) {
        this.nextScreenOnOff = nextScreenOnOff;
    }

    public Boolean getExtraInnerScreenOnOff() {
        return extraInnerScreenOnOff;
    }

    public void setExtraInnerScreenOnOff(Boolean extraInnerScreenOnOff) {
        this.extraInnerScreenOnOff = extraInnerScreenOnOff;
    }

    public Boolean getVpnOnOff() {
        return vpnOnOff;
    }

    public void setVpnOnOff(Boolean vpnOnOff) {
        this.vpnOnOff = vpnOnOff;
    }

    public Boolean getVpnDialog() {
        return vpnDialog;
    }

    public void setVpnDialog(Boolean vpnDialog) {
        this.vpnDialog = vpnDialog;
    }

    public Integer getVpnDialogTime() {
        return vpnDialogTime;
    }

    public void setVpnDialogTime(Integer vpnDialogTime) {
        this.vpnDialogTime = vpnDialogTime;
    }

    public VpnDefaultCountry getVpnDefaultCountry() {
        return vpnDefaultCountry;
    }

    public void setVpnDefaultCountry(VpnDefaultCountry vpnDefaultCountry) {
        this.vpnDefaultCountry = vpnDefaultCountry;
    }

    public List<VpnListCountry> getVpnListCountry() {
        return vpnListCountry;
    }

    public void setVpnListCountry(List<VpnListCountry> vpnListCountry) {
        this.vpnListCountry = vpnListCountry;
    }

    public String getVpnUrl() {
        return vpnUrl;
    }

    public void setVpnUrl(String vpnUrl) {
        this.vpnUrl = vpnUrl;
    }

    public String getVpnCarrierId() {
        return vpnCarrierId;
    }

    public void setVpnCarrierId(String vpnCarrierId) {
        this.vpnCarrierId = vpnCarrierId;
    }

    public String getDialogForAppStopText() {
        return dialogForAppStopText;
    }

    public void setDialogForAppStopText(String dialogForAppStopText) {
        this.dialogForAppStopText = dialogForAppStopText;
    }

    public Boolean getDialogForAppStopOnOff() {
        return dialogForAppStopOnOff;
    }

    public void setDialogForAppStopOnOff(Boolean dialogForAppStopOnOff) {
        this.dialogForAppStopOnOff = dialogForAppStopOnOff;
    }

    public class VpnDefaultCountry {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("flag")
        @Expose
        private String flag;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
}



