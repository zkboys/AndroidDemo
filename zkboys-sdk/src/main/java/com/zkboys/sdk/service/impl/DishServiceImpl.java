package com.zkboys.sdk.service.impl;

import com.zkboys.sdk.httpjson.BaseService;
import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceClient;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.DishCategoryInfo;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.service.DishService;

public class DishServiceImpl extends BaseService implements DishService {

    private static final String GET_ALL_DISHES = "/v1/dishes.json";

    public DishServiceImpl(ServiceClient serviceClient) {
        super(serviceClient);
    }


    @Override
    public ServiceTicket getAllDishes(Callback<Results<DishCategoryInfo>> callback) {
        return serviceClient.get(
                true,
                GET_ALL_DISHES,
                null,
                null, callback
        );
    }
}
