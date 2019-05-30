package com.cloud.file.aop;

import com.cloud.file.exception.FileCenterException;
import com.cloud.model.common.ErrorResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 文件中心模块的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author yulj
 * @create: 2019/05/20 17:35
 */
@ControllerAdvice
@Order(-1)
@Slf4j
public class FileCenterExceptionHandler {

    /**
     * 拦截FileCenterException
     */
    @ExceptionHandler(FileCenterException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData applicationInfo(FileCenterException e) {
        log.error("文件中心异常:", e);
        return new ErrorResponseData(e.getCode(), e.getMessage());
    }
}
