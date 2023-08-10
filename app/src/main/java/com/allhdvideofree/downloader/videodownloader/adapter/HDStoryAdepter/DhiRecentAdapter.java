package com.allhdvideofree.downloader.videodownloader.adapter.HDStoryAdepter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.StoryDownloader.DhiPreviewActivity;
import com.allhdvideofree.downloader.videodownloader.model.StatusModel;
import com.allhdvideofree.downloader.videodownloader.Utils.MyUtils;

import java.util.ArrayList;
import java.util.List;


public class DhiRecentAdapter extends BaseAdapter {
    List<StatusModel> arrayList;
    Fragment context;
    LayoutInflater inflater;
    public OnCheckboxListener onCheckboxListener;
    int width;

    public interface OnCheckboxListener {
        void onCheckboxListener(View view, List<StatusModel> list);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public DhiRecentAdapter(Fragment fragment, List<StatusModel> list, OnCheckboxListener onCheckboxListener2) {
        this.context = fragment;
        this.arrayList = list;
        this.inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.width = fragment.getResources().getDisplayMetrics().widthPixels;
        this.onCheckboxListener = onCheckboxListener2;
    }

    public int getCount() {
        return this.arrayList.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        View inflate = this.inflater.inflate(R.layout.row_recent, (ViewGroup) null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.play);
        if (!MyUtils.getBack(this.arrayList.get(i).getFilePath(), "((\\.mp4|\\.webm|\\.ogg|\\.mpK|\\.avi|\\.mkv|\\.flv|\\.mpg|\\.wmv|\\.vob|\\.ogv|\\.mov|\\.qt|\\.rm|\\.rmvb\\.|\\.asf|\\.m4p|\\.m4v|\\.mp2|\\.mpeg|\\.mpe|\\.mpv|\\.m2v|\\.3gp|\\.f4p|\\.f4a|\\.f4b|\\.f4v)$)").isEmpty()) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        int i2 = this.width;
        inflate.setLayoutParams(new AbsListView.LayoutParams((i2 * 320) / 1080, (i2 * 320) / 1080));
        Glide.with(this.context.getActivity()).load(this.arrayList.get(i).getFilePath()).into((ImageView) inflate.findViewById(R.id.gridImage));
        CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                arrayList.get(i).setSelected(z);
                if (onCheckboxListener != null) {
                    onCheckboxListener.onCheckboxListener(compoundButton, DhiRecentAdapter.this.arrayList);
                }
            }
        });
        if (this.arrayList.get(i).isSelected()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        inflate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.e("click", "click");

                new MainAds().showInterAds(context.getActivity(), new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        final Intent intent = new Intent(DhiRecentAdapter.this.context.getActivity(), DhiPreviewActivity.class);
                        intent.putParcelableArrayListExtra("images", (ArrayList) DhiRecentAdapter.this.arrayList);
                        intent.putExtra("position", i);
                        intent.putExtra("statusdownload", "");
                        context.startActivity(intent);

                    }
                });



            }
        });
        return inflate;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        Log.d("MyAdapter", "onActivityResult");
    }
}
