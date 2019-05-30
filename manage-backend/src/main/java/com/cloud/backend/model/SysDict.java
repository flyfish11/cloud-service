package com.cloud.backend.model;

import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysDict implements Serializable {
    @ApiModelProperty("字典Id")
    private int id;
    @ApiModelProperty("排序")
    private int num;
    @ApiModelProperty("父级Id")
    private int pid;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("提示")
    private String tips;
    @ApiModelProperty("值")
    private String code;
    @ApiModelProperty("辅助字段")
    private String datas;
}
