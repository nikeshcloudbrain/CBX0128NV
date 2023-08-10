package com.allhdvideofree.downloader.videodownloader.fragment.StoryFragment;

import androidx.documentfile.provider.DocumentFile;

import java.util.Comparator;

public final class RecentWappBusData implements Comparator {
    public static final RecentWappBusData INSTANCE = new RecentWappBusData();

    private RecentWappBusData() {
    }

    @Override
    public final int compare(Object obj, Object obj2) {
        int compare;
        compare = Long.compare(((DocumentFile) obj2).lastModified(), ((DocumentFile) obj).lastModified());
        return compare;
    }
}
