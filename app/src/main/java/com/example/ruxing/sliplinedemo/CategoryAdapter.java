package com.example.ruxing.sliplinedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ruxing on 2018/3/25.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context mContext;
    private List<CategoryModel> mData;
    private int preClickPosition = 0;

    private OnItemClickListener mListener;

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void isShowLine(int position, boolean isShow) {
        mData.get(position).setShowLine(isShow);
        notifyItemChanged(position);
    }

    public CategoryAdapter(Context context, List<CategoryModel> list) {
        this.mContext = context;
        this.mData = list;
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        holder.mTvName.setText(mData.get(position).getName());
        if (mData.get(position).isSelected()) {
            holder.mTvName.setTextColor(mContext.getResources().getColor(R.color.color_red));
        } else {
            holder.mTvName.setTextColor(mContext.getResources().getColor(R.color.color_black));
        }
        if (mData.get(position).isShowLine()) {
            holder.mIvLine.setVisibility(View.VISIBLE);
        } else {
            holder.mIvLine.setVisibility(View.INVISIBLE);
        }
        holder.mLlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != preClickPosition) {
                    mData.get(preClickPosition).setSelected(false);
                    mData.get(preClickPosition).setShowLine(false);
                    mData.get(position).setSelected(true);
                    notifyItemChanged(preClickPosition);
                    notifyItemChanged(position);
                    preClickPosition = position;
                }
                mListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mLlContent;
        private TextView mTvName;
        public ImageView mIvLine;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            mLlContent = (LinearLayout) itemView.findViewById(R.id.ll_content);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mIvLine = (ImageView) itemView.findViewById(R.id.iv_line);
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }
}
