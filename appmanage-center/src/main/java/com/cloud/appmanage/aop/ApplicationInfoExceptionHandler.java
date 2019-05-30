package com.cloud.appmanage.aop;

import com.cloud.appmanage.exception.AppManageException;
import com.cloud.model.common.ErrorResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 应用管理模块的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author yulj
 * @create: 2019/05/18 17:15
 */
@ControllerAdvice
@Order(-1)
@Slf4j
public class ApplicationInfoExceptionHandler {

    /**
     * 拦截AppManageException
     */
    @ExceptionHandler(AppManageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData applicationInfo(AppManageException e) {
        log.error("应用中心异常:", e);
        return new ErrorResponseData(e.getCode(), e.getMessage());
    }
}
