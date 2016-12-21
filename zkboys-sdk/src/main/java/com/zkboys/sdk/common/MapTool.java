package com.zkboys.sdk.common;

import java.util.HashMap;
import java.util.Map;

public class MapTool {

    private Map<String, Object> map = new HashMap<String, Object>();

    public static Map<String, Object> createMapWith(String key, Object value) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, value);

        return map;
    }

    public static Map<String, Object> createMapWith(Object[][] values) {

        Map<String, Object> map = new HashMap<String, Object>();

        for (Object[] item : values) {

            map.put(item[0].toString(), item[1]);
        }

        return map;
    }

    public static MapTool create() {

        MapTool helper = new MapTool();

        return helper;
    }

    public MapTool put(String key, Object value) {

        this.map.put(key, value);

        return this;
    }

    public MapTool putAll(Object[][] values) {

        for (Object[] item : values) {

            this.map.put(item[0].toString(), item[1]);
        }

        return this;
    }

    public Map<String, Object> value() {

        return this.map;
    }
}
