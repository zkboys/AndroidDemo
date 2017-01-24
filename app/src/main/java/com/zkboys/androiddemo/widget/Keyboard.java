package com.zkboys.androiddemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.utils.DensityUtil;

import java.util.Arrays;
import java.util.List;

public class Keyboard extends RecyclerView {
    private KeyboardAdapter mAdapter;

    private ItemClickListener itemClickListener;

    private List<String> mItems;

    private RecyclerView.LayoutManager mLayoutManager;

    private int[] mItemPadding;

    public Keyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mItems = Arrays.asList(getResources().getStringArray(R.array.number_keyboard));
        mItemPadding = new int[]{0, 30, 0, 30};
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        this.setLayoutManager(mLayoutManager);
        mAdapter = new KeyboardAdapter(mItems, getContext());
        this.setAdapter(mAdapter);
    }

    public Keyboard setSpanCount(int count) {
        mLayoutManager = new GridLayoutManager(getContext(), count);
        this.setLayoutManager(mLayoutManager);
        return this;
    }

    public Keyboard setItemPadding(int[] padding) {
        mItemPadding = padding;
        return this;
    }

    public Keyboard setItems(List<String> items) {
        this.mItems = items;
        mAdapter.setItems(this.mItems);
        return this;
    }

    public Keyboard setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }

    public interface ItemClickListener {
        void onClick(View v, String item);
    }


    class KeyboardAdapter extends RecyclerView.Adapter<KeyboardAdapter.KeyboardHolder> {
        private List<String> mItems;
        private Context context;

        public KeyboardAdapter(List<String> items, Context context) {
            this.mItems = items;
            this.context = context;
        }

        public void setItems(List<String> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        @Override
        public KeyboardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final KeyboardHolder holder = new KeyboardHolder(LayoutInflater.from(context).inflate(R.layout.item_keyboard, parent, false));
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    String itemText = mItems.get(position);
                    itemClickListener.onClick(v, itemText);
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(KeyboardHolder holder, int position) {
            String itemText = mItems.get(position);
            holder.item.setText(itemText);
            holder.item.setPadding(
                    DensityUtil.dip2px(context, mItemPadding[0]),
                    DensityUtil.dip2px(context, mItemPadding[1]),
                    DensityUtil.dip2px(context, mItemPadding[2]),
                    DensityUtil.dip2px(context, mItemPadding[3])
            );
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        class KeyboardHolder extends RecyclerView.ViewHolder {
            View container;
            Button item;

            public KeyboardHolder(View view) {
                super(view);
                container = view;
                item = (Button) view.findViewById(R.id.btn_item_keyboard_item);
            }
        }
    }

}
