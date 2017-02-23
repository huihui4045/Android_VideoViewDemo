package com.huihui.video.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.huihui.video.adapter.MainViewHolderAdapter;
import com.huihui.video.adapter.OnRecyclerViewItemClickListener;
import com.huihui.video.R;
import com.huihui.video.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnRecyclerViewItemClickListener {

    private RecyclerView mRecyclerView;

    private MainViewHolderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = ((RecyclerView) findViewById(R.id.recycler));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        List<String> datas = new ArrayList<>();
        datas.add("Video视频播放器");
        datas.add("Surface视频播放器");
        datas.add("音频播放器");

        mAdapter = new MainViewHolderAdapter(datas, getApplicationContext());
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getApplicationContext(), LinearLayoutManager.VERTICAL));

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(View view, int position) {

        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(MainActivity.this, VideoActivity.class);
                break;
            case 1:
                intent = new Intent(MainActivity.this, SurfaceActivity.class);
                break;
            default:
                intent = new Intent(getApplicationContext(), MediaActivity.class);


                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
