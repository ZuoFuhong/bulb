package com.maxzuo.bulb.boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zfh on 2020/04/28
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_MODIFIED)//返回304状态码
    @ResponseBody
    @ExceptionHandler(AsyncRequestTimeoutException.class) //捕获特定异常
    public void handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e, HttpServletRequest request) {
        System.out.println("handleAsyncRequestTimeoutException");
    }
}
