package com.allhdvideofree.downloader.videodownloader.adapter.HDStoryAdepter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader.DhiStoryVideoPlayerActivity;
import com.allhdvideofree.downloader.videodownloader.model.StatusModel;
import com.allhdvideofree.downloader.videodownloader.Utils.MyUtils;

import java.util.ArrayList;

public class DhiPreviewAdapter extends PagerAdapter {
    Activity activity;
    ArrayList<StatusModel> imageList;

    public DhiPreviewAdapter(Activity activity, ArrayList<StatusModel> arrayList) {
        this.activity = activity;
        this.imageList = arrayList;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, final int i) {
        View inflate = LayoutInflater.from(this.activity).inflate(R.layout.preview_list_item, viewGroup, false);
        ImageView imageView = inflate.findViewById(R.id.imageView);
        ImageView imageView2 =  inflate.findViewById(R.id.iconplayer);
        if (!MyUtils.getBack(this.imageList.get(i).getFilePath(), "((\\.mp4|\\.webm|\\.ogg|\\.mpK|\\.avi|\\.mkv|\\.flv|\\.mpg|\\.wmv|\\.vob|\\.ogv|\\.mov|\\.qt|\\.rm|\\.rmvb\\.|\\.asf|\\.m4p|\\.m4v|\\.mp2|\\.mpeg|\\.mpe|\\.mpv|\\.m2v|\\.3gp|\\.f4p|\\.f4a|\\.f4b|\\.f4v)$)").isEmpty()) {
            imageView2.setVisibility(View.VISIBLE);
        } else {
            imageView2.setVisibility(View.GONE);
        }
        Glide.with(this.activity).load(this.imageList.get(i).getFilePath()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public final void onClick(View view) {
                DhiPreviewAdapter.this.preview(i, view);
            }
        });
        viewGroup.addView(inflate);
        return inflate;
    }

    public void preview(int i, View view) {
        if (!MyUtils.getBack(this.imageList.get(i).getFilePath(), "((\\.mp4|\\.webm|\\.ogg|\\.mpK|\\.avi|\\.mkv|\\.flv|\\.mpg|\\.wmv|\\.vob|\\.ogv|\\.mov|\\.qt|\\.rm|\\.rmvb\\.|\\.asf|\\.m4p|\\.m4v|\\.mp2|\\.mpeg|\\.mpe|\\.mpv|\\.m2v|\\.3gp|\\.f4p|\\.f4a|\\.f4b|\\.f4v)$)").isEmpty()) {
            MyUtils.mPath = this.imageList.get(i).getFilePath();
            Intent intent = new Intent(this.activity, DhiStoryVideoPlayerActivity.class);
            intent.putExtra("PathVideo", imageList.get(i).getFilePath());
            activity.startActivity(intent);


        }
    }

    @Override
    public int getCount() {
        return this.imageList.size();
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((RelativeLayout) obj);
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == ((RelativeLayout) obj);
    }
}
