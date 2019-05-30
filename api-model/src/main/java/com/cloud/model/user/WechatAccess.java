package com.cloud.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class WechatAccess implements Serializable {

    private static final long serialVersionUID = 6571363417369764704L;

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
}
