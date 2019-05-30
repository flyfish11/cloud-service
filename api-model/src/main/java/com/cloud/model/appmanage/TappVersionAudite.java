package com.cloud.model.appmanage;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * T_APP_VERSION_AUDITE
 * 版本审核表
 * @author ysq
 */
@Data
public class TappVersionAudite implements Serializable {
    private static final long serialVersionUID = -7573884200517491845L;

    private String appVersionAuditeId;

    private String appVersionId;

    private Date auditeDate;

    private String isDeleted;

    private String auditeState;

    private String auditeOpinion;

    private String auditeUserId;

    private String blockItem;


}
