package com.zkboys.sdk.service;

import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.ResourceInfo;

import java.io.File;

public interface ResourceService {


    /**
     * 上传照片
     *
     * @param file     文件
     * @param callback
     * @return
     */
    ServiceTicket upload(File file, String fileName, String fileUrl, Callback<ResourceInfo> callback);

}
