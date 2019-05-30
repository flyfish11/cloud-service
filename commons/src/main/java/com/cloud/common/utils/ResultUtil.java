package com.cloud.common.utils;

import com.cloud.model.common.Result;

/**
 * @author: flyfish
 * @create: 2018-06-19 11:27
 * @description:
 */
public class ResultUtil {

    public static Result success() {
        Result result = new Result();
        result.setCode(0);
        result.setSuccess(true);
        result.setMsg("成功");
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result();
        result.setCode(0);
        result.setSuccess(true);
        result.setMsg(msg);
        return result;
    }

    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setSuccess(true);
        result.setMsg("成功");
        result.setData(object);
        result.setRows(object);
        return result;
    }

    public static Result success(int total, Object object) {
        Result result = new Result();
        result.setTotal(total);
        result.setCount(total);
        result.setCode(0);
        result.setSuccess(true);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setSuccess(false);
        result.setMsg(msg);
        return result;
    }
}