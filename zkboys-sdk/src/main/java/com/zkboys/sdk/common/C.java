package com.zkboys.sdk.common;

/**
 * 用来存储一些常量
 */
public class C {
    private static final String AUTHOR_SCOPE = "base";
    public static final String INTERNET_ERROR = "请求服务器失败";
    public static final String UNKNOWN_SERVER_ERROR = "未知系统错误";

    public class TableStatus {
        public static final String FREE = "free";             // 空闲
        public static final String OPENED = "opened";         // 已开桌
        public static final String DINING = "dining";         // 就餐
        public static final String NEED_CLEAN = "need_clean";     // 待清理
        public static final String RESERVED = "reserved";     // 预定
        public static final String LOCKED = "locked";       // 锁定
    }
}
