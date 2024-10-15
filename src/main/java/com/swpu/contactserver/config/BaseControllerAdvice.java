package com.swpu.contactserver.config;

import com.swpu.contactserver.common.exceptions.UnAuthException;
import com.swpu.contactserver.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 增强器，可以处理全局异常
 *
 * 全局异常处理优先级
 * 自定义异常>父类异常
 * 如果能匹配到自定义异常，则使用自定义异常方法处理，否则使用父类异常处理
 */
@RestControllerAdvice
public class BaseControllerAdvice {

    @ExceptionHandler(UnAuthException.class)
    public Result<?> handleUnAuthException(UnAuthException e) {
        e.printStackTrace();
        return new Result<>().unAuthorized(e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return new Result<>().error(e.getMessage());
    }
}
