package com.zkboys.androiddemo.presenter;

import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.androiddemo.presenter.vus.IHomePresenter;
import com.zkboys.androiddemo.view.activities.HomeActivity;
import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.model.UserInfo;
import com.zkboys.sdk.service.AuthorizeService;
import com.zkboys.sdk.service.DefaultCallback;
import com.zkboys.sdk.service.UserService;

public class HomePresenter implements IHomePresenter {
    protected HomeActivity view;
    protected UserService userService;
    private AuthorizeService authorizeService;

    public HomePresenter(HomeActivity view) {
        this.view = view;
        this.userService = ((ZKBoysApplication) view.getApplication()).getZKBoysSDK().getUserService();
        this.authorizeService = ((ZKBoysApplication) view.getApplication()).getZKBoysSDK().getAuthorizeService();
    }

    @Override
    public void getUserById(String userId) {
        view.addServiceTicket(userService.getUserById(userId, new DefaultCallback<UserInfo>() {

            @Override
            public void onSuccess(UserInfo result) {
                view.showShortToast(result.getName());
            }
        }));
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
}
