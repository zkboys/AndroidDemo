package com.zkboys.androiddemo.presenter;

import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.androiddemo.presenter.vus.IDishPresenter;
import com.zkboys.androiddemo.view.activities.OrderActivity;
import com.zkboys.sdk.ZKBoysSDK;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.DishCategoryInfo;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.service.DefaultCallback;
import com.zkboys.sdk.service.DishService;

import java.util.List;

public class DishPresenter implements IDishPresenter {
    private OrderActivity view;
    private DishService dishService;

    public DishPresenter(OrderActivity view) {
        this.view = view;
        ZKBoysSDK zkBoysSDK = ((ZKBoysApplication) view.getActivity().getApplication()).getZKBoysSDK();
        this.dishService = zkBoysSDK.getDishService();

    }

    @Override
    public ServiceTicket getAllDishes() {
        return dishService.getAllDishes(new DefaultCallback<Results<DishCategoryInfo>>() {
            @Override
            public void onSuccess(Results<DishCategoryInfo> result) {
                List<DishCategoryInfo> dishes = result.getResults();
                view.setInitCategoryData(dishes);
            }

            @Override
            public void onException(Exception exception, String message) {
                super.onException(exception, message);
                view.showShortToast(message);
            }
        });
    }
}
