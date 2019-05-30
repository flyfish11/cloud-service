package com.cloud.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class WechatUserInfo implements Serializable {

    private static final long serialVersionUID = 6750304307961875043L;

    private String id;
    private String openid;
    private String unionid;
    private String userId;
    private String app;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private Date createTime;
    private Date updateTime;
}
