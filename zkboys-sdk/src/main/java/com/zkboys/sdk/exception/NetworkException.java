package com.zkboys.sdk.exception;

public class NetworkException extends Exception {

    private static final long serialVersionUID = 2866088988644477966L;

    public NetworkException(Exception exception) {

        super(exception.getMessage(), exception);
    }

}
