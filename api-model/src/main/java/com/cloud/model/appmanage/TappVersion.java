package com.cloud.model.appmanage;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * T_APP_VERSION
 * 应用版本
 * @author ysq
 */
@Data
public class TappVersion implements Serializable {

    private static final long serialVersionUID = 4722294367027699881L;
    private String appPropertyValueID;

    private String appID;

    private String versionNumber;

    private String developLanguage;

    private String appFlag;

    private String packagePath;

    private String evaluateID;

    private String appDesc;

    private Date createTime;

    private String updateBy;

    private String  backCol2;

    private String  serviceName;

    private String isDelete;

    private String appConfigFilePath;
}
