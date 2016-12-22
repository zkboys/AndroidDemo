package com.zkboys.androiddemo.presenter;

import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.androiddemo.presenter.vus.SplashPresenterInteractor;
import com.zkboys.androiddemo.view.activities.SplashActivity;
import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.ClientVersionInfo;
import com.zkboys.sdk.service.AppService;
import com.zkboys.sdk.service.DefaultCallback;


public class SplashPresenter implements SplashPresenterInteractor {

    protected SplashActivity view;
    protected AppService appService;

    public SplashPresenter(SplashActivity view) {
        this.view = view;
        this.appService = ((ZKBoysApplication) view.getApplication()).getZKBoysSDK().getAppService();
    }

    @Override
    public ServiceTicket checkVision(String appType, Integer clientVersion) {
        return appService.checkVersion(appType, clientVersion, new DefaultCallback<ClientVersionInfo>() {
            @Override
            public void onSuccess(ClientVersionInfo result) {
                view.checkVisionResult(true, null, result);
            }

            @Override
            public void onServiceException(ServiceException exception) {
                view.checkVisionResult(false, exception.getMessage(), null);
            }

            @Override
            public void onNetworkException(NetworkException exception) {
                view.checkVisionResult(false, "网络连接错误", null);
            }
        });
    }
}
