package com.zkboys.androiddemo.view.splash;

import com.zkboys.sdk.httpjson.ServiceTicket;

public interface ISplashPresenter {

    /**
     * 检测版本更新
     *
     * @return
     */
    ServiceTicket checkVision(String appType, Integer clientVersion);

}
