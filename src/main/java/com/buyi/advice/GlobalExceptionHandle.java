package com.buyi.advice;

import com.buyi.common.Result;
import com.buyi.exception.BizException;
import com.buyi.exception.ExceptionEnum;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(BizException.class)
    public Result bizException(BizException e) {
        e.printStackTrace();
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public Result throwable(Throwable e) {
        e.printStackTrace();
        return Result.error(ExceptionEnum.SYSTEM_EXCEPTION);
    }
}