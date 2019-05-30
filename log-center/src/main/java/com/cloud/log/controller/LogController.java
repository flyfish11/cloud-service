package com.cloud.log.controller;

import java.util.Map;

import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.log.service.LogService;
import com.cloud.model.common.Page;
import com.cloud.model.log.Log;
import com.cloud.model.log.constants.LogModule;

@RestController
public class LogController {

	@Autowired
	private LogService logService;

	@PostMapping("/logs-anon/internal")
	public void save(@RequestBody Log log) {
		logService.save(log);
	}

	/**
	 * 日志模块
	 * 
	 * @return
	 */
	@PreAuthorize("hasAuthority('log:query')")
	@GetMapping("/logs-modules")
	public Map<String, String> logModule() {
		Map<String, String> data = LogModule.MODULES;
		return data;
	}

	/**
	 * 日志查询
	 * 
	 * @param params
	 * @return
	 */
	@PreAuthorize("hasAuthority('log:query')")
	@GetMapping("/logs")
	public Result findLogs(@RequestParam Map<String, Object> params) {
		//前后端参数名不匹配，
		params.put("start",params.get("page"));
		params.put("length",params.get("limit"));
		params.remove("page");
		params.remove("limit");
		Page<Log> lis= logService.findLogs(params);
		return	ResultUtil.success(lis.getData());
	}

}
