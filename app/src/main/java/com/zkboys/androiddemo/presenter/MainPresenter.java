package com.zkboys.androiddemo.presenter;

import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.androiddemo.presenter.vus.IMainPresenter;
import com.zkboys.androiddemo.utils.PreferenceUtil;
import com.zkboys.androiddemo.view.activities.MainActivity;
import com.zkboys.sdk.ZKBoysSDK;
import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.model.TableRegionInfo;
import com.zkboys.sdk.service.AuthorizeService;
import com.zkboys.sdk.service.DefaultCallback;
import com.zkboys.sdk.service.TableService;

import java.util.List;

public class MainPresenter implements IMainPresenter {
    protected MainActivity view;
    private AuthorizeService authorizeService;
    private TableService tableService;

    public MainPresenter(MainActivity view) {
        this.view = view;
        ZKBoysSDK zkBoysSDK = ((ZKBoysApplication) view.getApplication()).getZKBoysSDK();
        this.authorizeService = zkBoysSDK.getAuthorizeService();
        this.tableService = zkBoysSDK.getTableService();

    }

    @Override
    public void logout() {
        try {
            authorizeService.logout();
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (NetworkException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServiceTicket getTables() {
        PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(view);
        String mchId = preferenceUtil.getMerchantId();
        String storeId = preferenceUtil.getStoreId();
        return tableService.getRegions(mchId, storeId, new DefaultCallback<Results<TableRegionInfo>>() {
            @Override
            public void onSuccess(Results<TableRegionInfo> result) {
                List<TableRegionInfo> tables = result.getResults();
                view.initFragmentPages(tables);
            }

            @Override
            public void onException(Exception exception, String message) {
                super.onException(exception, message);
                view.showShortToast(message);
            }
        });
    }
}
