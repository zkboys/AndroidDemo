package com.zkboys.sdk.exception;

import java.util.ArrayList;
import java.util.List;

public class ServiceException extends Exception {

    public static final long CODE_1000_UNKNOWN_SYSTEM_ERROR = 1000;

    public static final String CODE_1000_UNKNOWN_SYSTEM_ERROR_MESSAGE = "未知系统错误";

    // 参数验证失败的错误提示
    public static final long CODE_10000_VALIDATION_FAILS = 10000;

    // 数据不存在的错误提示
    public static final long CODE_10001_NOT_FOUND = 10001;

    // OAuth相关错误提示
    public static final long INVALID_ACCESS_TOKEN = 2011; // access token 不合法
    public static final long USER_NOT_FOUND = 3001; // 用户不存在
    public static final long USER_IS_INVALID = 3000;// 用户不合法
    public static final String INVALID_ACCESS_TOKEN_MESSAGE = "无效的access token";
    public static final long ACCESS_TOKEN_HAS_EXPIRED = 2006; // access token 过期

    public static final long INVALID_REFRESH_TOKEN = 2005; // refresh token 不合法

    public static final long REFRESH_TOKEN_HAS_EXPIRED = 2007; // refresh token 过期

    private static final long serialVersionUID = 2866088988644477966L;

    private long code;

    /**
     * 服务的数据有效性验证异常（可选）
     */
    private List<ErrorItem> errorItems;

    public static ServiceException build(long code, String message, Throwable cause) {
        return new ServiceException(code, message, cause);
    }

    public static ServiceException build(Throwable cause) {
        return build(CODE_1000_UNKNOWN_SYSTEM_ERROR, CODE_1000_UNKNOWN_SYSTEM_ERROR_MESSAGE, cause);
    }

    public ServiceException(long code) {
        this(code, null);
    }

    public ServiceException(long code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(long code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public ServiceException addErrorItem(String field, String message) {
        if (this.errorItems == null) {
            this.errorItems = new ArrayList<ErrorItem>();
        }
        errorItems.add(new ErrorItem(field, message));
        return this;
    }

    public long getCode() {
        return code;
    }

    public List<ErrorItem> getErrorItems() {
        return errorItems;
    }

    public void setErrorItems(List<ErrorItem> errorItems) {
        this.errorItems = errorItems;
    }

    /**
     * 是否是数据有效性验证异常
     *
     * @return
     */
    public boolean isValidationError() {
        return this.errorItems != null;
    }

    public static class ErrorItem {

        private String field;
        private String message;

        public ErrorItem(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "ErrorItem{" +
                    "field='" + field + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

}
