package com.zkboys.sdk.model;

import java.util.List;

/**
 * Created by HuaHua on 2016/9/20 0020.
 */

public class OrderDmCountResult {

    List<OrderDmScoreInfo> results;

    Integer totalCount;

    public List<OrderDmScoreInfo> getResults() {
        return results;
    }

    public void setResults(List<OrderDmScoreInfo> results) {
        this.results = results;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "OrderDmCountResult{" +
                "results=" + results +
                ", totalCount=" + totalCount +
                '}';
    }
}
