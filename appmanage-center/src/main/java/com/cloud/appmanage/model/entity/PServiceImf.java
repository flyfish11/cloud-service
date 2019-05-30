package com.cloud.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (PServiceImf)实体类
 *
 * @author makejava
 * @since 2019-05-23 11:22:21
 */
public class PServiceImf implements Serializable {
    private static final long serialVersionUID = 730630400092069246L;
   /**
    * 服务表主键
    */
    private String id;
   /**
    * 服务名
    */
    private String name;
   /**
    * 服务简称
    */
    private String shortName;
   /**
    * 服务描述
    */
    private String description;
   /**
    * 服务识别id
    */
    private String serviceUnicId;
   /**
    * 服务所属应用外键id
    */
    private String belongApplication;
   /**
    * 服务分组外键id
    */
    private Integer serviceGroup;
   /**
    * 创建人
    */
    private String createBy;
   /**
    * 创建时间
    */
    private Date createTime;
   /**
    * 修改人
    */
    private String updateBy;
   /**
    * 修改时间
    */
    private Date updateTimre;
   /**
    * 删除标记
    */
    private Integer isDelete;
   /**
    * 构建日志
    */
    private String jenkinslog;
   /**
    * 服务jar包地址
    */
    private String jarAddr;
   /**
    * 服务类型（jar ：j上传jar包的项目；gitlab ：从gitlab拉取源码的项目）
    */
    private String type;
   /**
    * 服务端口
    */
    private String sevrvicePort;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceUnicId() {
        return serviceUnicId;
    }

    public void setServiceUnicId(String serviceUnicId) {
        this.serviceUnicId = serviceUnicId;
    }

    public String getBelongApplication() {
        return belongApplication;
    }

    public void setBelongApplication(String belongApplication) {
        this.belongApplication = belongApplication;
    }

    public Integer getServiceGroup() {
        return serviceGroup;
    }

    public void setServiceGroup(Integer serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTimre() {
        return updateTimre;
    }

    public void setUpdateTimre(Date updateTimre) {
        this.updateTimre = updateTimre;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getJenkinslog() {
        return jenkinslog;
    }

    public void setJenkinslog(String jenkinslog) {
        this.jenkinslog = jenkinslog;
    }

    public String getJarAddr() {
        return jarAddr;
    }

    public void setJarAddr(String jarAddr) {
        this.jarAddr = jarAddr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSevrvicePort() {
        return sevrvicePort;
    }

    public void setSevrvicePort(String sevrvicePort) {
        this.sevrvicePort = sevrvicePort;
    }

}