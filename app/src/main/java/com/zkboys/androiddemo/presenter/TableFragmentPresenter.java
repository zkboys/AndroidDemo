package com.zkboys.androiddemo.presenter;

import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.androiddemo.presenter.vus.ITableFragmentPresenter;
import com.zkboys.androiddemo.view.fragment.TableListFragment;
import com.zkboys.sdk.ZKBoysSDK;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.model.TablesInfo;
import com.zkboys.sdk.service.DefaultCallback;
import com.zkboys.sdk.service.TableService;

import java.util.List;

public class TableFragmentPresenter implements ITableFragmentPresenter {
    private TableListFragment tableListFragment;
    private TableService tableService;

    public TableFragmentPresenter(TableListFragment tableListFragment) {
        this.tableListFragment = tableListFragment;
        ZKBoysSDK zkBoysSDK = ((ZKBoysApplication) tableListFragment.getActivity().getApplication()).getZKBoysSDK();
        this.tableService = zkBoysSDK.getTableService();

    }

    @Override
    public ServiceTicket pullRefresh() {
        return tableService.getTables("1", "1", new DefaultCallback<Results<TablesInfo>>() {
            @Override
            public void onSuccess(Results<TablesInfo> result) {
                List<TablesInfo> tables = result.getResults();
//                view.refreshFragmentPages(tables);
                tableListFragment.setTables(tables);
            }

            @Override
            public void onException(Exception exception, String message) {
                super.onException(exception, message);
                tableListFragment.showRefreshError(message);
            }
        });
    }
}
