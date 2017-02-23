package com.huihui.video.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.huihui.video.DividerGridItemDecoration;
import com.huihui.video.R;
import com.huihui.video.VideoProvider;
import com.huihui.video.adapter.OnRecyclerViewItemClickListener;
import com.huihui.video.adapter.VideoAdapter;
import com.huihui.video.bean.Video;

import java.util.List;

public class VideoActivity extends BaseActivity implements OnRecyclerViewItemClickListener {

    private RecyclerView mRecyclerVideo;
    private VideoView mVideoView;
    private VideoAdapter mVideoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        VideoProvider mVideoProvider = new VideoProvider(getApplicationContext());

        List<Video> videos = mVideoProvider.getList();

        mRecyclerVideo = ((RecyclerView) findViewById(R.id.recycler_video));

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerVideo.setLayoutManager(mGridLayoutManager);

        mRecyclerVideo.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));

        if (videos != null && videos.size() > 0) {

            mVideoAdapter = new VideoAdapter(getApplicationContext(), videos);
            mRecyclerVideo.setAdapter(mVideoAdapter);
        }


        mVideoAdapter.setOnItemClickListener(this);


        mVideoView = ((VideoView) findViewById(R.id.video_view));

        mVideoView.setMediaController(new MediaController(this));

        mVideoView.setOnCompletionListener(new MyPlayerOnCompletionListener());

        Video video = videos.get(1);

        mVideoView.setVideoPath(video.getPath());

        setVideoViewLayoutParams(mVideoView, 1);

        mVideoView.start();


    }


    /**
     * 设置videiview的全屏和窗口模式
     *
     * @param paramsType 标识 1为全屏模式 2为窗口模式
     */
    public void setVideoViewLayoutParams(VideoView videoView, int paramsType) {
        //全屏模式
        if (1 == paramsType) {
            //设置充满整个父布局
            RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            //设置相对于父布局四边对齐
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            //为VideoView添加属性
            videoView.setLayoutParams(LayoutParams);
        } else {
            //窗口模式
            //获取整个屏幕的宽高
            DisplayMetrics DisplayMetrics = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(DisplayMetrics);
            //设置窗口模式距离边框50
            int videoHeight = DisplayMetrics.heightPixels - 50;
            int videoWidth = DisplayMetrics.widthPixels - 50;
            RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(videoWidth, videoHeight);
            //设置居中
            LayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            //为VideoView添加属性
            videoView.setLayoutParams(LayoutParams);
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        Video itemData = mVideoAdapter.getItemData(position);

        Intent intent = new Intent(getApplicationContext(), SurfaceActivity.class);

        intent.putExtra("data",itemData);

        startActivity(intent);

    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText(VideoActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }
}
