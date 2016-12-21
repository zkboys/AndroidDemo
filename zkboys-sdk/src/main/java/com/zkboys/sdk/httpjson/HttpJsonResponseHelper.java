package com.zkboys.sdk.httpjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zkboys.sdk.common.ClassUtil;
import com.zkboys.sdk.common.TypeInfo;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.model.ResultsTotal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import okhttp3.Response;


public class HttpJsonResponseHelper {

    // 数据读取的缓冲大小（缺省为8K）
    private static final int BUFFER_SIZE = 8192;

    /**
     * 读取Response Entity中的JSON数据字符串
     *
     * @param input
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String readContentAsString(InputStream input, String encoding) throws IOException {

        int size = BUFFER_SIZE;

        BufferedReader reader = new BufferedReader(new InputStreamReader(input, encoding), size);

        String line = null;
        StringBuilder sb = new StringBuilder();

        while ((line = reader.readLine()) != null) {

            sb.append(line);
        }

        reader.close();
        input.close();

        return sb.toString();
    }

    static <T> T getResult(Response response, TypeInfo typeInfo) throws IOException, ServiceException {
        if (response.isSuccessful()) {
            String respJson = readContentAsString(response.body().byteStream(), HttpJsonServiceClient.CHARSET);
            return parseResult(typeInfo, JSONObject.parse(respJson));
        } else {
            String respJson = readContentAsString(response.body().byteStream(), HttpJsonServiceClient.CHARSET);
            JSONObject jsonObject = JSONObject.parseObject(respJson);
            throw parseToServiceException(jsonObject);
        }
    }

    private static ServiceException parseToServiceException(JSONObject jsonObject) {
        ServiceException serviceException = new ServiceException(jsonObject.getLongValue("code"), jsonObject.getString("message"));
        JSONObject jsonErrorItems = jsonObject.getJSONObject("errorItems");
        if (jsonErrorItems != null) {
            for (Map.Entry<String, Object> entry : jsonErrorItems.entrySet()) {
                serviceException.addErrorItem(entry.getKey(), entry.getValue().toString());
            }
        }
        return serviceException;
    }

    /**
     * 将JSON对象转换成指定的用户返回值类型
     *
     * @param type
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    @SuppressWarnings("all")
    public static <T> T parseResult(TypeInfo type, Object jsonObject) throws JSONException {

        if (Void.class.isAssignableFrom(type.getComponentType())) { // 处理Void类型的返回值
            return (T) Void.instance();
        }

        if (jsonObject == null) {
            throw new JSONException("result is empty.");
        }

        T result = null;

        Class<?> rawType = type.getRawType();

        boolean isArray = rawType != null ? Array.class.isAssignableFrom(rawType) : false;
        boolean isCollection = rawType != null ? Collection.class.isAssignableFrom(rawType) : false;
        Class componentType = type.getComponentType();

        if ((isArray || isCollection) && jsonObject instanceof JSONArray) {
            result = (T) convertToList(jsonObject, isArray, componentType);

        } else if (rawType != null && Map.class.isAssignableFrom(rawType)) {
            result = (T) JSONObject.toJavaObject((JSONObject) jsonObject, rawType);

        } else if (rawType != null && (Results.class.isAssignableFrom(rawType) ||
                ResultsTotal.class.isAssignableFrom(rawType))) {

            JSONObject jsObj = (JSONObject) jsonObject;
            result = (T) JSONObject.toJavaObject(jsObj, rawType);
            Results results = (Results) result;
            results.setResults((List) convertToList(jsObj.getJSONArray("results"), false, componentType));

        } else if (rawType != null) {
            if (ClassUtil.isPrimitiveOrWrapper(componentType)) {
                result = (T) JSONObject.toJavaObject((JSONObject) jsonObject, rawType);
            } else {
                result = (T) ((JSONObject) jsonObject).getObject(type.getComponentProperty(), componentType);
            }
        } else {
            //rawType == null
            if (ClassUtil.isPrimitiveOrWrapper(componentType)) {
                result = (T) ((JSONObject) jsonObject).getObject("result", componentType);
            } else {
                result = (T) JSONObject.toJavaObject((JSONObject) jsonObject, componentType);
            }
        }

        return result;
    }

    @SuppressWarnings("all")
    private static <T> T convertToList(Object resultObject, boolean isArray, Class componentType) {
        T result;
        JSONArray jsonArray = (JSONArray) resultObject;
        int size = jsonArray.size();

        if (isArray) {
            result = (T) Array.newInstance(componentType, size);
        } else {
            result = (T) new ArrayList(size);
        }

        for (int i = 0; i < size; i++) {

            JSONObject value = jsonArray.getJSONObject(i);

            if (isArray) {
                Array.set(result, i, JSON.parseObject(value.toJSONString(), componentType));
            } else {
                ((List) result).add(JSON.parseObject(value.toJSONString(), componentType));
            }
        }
        return result;
    }


}
