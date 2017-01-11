package com.zkboys.androiddemo.presenter;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.androiddemo.presenter.vus.ILoginPresenter;
import com.zkboys.androiddemo.view.activities.vus.ILoginActivity;
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
    public ServiceTicket login() {
        view.clearFailedError();
        view.clearUserNameError();
        view.clearPasswordError();

        String userName = view.getUserName();
        String password = view.getPassword();

        if (null == userName || "".equals(userName)) {
            view.showUserNameError(R.string.prompt_email);
            view.focusUserName();
            return null;
        }

        if (null == password || "".equals(password)) {
            view.showPasswordError(R.string.prompt_password);
            view.focusPassword();
            return null;
        }

        if (password.length() < 4) {
            view.showPasswordError(R.string.error_invalid_password);
            view.focusPassword();
            return null;
        }

        return authorizeService.accessToken(userName, password, "base", new DefaultCallback<OAuthToken>() {
            @Override
            public boolean onPreExecute(ServiceTicket ticket, Object object, Map<String, String> headers) {
                view.showLoading();
                return super.onPreExecute(ticket, object, headers);
            }

            @Override
            public void onSuccess(OAuthToken result) {
                System.out.println(result);
                ((ZKBoysApplication) view.getApplication()).getOAuthContext().store(result);
                view.doNext();
            }

            @Override
            public void onException(Exception exception, String message) {
                view.hideLoading();
                view.showFailedError(message);
            }

        });
    }
}
