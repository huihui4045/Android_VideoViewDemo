package com.huihui.video.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huihui.video.R;
import com.huihui.video.bean.Video;

import java.util.List;

/**
 * Created by gavin on 2017/2/22.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context mContext;

    private List<Video> mVideos;

    public VideoAdapter(Context mContext, List<Video> mVideos) {
        this.mContext = mContext;
        this.mVideos = mVideos;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false));
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {

        Video video = mVideos.get(position);
        if (video != null) {


            holder.mTvName.setText(video.getTitle());

            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(video.getPath());
            Bitmap bitmap = media.getFrameAtTime();
            holder.mImageView.setImageBitmap(bitmap);

        }
    }


    @Override
    public int getItemCount() {
        return mVideos == null ? null : mVideos.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvName;
        private final ImageView mImageView;


        public VideoViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.image_thumb);
            mTvName = ((TextView) itemView.findViewById(R.id.tv_videoName));
        }
    }
}
