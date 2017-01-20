package com.zkboys.androiddemo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zkboys.androiddemo.R;
import com.zkboys.sdk.model.DishCategoryInfo;

import java.util.ArrayList;
import java.util.List;

public class DishCategoryAdapter extends RecyclerView.Adapter<DishCategoryAdapter.MyViewHolder> {
    protected LayoutInflater mLayoutInflater;
    private List<DishCategoryInfo> mData;
    private Context mContext;
    private int mRVLayout = R.layout.item_dish_category;
    private int mCurrentSelected = 0;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item;

        public MyViewHolder(View view) {
            super(view);
            item = (TextView) view.findViewById(R.id.tv_item_dish_category);
        }
    }

    public interface OnItemClickListener {
        void onClick(DishCategoryInfo categoryInfo);
    }

    private OnItemClickListener onItemClickListener;

    public DishCategoryAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyViewHolder holder = new MyViewHolder(mLayoutInflater.inflate(mRVLayout, parent, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                setCurrentSelectedPosition(position);
                if (onItemClickListener != null) {
                    DishCategoryInfo categoryInfo = mData.get(position);
                    onItemClickListener.onClick(categoryInfo);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DishCategoryInfo categoryInfo = mData.get(position);
        holder.item.setText(categoryInfo.getName());

        if (position == mCurrentSelected) {
            holder.item.setBackground(mContext.getDrawable(R.drawable.bg_item_category_active));
            holder.item.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            holder.item.setBackground(null);
            holder.item.setTextColor(mContext.getResources().getColor(R.color.textColorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void initData(List<DishCategoryInfo> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setCurrentSelectedPosition(int currentSelectedPosition) {
        int oldPosition = this.mCurrentSelected;
        this.mCurrentSelected = currentSelectedPosition;
        notifyItemChanged(oldPosition);
        notifyItemChanged(currentSelectedPosition);
    }
}