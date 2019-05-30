package com.cloud.model.common;

/**
 * 请求成功的返回
 *
 * @author yulj
 * @create: 2019/05/18 17:09
 */
public class SuccessResponseData extends ResponseData {

    public SuccessResponseData() {
        super(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, null);
    }

    public SuccessResponseData(Object object) {
        super(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, object);
    }

    public SuccessResponseData(Integer code, String message, Object object) {
        super(true, code, message, object);
    }
}
