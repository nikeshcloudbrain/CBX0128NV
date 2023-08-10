package com.allhdvideofree.downloader.videodownloader.fragment.StoryFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.documentfile.provider.DocumentFile;
//import androidx.fragment.app.C0277Fragment;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.allhdvideofree.downloader.videodownloader.adapter.HDStoryAdepter.DhiRecentAdapter;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.model.StatusModel;
import com.allhdvideofree.downloader.videodownloader.Utils.MyUtils;
import com.allhdvideofree.downloader.videodownloader.Utils.SharedPrefs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class RecentWappBus extends Fragment implements DhiRecentAdapter.OnCheckboxListener {
    LinearLayout actionLay;
    loadDataAsync async;
    LinearLayout deleteIV;
    LinearLayout downloadIV;
    RelativeLayout emptyLay;
    GridView imageGrid;
    RelativeLayout loaderLay;
    DhiRecentAdapter myAdapter;
    LinearLayout sAccessBtn;
    CheckBox selectAll;
    SwipeRefreshLayout swipeToRefresh;
    ArrayList<StatusModel> f = new ArrayList<>();
    ArrayList<StatusModel> filesToDelete = new ArrayList<>();
    int REQUEST_ACTION_OPEN_DOCUMENT_TREE = 1001;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.recent_fragment, viewGroup, false);
        this.loaderLay = (RelativeLayout) inflate.findViewById(R.id.loaderLay);
        this.emptyLay = (RelativeLayout) inflate.findViewById(R.id.emptyLay);
        this.imageGrid = (GridView) inflate.findViewById(R.id.WorkImageGrid);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.swipeToRefresh);
        this.swipeToRefresh = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public final void onRefresh() {
                RecentWappBus.this.m1792x9ac105ef();
            }
        });
        this.actionLay = (LinearLayout) inflate.findViewById(R.id.actionLay);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.deleteIV);
        this.deleteIV = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                RecentWappBus.this.m1794xb6d959ad(view);
            }
        });
        LinearLayout linearLayout2 = (LinearLayout) inflate.findViewById(R.id.downloadIV);
        this.downloadIV = linearLayout2;
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                RecentWappBus.this.m1795xc4e5838c(view);
            }
        });
        CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.selectAll);
        this.selectAll = checkBox;
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                RecentWappBus.this.m1796xd2f1ad6b(compoundButton, z);
            }
        });
        LinearLayout linearLayout3 = (LinearLayout) inflate.findViewById(R.id.sAccessBtn);
        this.sAccessBtn = linearLayout3;
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                RecentWappBus.this.m1797xe0fdd74a(view);
            }
        });
        if (!SharedPrefs.getWBTree(getActivity()).equals("")) {
            populateGrid();
        }
        return inflate;
    }


    public void m1792x9ac105ef() {
        if (!SharedPrefs.getWBTree(getActivity()).equals("")) {
            Iterator<StatusModel> it = this.filesToDelete.iterator();
            while (it.hasNext()) {
                ArrayList<StatusModel> arrayList = this.f;
                it.next().selected = false;
                arrayList.contains(false);
            }
            DhiRecentAdapter dhiRecentAdapter = this.myAdapter;
            if (dhiRecentAdapter != null) {
                dhiRecentAdapter.notifyDataSetChanged();
            }
            this.filesToDelete.clear();
            this.selectAll.setChecked(false);
            this.actionLay.setVisibility(View.GONE);
            populateGrid();
        }
        this.swipeToRefresh.setRefreshing(false);
    }


    public void m1794xb6d959ad(View view) {
        if (!this.filesToDelete.isEmpty()) {
            new AlertDialog.Builder(getContext()).setMessage(getResources().getString(R.string.delete_alert)).setCancelable(true).setNegativeButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() { // from class: superfast.fastdownloader.free.video.downloader.allvideodownloader.AppContent.Downloader.Fragment.RecentWappBus$$ExternalSyntheticLambda0
                @Override
                public final void onClick(DialogInterface dialogInterface, int i) {
                    RecentWappBus.this.m1793xa8cd2fce(dialogInterface, i);
                }
            }).setPositiveButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }
    }


    public void m1793xa8cd2fce(DialogInterface dialogInterface, int i) {
        ArrayList arrayList = new ArrayList();
        Iterator<StatusModel> it = this.filesToDelete.iterator();
        char c = '\uffff';
        while (it.hasNext()) {
            StatusModel next = it.next();
            DocumentFile fromSingleUri = DocumentFile.fromSingleUri(getActivity(), Uri.parse(next.getFilePath()));
            if (!fromSingleUri.exists() || !fromSingleUri.delete()) {
                c = 0;
            } else {
                arrayList.add(next);
                if (c != 0) {
                    c = 1;
                } else {
                    return;
                }
            }
        }
        this.filesToDelete.clear();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            this.f.remove((StatusModel) it2.next());
        }
        this.myAdapter.notifyDataSetChanged();
        if (c == 0) {
            Toast.makeText(getContext(), getResources().getString(R.string.delete_error), Toast.LENGTH_SHORT).show();
        } else if (c == 1) {
            Toast.makeText(getActivity(), getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
        }
        this.actionLay.setVisibility(View.GONE);
        this.selectAll.setChecked(false);
    }


    public void m1795xc4e5838c(View view) {
        if (!this.filesToDelete.isEmpty()) {
            char c = '\uffff';
            ArrayList arrayList = new ArrayList();
            Iterator<StatusModel> it = this.filesToDelete.iterator();
            while (it.hasNext()) {
                StatusModel next = it.next();
                if (!DocumentFile.fromSingleUri(getActivity(), Uri.parse(next.getFilePath())).exists() || !MyUtils.download(getActivity(), next.getFilePath())) {
                    c = 0;
                } else {
                    arrayList.add(next);
                    if (c != 0) {
                        c = 1;
                    } else {
                        return;
                    }
                }
            }
            this.filesToDelete.clear();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ArrayList<StatusModel> arrayList2 = this.f;
                ((StatusModel) it2.next()).selected = false;
                arrayList2.contains(false);
            }
            this.myAdapter.notifyDataSetChanged();
            if (c == 0) {
                Toast.makeText(getContext(), getResources().getString(R.string.save_error), Toast.LENGTH_SHORT).show();
            } else if (c == 1) {
                Toast.makeText(getActivity(), getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
            }
            this.actionLay.setVisibility(View.GONE);
        }
    }


    public void m1796xd2f1ad6b(CompoundButton compoundButton, boolean z) {
        if (compoundButton.isPressed()) {
            this.filesToDelete.clear();
            int i = 0;
            while (true) {
                if (i >= this.f.size()) {
                    break;
                } else if (!this.f.get(i).selected) {
                    z = true;
                    break;
                } else {
                    i++;
                }
            }
            if (z) {
                for (int i2 = 0; i2 < this.f.size(); i2++) {
                    this.f.get(i2).selected = true;
                    this.filesToDelete.add(this.f.get(i2));
                }
                this.selectAll.setChecked(true);
            } else {
                for (int i3 = 0; i3 < this.f.size(); i3++) {
                    this.f.get(i3).selected = false;
                }
                this.actionLay.setVisibility(View.GONE);
            }
            this.myAdapter.notifyDataSetChanged();
        }
    }


    public void m1797xe0fdd74a(View view) {
        Intent intent;
        if (MyUtils.appInstalledOrNot(getActivity(), "com.whatsapp.w4b")) {
            StorageManager storageManager = (StorageManager) getActivity().getSystemService(Context.STORAGE_SERVICE);
            String whatsupFolder = getWhatsupFolder();
            if (Build.VERSION.SDK_INT >= 29) {
                intent = storageManager.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
                String replace = ((Uri) intent.getParcelableExtra("android.provider.extra.INITIAL_URI")).toString().replace("/root/", "/document/");
                intent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse(replace + "%3A" + whatsupFolder));
            } else {
                intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
                intent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse("content://com.android.externalstorage.documents/document/primary%3A" + whatsupFolder));
            }
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            startActivityForResult(intent, this.REQUEST_ACTION_OPEN_DOCUMENT_TREE);
            return;
        }
        Toast.makeText(getActivity(), "Please Install WhatsApp Business For Download Status!!!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        DhiRecentAdapter dhiRecentAdapter = this.myAdapter;
        if (dhiRecentAdapter != null) {
            dhiRecentAdapter.onActivityResult(i, i2, intent);
        }
        if (i == 10 && i2 == 10) {
            this.f = new ArrayList<>();
            DocumentFile[] fromSdcard = getFromSdcard();
            for (int i3 = 0; i3 < fromSdcard.length - 1; i3++) {
                if (!fromSdcard[i3].getUri().toString().contains(".nomedia")) {
                    this.f.add(new StatusModel(fromSdcard[i3].getUri().toString()));
                }
            }
            DhiRecentAdapter dhiRecentAdapter2 = new DhiRecentAdapter(this, this.f, this);
            this.myAdapter = dhiRecentAdapter2;
            this.imageGrid.setAdapter((ListAdapter) dhiRecentAdapter2);
            this.actionLay.setVisibility(View.GONE);
            this.selectAll.setChecked(false);
        }
        if (i == this.REQUEST_ACTION_OPEN_DOCUMENT_TREE && i2 == -1) {
            Uri data = intent.getData();
            Log.e("onActivityResult: ", "" + intent.getData());
            try {
                if (Build.VERSION.SDK_INT >= 19) {
                    requireContext().getContentResolver().takePersistableUriPermission(data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            SharedPrefs.setWBTree(getActivity(), data.toString());
            populateGrid();
        }
    }

    public void populateGrid() {
        loadDataAsync loaddataasync = new loadDataAsync();
        this.async = loaddataasync;
        loaddataasync.execute(new Void[0]);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadDataAsync loaddataasync = this.async;
        if (loaddataasync != null) {
            loaddataasync.cancel(true);
        }
    }


    public class loadDataAsync extends AsyncTask<Void, Void, Void> {
        DocumentFile[] allFiles;

        loadDataAsync() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            RecentWappBus.this.loaderLay.setVisibility(View.VISIBLE);
            RecentWappBus.this.imageGrid.setVisibility(View.GONE);
            RecentWappBus.this.sAccessBtn.setVisibility(View.GONE);
            RecentWappBus.this.emptyLay.setVisibility(View.GONE);
        }


        public Void doInBackground(Void... voidArr) {
            this.allFiles = null;
            RecentWappBus.this.f = new ArrayList<>();
            DocumentFile[] fromSdcard = RecentWappBus.this.getFromSdcard();
            if (fromSdcard != null) {


                this.allFiles = fromSdcard;
                Arrays.sort(fromSdcard, RecentWappBusData.INSTANCE);
                int i = 0;
                while (true) {
                    DocumentFile[] documentFileArr = this.allFiles;
                    if (i >= documentFileArr.length - 1) {
                        return null;
                    }
                    if (!documentFileArr[i].getUri().toString().contains(".nomedia")) {
                        RecentWappBus.this.f.add(new StatusModel(this.allFiles[i].getUri().toString()));
                    }
                    i++;
                }
            }
            return null;
        }


        public void onPostExecute(Void r4) {
            super.onPostExecute(r4);
            new Handler().postDelayed(new Runnable() {
                @Override
                public final void run() {
                    RecentWappBus.loadDataAsync.this.m1798x166d165a();
                }
            }, 1000L);
        }

        public void m1798x166d165a() {
            if (RecentWappBus.this.getActivity() != null) {
                if (!(RecentWappBus.this.f == null || RecentWappBus.this.f.size() == 0)) {
                    RecentWappBus recentWappBus = RecentWappBus.this;
                    RecentWappBus recentWappBus2 = RecentWappBus.this;
                    recentWappBus.myAdapter = new DhiRecentAdapter(recentWappBus2, recentWappBus2.f, RecentWappBus.this);
                    RecentWappBus.this.imageGrid.setAdapter((ListAdapter) RecentWappBus.this.myAdapter);
                    RecentWappBus.this.imageGrid.setVisibility(View.VISIBLE);
                }
                RecentWappBus.this.loaderLay.setVisibility(View.GONE);
            }
            if (RecentWappBus.this.f == null || RecentWappBus.this.f.size() == 0) {
                RecentWappBus.this.emptyLay.setVisibility(View.VISIBLE);
            } else {
                RecentWappBus.this.emptyLay.setVisibility(View.GONE);
            }
        }
    }


    public DocumentFile[] getFromSdcard() {
        DocumentFile fromTreeUri = DocumentFile.fromTreeUri(requireContext().getApplicationContext(), Uri.parse(SharedPrefs.getWBTree(getActivity())));
        if (fromTreeUri == null || !fromTreeUri.exists() || !fromTreeUri.isDirectory() || !fromTreeUri.canRead() || !fromTreeUri.canWrite()) {
            return null;
        }
        return fromTreeUri.listFiles();
    }

    public String getWhatsupFolder() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append("Android/media/com.whatsapp.w4b/WhatsApp Business");
        sb.append(File.separator);
        sb.append("Media");
        sb.append(File.separator);
        sb.append(".Statuses");
        return new File(sb.toString()).isDirectory() ? "Android%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp Business%2FMedia%2F.Statuses" : "WhatsApp Business%2FMedia%2F.Statuses";
    }

    @Override
    public void onCheckboxListener(View view, List<StatusModel> list) {
        this.filesToDelete.clear();
        for (StatusModel statusModel : list) {
            if (statusModel.isSelected()) {
                this.filesToDelete.add(statusModel);
            }
        }
        if (this.filesToDelete.size() == this.f.size()) {
            this.selectAll.setChecked(true);
        }
        if (!this.filesToDelete.isEmpty()) {
            this.actionLay.setVisibility(View.VISIBLE);
            return;
        }
        this.selectAll.setChecked(false);
        this.actionLay.setVisibility(View.GONE);
    }

}
