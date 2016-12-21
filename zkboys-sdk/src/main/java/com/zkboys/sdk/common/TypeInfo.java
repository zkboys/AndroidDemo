package com.zkboys.sdk.common;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public class TypeInfo {

    private Class<?> componentType;
    private Class<?> rawType;

    private TypeInfo(Class<?> rawType, Class<?> componentType) {
        this.componentType = componentType;
        this.rawType = rawType;
    }

    public static TypeInfo createArrayType(Class<?> componentType) {
        return new TypeInfo(Array.class, componentType);
    }

    public static TypeInfo createNormalType(Class<?> componentType) {
        return new TypeInfo(null, componentType);
    }

    public static TypeInfo createParameterizedType(Class<?> rawType, Class<?> componentType) {
        return new TypeInfo(rawType, componentType);
    }

    public TypeInfo(Type type) {

        if (type instanceof ParameterizedType) {

            this.rawType = (Class<?>) ((ParameterizedType) type).getRawType();
            this.componentType = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else if (type instanceof GenericArrayType) {

            this.rawType = Array.class;
            this.componentType = (Class<?>) ((GenericArrayType) type).getGenericComponentType();
        } else {

            this.componentType = (Class<?>) type;
        }

    }

    public Class<?> getComponentType() {
        return componentType;
    }

    public void setComponentType(Class<?> componentType) {
        this.componentType = componentType;
    }

    public Class<?> getRawType() {
        return rawType;
    }

    public String getComponentProperty() {
        if (rawType != null && ((!Array.class.isAssignableFrom(componentType)) &&
                (!Collection.class.isAssignableFrom(componentType)) ||
                (!Map.class.isAssignableFrom(componentType)))) {
            Field[] fields = rawType.getDeclaredFields();
            for (Field field : fields) {
                if (field.getDeclaringClass().isAssignableFrom(componentType)) {
                    return field.getName();
                }
            }
        }
        return null;
    }

    public void setRawType(Class<?> rawType) {
        this.rawType = rawType;
    }

    @Override
    public String toString() {
        return String.format("rowType:%s, componentType:%s", this.rawType, this.componentType);
    }

}
