package com.brycehan.boot.core;

import com.brycehan.boot.core.ResultWrapper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Bryce Han
 * @since 2021/2/24
 */
//@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultWrapper defaultException(Exception ex){
        return ResultWrapper.builder()
                .status(500)
                .message("内部错误")
                .result(ex.getMessage())
                .build();
    }
}
