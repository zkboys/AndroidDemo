package com.zkboys.sdk.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtil {

    public static Type[] getParameterizedTypes(Object object) {
        Type superclassType = object.getClass().getGenericSuperclass();
        if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
            return null;
        }
        return ((ParameterizedType) superclassType).getActualTypeArguments();
    }

    private static final String TYPE_NAME_PREFIX = "class ";

    public static String getClassName(Type type) {
        if (type == null) {
            return "";
        }
        String className = type.toString();
        if (className.startsWith(TYPE_NAME_PREFIX)) {
            className = className.substring(TYPE_NAME_PREFIX.length());
        }
        return className;
    }

    public static Class<?> getClass(Type type)
            throws ClassNotFoundException {
        String className = getClassName(type);
        if (className == null || "".equals(className.trim())) {
            return null;
        }
        return Class.forName(className);
    }
}
