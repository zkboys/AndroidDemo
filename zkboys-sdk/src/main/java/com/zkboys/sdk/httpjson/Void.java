package com.zkboys.sdk.httpjson;

public class Void {

    volatile static Void instance = new Void();

    private Void() {
    }

    public static Void instance() {
        return instance;
    }
}
