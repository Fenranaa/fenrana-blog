package cn.fenrana.blog.handler;

import cn.fenrana.blog.utils.ResultJson;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * controller全局异常处理
 * */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object handlerException(Exception e) {
        e.printStackTrace();
        return new ResultJson<Object>(500, null, e.getMessage());
    }
}
