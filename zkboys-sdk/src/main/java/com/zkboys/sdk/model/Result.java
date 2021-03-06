package com.zkboys.sdk.model;

import java.io.Serializable;

/**
 * 用于对于单个的返回对象
 * <p>
 * 比如返回一个String、int等类型并不是一个json对象，为避免返回非json对象的情况一律返回对象
 * </p>
 * Created by freeway on 16/5/16.
 */
public class Result<T> implements Serializable {

    private T result;

    public static <T> Result<T> build(T value) {
        return new Result<T>().setResult(value);
    }

    /**
     * 返回值
     *
     * @return
     */
    public T getResult() {
        return result;
    }


    /**
     * 设定值
     *
     * @param result
     * @return
     */
    public Result<T> setResult(T result) {
        this.result = result;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result=" + result +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result<?> resultVO = (Result<?>) o;

        return !(result != null ? !result.equals(resultVO.result) : resultVO.result != null);

    }

    @Override
    public int hashCode() {
        return result != null ? result.hashCode() : 0;
    }
}
