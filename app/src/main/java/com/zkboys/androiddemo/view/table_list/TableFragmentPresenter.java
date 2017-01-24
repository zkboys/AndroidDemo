package com.zkboys.androiddemo.view.table_list;

import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.sdk.ZKBoysSDK;
import com.zkboys.sdk.common.C;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.model.TableRegionInfo;
import com.zkboys.sdk.service.DefaultCallback;
import com.zkboys.sdk.service.TableService;

import java.util.List;

public class TableFragmentPresenter {
    private TableListFragment view;
    private TableService tableService;

    public TableFragmentPresenter(TableListFragment tableListFragment) {
        this.view = tableListFragment;
        ZKBoysSDK zkBoysSDK = ((ZKBoysApplication) tableListFragment.getActivity().getApplication()).getZKBoysSDK();
        this.tableService = zkBoysSDK.getTableService();

    }

    public ServiceTicket pullRefresh() {
        return tableService.getRegions(new DefaultCallback<Results<TableRegionInfo>>() {
            @Override
            public void onSuccess(Results<TableRegionInfo> result) {
                List<TableRegionInfo> tables = result.getResults();
//                view.refreshFragmentPages(tables);
                view.setTables(tables);
            }

            @Override
            public void onException(Exception exception, String message) {
                super.onException(exception, message);
                view.showRefreshError(message);
            }
        });
    }

    public ServiceTicket cleanTable(final String tableId) {
        return tableService.cleanTable(tableId, new DefaultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                view.setTableStatus(tableId, C.TableStatus.FREE);
            }

            @Override
            public void onException(Exception exception, String message) {
                super.onException(exception, message);
                view.showToast(message);
            }
        });
    }
}
