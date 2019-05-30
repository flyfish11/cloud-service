package com.cloud.model.appmanage;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 服务数据实体
 *
 * @author yulj
 * @create: 2019/05/08 23:11
 */
@Data
public class ServiceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务表主键
     */
    @ApiModelProperty("serviceId")
    private String id;

    /**
     * 服务名
     */
    @ApiModelProperty("服务名")
    private String name;

    /**
     * 服务简称
     */
    @ApiModelProperty("服务简称")
    private String shortName;

    /**
     * 服务描述
     */
    @ApiModelProperty("服务名")
    private String description;

    /**
     * 服务识别id
     */
    @ApiModelProperty("服务识别id")
    private String serviceUnicId;

    /**
     * 服务所属应用外键id
     */
    @ApiModelProperty("服务所属应用外键id")
    private String belongApplication;

    /**
     * 服务分组外键id
     */
    @ApiModelProperty("服务分组外键id")
    private Integer serviceGroup;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTimre;

    /**
     * 删除标记
     */
    @ApiModelProperty("删除标记")
    private Integer isDelete;

    /**
     * JAR包地址
     */
    @ApiModelProperty("JAR包地址")
    private String jarAddr;
    /**
     * jenkins任务日志
     */
    @ApiModelProperty("JAR包地址")
    private String jenkinslog;

    /**
     * 服务类型
     */
    @ApiModelProperty("服务类型")
    private String type;

    /**
     * 服务端口
     */
    @ApiModelProperty("服务端口")
    private String servicePort;
}