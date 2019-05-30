package com.cloud.backend.controller;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.backend.model.BlackIP;
import com.cloud.backend.service.BlackIPService;
import com.cloud.model.common.Page;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;

@RestController
public class BlackIPController {

	@Autowired
	private BlackIPService blackIPService;

	/**
	 * 添加黑名单ip
	 *
	 * @param blackIP
	 */
	@LogAnnotation(module = LogModule.ADD_BLACK_IP)
	@PreAuthorize("hasAuthority('ip:black:save')")
	@ApiOperation(value = "添加黑名单ip", notes = "添加黑名单ip")
	@ApiImplicitParam(name = "blackIP", value = "黑名单实体", required = false, dataType = "BlackIP ", paramType = "query")
	@PostMapping("/blackIPs")
	public void save(@RequestBody BlackIP blackIP) {
		blackIP.setCreateTime(new Date());

		blackIPService.save(blackIP);
	}

	/**
	 * 删除黑名单ip
	 *
	 * @param ip
	 */
	@LogAnnotation(module = LogModule.DELETE_BLACK_IP)
	@PreAuthorize("hasAuthority('ip:black:delete')")
	@ApiOperation(value = "删除黑名单ip", notes = "通过ip删除黑名单ip")
	@ApiImplicitParam(name = "ip", value = "黑名ip", required = false, dataType = "BlackIP ", paramType = "path")
	@DeleteMapping("/blackIPs/{ip}")
	public void delete(@PathVariable String ip) {
		blackIPService.delete(ip);
	}

	/**
	 * 查询黑名单
	 *
	 * @param params
	 * @return
	 */
	@PreAuthorize("hasAuthority('ip:black:query')")
	@ApiOperation(value = "查询黑名单", notes = "通过参数查询黑名单ip")
	@ApiImplicitParam(name = "params", value = "查询参数", required = false, dataType = "Map ", paramType = "query")
	@GetMapping("/blackIPs")
	public Result findBlackIPs(@RequestParam Map<String, Object> params) {

		Page<BlackIP> lis= blackIPService.findBlackIPs(params);

		return ResultUtil.success(lis.getData());
	}

	/**
	 * 查询黑名单<br>
	 * 可内网匿名访问
	 *
	 * @param params
	 * @return
	 */
	@ApiOperation(value = "查询可内网匿名访问黑名单", notes = "通过参数查询黑名单ip")
	@ApiImplicitParam(name = "params", value = "查询参数", required = false, dataType = "Map ", paramType = "query")
	@GetMapping("/backend-anon/internal/blackIPs")
	public Set<String> findAllBlackIPs(@RequestParam Map<String, Object> params) {
		Page<BlackIP> page = blackIPService.findBlackIPs(params);
		if (page.getTotal() > 0) {
			return page.getData().stream().map(BlackIP::getIp).collect(Collectors.toSet());
		}
		return Collections.emptySet();
	}
}
