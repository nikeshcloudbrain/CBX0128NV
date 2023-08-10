package com.allhdvideofree.downloader.videodownloader.adapter.HDStoryAdepter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader.DhiFullViewActivity;
import com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader.DhiStoryVideoPlayerActivity;
import com.allhdvideofree.downloader.videodownloader.Utils.MyUtils;

import java.io.File;
import java.util.ArrayList;

public class DhiShowImagesAdapter extends PagerAdapter {
    public Context context;
    DhiFullViewActivity dhiFullViewActivity;
    public ArrayList<File> imageList;
    private LayoutInflater inflater;

    public int getItemPosition(Object obj) {
        return -2;
    }

    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    public Parcelable saveState() {
        return null;
    }

    public DhiShowImagesAdapter(Context context2, ArrayList<File> arrayList, DhiFullViewActivity dhiFullViewActivity2) {
        this.context = context2;
        this.imageList = arrayList;
        this.dhiFullViewActivity = dhiFullViewActivity2;
        this.inflater = LayoutInflater.from(context2);
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    public Object instantiateItem(ViewGroup viewGroup, final int i) {
        View inflate = this.inflater.inflate(R.layout.slidingimages_layout, viewGroup, false);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.im_vpPlay);
        ImageView imageView2 = (ImageView) inflate.findViewById(R.id.im_share);
        ImageView imageView3 = (ImageView) inflate.findViewById(R.id.im_delete);
        Glide.with(this.context).load(this.imageList.get(i).getPath()).into((ImageView) inflate.findViewById(R.id.im_fullViewImage));
        viewGroup.addView(inflate, 0);
        if (this.imageList.get(i).getName().substring(this.imageList.get(i).getName().lastIndexOf(".")).equals(".mp4")) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {

                new MainAds().showInterAds((Activity) context, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(DhiShowImagesAdapter.this.context, DhiStoryVideoPlayerActivity.class);
                        intent.putExtra("PathVideo", DhiShowImagesAdapter.this.imageList.get(i).getPath());
                        DhiShowImagesAdapter.this.context.startActivity(intent);

                    }
                });


            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (DhiShowImagesAdapter.this.imageList.get(i).delete()) {
                    DhiShowImagesAdapter.this.dhiFullViewActivity.deleteFileAA(i);
                }
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (DhiShowImagesAdapter.this.imageList.get(i).getName().substring(DhiShowImagesAdapter.this.imageList.get(i).getName().lastIndexOf(".")).equals(".mp4")) {
                    MyUtils.shareVideo(DhiShowImagesAdapter.this.context, DhiShowImagesAdapter.this.imageList.get(i).getPath());
                } else {
                    MyUtils.shareImage(DhiShowImagesAdapter.this.context, DhiShowImagesAdapter.this.imageList.get(i).getPath());
                }
            }
        });
        return inflate;
    }

    public int getCount() {
        return this.imageList.size();
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view.equals(obj);
    }
}
