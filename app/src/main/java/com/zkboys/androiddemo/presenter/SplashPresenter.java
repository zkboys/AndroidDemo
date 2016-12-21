package com.zkboys.androiddemo.presenter;

import com.zkboys.androiddemo.presenter.vus.SplashPresenterInteractor;
import com.zkboys.androiddemo.view.activities.SplashActivity;
import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.model.ClientVersionInfo;
import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.service.DeliverymanService;
import com.zkboys.sdk.httpjson.ServiceTicket;

import java.util.Map;


public class SplashPresenter implements SplashPresenterInteractor {

    protected SplashActivity view;
    protected DeliverymanService service;

    public SplashPresenter(SplashActivity view, DeliverymanService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public ServiceTicket checkVision(String appType, Integer clientVersion) {
        return service.checkVersion(appType, clientVersion, new Callback<ClientVersionInfo>() {
            @Override
            public boolean onPreExecute(ServiceTicket ticket, Object object, Map<String, String> headers) {
                return true;
            }

            @Override
            public void onPostExecute() {

            }

            @Override
            public void onProgressUpdate(Integer... values) {

            }

            @Override
            public void onSuccess(ClientVersionInfo result) {
                System.out.println(result);
                view.checkVisionResult(true, null, result);
            }

            @Override
            public void onServiceException(ServiceException exception) {
                view.checkVisionResult(false, null, null);

            }

            @Override
            public void onNetworkException(NetworkException exception) {
                view.checkVisionResult(false, null, null);

            }
        });
    }
}
