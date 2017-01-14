package com.zkboys.androiddemo.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.presenter.TableFragmentPresenter;
import com.zkboys.androiddemo.presenter.vus.ITableFragmentPresenter;
import com.zkboys.androiddemo.view.activities.CustomerInfoActivity;
import com.zkboys.sdk.common.C;
import com.zkboys.sdk.model.TableInfo;
import com.zkboys.sdk.model.TablesInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TableListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TableListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TableListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TABLES = "tables";

    // TODO: Rename and change types of parameters
    TablesInfo tables;
    private TableAdapter mAdapter;
    private RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;
    private ITableFragmentPresenter presenter;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private String tabRegionId;

    public TableListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TableListFragment newInstance(TablesInfo tablesInfo) {
        TableListFragment fragment = new TableListFragment();
        Bundle args = new Bundle();
        args.putParcelable(TABLES, tablesInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tables = getArguments().getParcelable(TABLES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tables, container, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 5);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_main_tables);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new TableAdapter(tables.getTableList(), getContext());
        recyclerView.setAdapter(mAdapter);

        presenter = new TableFragmentPresenter(this);
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.srl_tables);
        mSwipeRefreshWidget.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
//        mSwipeRefreshWidget.setProgressViewOffset(true, 150, 300); //调整进度条距离屏幕顶部的距离
        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshWidget.setRefreshing(true);
                presenter.pullRefresh();
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setTables(List<TablesInfo> tableInfoList) {
        // 先清空再赋值
        this.tables.setTableList(new ArrayList<TableInfo>());
        if (tableInfoList != null && tableInfoList.size() > 0) {
            for (int i = 0; i < tableInfoList.size(); i++) {
                if (this.tabRegionId.equals(tableInfoList.get(i).getTabRegionId())) {
                    this.tables.setTableList(tableInfoList.get(i).getTableList());
                    break;
                }
            }
        }
        mAdapter.setTables(this.tables.getTableList());
        mSwipeRefreshWidget.setRefreshing(false);
        Toast.makeText(getActivity(), getContext().getString(R.string.refresh_success), Toast.LENGTH_SHORT).show();
    }

    public void showRefreshError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        mSwipeRefreshWidget.setRefreshing(false);
    }

    public void setTabRegionId(String tabRegionId) {
        this.tabRegionId = tabRegionId;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder> {
        private List<TableInfo> tableInfoList;
        private Context context;

        public TableAdapter(List<TableInfo> tableInfoList, Context context) {
            this.tableInfoList = tableInfoList;
            this.context = context;
        }

        public void setTables(List<TableInfo> tableInfoList) {
            this.tableInfoList = tableInfoList;
            notifyDataSetChanged();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_table, parent, false));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    TableInfo table = tableInfoList.get(position);
                    String status = table.getTabStatus();
                    switch (status) {
                        case C.TableStatus.STATU_FREE:
                            CustomerInfoActivity.actionStart(getActivity(), table);
                            break;
                        case C.TableStatus.STATU_OPENED:
                            Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                            break;

                        case C.TableStatus.STATU_DINING:
                            Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                            break;

                        case C.TableStatus.STATU_CLEANING:
                            new AlertDialog.Builder(context)
                                    .setTitle("提示")
                                    .setMessage("此桌清理完成了？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(getActivity(), "清理完成了", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .create().show();
                            break;

                        case C.TableStatus.STATU_RESERVED:
                            Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                            break;
                        case C.TableStatus.STATU_LOCKING:
                            Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                            break;
                    }
//                    notifyDataSetChanged();
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            String free = context.getString(R.string.table_free);
            String opened = context.getString(R.string.table_opened);
            String dining = context.getString(R.string.table_dining);
            String cleaning = context.getString(R.string.table_cleaning);
            String reserved = context.getString(R.string.table_reserved);
            String locking = context.getString(R.string.table_locking);

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

}
