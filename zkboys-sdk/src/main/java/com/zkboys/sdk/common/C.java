package com.zkboys.sdk.common;

/**
 * 用来存储一些常量
 */
public class C {
    private static final String AUTHOR_SCOPE = "base";
    public static final String INTERNET_ERROR = "请求服务器失败";
    public static final String UNKNOWN_SERVER_ERROR = "未知系统错误";

    public class TableStatus {
        public static final String STATU_FREE = "free";             // 空闲
        public static final String STATU_OPENED = "opened";         // 已开桌
        public static final String STATU_DINING = "dining";         // 就餐
        public static final String STATU_CLEANING = "cleaning";     // 待清理
        public static final String STATU_RESERVED = "reserved";     // 预定
        public static final String STATU_LOCKING = "locking";       // 锁定
    }
}
