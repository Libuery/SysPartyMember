package com.buyi.exception;

import lombok.Getter;

@Getter
public enum ExceptionEnum {

    SYSTEM_EXCEPTION(500,"系统异常");

    private final int code;
    private final String msg;

    ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}