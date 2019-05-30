package com.cloud.model.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class AppUser implements Serializable {

	private static final long serialVersionUID = 611197991672067628L;
	private String id;
	private String username;
	private String account;
	private String password;
	private String fullName;
	private String headImgUrl;
	private String phone;
	private String email;
	private String idNumber;
	private Integer sex;
	private Boolean Enabled;
	private Boolean isDelete;
	private String type;
	private Date createTime;
	private Date updateTime;
	private String remark;
	private String  depId;
	private String version;
	private String createBy;
	private String status;


}
