package com.allhdvideofree.downloader.videodownloader.Myinterfaces;


import com.allhdvideofree.downloader.videodownloader.model.FBStoryModel.NodeModel;
import com.allhdvideofree.downloader.videodownloader.model.TrayModel;

public interface UserListInterface {
    void fbUserListClick(int i, NodeModel nodeModel);

    void userListClick(int i, TrayModel trayModel);
}
