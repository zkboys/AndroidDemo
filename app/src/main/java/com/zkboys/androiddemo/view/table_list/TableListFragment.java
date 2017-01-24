package com.zkboys.androiddemo.view.table_list;

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
import android.widget.Toast;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.view.customer.CustomerInfoActivity;
import com.zkboys.androiddemo.view.order.OrderActivity;
import com.zkboys.sdk.common.C;
import com.zkboys.sdk.model.TableInfo;
import com.zkboys.sdk.model.TableRegionInfo;

import java.util.ArrayList;
import java.util.List;

public class TableListFragment extends Fragment {
    private static final String TABLE_REGIONS = "table_regions";
    TableRegionInfo tableRegion;
    private TableListAdapter mAdapter;
    private RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;
    private TableFragmentPresenter presenter;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private String tabRegionId;

    public TableListFragment() {
    }

    public static TableListFragment newInstance(TableRegionInfo tableRegionInfo) {
        TableListFragment fragment = new TableListFragment();
        Bundle args = new Bundle();
        args.putParcelable(TABLE_REGIONS, tableRegionInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tableRegion = getArguments().getParcelable(TABLE_REGIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tables, container, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 5);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_main_tables);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new TableListAdapter(getContext());
        recyclerView.setAdapter(mAdapter);
        mAdapter.initData(tableRegion.getTableList());

        mAdapter.setOnClickListener(new TableListAdapter.OnTableClickListener() {
            @Override
            public void onClick(int position, final TableInfo table) {
                handleTableClick(position, table);
            }
        });

        presenter = new TableFragmentPresenter(this);

        // 下拉刷新
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.srl_tables);
        mSwipeRefreshWidget.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
//        mSwipeRefreshWidget.setProgressViewOffset(true, 150, 300); //调整进度条距离屏幕顶部的距离
        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
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

    public void handleTableClick(int position, final TableInfo table) {
        String status = table.getTabStatus();
        switch (status) {
            case C.TableStatus.FREE:
                CustomerInfoActivity.actionStart(getActivity(), table);
                break;

            case C.TableStatus.OPENED:
                OrderActivity.actionStart(getActivity(), table.getId());
                break;

            case C.TableStatus.DINING:
                // TODO：点击用餐中逻辑
                Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                break;

            case C.TableStatus.NEED_CLEAN:
                new AlertDialog.Builder(getActivity())
                        .setTitle("提示")
                        .setMessage("确定 \"" + table.getName() + "\" 已经清理完成了？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.cleanTable(table.getId());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create().show();
                break;

            case C.TableStatus.RESERVED:
                //TODO：点击预定逻辑
                Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                break;
            case C.TableStatus.LOCKED:
                // TODO: 点击锁定逻辑
                Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void setTables(List<TableRegionInfo> tableRegionInfoList) {
        // 先清空再赋值
        this.tableRegion.setTableList(new ArrayList<TableInfo>());

        if (tableRegionInfoList != null && tableRegionInfoList.size() > 0) {
            for (int i = 0; i < tableRegionInfoList.size(); i++) {
                if (this.tabRegionId.equals(tableRegionInfoList.get(i).getId())) {
                    this.tableRegion.setTableList(tableRegionInfoList.get(i).getTableList());
                    break;
                }
            }
        }
        mAdapter.initData(this.tableRegion.getTableList());
        mSwipeRefreshWidget.setRefreshing(false);
        Toast.makeText(getActivity(), getContext().getString(R.string.refresh_success), Toast.LENGTH_SHORT).show();
    }

    public void showRefreshError(String message) {
        showToast(message);
        mSwipeRefreshWidget.setRefreshing(false);
    }

    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void setTableStatus(String tableId, String status) {
        List<TableInfo> tableInfoList = tableRegion.getTableList();

        for (int i = 0; i < tableInfoList.size(); i++) {
            TableInfo tableInfo = tableInfoList.get(i);
            if (tableInfo.getId().equals(tableId)) {
                tableInfo.setTabStatus(status);
                mAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    public void setTabRegionId(String tabRegionId) {
        this.tabRegionId = tabRegionId;
    }

    public void refresh() {
        mSwipeRefreshWidget.setRefreshing(true);
        presenter.pullRefresh();
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

}
