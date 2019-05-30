package com.cloud.model.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SysRole implements Serializable {

	private static final long serialVersionUID = -2054359538140713354L;

	private String id;
	private String code;
	private String name;
	private Date createTime;
	private Date updateTime;
}
