package com.zkboys.sdk.service;

import android.util.Log;

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
    }

    @Override
    public void onNetworkException(NetworkException exception) {
        Log.e("ordering-sdk", "onNetworkException()=" + exception.toString());
    }
}
