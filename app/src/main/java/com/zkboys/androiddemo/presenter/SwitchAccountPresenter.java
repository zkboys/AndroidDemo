package com.zkboys.androiddemo.presenter;

import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.androiddemo.presenter.vus.ISwitchAccountPresenter;
import com.zkboys.androiddemo.view.activities.SwitchAccountActivity;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.model.UserInfo;
import com.zkboys.sdk.service.DefaultCallback;
import com.zkboys.sdk.service.UserService;

import java.util.List;
import java.util.Map;


public class SwitchAccountPresenter implements ISwitchAccountPresenter {

    protected SwitchAccountActivity view;
    private UserService userService;

    public SwitchAccountPresenter(SwitchAccountActivity view) {
        this.view = view;

        this.userService = ((ZKBoysApplication) view.getApplication()).getZKBoysSDK().getUserService();
    }

    @Override
    public ServiceTicket getAllUsers() {
        return userService.getAllUsers("", "", new DefaultCallback<Results<UserInfo>>() {
            @Override
            public boolean onPreExecute(ServiceTicket ticket, Object object, Map<String, String> headers) {
                return super.onPreExecute(ticket, object, headers);
            }

            @Override
            public void onSuccess(Results<UserInfo> result) {
                List<UserInfo> users = result.getResults();
                view.showUsers(users);
            }

            @Override
            public void onException(Exception exception, String message) {
            }

        });
    }
}
