package com.nembx.itemservice.result;

/**
 * @author Lian
 */
public enum ResponseCodeEnum {
    // 状态码
    SUCCESS(200, "success"),
    FAIL(500, "fail"),
    ERROR(400, "error"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden"),
    NOT_FOUND(404, "not found"),
    METHOD_NOT_ALLOWED(405, "method not allowed"),
    REQUEST_TIMEOUT(408, "request timeout"),
    CONFLICT(409, "conflict"),
    UNSUPPORTED_MEDIA_TYPE(415, "unsupported media type");

    private final int code;
    private final String msg;

    ResponseCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
