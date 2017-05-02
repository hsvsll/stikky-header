package com.practice.hs.mystickyheaderapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by qiyue on 17/4/30.
 */
public class StickyRecycleViewAdapter extends RecyclerView.Adapter<StickyRecycleViewAdapter.MyStickyViewHolder>{
    private List<String> mContentList;

    public StickyRecycleViewAdapter(List<String> contentList){
        this.mContentList = contentList;
    }
    @Override
    public MyStickyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyStickyViewHolder holder = new MyStickyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycleview_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyStickyViewHolder holder, int position) {
        holder.mTv.setText(mContentList.get(position));
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    class MyStickyViewHolder extends RecyclerView.ViewHolder{
        TextView mTv;
        public MyStickyViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.tvContent);
        }
    }
}
