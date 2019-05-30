package com.cloud.model.appmanage;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用数据实体
 *
 * @author yulj
 * @create: 2019/05/07 00:54
 */
@Data
public class ApplicationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用表主键
     */

    private String id;

    /**
     * 应用名
     */
    private String name;

    /**
     * 应用简称
     */
    private String shortName;

    /**
     * 应用版本号
     */
    private String version;

    /**
     * 应用描述
     */
    private String description;

    /**
     * 应用所属部门
     */
    private String belongDepartment;

    /**
     * 应用下属附件
     */
    private String accessoryId;

    /**
     * 应用识别id
     */
    private String applicationUnicId;

    /**
     * 应用排序
     */
    private Integer orderLeve;

    /**
     * 应用分组外键id
     */
    private Integer applicationGroup;

    /**
     * 应用运行状态
     */
    private Integer runState;

    /**
     * 应用创建状态（1待创建服务,2待配置服务，3待分级授权，4待应用发布，5创建完成）
     */
    private Integer createState;

    /**
     * 应用创建人
     */
    private String createBy;

    /**
     * 应用创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 应用修改人
     */
    private String updateBy;

    /**
     * 应用修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 应用logo路径
     */
    private String logoSrc;

    /**
     * 删除标记
     */
    private Integer isDelete;

    /**
     * 应用可见范围(部门)
     */
    private String authorityArea;

    /**
     * 应用可见范围(人员)
     */
    private String authorityOwner;

    /**
     * 应用管理员
     */
    private String authorityManager;

    /**
     * 项目git地址
     */
    private String projectRepoUrl;

    /**
     * 项目类型
     */
    private String type;
}