package com.cloud.model.appmanage;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * T_APP_INFO
 * 应用主表
 * @author ysq
 */
@Data
public class TAppInfo  implements Serializable{

    private static final long serialVersionUID = -7771085755840693740L;
    private String appId;

    private String appName;

    private String currentVersion;

    private String appType;

    private String appPic;

    private String unitCode;

    private String appDesc;

    private Date createTime;

    private String createID;

    private Date lastTime;

    private String lastName;

    private String isDelete;
}
