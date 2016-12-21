package com.zkboys.sdk.model;

import java.io.Serializable;

/**
 * Created by HuaHua on 2016/9/12 0012.
 */

public class ResourceInfo implements Serializable{
    private Long id;

    private String path;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ResourceInfo{" +
                "id=" + id +
                ", path='" + path + '\'' +
                '}';
    }
}
