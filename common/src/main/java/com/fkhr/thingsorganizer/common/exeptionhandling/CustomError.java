package com.fkhr.thingsorganizer.common.exeptionhandling;

public enum CustomError {
    THING_NOT_FOUND(1101, "thing-not-found"),
    CONTAINER_NOT_FOUND(1201, "container-not-found"),
    PARENT_CONTAINER_NOT_FOUND(1202, "parent-container-not-found"),
    CONTENT_NOT_FOUND(1301, "content-not-found");
    private final int code;
    private final String message;

    private CustomError(int value, String message) {
        this.code = value;
        this.message = message;
    }

    public int code() {
        return this.code;
    }
    public String message() {
        return this.message;
    }
}
