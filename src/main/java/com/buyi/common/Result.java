package com.buyi.common;

import com.buyi.exception.ExceptionEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    @Serial
    private static final long serialVersionUID = 1L;

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return new Result<>(200, "ok", null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(200, "ok", data);
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> error(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public static <T> Result<T> error(ExceptionEnum e) {
        return new Result<>(e.getCode(), e.getMsg(), null);
    }

    public static <T> Result<T> error(ExceptionEnum e, T data) {
        return new Result<>(e.getCode(), e.getMsg(), data);
    }
}
