package com.zkboys.androiddemo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.utils.LogUtil;
import com.zkboys.sdk.model.MerchantInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectStoreMerchantAdapter extends RecyclerView.Adapter<SelectStoreMerchantAdapter.MerchantViewHolder> {

    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    private List<MerchantInfo> merchantInfoList;
    private int nowChoosePosition = 0;

    public interface OnChooseListener {
        void onChoose(MerchantInfo merchantInfo);
    }

    private OnChooseListener chooseListener;

    public SelectStoreMerchantAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        merchantInfoList = new ArrayList<>();
    }

    @Override
    public MerchantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.d("SelectStoreActivity", "onCreateViewHolder");
        final MerchantViewHolder holder = new MerchantViewHolder(mLayoutInflater.inflate(R.layout.item_select_merchant, parent, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                if (chooseListener != null && position != nowChoosePosition) {
                    final MerchantInfo merchantInfo = merchantInfoList.get(position);
                    chooseListener.onChoose(merchantInfo);
                    setNowChoosePosition(position);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MerchantViewHolder holder, final int position) {
        LogUtil.d("SelectStoreActivity", "onBindViewHolder");
        final MerchantInfo merchantInfo = merchantInfoList.get(position);

        holder.mName.setText(merchantInfo.getName());
        holder.mLogo.setImageURI(merchantInfo.getLogo());

        if (position == nowChoosePosition) {
            // 选中
            holder.mContainer.setBackgroundResource(R.color.white);
            holder.mEnter.setImageResource(R.drawable.ic_right_accent);
            holder.mName.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            holder.mContainer.setBackgroundResource(R.color.colorPrimary);
            holder.mEnter.setImageResource(R.drawable.ic_right);
            holder.mEnter.setAlpha(Float.valueOf("0.5"));
            holder.mName.setTextColor(mContext.getResources().getColor(R.color.textColorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        return merchantInfoList.size();
    }

    public void setChooseListener(OnChooseListener chooseListener) {
        this.chooseListener = chooseListener;
    }

    public void initData(List<MerchantInfo> data) {
        merchantInfoList.clear();
        merchantInfoList.addAll(data);
        notifyDataSetChanged();
    }

    public void setNowChoosePosition(int nowChoosePosition) {
        int oldPosition = this.nowChoosePosition;
        this.nowChoosePosition = nowChoosePosition;
        notifyItemChanged(oldPosition);
        notifyItemChanged(nowChoosePosition);
    }

    public class MerchantViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ll_item_merchant_container)
        LinearLayout mContainer;

        @Bind(R.id.iv_item_merchant_logo)
        SimpleDraweeView mLogo;

        @Bind(R.id.tv_item_merchant_name)
        TextView mName;

        @Bind(R.id.iv_item_merchant_enter)
        ImageView mEnter;

        public MerchantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
