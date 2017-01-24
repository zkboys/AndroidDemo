package com.zkboys.androiddemo.view.table_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zkboys.androiddemo.R;
import com.zkboys.sdk.model.TableInfo;

import java.util.ArrayList;
import java.util.List;


public class TableListAdapter extends RecyclerView.Adapter<TableListAdapter.MyViewHolder> {
    private List<TableInfo> tableInfoList = new ArrayList<>();
    private Context context;
    private OnTableClickListener clickListener;

    public interface OnTableClickListener {
        void onClick(int position, TableInfo tableInfo);
    }

    public TableListAdapter(Context context) {
        this.context = context;
    }

    public void initData(List<TableInfo> data) {
        this.tableInfoList.clear();
        this.tableInfoList.addAll(data);
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnTableClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public TableListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final TableListAdapter.MyViewHolder holder = new TableListAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_table, parent, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                final TableInfo table = tableInfoList.get(position);
                if (clickListener != null) {
                    clickListener.onClick(position, table);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(TableListAdapter.MyViewHolder holder, int position) {

        String free = context.getString(R.string.table_free);
        String opened = context.getString(R.string.table_opened);
        String dining = context.getString(R.string.table_dining);
        String cleaning = context.getString(R.string.table_cleaning);
        String reserved = context.getString(R.string.table_reserved);
        String locked = context.getString(R.string.table_locking);

        TableInfo table = tableInfoList.get(position);
        holder.title.setText(table.getName());
        holder.userNum.setText(table.getSeatNum() + "人桌/" + table.getSeatedNum() + "人");


        holder.container.setBackground(context.getDrawable(R.drawable.bg_table_item_gray));
        holder.status.setText(free);

//            if (table.isFree()) {
//                holder.container.setBackground(context.getDrawable(R.drawable.bg_table_item_gray));
//                holder.status.setText(free);
//            }

        if (table.isOpened()) {
            holder.container.setBackground(context.getDrawable(R.drawable.bg_table_item_green));
            holder.status.setText(opened);
        }

        if (table.isDining()) {
            holder.container.setBackground(context.getDrawable(R.drawable.bg_table_item_blue));
            holder.status.setText(dining);
        }

        if (table.isCleaning()) {
            holder.container.setBackground(context.getDrawable(R.drawable.bg_table_item_orange));
            holder.status.setText(cleaning);
        }

        if (table.isReserved()) {
            holder.container.setBackground(context.getDrawable(R.drawable.bg_table_item_yellow));
            holder.status.setText(reserved);
        }

        if (table.isLocked()) {
            holder.container.setBackground(context.getDrawable(R.drawable.bg_table_item_red));
            holder.status.setText(locked);
        }

    }

    @Override
    public int getItemCount() {
        return tableInfoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        View container;
        TextView title;
        TextView status;
        TextView userNum;

        public MyViewHolder(View view) {
            super(view);
            container = view;
            title = (TextView) view.findViewById(R.id.tv_item_table_title);
            status = (TextView) view.findViewById(R.id.tv_item_table_status);
            userNum = (TextView) view.findViewById(R.id.tv_item_table_user_num);
        }
    }
}
