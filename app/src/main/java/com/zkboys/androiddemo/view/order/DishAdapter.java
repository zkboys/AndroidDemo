package com.zkboys.androiddemo.view.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zkboys.androiddemo.R;
import com.zkboys.sdk.model.DishCategoryInfo;
import com.zkboys.sdk.model.DishInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DishAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected LayoutInflater mLayoutInflater;
    private List<DishCategoryInfo> mData;

    private Map<Integer, DishCategoryInfo> mCategoryInfoMap;
    private Map<Integer, DishInfo> mDishInfoMap;

    private Context mContext;
    private int mCurrentSelected = 0;

    public enum ITEM_TYPE {
        ITEM_TYPE_CATEGORY,
        ITEM_TYPE_DISH,
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_item_dish_category_name)
        TextView mCategoryName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class DishViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_item_dish_name)
        TextView mName;
        @Bind(R.id.tv_item_dish_price)
        TextView mPrice;
        @Bind(R.id.tv_item_dish_count)
        TextView mCount;

        public DishViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick(DishInfo dishInfo, int position);
    }

    private OnItemClickListener onItemClickListener;

    public DishAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        mCategoryInfoMap = new HashMap<>();
        mDishInfoMap = new HashMap<>();
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE.ITEM_TYPE_CATEGORY.ordinal()) {
            return new CategoryViewHolder(mLayoutInflater.inflate(R.layout.item_dish_category_name, parent, false));
        }
        final DishViewHolder holder = new DishViewHolder(mLayoutInflater.inflate(R.layout.item_dish, parent, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                setCurrentSelectedPosition(position);
                DishInfo dishInfo = getDish(position);
                increaseDishCount(position);
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(dishInfo, position);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        DishCategoryInfo categoryInfo = mData.get(position);
        if (holder instanceof CategoryViewHolder) {
            DishCategoryInfo dishCategoryInfo = getCategory(position);
            ((CategoryViewHolder) holder).mCategoryName.setText(dishCategoryInfo.getName());
            return;
        }
        DishInfo dishInfo = getDish(position);
        int count = dishInfo.getTotal();

        DishViewHolder dishViewHolder = ((DishViewHolder) (holder));
        dishViewHolder.mName.setText(dishInfo.getName());
        dishViewHolder.mPrice.setText("￥" + String.valueOf(dishInfo.getPrice()));

        if (count > 0) {
            dishViewHolder.mCount.setText(String.valueOf(count));
            dishViewHolder.mCount.setVisibility(View.VISIBLE);
        } else {
            dishViewHolder.mCount.setText("");
            dishViewHolder.mCount.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        int categoryCount = mData.size();
        int dishCount = 0;
        for (int i = 0; i < categoryCount; i++) {
            List<DishInfo> dishInfos = mData.get(i).getDishes();
            if (dishInfos != null && dishInfos.size() > 0) {
                dishCount += dishInfos.size();
            }
        }
        return categoryCount + dishCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (isCategory(position)) {
            return ITEM_TYPE.ITEM_TYPE_CATEGORY.ordinal();
        }
        return ITEM_TYPE.ITEM_TYPE_DISH.ordinal();
    }

    public boolean isCategory(int position) {
        return mCategoryInfoMap.get(position) != null;
    }

    public DishCategoryInfo getCategory(int position) {
        return mCategoryInfoMap.get(position);
    }

    public DishInfo getDish(int position) {
        return mDishInfoMap.get(position);
    }

    public void increaseDishCount(int position) {
        DishInfo dishInfo = getDish(position);
        int oldCount = dishInfo.getTotal();
        int newCount = oldCount + 1;

        dishInfo.setTotal(newCount);
        notifyItemChanged(position);
    }

    public void decreaseDishCount(int position) {
        DishInfo dishInfo = getDish(position);
        int oldCount = dishInfo.getTotal();
        int newCount = oldCount - 1;
        if (newCount < 0) {
            newCount = 0;
        }

        dishInfo.setTotal(newCount);
        notifyItemChanged(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void initData(List<DishCategoryInfo> data) {
        mData.clear();
        mData.addAll(data);

        int categoryCount = mData.size();
        int dishCount = 0;
        for (int i = 0; i < categoryCount; i++) {
            DishCategoryInfo dishCategoryInfo = mData.get(i);
            if (i == 0) {
                mCategoryInfoMap.put(i, dishCategoryInfo);
            }
            List<DishInfo> dishInfos = dishCategoryInfo.getDishes();
            if (dishInfos != null && dishInfos.size() > 0) {
                for (int j = 0; j < dishInfos.size(); j++) {
                    mDishInfoMap.put(dishCount + i + j + 1, dishInfos.get(j));
                }
                dishCount += dishInfos.size();
                if (i < categoryCount - 1) {
                    System.out.println(dishCount + i + 1);
                    mCategoryInfoMap.put(dishCount + i + 1, mData.get(i + 1));
                }
            }
        }

        notifyDataSetChanged();
    }

    public void setCurrentSelectedPosition(int currentSelectedPosition) {
        int oldPosition = this.mCurrentSelected;
        this.mCurrentSelected = currentSelectedPosition;
        notifyItemChanged(oldPosition);
        notifyItemChanged(currentSelectedPosition);
    }
}