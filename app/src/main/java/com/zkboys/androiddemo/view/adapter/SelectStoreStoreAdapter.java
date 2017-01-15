package com.zkboys.androiddemo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zkboys.androiddemo.R;
import com.zkboys.sdk.model.StoreInfo;

import java.util.ArrayList;
import java.util.List;

public class SelectStoreStoreAdapter extends RecyclerView.Adapter<SelectStoreStoreAdapter.MyViewHolder> {
    protected LayoutInflater mLayoutInflater;
    private List<StoreInfo> mStores;
    private Context mContext;

    public interface OnChooseListener {
        void onChoose(StoreInfo storeInfo);
    }

    private OnChooseListener chooseListener;

    public SelectStoreStoreAdapter(Context context) {
        this.mContext = context;
        mStores = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyViewHolder holder = new MyViewHolder(mLayoutInflater.inflate(R.layout.item_select_store, parent, false));
        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (chooseListener != null) {
                    StoreInfo storeInfo = mStores.get(position);
                    chooseListener.onChoose(storeInfo);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StoreInfo store = mStores.get(position);
        holder.bt.setText(store.getName());
    }

    @Override
    public int getItemCount() {
        return mStores.size();
    }

    public void setChooseListener(OnChooseListener chooseListener) {
        this.chooseListener = chooseListener;
    }

    public void initData(List<StoreInfo> data) {
        mStores.clear();
        mStores.addAll(data);
        notifyDataSetChanged();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        Button bt;

        public MyViewHolder(View view) {
            super(view);
            bt = (Button) view.findViewById(R.id.bt_item_select_store);
        }
    }
}