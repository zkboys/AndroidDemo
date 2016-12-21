package com.zkboys.sdk.httpjson;

import com.zkboys.sdk.common.TypeInfo;
import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.oauth.OAuthProvider;

import java.io.File;
import java.util.Map;

/**
 * 客户端服务接口.
 * 发送各种请求
 * 获取OAuth provider
 */
public interface ServiceClient {

    OAuthProvider getOAuthProvider();

    /**
     * 以<b>POST+异步</b>的方式访问Open-API，（通用参数描述见带callback的'get'方法）
     *
     * @param object
     * @param callback
     */
    <T> ServiceTicket call(boolean authenticated, String name, Object object, Map<String, String> headers, Callback<T> callback);

    /**
     * 以<b>同步读操作</b>的方式访问Open-API
     *
     * @param params
     * @param typeInfo
     * @return
     * @throws NetworkException
     * @throws ServiceException
     */
    <T> T call(boolean authenticated, String url, Object params, Map<String, String> headers, TypeInfo typeInfo) throws NetworkException, ServiceException;

    /**
     * 以<b>POST+Stream+异步</b>的方式访问Open-API，（通用参数描述见带callback的'get'方法）
     *
     * @param parameters
     * @param callback
     */
    <T> ServiceTicket callWithFile(boolean authenticated, String name, Map<String, Object> parameters, File file, final String filename, String fileUrl, Map<String, String> headers, Callback<T> callback);

    /**
     * 以<b>POST+Stream+异步</b>的方式访问Open-API，（通用参数描述见带callback的'get'方法）
     *
     * @param parameters
     * @param callback
     */
    <T> ServiceTicket callWithFiles(boolean authenticated, String name, Map<String, Object> parameters, Map<String, File> files, Map<String, String> headers, Callback<T> callback);
}
