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
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader.DhiStoryVideoPlayerActivity;
import com.allhdvideofree.downloader.videodownloader.model.FBStoryModel.NodeModel;
import com.allhdvideofree.downloader.videodownloader.Utils.MyUtils;

import java.util.ArrayList;


public class DhiFBStoriesListAdapter extends RecyclerView.Adapter<DhiFBStoriesListAdapter.ViewHolder> {
    public Context context;
    private ArrayList<NodeModel> nodeModels;

    public DhiFBStoriesListAdapter(Context context2, ArrayList<NodeModel> arrayList) {
        this.context = context2;
        this.nodeModels = arrayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.items_whatsapp_view, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final NodeModel nodeModel = this.nodeModels.get(i);
        try {
            if (nodeModel.getNodeDataModel().getAttachmentsList().get(0).getMediaDataModel().get__typename().equalsIgnoreCase("Video")) {
                viewHolder.ivPlay.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ivPlay.setVisibility(View.GONE);
            }
            viewHolder.ivPlay.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Intent intent = new Intent(DhiFBStoriesListAdapter.this.context, DhiStoryVideoPlayerActivity.class);
                    intent.putExtra("PathVideo", nodeModel.getNodeDataModel().getAttachmentsList().get(0).getMediaDataModel().getPlayable_url_quality_hd());
                    DhiFBStoriesListAdapter.this.context.startActivity(intent);
                }
            });
            Glide.with(this.context).load(nodeModel.getNodeDataModel().getAttachmentsList().get(0).getMediaDataModel().getPreviewImage().get("uri").getAsString()).thumbnail(0.2f).into(viewHolder.pcw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewHolder.tvDownload.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                if (nodeModel.getNodeDataModel().getAttachmentsList().get(0).getMediaDataModel().get__typename().equalsIgnoreCase("Video")) {
                    String playable_url_quality_hd = nodeModel.getNodeDataModel().getAttachmentsList().get(0).getMediaDataModel().getPlayable_url_quality_hd();
                    String str = MyUtils.RootDirectoryFacebook;
                    Context context = DhiFBStoriesListAdapter.this.context;
                    MyUtils.startNewDownload(playable_url_quality_hd, str, (Activity) context, "fbstory_" + System.currentTimeMillis() + ".mp4");
                    return;
                }
                String asString = nodeModel.getNodeDataModel().getAttachmentsList().get(0).getMediaDataModel().getPreviewImage().get("uri").getAsString();
                String str2 = MyUtils.RootDirectoryFacebook;
                Context context2 = DhiFBStoriesListAdapter.this.context;
                MyUtils.startNewDownload(asString, str2, (Activity) context2, "fbstory_" + System.currentTimeMillis() + ".png");
            }
        });
    }

    public int getItemCount() {
        ArrayList<NodeModel> arrayList = this.nodeModels;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout RLM;
        public ImageView ivPlay;
        public ImageView pcw;
        public RelativeLayout rlMain;
        public TextView tvDownload;

        public ViewHolder(View view) {
            super(view);
            this.RLM =  view.findViewById(R.id.RLM);
            this.ivPlay =  view.findViewById(R.id.iv_play);
            this.pcw =  view.findViewById(R.id.pcw);
            this.rlMain =  view.findViewById(R.id.rl_main);
            this.tvDownload =  view.findViewById(R.id.tv_download);
        }
    }
}
