package com.cloud.user.controller;

import java.util.Map;
import java.util.Set;

import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.model.common.Page;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.SysPermission;
import com.cloud.model.user.SysRole;
import com.cloud.user.service.SysRoleService;

@RestController
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 管理后台添加角色
	 * 
	 * @param sysRole
	 * @return
	 */
	@LogAnnotation(module = LogModule.ADD_ROLE)
	@PreAuthorize("hasAuthority('back:role:save')")
	@PostMapping("/roles")
	public SysRole save(@RequestBody SysRole sysRole) {
		if (StringUtils.isBlank(sysRole.getCode())) {
			throw new IllegalArgumentException("角色code不能为空");
		}
		if (StringUtils.isBlank(sysRole.getName())) {
			sysRole.setName(sysRole.getCode());
		}

		sysRoleService.save(sysRole);

		return sysRole;
	}

	/**
	 * 管理后台删除角色
	 * 
	 * @param id
	 */
	@LogAnnotation(module = LogModule.DELETE_ROLE)
	@PreAuthorize("hasAuthority('back:role:delete')")
	@DeleteMapping("/roles/{id}")
	public void deleteRole(@PathVariable String id) {
		sysRoleService.deleteRole(id);
	}

	/**
	 * 管理后台修改角色
	 * 
	 * @param sysRole
	 * @return
	 */
	@LogAnnotation(module = LogModule.UPDATE_ROLE)
	@PreAuthorize("hasAuthority('back:role:update')")
	@PutMapping("/roles")
	public SysRole update(@RequestBody SysRole sysRole) {
		if (StringUtils.isBlank(sysRole.getName())) {
			throw new IllegalArgumentException("角色名不能为空");
		}

		sysRoleService.update(sysRole);

		return sysRole;
	}

	/**
	 * 管理后台给角色分配权限
	 * 
	 * @param id
	 * @param permissionIds
	 */
	@LogAnnotation(module = LogModule.SET_PERMISSION)
	@PreAuthorize("hasAuthority('back:role:permission:set')")
	@PostMapping("/roles/{id}/permissions")
	public void setPermissionToRole(@PathVariable String id, @RequestBody Set<String> permissionIds) {
		sysRoleService.setPermissionToRole(id, permissionIds);
	}

	/**
	 * 获取角色的权限
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('back:role:permission:set','role:permission:byroleid')")
	@GetMapping("/roles/{id}/permissions")
	public Set<SysPermission> findPermissionsByRoleId(@PathVariable String id) {
		return sysRoleService.findPermissionsByRoleId(id);
	}

	@PreAuthorize("hasAuthority('back:role:query')")
	@GetMapping("/roles/{id}")
	public SysRole findById(@PathVariable String id) {
		return sysRoleService.findById(id);
	}

	/**
	 * 搜索角色
	 * @param params
	 * @return
	 */
	@PreAuthorize("hasAuthority('back:role:query')")
	@GetMapping("/roles")
	public Page<SysRole> findRoles(@RequestParam Map<String, Object> params) {

		Page<SysRole> roles = sysRoleService.findRoles(params);

		return roles;
	}

	/**
	 * 搜索角色
	 * @param params
	 * @return
	 */
	@PreAuthorize("hasAuthority('back:role:query')")
	@GetMapping("/roles1")
	public Result findRoles1(@RequestParam Map<String, Object> params) {

		Page<SysRole> roles = sysRoleService.findRoles(params);
		return ResultUtil.success(roles.getTotal(),roles.getData());
	}

	@GetMapping("/act/roles")
	public Page<SysRole> actFindRoles(@RequestParam Map<String, Object> params) {
		return sysRoleService.findRoles(params);
	}
	@GetMapping("/organize/roles")
	public Page<SysRole> userFindRoles(@RequestParam Map<String, Object> params) {
		return sysRoleService.findRoles(params);
	}
}
