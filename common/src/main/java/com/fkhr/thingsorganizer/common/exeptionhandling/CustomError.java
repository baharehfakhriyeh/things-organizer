package com.fkhr.thingsorganizer.common.exeptionhandling;

public enum CustomError {
    USER_NOT_FOUND(1001, "user-not-found"),
    USER_ALREADY_EXISTS(1002, "user-already-exists"),
    INCORRECT_USERNAME_PASSWORD(1003, "incorrect-username-password"),
    PASSWORD_EXPIRED(1004, "password-expired"),
    USER_NOT_ACTIVE(1005, "user-not-active"),
    NO_USER_LOGGED_IN(1006, "no-user-logged-in"),
    REQUIRED_FIELD(1010, "required-field"),
    THING_NOT_FOUND(1101, "thing-not-found"),
    THING_NOT_DELETED_CONTENT_NOT_DELETED(1102, "thing-not-deleted-because-contents-not-deleted"),
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
