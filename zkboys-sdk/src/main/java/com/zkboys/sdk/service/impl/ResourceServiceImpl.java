package com.zkboys.sdk.service.impl;

import com.zkboys.sdk.httpjson.BaseService;
import com.zkboys.sdk.model.ResourceInfo;
import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.service.ResourceService;
import com.zkboys.sdk.httpjson.ServiceClient;
import com.zkboys.sdk.httpjson.ServiceTicket;

import java.io.File;


public class ResourceServiceImpl extends BaseService implements ResourceService {

    public static final String RESOURCE_UPLOAD = "/v1/resource/add.json";


    public ResourceServiceImpl(ServiceClient serviceClient) {
        super(serviceClient);
    }

    @Override
    public ServiceTicket upload(File file, String fileName, String fileUrl, Callback<ResourceInfo> callback) {
        return serviceClient.uploadFile(true, RESOURCE_UPLOAD, null, file, fileName, fileUrl, null, callback);
    }
}
