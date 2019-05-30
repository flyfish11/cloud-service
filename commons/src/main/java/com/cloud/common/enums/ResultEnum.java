package com.cloud.common.enums;

/**
 * 结果返回枚举
 *
 * @author yulj
 * @create: 2019/05/08 16:53
 */
public enum ResultEnum implements AbstractBaseExceptionEnum {

    PARAM_ERROR(1, "参数错误"),
    CREATE_REPO_ERROR(2, "创建git远程仓库错误"),
    CANT_DELETE_ERROR(3, "该项目中存在关联服务，不能删除！"),
    JENKINS_NOJOB_ERROR(4, "没有在jenkins中创建对应的任务和配置!"),
    JENKINS_RUN_ERROR(5, "Jenkins没有启动或者启动不正常"),
    PROJECT_ALREDY_EXISTS(6, "该项目名称已经存在！"),
    SERVICE_ALREDY_EXISTS(7, "该服务名称已经存在"),
    FILE_READING_ERROR(8, "FILE_READING_ERROR!"),
    FILE_NOT_FOUND(9, "FILE_NOT_FOUND!");


    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
