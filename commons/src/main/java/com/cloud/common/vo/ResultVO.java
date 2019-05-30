package com.cloud.common.vo;

import lombok.Data;

/**
 * 返回结果vo
 *
 * @author yulj
 * @create: 2019/05/08 16:58
 */

@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
