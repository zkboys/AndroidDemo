package com.zkboys.sdk.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by freeway on 16/5/16.
 */
public class ResultsTotal<T> implements Serializable {

    /**
     * 当前页返回数据列表
     */
    private List<T> results;

    private Long totalCount;

    public static <T> ResultsTotal<T> build() {
        return new ResultsTotal<T>();
    }

    public static <T> ResultsTotal<T> build(List<T> results) {
        return new ResultsTotal<T>().setResults(results);
    }

    public static <T> ResultsTotal<T> build(List<T> results, Long totalCount) {
        return new ResultsTotal<T>().setResults(results).setTotalCount(totalCount);
    }

    public List<T> getResults() {
        return results;
    }

    public ResultsTotal<T> setResults(List<T> results) {
        this.results = results;
        return this;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public ResultsTotal setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    @Override
    public String toString() {
        return "ResultsTotal{" +
                "results=" + results +
                ", totalCount=" + totalCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResultsTotal<?> that = (ResultsTotal<?>) o;

        if (results != null ? !results.equals(that.results) : that.results != null) return false;
        return !(totalCount != null ? !totalCount.equals(that.totalCount) : that.totalCount != null);

    }

    @Override
    public int hashCode() {
        int result = results != null ? results.hashCode() : 0;
        result = 31 * result + (totalCount != null ? totalCount.hashCode() : 0);
        return result;
    }
}
