package com.zkboys.sdk.model;

import java.io.Serializable;
import java.util.List;

/**
 * 列表返回
 * Created by freeway on 16/5/16.
 */
public class Results<T> implements Serializable {

    /**
     * 当前页返回数据列表
     */
    private List<T> results;

    public static <T> Results<T> build() {
        return new Results<T>();
    }

    public static <T> Results<T> build(List<T> results) {
        return new Results<T>().setResults(results);
    }

    public List<T> getResults() {
        return results;
    }

    public Results<T> setResults(List<T> results) {
        this.results = results;
        return this;
    }

    @Override
    public String toString() {
        return "Results{" +
                "results=" + results +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Results<?> listVO = (Results<?>) o;

        return !(results != null ? !results.equals(listVO.results) : listVO.results != null);

    }

    @Override
    public int hashCode() {
        return results != null ? results.hashCode() : 0;
    }
}
