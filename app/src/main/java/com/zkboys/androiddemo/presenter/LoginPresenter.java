package com.zkboys.androiddemo.presenter;

import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.androiddemo.presenter.vus.ILoginPresenter;
import com.zkboys.androiddemo.view.activities.vus.IUserLoginView;
import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.oauth.model.OAuthToken;
import com.zkboys.sdk.service.AuthorizeService;
import com.zkboys.sdk.service.DefaultCallback;


public class LoginPresenter implements ILoginPresenter {

    protected IUserLoginView view;
    protected AuthorizeService authorizeService;

    public LoginPresenter(IUserLoginView view) {
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
        }));

        return true;
    }
}
