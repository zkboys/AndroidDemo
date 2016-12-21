package com.zkboys.sdk.httpjson;

public class BaseService {

    protected ServiceClient serviceClient;

    public BaseService(ServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

}
