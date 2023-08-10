package com.allhdvideofree.downloader.videodownloader.adapter.HDStoryAdepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.model.FBStoryModel.NodeModel;
import com.allhdvideofree.downloader.videodownloader.Myinterfaces.UserListInterface;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class DhiFbStoryUserListAdapter extends RecyclerView.Adapter<DhiFbStoryUserListAdapter.ViewHolder> {
    private Context context;
    public ArrayList<NodeModel> trayModelArrayList;
    public UserListInterface userListInterface;

    public DhiFbStoryUserListAdapter(Context context2, ArrayList<NodeModel> arrayList, UserListInterface userListInterface2) {
        this.context = context2;
        this.trayModelArrayList = arrayList;
        this.userListInterface = userListInterface2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_user_list, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        NodeModel nodeModel = this.trayModelArrayList.get(i);
        viewHolder.realName.setText(nodeModel.getNodeDataModel().getStory_bucket_owner().get("name").getAsString());
        Glide.with(this.context).load(nodeModel.getNodeDataModel().getOwner().getAsJsonObject("profile_picture").get("uri").getAsString()).thumbnail(0.2f).into((ImageView) viewHolder.storyPc);
        viewHolder.RLStoryLayout.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DhiFbStoryUserListAdapter.this.userListInterface.fbUserListClick(i, (NodeModel) DhiFbStoryUserListAdapter.this.trayModelArrayList.get(i));
            }
        });
    }

    public int getItemCount() {
        ArrayList<NodeModel> arrayList = this.trayModelArrayList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout RLStoryLayout;
        public TextView realName;
        public CircleImageView storyPc;
        public TextView userName;

        public ViewHolder(View view) {
            super(view);
            this.RLStoryLayout = (RelativeLayout) view.findViewById(R.id.RLStoryLayout);
            this.realName = (TextView) view.findViewById(R.id.real_name);
            this.storyPc = (CircleImageView) view.findViewById(R.id.story_pc);
            this.userName = (TextView) view.findViewById(R.id.user_name);
        }
    }
}
