package com.zkboys.sdk.common;

import com.zkboys.sdk.httpjson.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class CallbackUtil {

    public static TypeInfo getCallbackGenericType(Callback<?> callback) {

        Class<?> clazz = callback.getClass();

        TypeInfo type = getGetnericType(clazz.getGenericSuperclass());

        if (type == null) {
            type = getGetnericType(clazz.getGenericInterfaces()[0]);
        }

        return type;
    }

    private static TypeInfo getGetnericType(Type type) {

        if (type != null && type instanceof ParameterizedType) {

            Type[] args = ((ParameterizedType) type).getActualTypeArguments();
            if (args != null && args.length > 0) {

                return new TypeInfo(args[0]);
            }
        }

        return null;
    }
}
