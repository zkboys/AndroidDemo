package com.zkboys.androiddemo.presenter;

import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.androiddemo.presenter.vus.ITableFragmentPresenter;
import com.zkboys.androiddemo.utils.PreferenceUtil;
import com.zkboys.androiddemo.view.fragment.TableListFragment;
import com.zkboys.sdk.ZKBoysSDK;
import com.zkboys.sdk.common.C;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.model.TableRegionInfo;
import com.zkboys.sdk.service.DefaultCallback;
import com.zkboys.sdk.service.TableService;

import java.util.List;

public class TableFragmentPresenter implements ITableFragmentPresenter {
    private TableListFragment view;
    private TableService tableService;

    public TableFragmentPresenter(TableListFragment tableListFragment) {
        this.view = tableListFragment;
        ZKBoysSDK zkBoysSDK = ((ZKBoysApplication) tableListFragment.getActivity().getApplication()).getZKBoysSDK();
        this.tableService = zkBoysSDK.getTableService();

    }

    @Override
    public ServiceTicket pullRefresh() {
        PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(view.getActivity());
        String mchId = preferenceUtil.getMerchantId();
        String storeId = preferenceUtil.getStoreId();
        return tableService.getRegions(mchId, storeId, new DefaultCallback<Results<TableRegionInfo>>() {
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

    @Override
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
