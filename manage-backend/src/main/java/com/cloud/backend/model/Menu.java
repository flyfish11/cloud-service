package com.cloud.backend.model;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.transaction.annotation.Transactional;

@Data
public class Menu implements Serializable {

	private static final long serialVersionUID = 749360940290141180L;
	@ApiModelProperty("菜单ID")
	private String id;
	@ApiModelProperty("父菜单ID")
	private String parentId;
	@ApiModelProperty("菜单名称")
	private String name;
	@ApiModelProperty("")
	private String css;
	@ApiModelProperty("请求地址")
	private String url;
	@ApiModelProperty("")
	private Integer sort;
	@ApiModelProperty("创建时间")
	private Date createTime;
	@ApiModelProperty("跟新时间")
	private Date updateTime;
	@ApiModelProperty("应用ID")
	private String applicationId;
	@ApiModelProperty("child")
	private transient List<Menu> child;
}
