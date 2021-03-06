package com.zkboys.androiddemo.view.splash;

import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.sdk.common.C;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.ClientVersionInfo;
import com.zkboys.sdk.service.AppService;
import com.zkboys.sdk.service.DefaultCallback;


public class SplashPresenter implements ISplashPresenter {

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
                Byte promote = result.getPromote();
                if (promote == C.NEED_UPDATE || promote == C.FORCE_UPDATE) {
                    view.needUpdate(result);
                } else {
                    view.doNext();
                }
            }

            @Override
            public void onException(Exception exception, String message) {
                view.checkVisionFail(message);
            }
        });
    }
}
