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

    <T> ServiceTicket get(boolean authenticated, String url, Object params, Map<String, String> headers, Callback<T> callback);

    <T> T get(boolean authenticated, String url, Object params, Map<String, String> headers, TypeInfo typeInfo) throws NetworkException, ServiceException;

    <T> ServiceTicket post(boolean authenticated, String url, Object params, Map<String, String> headers, Callback<T> callback);

    <T> T post(boolean authenticated, String url, Object params, Map<String, String> headers, TypeInfo typeInfo) throws NetworkException, ServiceException;

    <T> ServiceTicket put(boolean authenticated, String url, Object params, Map<String, String> headers, Callback<T> callback);

    <T> T put(boolean authenticated, String url, Object params, Map<String, String> headers, TypeInfo typeInfo) throws NetworkException, ServiceException;

    <T> ServiceTicket delete(boolean authenticated, String url, Object params, Map<String, String> headers, Callback<T> callback);

    <T> T delete(boolean authenticated, String url, Object params, Map<String, String> headers, TypeInfo typeInfo) throws NetworkException, ServiceException;

    <T> ServiceTicket uploadFile(boolean authenticated, String name, Map<String, Object> parameters, File file, final String filename, String fileUrl, Map<String, String> headers, Callback<T> callback);

    <T> ServiceTicket uploadFiles(boolean authenticated, String name, Map<String, Object> parameters, Map<String, File> files, Map<String, String> headers, Callback<T> callback);
}
