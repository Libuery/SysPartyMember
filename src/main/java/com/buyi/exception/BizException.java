package com.buyi.exception;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException{

    private final int code;

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public BizException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    public BizException(String msg) {
        super(msg);
        this.code = 401;
    }

}