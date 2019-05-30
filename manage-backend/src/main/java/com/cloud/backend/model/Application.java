package com.cloud.backend.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Application implements Serializable {

	private static final long serialVersionUID = 749360940290141180L;
	@ApiModelProperty("应用ID")
	private String applicationId;
	@ApiModelProperty("应用名")
	private String applicationName;
	@ApiModelProperty("创建时间")
	private Date createTime;
	@ApiModelProperty("更新时间")
	private Date updateTime;

}
