package com.zkboys.sdk.common;

/**
 * 一些常量
 */
public class C {
    public static final String APPLICATION_LOGIN = "application_login";
    public static final int NOT_NEED_UPDATE = 0;
    public static final int NEED_UPDATE = 1;
    public static final int FORCE_UPDATE = 2;
    public static final int SPLASH_SHOW_TIME = 2000;

    public static final String MERCHANT_ID = "merchant_id";

    public static final String STORE_ID = "store_id";

    private static final String AUTHOR_SCOPE = "base";
    public static final String INTERNET_ERROR = "请求服务器失败";
    public static final String UNKNOWN_SERVER_ERROR = "未知系统错误";

    public class USER {
        public static final String USER_NAME = "user_name"; // 用户名
        public static final String LOGIN_NAME = "login_name"; // 用户名
    }

    public class TableStatus {
        public static final String FREE = "free";             // 空闲
        public static final String OPENED = "opened";         // 已开桌
        public static final String DINING = "dining";         // 就餐
        public static final String NEED_CLEAN = "need_clean";     // 待清理
        public static final String RESERVED = "reserved";     // 预定
        public static final String LOCKED = "locked";       // 锁定
    }
}
