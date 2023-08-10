package com.allhdvideofree.downloader.videodownloader.Allweb;

import android.media.MediaScannerConnection;
import android.net.Uri;


public final  class Fbutil2 implements MediaScannerConnection.OnScanCompletedListener {
    public static final Fbutil2 INSTANCE = new Fbutil2();

    private Fbutil2() {
    }

    public final void onScanCompleted(String str, Uri uri) {
        AndroidUtils.lambda$notifyMediaScannerService$1(str, uri);
    }
}
