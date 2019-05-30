package com.cloud.file.exception;

import com.cloud.common.enums.AbstractBaseExceptionEnum;

/**
 * 文件中心模块异常处理
 *
 * @author yulj
 * @create: 2019/05/20 17:31
 */
public class FileCenterException extends RuntimeException {

    private Integer code;
    private String errorMessage;

    public FileCenterException(Integer code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public FileCenterException(AbstractBaseExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
