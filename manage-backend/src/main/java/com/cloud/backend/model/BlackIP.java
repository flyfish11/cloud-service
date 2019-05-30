package com.cloud.backend.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * IP黑名单
 * 
 * @author hlxd
 *
 */
@Data
public class BlackIP implements Serializable {

	private static final long serialVersionUID = -2064187103535072261L;
	@ApiModelProperty("IP地址")
	private String ip;
	@ApiModelProperty("创建时间")
	private Date createTime;
}
