package com.zkboys.sdk.service;

import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.DishCategoryInfo;
import com.zkboys.sdk.model.Results;

public interface DishService {
    ServiceTicket getAllDishes(Callback<Results<DishCategoryInfo>> callback);
}
