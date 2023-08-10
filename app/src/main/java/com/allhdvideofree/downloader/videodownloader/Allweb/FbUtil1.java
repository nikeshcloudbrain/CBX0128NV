package com.allhdvideofree.downloader.videodownloader.Allweb;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;


public final class FbUtil1 implements HostnameVerifier {
    public static final FbUtil1 INSTANCE = new FbUtil1();

    private FbUtil1() {
    }

    public final boolean verify(String str, SSLSession sSLSession) {
        return AndroidUtils.lambda$getUnsafeOkHttpClient$0(str, sSLSession);
    }
}
