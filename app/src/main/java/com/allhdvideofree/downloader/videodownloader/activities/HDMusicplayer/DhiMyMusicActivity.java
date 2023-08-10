package com.allhdvideofree.downloader.videodownloader.activities.HDMusicplayer;

import static com.allhdvideofree.downloader.videodownloader.Utils.Constant.setToolBar;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;
import com.allhdvideofree.downloader.videodownloader.Utils.Constant;
import com.allhdvideofree.downloader.videodownloader.R;
import com.allhdvideofree.downloader.videodownloader.activities.DhiActivityBase;
import com.allhdvideofree.downloader.videodownloader.Utils.VideoMusicFileManger;
import com.allhdvideofree.downloader.videodownloader.Utils.VideoMusicInternalList;
import com.allhdvideofree.downloader.videodownloader.Utils.onClickAudio;

import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

public class DhiMyMusicActivity extends DhiActivityBase implements onClickAudio, LoaderManager.LoaderCallbacks<Cursor> {
    RelativeLayout RLM_ListItem;
    RecyclerView RVM_List;
    AppCompatTextView TVM_List;
    private String filepath;
    private ArrayList<VideoMusicInternalList> getvideo_list = new ArrayList();
    private ArrayList<VideoMusicFileManger> musicFileMDS = new ArrayList();
    public static MediaPlayer mp;
    public static MusicListAdp musicListAdp;
    public static int pos_music_grid = -1;
    public static int folder_pos_old = -1;
    public static int Online_pos_old = -1;
    AppCompatTextView TVM_lftTime;
    AppCompatTextView TVM_RigtTime;
    int Viduration;
    int filzero;
    public static int mStartMs, mEndMs;
    CrystalRangeSeekbar SBM_songCut;
    boolean isSingle;
    private Runnable r;
    RecyclerView RVM_FileMang;
    public static AudioFolderAdapter audioFolderAdapter;
    AppCompatTextView TVM_FileMang;
    AppCompatButton Btn_MusicList, Btn_FolderList;
    RelativeLayout RLM_FileMang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhi_my_music);

        RLM_ListItem = findViewById(R.id.RLM_ListItem);
        RVM_List = findViewById(R.id.RVM_List);
        TVM_List = findViewById(R.id.TVM_List);
        TVM_lftTime = findViewById(R.id.TVM_lftTime);
        TVM_RigtTime = findViewById(R.id.TVM_RigtTime);
        SBM_songCut = findViewById(R.id.SBM_songCut);
        RVM_FileMang = findViewById(R.id.RVM_FileMang);
        TVM_FileMang = findViewById(R.id.TVM_FileMang);
        RLM_FileMang = findViewById(R.id.RLM_FileMang);

        Btn_MusicList = findViewById(R.id.Btn_MusicList);
        Btn_FolderList = findViewById(R.id.Btn_FolderList);

        setToolBar(DhiMyMusicActivity.this,"Music Player");

        getLoaderManager().initLoader(15, null, this);


        getFst_Folder("/storage/emulated/0");

        Btn_MusicList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBtnClr(Btn_MusicList, RLM_ListItem);
                Btn_MusicList.setBackground(getResources().getDrawable(R.drawable.btn_wa));
                Btn_MusicList.setTextColor(getResources().getColor(R.color.white));
                Btn_FolderList.setBackground(getResources().getDrawable(R.drawable.btn_gradient));
                Btn_FolderList.setTextColor(getResources().getColor(R.color.white));
            }
        });

        Btn_FolderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBtnClr(Btn_FolderList, RLM_FileMang);
                Btn_FolderList.setBackground(getResources().getDrawable(R.drawable.btn_wa));
                Btn_FolderList.setTextColor(getResources().getColor(R.color.white));
                Btn_MusicList.setBackground(getResources().getDrawable(R.drawable.btn_gradient));
                Btn_MusicList.setTextColor(getResources().getColor(R.color.white));
            }
        });


    }

    private void getFst_Folder(String str) {

        File dir = new File(str);

        if (dir.exists()) {
            musicFileMDS.clear();

            if (dir.listFiles() != null) {
                for (File f : dir.listFiles()) {
                    if (f.getName().endsWith(".mp3") || f.getName().endsWith(".MP3") || f.isDirectory()) {
                        if (!f.getName().startsWith(".")) {
                            VideoMusicFileManger objfileitem = new VideoMusicFileManger();

                            objfileitem.setFst_file_name(f.getAbsolutePath());

                            musicFileMDS.add(objfileitem);

                            RVM_FileMang.setLayoutManager(new LinearLayoutManager(this));
                            audioFolderAdapter = new AudioFolderAdapter(musicFileMDS);
                            TVM_FileMang.setVisibility(View.GONE);
                            RVM_FileMang.setVisibility(View.VISIBLE);
                            RVM_FileMang.setAdapter(audioFolderAdapter);
                            runLayoutAnimation(RVM_FileMang);
                            audioFolderAdapter.notifyDataSetChanged();
                        }

                    }


                }
            }
        }
    }

    private void changeBtnClr(AppCompatButton SelBtn, RelativeLayout SelRel) {


        RLM_ListItem.setVisibility(View.GONE);



        SelRel.setVisibility(View.VISIBLE);

    }

    private void runLayoutAnimation(RecyclerView recyclerView) {
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.layoutanim_downtoup));
        recyclerView.scheduleLayoutAnimation();
    }

    public class AudioFolderAdapter extends RecyclerView.Adapter<AudioFolderAdapter.MyViewHolder> {
        private ArrayList<VideoMusicFileManger> musicFileMDS;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            AppCompatImageView ImgM_Folder, ImgM_Play;
            LinearLayout LLMF_MainClk;
            AppCompatTextView TVML_FolderName;
//            CardView CrdMF_Use;

            public MyViewHolder(View view) {
                super(view);

                this.ImgM_Folder = view.findViewById(R.id.ImgM_Folder);
                this.TVML_FolderName = view.findViewById(R.id.TVML_FolderName);
                this.LLMF_MainClk = view.findViewById(R.id.LLMF_MainClk);
                this.ImgM_Play = view.findViewById(R.id.ImgM_Play);
//                this.CrdMF_Use = view.findViewById(R.id.CrdMF_Use);

            }
        }

        public AudioFolderAdapter(ArrayList<VideoMusicFileManger> musicFileMDS) {

            this.musicFileMDS = musicFileMDS;
        }

        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cust_music_folder, viewGroup, false));
        }

        @RequiresApi(api = 16)
        public void onBindViewHolder(final MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {

            final String folder_nm = new File(((VideoMusicFileManger) this.musicFileMDS.get(i)).getFst_file_name()).getName();

            myViewHolder.TVML_FolderName.setText(folder_nm);

            if (folder_nm.endsWith(".mp3") || folder_nm.endsWith(".MP3")) {
                myViewHolder.ImgM_Folder.setImageResource(R.drawable.ic_newplay);
//                myViewHolder.CrdMF_Use.setVisibility(View.VISIBLE);


                try {
                    if (folder_pos_old != i) {
                        myViewHolder.ImgM_Play.setVisibility(View.GONE);
//                        myViewHolder.CrdMF_Use.setVisibility(View.GONE);
                        myViewHolder.ImgM_Folder.setVisibility(View.VISIBLE);
                    } else if (mp != null) {
                        myViewHolder.ImgM_Play.setVisibility(View.VISIBLE);
//                        myViewHolder.CrdMF_Use.setVisibility(View.VISIBLE);
                        myViewHolder.ImgM_Folder.setVisibility(View.GONE);
                    } else {
                        myViewHolder.ImgM_Folder.setVisibility(View.VISIBLE);
                        myViewHolder.ImgM_Play.setVisibility(View.GONE);
//                        myViewHolder.CrdMF_Use.setVisibility(View.GONE);
                    }

                } catch (Exception e2) {
                    e2.printStackTrace();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(e2);
                }


            } else {
                myViewHolder.ImgM_Folder.setImageResource(R.drawable.folder1);
                myViewHolder.ImgM_Folder.setVisibility(View.VISIBLE);
                myViewHolder.ImgM_Play.setVisibility(View.GONE);
//                myViewHolder.CrdMF_Use.setVisibility(View.GONE);

            }


            myViewHolder.LLMF_MainClk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (folder_nm.endsWith(".mp3") || folder_nm.endsWith(".MP3")) {
                        folder_pos_old = i;

                        pos_music_grid = -1;
                        Online_pos_old = -1;
                        resetdata();

                        onclick(new File(((VideoMusicFileManger) AudioFolderAdapter.this.musicFileMDS.get(i)).getFst_file_name()).getAbsolutePath(), i, 0);


                    } else {

                        File sdCardRoot = new File(new File(((VideoMusicFileManger) AudioFolderAdapter.this.musicFileMDS.get(i)).getFst_file_name()).getAbsolutePath());

                        File dir = new File(sdCardRoot.getAbsolutePath());
                        if (dir.exists()) {
                            musicFileMDS.clear();
                            if (dir.listFiles() != null) {

                                if (dir.listFiles().length > 0) {
                                    for (File f : dir.listFiles()) {


                                        if (f.getName().endsWith(".mp3") || f.getName().endsWith(".MP3") || f.isDirectory()) {
                                            if (!f.getName().startsWith(".")) {

                                                VideoMusicFileManger objfileitem = new VideoMusicFileManger();

                                                objfileitem.setFst_file_name(f.getAbsolutePath());
                                                musicFileMDS.add(objfileitem);
                                                AudioFolderAdapter.this.notifyDataSetChanged();

                                            }

                                            TVM_FileMang.setVisibility(View.GONE);
                                            RVM_FileMang.setVisibility(View.VISIBLE);

                                        } else {

                                            TVM_FileMang.setVisibility(View.VISIBLE);
                                            RVM_FileMang.setVisibility(View.GONE);

                                        }

                                        if (musicFileMDS.size() > 0) {
                                            TVM_FileMang.setVisibility(View.GONE);
                                            RVM_FileMang.setVisibility(View.VISIBLE);
                                        } else {
                                            TVM_FileMang.setVisibility(View.VISIBLE);
                                            RVM_FileMang.setVisibility(View.GONE);
                                        }
                                    }

                                } else {

                                    TVM_FileMang.setVisibility(View.VISIBLE);
                                    RVM_FileMang.setVisibility(View.GONE);

                                }

                            } else {

                                Toast.makeText(DhiMyMusicActivity.this, "No Data 3", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(DhiMyMusicActivity.this, "No Data 2", Toast.LENGTH_SHORT).show();

                        }
                        AudioFolderAdapter.this.notifyDataSetChanged();

                    }
                }
            });

            myViewHolder.ImgM_Play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (folder_pos_old == i) {
                        if (mp != null) {
                            if (mp.isPlaying()) {

                                myViewHolder.ImgM_Folder.setVisibility(View.VISIBLE);
                                myViewHolder.ImgM_Play.setVisibility(View.GONE);
//                                myViewHolder.CrdMF_Use.setVisibility(View.GONE);

                            } else {

                                myViewHolder.ImgM_Folder.setVisibility(View.GONE);
                                myViewHolder.ImgM_Play.setVisibility(View.VISIBLE);
//                                myViewHolder.CrdMF_Use.setVisibility(View.VISIBLE);

                            }
                        }

                        onclick(new File(((VideoMusicFileManger) AudioFolderAdapter.this.musicFileMDS.get(i)).getFst_file_name()).getAbsolutePath(), i, 1);

                    }


                }
            });

//            myViewHolder.CrdMF_Use.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (myViewHolder.ImgM_Play.getVisibility() == View.VISIBLE) {
//
//                        MpPause();
//
//                        if (filepath != null) {
//
//                            if (filzero == 0) {
//
//                                Toast.makeText(MyMusicActivity.this, "Please Select Correct Audio File", Toast.LENGTH_SHORT).show();
//
//                            } else {
//
//                                if (filepath.endsWith(".mp3")) {
////                                    executeAudio(filepath);
//                                } else {
//                                    Toast.makeText(MyMusicActivity.this, "Please Select only mp3 Audio file", Toast.LENGTH_SHORT).show();
//
//
//                                }
//
//
//                            }
//
//                        } else {
//                            Toast.makeText(MyMusicActivity.this, "Please Select Audio File", Toast.LENGTH_LONG).show();
//                        }
//
//
//                    } else {
//                        //  Toast.makeText(ActOnlineMusic.this, "Please Select Audio", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

        }

        public int getItemCount() {
            return musicFileMDS.size();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new android.content.CursorLoader(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.MediaColumns.DATE_ADDED);

    }

    @SuppressLint("Range")
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        try {
            getvideo_list.clear();
            cursor.moveToFirst();
            do {
                try {
                    if (!(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)).endsWith(".mp3") && cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)).endsWith(".MP3"))) {

                        VideoMusicInternalList musicList = new VideoMusicInternalList();

                        musicList.setFilepath(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
                        musicList.setFilesize(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)));
                        getvideo_list.add(musicList);
                    }
                } catch (Exception loader2) {
                    loader2.printStackTrace();
                }
            } while (cursor.moveToNext());

            RVM_List.setLayoutManager(new LinearLayoutManager(this));
            musicListAdp = new MusicListAdp(getvideo_list);
            RVM_List.setAdapter(musicListAdp);
            runLayoutAnimation(RVM_List);
            if (getvideo_list.size() > 0) {
                RVM_List.setVisibility(View.VISIBLE);
                TVM_List.setVisibility(View.GONE);
            } else {
                RVM_List.setVisibility(View.GONE);
                TVM_List.setVisibility(View.VISIBLE);
            }


        } catch (Exception loader22) {
            loader22.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public class MusicListAdp extends RecyclerView.Adapter<MusicListAdp.MyViewHolder> {

        private ArrayList<VideoMusicInternalList> arrayList;


        public class MyViewHolder extends RecyclerView.ViewHolder {

            AppCompatImageView Img_ML_Play, Img_ML_Pause;
            LinearLayout LLML_Name;
            AppCompatTextView TVML_MusicName, TVML_MusicTime;
//            CardView CrdML_Use;

            public MyViewHolder(View view) {
                super(view);
                this.LLML_Name = view.findViewById(R.id.LLML_Name);
                this.Img_ML_Play = view.findViewById(R.id.Img_ML_Play);
                this.Img_ML_Pause = view.findViewById(R.id.Img_ML_Pause);
                this.TVML_MusicName = view.findViewById(R.id.TVML_MusicName);
                this.TVML_MusicTime = view.findViewById(R.id.TVML_MusicTime);
//                this.CrdML_Use = view.findViewById(R.id.CrdML_Use);
            }
        }

        public MusicListAdp(ArrayList<VideoMusicInternalList> arrayList) {
            this.arrayList = arrayList;
        }

        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cust_music_audio, viewGroup, false));
        }

        @RequiresApi(api = 16)
        public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {

            myViewHolder.TVML_MusicName.setText(new File(((VideoMusicInternalList) this.arrayList.get(i)).getFilepath()).getName());


            try {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(((VideoMusicInternalList) this.arrayList.get(i)).getFilepath());
                String stringForTime = stringForTime(Integer.parseInt(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)));
                File file = new File(((VideoMusicInternalList) this.arrayList.get(i)).getFilepath());

                double bytes = file.length();
                double kilobytes = (bytes / 1024);
                double megabytes = (kilobytes / 1024);
                myViewHolder.TVML_MusicTime.setText(stringForTime + " | " + String.format("%.2f", megabytes) + " MB");


            } catch (Exception e) {
                e.printStackTrace();
            }
            try {


                if (pos_music_grid != i) {
                    myViewHolder.Img_ML_Play.setVisibility(View.GONE);
//                    myViewHolder.CrdML_Use.setVisibility(View.GONE);
                    myViewHolder.Img_ML_Pause.setVisibility(View.VISIBLE);
                } else if (mp != null) {

                    myViewHolder.Img_ML_Play.setVisibility(View.VISIBLE);
//                    myViewHolder.CrdML_Use.setVisibility(View.VISIBLE);
                    myViewHolder.Img_ML_Pause.setVisibility(View.GONE);
                } else {
                    myViewHolder.Img_ML_Pause.setVisibility(View.VISIBLE);
                    myViewHolder.Img_ML_Play.setVisibility(View.GONE);
//                    myViewHolder.CrdML_Use.setVisibility(View.GONE);

                }
            } catch (Exception e2) {
                e2.printStackTrace();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(e2);
            }
            myViewHolder.Img_ML_Play.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (pos_music_grid == i) {
                        if (mp != null) {
                            if (mp.isPlaying()) {

                                myViewHolder.Img_ML_Pause.setVisibility(View.VISIBLE);
                                myViewHolder.Img_ML_Play.setVisibility(View.GONE);
//                                myViewHolder.CrdML_Use.setVisibility(View.GONE);

                            } else {
                                myViewHolder.Img_ML_Pause.setVisibility(View.GONE);
                                myViewHolder.Img_ML_Play.setVisibility(View.VISIBLE);
//                                myViewHolder.CrdML_Use.setVisibility(View.VISIBLE);

                            }
                        }

                        onclick(new File(((VideoMusicInternalList) MusicListAdp.this.arrayList.get(i)).getFilepath()).getAbsolutePath(), i, 1);
                    }
                }
            });

            myViewHolder.Img_ML_Pause.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    pos_music_grid = i;


                    Online_pos_old = -1;
                    folder_pos_old = -1;
                    resetdata();

                    myViewHolder.Img_ML_Pause.setVisibility(View.GONE);
                    myViewHolder.Img_ML_Play.setVisibility(View.VISIBLE);
//                    myViewHolder.CrdML_Use.setVisibility(View.VISIBLE);

                    onclick(new File(((VideoMusicInternalList) MusicListAdp.this.arrayList.get(i)).getFilepath()).getAbsolutePath(), i, 0);

                }
            });

            myViewHolder.LLML_Name.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    pos_music_grid = i;
                    Online_pos_old = -1;
                    folder_pos_old = -1;
                    resetdata();

                    myViewHolder.Img_ML_Pause.setVisibility(View.GONE);
                    myViewHolder.Img_ML_Play.setVisibility(View.VISIBLE);
//                    myViewHolder.CrdML_Use.setVisibility(View.VISIBLE);
                    onclick(new File(((VideoMusicInternalList) MusicListAdp.this.arrayList.get(i)).getFilepath()).getAbsolutePath(), i, 0);


                }
            });

//            myViewHolder.CrdML_Use.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    if (myViewHolder.Img_ML_Play.getVisibility() == View.VISIBLE) {
//
//                        MpPause();
//
//                        if (filepath != null) {
//
//                            if (filzero == 0) {
//
//                                Toast.makeText(MyMusicActivity.this, "Please Select Correct Audio File", Toast.LENGTH_SHORT).show();
//
//                            } else {
//
//                                if (filepath.endsWith(".mp3")) {
////                                    executeAudio(filepath);
//                                } else {
//                                    Toast.makeText(MyMusicActivity.this, "Please Select only mp3 Audio file", Toast.LENGTH_SHORT).show();
//
//
//                                }
//
//
//                            }
//
//                        } else {
//                            Toast.makeText(MyMusicActivity.this, "Please Select Audio File", Toast.LENGTH_LONG).show();
//                        }
//
//
//                    } else {
//                        //  Toast.makeText(ActOnlineMusic.this, "Please Select Audio", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            });
        }

        public int getItemCount() {
            return this.arrayList.size();
        }
    }


    public static String stringForTime(int i) {
        if (i > 0) {
            if (i < 86400000) {
                i /= 1000;
                int i2 = i % 60;
                int i3 = (i / 60) % 60;
                i /= 3600;
                Formatter formatter = new Formatter(new StringBuilder(), Locale.getDefault());
                if (i > 0) {
                    return formatter.format("%d:%02d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(i3), Integer.valueOf(i2)}).toString();
                }
                return formatter.format("%02d:%02d", new Object[]{Integer.valueOf(i3), Integer.valueOf(i2)}).toString();
            }
        }
        return "00:00";
    }

    @Override
    public void onclick(String str, int i, int i2) {
        filepath = str;

        if (i2 == 0) {
            new_method_play(str);
        } else if (i2 == 1) {
            performVideoViewClick_music();
        }
    }

    private void new_method_play(String str) {

        try {

            if (mp != null) {
                mp.reset();

            }
            mp = MediaPlayer.create(getApplicationContext(), Uri.parse(str));
            mp.setLooping(true);
            mp.start();

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(String.valueOf(Uri.parse(str)));
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long timeInmillisec = Long.parseLong(time);
            final long duration = timeInmillisec / 1000;

            TVM_lftTime.setText("00:00:00");
            TVM_RigtTime.setText(getTime((int) duration));

            SBM_songCut.setMinValue(0);
            SBM_songCut.setMaxValue(duration);
            if (!isSingle) {
                SBM_songCut.setFixGap(Viduration);
                SBM_songCut.apply();
            }
            SBM_songCut.setEnabled(true);

            mStartMs = 0;
            mEndMs = 0;
            filzero = 1;

            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(final MediaPlayer mp) {

                    SBM_songCut.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                        @Override
                        public void valueChanged(Number minValue, Number maxValue) {
                            try {

                                int value = minValue.intValue();
                                int value2 = maxValue.intValue();


                                TVM_lftTime.setText(getTime(value));
                                mStartMs = value * 1000;
                                long exoplyStrt = (int) value * 1000;
                                mp.seekTo((int) exoplyStrt);


                                TVM_RigtTime.setText(getTime(value2));
                                mEndMs = value2 * 1000;


                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    });


                    final Handler handler = new Handler();
                    handler.postDelayed(r = new Runnable() {
                        @Override
                        public void run() {

                            try {
                                if (mEndMs != 0) {

                                    if (mp.getCurrentPosition() >= mEndMs) mp.seekTo(mStartMs);


                                    handler.postDelayed(r, 1000);
                                } else {
                                    mEndMs = (int) (duration * 1000);
                                    if (mp.getCurrentPosition() >= mEndMs) mp.seekTo(mStartMs);

                                    handler.postDelayed(r, 1000);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();


                            }


                        }
                    }, 1000);


                }
            });
        } catch (Exception e) {
            e.printStackTrace();

            TVM_lftTime.setText("00:00");
            TVM_RigtTime.setText("00:00");
            filzero = 0;
            SBM_songCut.setMinValue(0);
            SBM_songCut.setMaxValue(0);
            SBM_songCut.setEnabled(false);

        }

    }


    private void resetdata() {

        if (getvideo_list.size() > 0) {
            musicListAdp.notifyDataSetChanged();
        }
//        if (videoOnlineAudioMDS.size() > 0) {
//            onlineMusicAdp.notifyDataSetChanged();
//        }
//        if (musicFileMDS.size() > 0) {
//            audioFolderAdapter.notifyDataSetChanged();
//        }


    }


    private void performVideoViewClick_music() {
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.pause();
                return;
            }
            mp.start();
        }
    }

    private void MpPause() {
        if (mp != null) {
            mp.pause();
            mp.release();
            mp = null;
        }
    }

    public static String getTime(int seconds) {
        int hr = seconds / 3600;
        int rem = seconds % 3600;
        int mn = rem / 60;
        int sec = rem % 60;
        return String.format("%02d", mn) + ":" + String.format("%02d", sec);
        //return String.format("%02d", hr) + ":" + String.format("%02d", mn) + ":" + String.format("%02d", sec);
    }

//    private void executeAudio(String path) {
//
//        if (!MainFileFolder.exists())
//            MainFileFolder.mkdir();
//
//        if (!TempFolder.exists())
//            TempFolder.mkdir();
//
//
//        if (UserSelectAudio.exists()) {
//            if (UserSelectAudio.delete()) {
//                Log.e("TrimeActivity", "file delete");
//
//            } else {
//                Log.e("TrimeActivity", "file not Deleted ");
//            }
//            Log.e("TrimeActivity", "exit");
//        } else {
//            Log.e("TrimeActivity", "not exit");
//        }
//
//        final String filePath = UserSelectAudio.getAbsolutePath();
//
//        EpVideo epVideo = new EpVideo(path);
//        epVideo.clip((mStartMs / 1000), (mEndMs - mStartMs) / 1000);
//        EpEditor.OutputOption outputOption = new EpEditor.OutputOption(filePath);
//
//        EpEditor.exec(epVideo, outputOption, new OnEditorListener() {
//            @Override
//            public void onSuccess() {
//
////                Log.e("TrimeActivity", "TrimeActivity***************************  onSuccess   1");
//                runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//
////                        Log.e("TrimeActivity", "TrimeActivity***************************  onSuccess   2");
//                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//
//                        finish();
//
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure() {
//
//                Log.e("TrimeActivity", "TrimeActivity***************************  onFailure   1");
//
//            }
//
//            @Override
//            public void onProgress(final float progress) {
//                Log.e("TrimeActivity", "TrimeActivity***********---------------------------------------onProgress");
//            }
//        });
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        new MainAds().showBannerAds(this, null);

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MpPause();
        new MainAds().showBackInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });

    }
}