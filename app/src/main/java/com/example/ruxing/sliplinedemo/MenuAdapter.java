package com.example.ruxing.sliplinedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ruxing on 2018/3/25.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context mContext;
    private List<String> mData;

    public MenuAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mData = list;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.mTvName.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mLiContent;
        private TextView mTvName;

        public MenuViewHolder(View itemView) {
            super(itemView);
            mLiContent=(LinearLayout)itemView.findViewById(R.id.ll_content);
            mTvName=(TextView)itemView.findViewById(R.id.tv_name);
        }
    }
}
