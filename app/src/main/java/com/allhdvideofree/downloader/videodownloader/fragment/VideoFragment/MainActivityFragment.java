package com.allhdvideofree.downloader.videodownloader.fragment.VideoFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.allhdvideofree.downloader.videodownloader.HDFilemanager.FilePicker;
import com.allhdvideofree.downloader.videodownloader.Other.PreferenceUtil;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.adapter.videoadapter.FolderAdapter;
import com.allhdvideofree.downloader.videodownloader.model.MediaQuery;
import com.allhdvideofree.downloader.videodownloader.model.VideoItem;
import com.allhdvideofree.downloader.videodownloader.model.folder;
import com.allhdvideofree.downloader.videodownloader.activities.AllVideoPlayer.DhiHDVideoPlayerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivityFragment extends BaseFragment implements FolderAdapter.MyListener {
    Context hdvideoplayer_context;
    private FolderAdapter hdvideoplayer_folderAdapter;
    private ArrayList<folder> hdvideoplayer_folderList;
    private LinearLayoutManager hdvideoplayer_layoutManager;
    public SwipeRefreshLayout hdvideoplayer_mSwipeRefreshLayout;
    int hdvideoplayer_pos;
    private PreferenceUtil hdvideoplayer_preferenceUtil;
    private RecyclerView hdvideoplayer_rvFolders;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.activity_dhi_main1, viewGroup, false);
        hdvideoplayer_context = getContext();
        hdvideoplayer_preferenceUtil = PreferenceUtil.getInstance(this.hdvideoplayer_context);
        hdvideoplayer_mSwipeRefreshLayout = inflate.findViewById(R.id.swipe);
        hdvideoplayer_rvFolders = inflate.findViewById(R.id.recycle);
        hdvideoplayer_layoutManager = new LinearLayoutManager(this.hdvideoplayer_context, RecyclerView.VERTICAL, false);
        hdvideoplayer_rvFolders.setLayoutManager(hdvideoplayer_layoutManager);
        hdvideoplayer_mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(17170444), getResources().getColor(17170459), getResources().getColor(17170452), getResources().getColor(17170456), getResources().getColor(17170454));
        hdvideoplayer_mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                loadVideoFolder();
            }
        });
        try {
            File file = new File(String.valueOf(this.hdvideoplayer_context.getExternalFilesDir(getResources().getString(R.string.private_folder_name))));
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inflate;
    }

    public void onResume() {
        super.onResume();

        this.hdvideoplayer_pos = this.hdvideoplayer_context.getSharedPreferences("theme", 0).getInt("pos", 1);
        loadVideoFolder();
        try {
            PreferenceUtil.getInstance(this.hdvideoplayer_context).getVideoURL().equalsIgnoreCase("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActivityRequestResult(int i, int i2, Intent intent, String str) {
        try {
            FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
            if (supportFragmentManager.getFragments().size() > 0) {
                for (int i3 = 0; i3 < supportFragmentManager.getFragments().size(); i3++) {
                    Fragment fragment = supportFragmentManager.getFragments().get(i3);
                    if (fragment != null && fragment.getClass().getSimpleName().equalsIgnoreCase(str)) {
                        fragment.onActivityResult(i, i2, intent);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 21 && intent.hasExtra(FilePicker.EXTRA_FILE_PATH)) {
            File file = new File(intent.getStringExtra(FilePicker.EXTRA_FILE_PATH));
            ArrayList arrayList = new ArrayList();
            VideoItem videoItem = new VideoItem();
            videoItem.set_ID("kfdked");
            videoItem.setSIZE("");
            videoItem.setDATE("");
            videoItem.setDATA(file.getAbsolutePath());
            videoItem.setDISPLAY_NAME(file.getName());
            videoItem.setDURATION("");
            videoItem.setVideoSize(2121121);
            arrayList.add(videoItem);
            Intent intent2 = new Intent(this.hdvideoplayer_context, DhiHDVideoPlayerActivity.class);
            intent2.putExtra("list", arrayList);
            intent2.putExtra("position", 0);
            startActivity(intent2);
        }
    }

    public void sortByVideo() {
        int folderSortOrder = this.hdvideoplayer_preferenceUtil.getFolderSortOrder();
        boolean folderAsc = this.hdvideoplayer_preferenceUtil.getFolderAsc();
        if (folderSortOrder == 0) {
            if (folderAsc) {
                Collections.sort(this.hdvideoplayer_folderList, new Comparator<folder>() {
                    public int compare(folder folder, folder folder2) {
                        return folder.getBucket().compareTo(folder2.getBucket());
                    }
                });
            } else {
                Collections.sort(this.hdvideoplayer_folderList, new Comparator<folder>() {
                    public int compare(folder folder, folder folder2) {
                        return folder2.getBucket().compareTo(folder.getBucket());
                    }
                });
            }
        } else if (folderSortOrder == 1) {
            if (folderAsc) {
                Collections.sort(this.hdvideoplayer_folderList, new Comparator<folder>() {
                    public int compare(folder folder, folder folder2) {
                        return folder.getDate().compareTo(folder2.getDate());
                    }
                });
            } else {
                Collections.sort(this.hdvideoplayer_folderList, new Comparator<folder>() {
                    public int compare(folder folder, folder folder2) {
                        return folder2.getDate().compareTo(folder.getDate());
                    }
                });
            }
        } else if (folderSortOrder == 2) {
            if (folderAsc) {
                Collections.sort(this.hdvideoplayer_folderList, new Comparator<folder>() {
                    public int compare(folder folder, folder folder2) {
                        return Long.valueOf(folder.getFolderSize()).compareTo(Long.valueOf(folder2.getFolderSize()));
                    }
                });
            } else {
                Collections.sort(this.hdvideoplayer_folderList, new Comparator<folder>() {
                    public int compare(folder folder, folder folder2) {
                        return Long.valueOf(folder2.getFolderSize()).compareTo(Long.valueOf(folder.getFolderSize()));
                    }
                });
            }
        } else if (folderSortOrder == 3) {
            if (folderAsc) {
                Collections.sort(this.hdvideoplayer_folderList, new Comparator<folder>() {
                    public int compare(folder folder, folder folder2) {
                        return folder.getCount().compareTo(folder2.getCount());
                    }
                });
            } else {
                Collections.sort(this.hdvideoplayer_folderList, new Comparator<folder>() {
                    public int compare(folder folder, folder folder2) {
                        return folder2.getCount().compareTo(folder.getCount());
                    }
                });
            }
        }
        this.hdvideoplayer_rvFolders.getRecycledViewPool().clear();
        this.hdvideoplayer_folderAdapter.notifyDataSetChanged();
    }

    public void loadVideoFolder() {
        this.hdvideoplayer_folderList = null;
        int folderSortOrder = this.hdvideoplayer_preferenceUtil.getFolderSortOrder();
        boolean folderAsc = this.hdvideoplayer_preferenceUtil.getFolderAsc();
        this.hdvideoplayer_mSwipeRefreshLayout.setRefreshing(true);
        ArrayList<folder> folderList = new MediaQuery(this.hdvideoplayer_context).getFolderList();
        this.hdvideoplayer_folderList = folderList;
        if (folderList != null) {
            if (folderSortOrder == 0) {
                if (folderAsc) {
                    Collections.sort(folderList, new Comparator<folder>() {
                        public int compare(folder folder, folder folder2) {
                            return folder.getBucket().compareTo(folder2.getBucket());
                        }
                    });
                } else {
                    Collections.sort(folderList, new Comparator<folder>() {
                        public int compare(folder folder, folder folder2) {
                            return folder2.getBucket().compareTo(folder.getBucket());
                        }
                    });
                }
            } else if (folderSortOrder == 1) {
                if (folderAsc) {
                    Collections.sort(folderList, new Comparator<folder>() {
                        public int compare(folder folder, folder folder2) {
                            return folder.getDate().compareTo(folder2.getDate());
                        }
                    });
                } else {
                    Collections.sort(folderList, new Comparator<folder>() {
                        public int compare(folder folder, folder folder2) {
                            return folder2.getDate().compareTo(folder.getDate());
                        }
                    });
                }
            } else if (folderSortOrder == 2) {
                if (folderAsc) {
                    Collections.sort(folderList, new Comparator<folder>() {
                        public int compare(folder folder, folder folder2) {
                            return Long.valueOf(folder.getFolderSize()).compareTo(Long.valueOf(folder2.getFolderSize()));
                        }
                    });
                } else {
                    Collections.sort(folderList, new Comparator<folder>() {
                        public int compare(folder folder, folder folder2) {
                            return Long.valueOf(folder2.getFolderSize()).compareTo(Long.valueOf(folder.getFolderSize()));
                        }
                    });
                }
            } else if (folderSortOrder == 3) {
                if (folderAsc) {
                    Collections.sort(folderList, new Comparator<folder>() {
                        public int compare(folder folder, folder folder2) {
                            return folder.getCount().compareTo(folder2.getCount());
                        }
                    });
                } else {
                    Collections.sort(folderList, new Comparator<folder>() {
                        public int compare(folder folder, folder folder2) {
                            return folder2.getCount().compareTo(folder.getCount());
                        }
                    });
                }
            }
        }
        this.hdvideoplayer_rvFolders.getRecycledViewPool().clear();
        FolderAdapter folderAdapter = new FolderAdapter((Activity) this.hdvideoplayer_context, this.hdvideoplayer_folderList, this);
        hdvideoplayer_folderAdapter = folderAdapter;
        hdvideoplayer_rvFolders.setAdapter(folderAdapter);
        hdvideoplayer_mSwipeRefreshLayout.setRefreshing(false);
    }

    public void clickFoldermenu(int i, int i2) {
        hdvideoplayer_folderList.clear();
        hdvideoplayer_mSwipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                hdvideoplayer_mSwipeRefreshLayout.setRefreshing(false);
                loadVideoFolder();
            }
        }, 300);
    }
}
