package com.zkboys.androiddemo.view.login;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.androiddemo.utils.PreferenceUtil;
import com.zkboys.sdk.ZKBoysSDK;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.MerchantInfo;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.model.StoreInfo;
import com.zkboys.sdk.model.UserInfo;
import com.zkboys.sdk.oauth.model.OAuthToken;
import com.zkboys.sdk.service.AuthorizeService;
import com.zkboys.sdk.service.DefaultCallback;
import com.zkboys.sdk.service.MerchantService;
import com.zkboys.sdk.service.UserService;

import java.util.List;
import java.util.Map;


public class LoginPresenter implements ILoginPresenter {

    protected ILoginActivity view;
    private AuthorizeService authorizeService;
    private MerchantService merchantService;
    private UserService userService;

    public LoginPresenter(ILoginActivity view) {
        this.view = view;
        ZKBoysSDK zkBoysSDK = ((ZKBoysApplication) view.getApplication()).getZKBoysSDK();
        this.authorizeService = zkBoysSDK.getAuthorizeService();
        this.merchantService = zkBoysSDK.getMerchantService();
        this.userService = zkBoysSDK.getUserService();
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

    @Override
    public ServiceTicket getMerchants() {
        return merchantService.getMerchants(new DefaultCallback<Results<MerchantInfo>>() {
            @Override
            public void onSuccess(Results<MerchantInfo> result) {
                view.hideLoading();
                List<MerchantInfo> merchantInfoList = result.getResults();
                if (merchantInfoList == null || merchantInfoList.size() == 0) {
                    view.showNoStoreError();
                    return;
                }

                if (merchantInfoList.size() == 1) {
                    MerchantInfo merchant = merchantInfoList.get(0);
                    List<StoreInfo> stores = merchant.getStores();
                    if (stores == null || stores.size() == 0) {
                        view.showNoStoreError();
                        return;
                    }
                    if (stores.size() == 1) {
                        StoreInfo store = stores.get(0);
                        PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(view.getApplication().getApplicationContext());
                        preferenceUtil.setMerchantId(merchant.getId());
                        preferenceUtil.setStoreId(store.getId());
                        view.toMain();
                        return;
                    }
                }

                view.toSelectStore(merchantInfoList);
            }

            @Override
            public void onException(Exception exception, String message) {
                view.hideLoading();
                view.showFailedError(message);
            }
        });
    }

    @Override
    public ServiceTicket getCurrentLoginUser() {
        return userService.getCurrentLoginUser(new DefaultCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {
                PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(view.getApplication().getApplicationContext());
                preferenceUtil.setUserName(result.getName());
                preferenceUtil.setLoginName(result.getLoginname());
                getMerchants();
            }

            @Override
            public void onException(Exception exception, String message) {
                view.hideLoading();
                view.showFailedError(message);
            }
        });
    }
}
