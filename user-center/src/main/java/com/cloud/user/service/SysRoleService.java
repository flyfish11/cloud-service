package com.cloud.user.service;

import java.util.Map;
import java.util.Set;

import com.cloud.model.common.Page;
import com.cloud.model.user.SysPermission;
import com.cloud.model.user.SysRole;

public interface SysRoleService {

	void save(SysRole sysRole);

	void update(SysRole sysRole);

	void deleteRole(String id);

	void setPermissionToRole(String id, Set<String> permissionIds);

	SysRole findById(String id);

	Page<SysRole> findRoles(Map<String, Object> params);

	Set<SysPermission> findPermissionsByRoleId(String roleId);
}
