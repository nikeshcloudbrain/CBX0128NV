package com.allhdvideofree.downloader.videodownloader.adapter.HDStoryAdepter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.allhdvideofree.downloader.videodownloader.Myinterfaces.FileListClickInterface;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader.DhiStoryVideoPlayerActivity;

import java.io.File;
import java.util.ArrayList;

public class DhiFileListAdapter extends RecyclerView.Adapter<DhiFileListAdapter.ViewHolder> {

    public Context context;
    private ArrayList<File> fileArrayList;
    public FileListClickInterface fileListClickInterface;

    public DhiFileListAdapter(Context context2, ArrayList<File> arrayList, FileListClickInterface fileListClickInterface2) {
        this.context = context2;
        this.fileArrayList = arrayList;
        this.fileListClickInterface = fileListClickInterface2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.items_file_view, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final File file = this.fileArrayList.get(i);
        try {
            if (file.getName().substring(file.getName().lastIndexOf(".")).equals(".mp4")) {
                viewHolder.ivPlay.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ivPlay.setVisibility(View.GONE);
            }
            viewHolder.ivPlay.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {

                    new MainAds().showInterAds((Activity) context, new InterAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            Intent intent = new Intent(DhiFileListAdapter.this.context, DhiStoryVideoPlayerActivity.class);
                            intent.putExtra("PathVideo", file.getPath().toString());
                            context.startActivity(intent);

                        }
                    });


                }
            });
            Glide.with(this.context).load(file.getPath()).into(viewHolder.pc);
        } catch (Exception unused) {
        }
        viewHolder.rlMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DhiFileListAdapter.this.fileListClickInterface.getPosition(i, file);
            }
        });
    }

    public int getItemCount() {
        ArrayList<File> arrayList = this.fileArrayList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPlay;
        public ImageView pc;
        public RelativeLayout rlMain;
        public TextView tvFileName;

        public ViewHolder(View view) {
            super(view);
            this.ivPlay = (ImageView) view.findViewById(R.id.iv_play);
            this.pc = (ImageView) view.findViewById(R.id.pc);
            this.rlMain = (RelativeLayout) view.findViewById(R.id.rl_main);
            this.tvFileName = (TextView) view.findViewById(R.id.tv_fileName);
        }
    }
}
