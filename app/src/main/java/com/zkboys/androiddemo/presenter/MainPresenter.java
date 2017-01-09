package com.zkboys.androiddemo.presenter;

import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.androiddemo.presenter.vus.IMainPresenter;
import com.zkboys.androiddemo.view.activities.MainActivity;
import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.service.AuthorizeService;

public class MainPresenter implements IMainPresenter {
    protected MainActivity view;
    private AuthorizeService authorizeService;

    public MainPresenter(MainActivity view) {
        this.view = view;
        this.authorizeService = ((ZKBoysApplication) view.getApplication()).getZKBoysSDK().getAuthorizeService();
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
