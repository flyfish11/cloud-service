package com.cloud.common.utils;

import com.cloud.common.vo.ResultVO;

/**
 * 返回结果工具类
 *
 * @author yulj
 * @create: 2019/05/08 17:04
 */
public class ResultVOUtil {

    public static ResultVO buildResultVO(Integer code, String msg, Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(Object object) {
        return buildResultVO(0, "请求成功！", object);
    }

    public static ResultVO error(Object object, String msg) {
        return buildResultVO(1, msg, null);
    }
}
