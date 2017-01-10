package com.zkboys.sdk.service;

import android.util.Log;

import com.zkboys.sdk.common.C;
import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceTicket;

import java.util.Map;

/**
 * 对请求的callback的二次封装，处理了一些通用操作
 *
 * @param <T>
 */
public abstract class DefaultCallback<T> implements Callback<T> {
    @Override
    public boolean onPreExecute(ServiceTicket ticket, Object object, Map<String, String> headers) {
        return true;
    }

    @Override
    public void onPostExecute() {

    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public abstract void onSuccess(T result);

    @Override
    public void onServiceException(ServiceException exception) {
        Log.e("ordering-sdk", "ServiceException()=" + exception.toString());
        if (null != exception.getErrorItems()) {
            Log.e("test", "ServiceException()=" + exception.getErrorItems().toString());
        }
        onException(exception, exception.getMessage() != null ? exception.getMessage() : C.UNKNOWN_SERVER_ERROR);
    }

    @Override
    public void onNetworkException(NetworkException exception) {
        Log.e("ordering-sdk", "onNetworkException()=" + exception.toString());
        onException(exception, C.INTERNET_ERROR);
    }

    /**
     * 一般异常处理方式一样，这里提供一个统一处理错误信息的方法
     *
     * @param exception
     * @param message
     */
    public void onException(Exception exception, String message) {
        Log.e("ordering-sdk", "onNetworkException()=" + exception.toString());
    }


}
