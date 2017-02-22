package com.huihui.video.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huihui.video.R;

import java.util.List;

/**
 * Created by gavin on 2017/2/22.
 */

public class MainViewHolderAdapter extends RecyclerView.Adapter<MainViewHolderAdapter.MyViewHolder> {

    private List<String> datas;

    private Context mContext;

    private OnRecyclerViewItemClickListener listener;

    public MainViewHolderAdapter(List<String> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item, parent,false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.TvName.setText(datas.get(position));

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView TvName;

        public MyViewHolder(View itemView) {
            super(itemView);

            TvName = (TextView) itemView.findViewById(R.id.tv_type);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            listener.onItemClick(v, getAdapterPosition());

        }
    }
}
