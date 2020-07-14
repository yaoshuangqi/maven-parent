package com.quanroon.ysq.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 全局异常处理
 * @createtime 2020/7/14 23:14
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends RuntimeException{

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        log.error(e.getMessage(), e);

        if (e instanceof BindException) {
            BindException ex = (BindException) e;
            List<ObjectError> allErrors = ex.getAllErrors();
            ObjectError error = allErrors.get(0);
            String defaultMessage = error.getDefaultMessage();
            return defaultMessage;
        }  else {
            return "error";
        }
    }
}
