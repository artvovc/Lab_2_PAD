package com.enums;

/**
 * Created by Artemie on 04.10.2016.
 */
public enum RequestType {
    POST(0), GET(1);
    private int value;

    RequestType(int value) {
        this.value = value;
    }

    public static RequestType valueOf(int value) {
        switch (value) {
            case 0:
                return POST;
            case 1:
                return GET;
            default:
                return null;
        }
    }
}
