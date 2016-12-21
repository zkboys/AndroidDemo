package com.zkboys.androiddemo.presenter.vus;

import com.zkboys.sdk.httpjson.ServiceTicket;

public interface SplashPresenterInteractor {

    /**
     * 检测版本更新
     *
     * @return
     */
    ServiceTicket checkVision(String appType, Integer clientVersion);

}
