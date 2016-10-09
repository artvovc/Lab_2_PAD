package com.enums;

/**
 * Created by Artemie on 04.10.2016.
 */
public enum WhoRequest {
    USER(0), NODE(1), MAVEN(2);

    private int value;

    WhoRequest(int value) {
        this.value = value;
    }

    public static WhoRequest valueOf(int value) {
        switch (value) {
            case 0:
                return USER;
            case 1:
                return NODE;
            case 2:
                return MAVEN;
            default:
                return null;
        }
    }


}
