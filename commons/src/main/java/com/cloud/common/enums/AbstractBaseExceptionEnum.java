package com.cloud.common.enums;

/**
 * 抽象基础异常枚举
 *
 * @author yulj
 * @create: 2019/05/18 17:41
 */
public interface AbstractBaseExceptionEnum {

    Integer getCode();

    String getMessage();
}
