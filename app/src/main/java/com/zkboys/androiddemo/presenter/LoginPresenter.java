package com.zkboys.androiddemo.presenter;

import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.androiddemo.presenter.vus.ILoginPresenter;
import com.zkboys.androiddemo.view.activities.vus.ILoginActivity;
import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.oauth.model.OAuthToken;
import com.zkboys.sdk.service.AuthorizeService;
import com.zkboys.sdk.service.DefaultCallback;

import java.util.Map;


public class LoginPresenter implements ILoginPresenter {

    protected ILoginActivity view;
    protected AuthorizeService authorizeService;

    public LoginPresenter(ILoginActivity view) {
        this.view = view;
        this.authorizeService = ((ZKBoysApplication) view.getApplication()).getZKBoysSDK().getAuthorizeService();
    }


    @Override
    public boolean login() {
        String userName = view.getUserName();
        String password = view.getPassword();
        if (null == userName || "".equals(userName)) {
            view.showFailedError("用户名不能为空");
            return false;
        }

        if (null == password || "".equals(password)) {
            view.showFailedError("密码不能为空");
            return false;
        }

        view.addServiceTicket(authorizeService.accessToken(userName, password, "", new DefaultCallback<OAuthToken>() {
            @Override
            public boolean onPreExecute(ServiceTicket ticket, Object object, Map<String, String> headers) {
                view.showLoading();
                return super.onPreExecute(ticket, object, headers);
            }

            @Override
            public void onSuccess(OAuthToken result) {
                view.toMainActivity();
            }

            @Override
            public void onServiceException(ServiceException exception) {
                view.showFailedError(exception.getMessage());
            }

            @Override
            public void onNetworkException(NetworkException exception) {
                view.showFailedError("网络连接错误");
            }

            @Override
            public void onPostExecute() {
                super.onPostExecute();
                view.hideLoading();
            }
        }));

        return true;
    }
}
