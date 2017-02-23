package com.huihui.video.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.huihui.video.R;
import com.huihui.video.bean.Video;

import java.io.IOException;

import static com.huihui.video.R.id.btn_pause;
import static com.huihui.video.R.id.btn_play;
import static java.lang.Thread.sleep;


public class SurfaceActivity extends BaseActivity implements SurfaceHolder.Callback, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private SurfaceView mSurfaceView;
    private SeekBar mSeekBar;

    private Video mVideo;

    private String path;

    private MediaPlayer mMediaPlayer;

    private boolean isPlaying;
    private Button mBtnPlay;
    private Button mBtnPause;
    private Button mBtnRePlay;
    private Button mBtnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mVideo = getIntent().getParcelableExtra("data");

        path = mVideo.getPath();

        setContentView(R.layout.activity_surface);

        mSurfaceView = ((SurfaceView) findViewById(R.id.surface_view));

        mSeekBar = ((SeekBar) findViewById(R.id.seek_bar));

        mBtnPlay = ((Button) findViewById(btn_play));
        mBtnPause = ((Button) findViewById(btn_pause));
        mBtnRePlay = ((Button) findViewById(R.id.btn_replay));
        mBtnStop = ((Button) findViewById(R.id.btn_stop));


        mSurfaceView.getHolder().addCallback(this);


        mSeekBar.setOnSeekBarChangeListener(this);

        mMediaPlayer = new MediaPlayer();

        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);

    }

    public void play(View view) {

        Toast.makeText(SurfaceActivity.this, "play", Toast.LENGTH_LONG).show();
    }

    public void pause(View view) {
        Toast.makeText(SurfaceActivity.this, "pause", Toast.LENGTH_LONG).show();
    }

    public void replay(View view) {
        //Toast.makeText(SurfaceActivity.this, "replay", Toast.LENGTH_LONG).show();

        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {

            mMediaPlayer.seekTo(0);


            mBtnPause.setText("暂停");

            return;

        }

        isPlaying = false;

        play(0);
    }

    public void stop(View view) {
        //Toast.makeText(SurfaceActivity.this, "stop", Toast.LENGTH_LONG).show();

        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {

            mMediaPlayer.stop();

            mMediaPlayer.release();

            mMediaPlayer = null;

            mBtnPlay.setEnabled(true);

            isPlaying = false;

        }
    }

    /**
     * 播放视频
     *
     * @param postion
     */
    private void play(final int postion) {

        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // 设置播放的视频源

        try {
            mMediaPlayer.setDataSource(path);

            // 设置显示视频的SurfaceHolder

            mMediaPlayer.setDisplay(mSurfaceView.getHolder());

            Log.e(TAG, "开始装载");
            mMediaPlayer.prepareAsync();

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {


                    Log.e(TAG, "装载完成");

                    mMediaPlayer.start();

                    // 按照初始位置播放

                    mMediaPlayer.seekTo(postion);

                    // 设置进度条的最大进度为视频流的最大播放时长

                    mSeekBar.setMax(mMediaPlayer.getDuration());

                    // 开始线程，更新进度条的刻度

                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                            try {
                                isPlaying = true;

                                while (isPlaying) {

                                    int currentPosition = mMediaPlayer.getCurrentPosition();

                                    mSeekBar.setProgress(currentPosition);


                                }
                                sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                        }
                    }).start();

                    mBtnPlay.setEnabled(false);
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //播放完毕时回调
        mBtnPlay.setEnabled(true);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {

        isPlaying = false;
        // 发生错误重新播放
        play(0);
        return false;
    }
}
