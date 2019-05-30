package com.cloud.model.log;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log implements Serializable {

	private static final long serialVersionUID = -5398795297842978376L;

	private Long id;
	private String username;
	/** 模块 */
	private String module;
	/** 参数值 */
	private String params;
	private String remark;
	private Boolean flag;
	private Date createTime;
}
