package com.cloud.user.service;

import java.util.Map;
import java.util.Set;

import com.cloud.model.common.Page;
import com.cloud.model.user.SysPermission;

public interface SysPermissionService {

	/**
	 * 根绝角色ids获取权限集合
	 * 
	 * @param roleIds
	 * @return
	 */
	Set<SysPermission> findByRoleIds(Set<String> roleIds);

	void save(SysPermission sysPermission);

	void update(SysPermission sysPermission);

	void delete(String id);

	Page<SysPermission> findPermissions(Map<String, Object> params);

	Page<SysPermission> findPermissions1(Map<String, Object> params);
}
